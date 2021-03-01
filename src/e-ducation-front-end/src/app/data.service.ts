import { stringify } from '@angular/compiler/src/util';
import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {not} from "rxjs/internal-compatibility";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  private dataSource = new BehaviorSubject<SharedData>(new SharedData());
  currentData = this.dataSource.asObservable();

  constructor(private http: HttpClient) {}

  getHeaders(){
    return new HttpHeaders().set('Content-Type', 'application/json' ).set('Authorization','Bearer ' + this.dataSource.getValue().jwt);
  }

  changeData(data: SharedData) {
    this.dataSource.next(data);
  }

  /** HTTP CALLS -- return observable **/
  createProfile (username: string) {
    let newProfileRequest = {username:username};
    return this.http.post<idReturnType>("http://localhost:8080/profile",newProfileRequest);
  }
  getProfile(profileId: String){
    let getProfileRequest = {id: profileId};
    return this.http.put<Profile>("http://localhost:8080/profile",getProfileRequest);
  }

  createUser(username: string, password: string, profileId: string) {
    let newUserRequest = {username:username, password:password, profileId: profileId};
    return this.http.post("http://localhost:8080/user",newUserRequest);
  }
  loginUser(username: string, password: string) {
    // POST - /authorize
    let l : loginRequest = {username:username, password:password};

    return this.http.post<loginResponse>("http://localhost:8080/authenticate",l);
  }

  enrollClass(courseId: string) {
    // POST - /profile/join
    let enrollClassRequest = {profileId: this.dataSource.getValue().profile.id, courseId: courseId};
    return this.http.post<string>("http://localhost:8080/profile/join",enrollClassRequest, {headers:this.getHeaders()})
      .subscribe((_: string) => {});
  }
  getEnrolledClassData(){
    let c : courseRequest = {ids: this.dataSource.getValue().profile.coursesEnrolled};
    return this.http.put<ClassData[]>("http://localhost:8080/course/", c,{headers: this.getHeaders()});
  }
  createClass(courseName: string) {
    // POST - /course/create
    const newCourseRequest = {courseName:courseName};
    return this.http.post<idReturnType>("http://localhost:8080/course", newCourseRequest, {headers:this.getHeaders()})
      .subscribe((courseId:idReturnType) => {
        const hostCourseRequest = {profileId:this.dataSource.getValue().profile.id, courseId:courseId.id};
        this.http.post<string>("http://localhost:8080/profile/create",hostCourseRequest, {headers:this.getHeaders()})
          .subscribe((_: string) => {});
      });
  }
  getOwnedClassData(){
    const c : courseRequest = {ids: this.dataSource.getValue().profile.coursesOwned};
    return this.http.put<ClassData[]>("http://localhost:8080/course/", c,{headers: this.getHeaders()});
  }

  getSessionsData(sessions: string[]) {
    // Load session data from ID
    let getSessionsRequest = {sessionIds: sessions};
    return this.http.put<Session[]>("http://localhost:8080/course/session",getSessionsRequest, {headers:this.getHeaders()});
  }
  createSession(sessionName: string) {
    // POST - /course/session
    const courseId = this.dataSource.getValue().currentClass.id;
    const newSessionRequest = {courseId:courseId, sessionName:sessionName};
    return this.http.post<string>("http://localhost:8080/course/session",newSessionRequest, {headers:this.getHeaders()})
      .subscribe((_: string) => {});
  }

  loadPromptletData(promptletId: string) {
    // Load promptlet data from ID

    let localData = this.dataSource.getValue();
    localData.currentPromptlet = this.dataSource.getValue().currentSession.promptlets.filter( item => item.id == promptletId)[0];
    this.changeData(localData);
  }

  createPromptlet(prompt: string, promptlet_type: string, answerPool: string[], correctAnswer: string[]) {
    // POST - /course/session/promptlet
    const promptletRequest = {sessionId: this.dataSource.getValue().currentSession.id, prompt: prompt,
          promptlet_type: promptlet_type, answerPool: answerPool, correctAnswer: correctAnswer};
    return this.http.post<string>("http://localhost:8080/course/session/promptlet", promptletRequest, {headers:this.getHeaders()})
      .subscribe((_: string) => {});
  }
  getPromptletData(sessionIds : string[]) {
    // Load promptlet data from ID
    let getPromptletRequest = {ids: sessionIds};
    return this.http.put<Promptlet[]>("http://localhost:8080/course/session/promptlet",getPromptletRequest, {headers:this.getHeaders()});
  }
  submitPromptletResponse(promptletId: string, response: string[]) {
    const profileId = this.dataSource.getValue().user.profileId;
    // Unsure if this should be UserData or Profile
    // Perhaps we can concatenate user ID as the first line of 'response' so we only send one string per response
    let promptletResponse = {promptletId: promptletId, profileId: profileId, response: response};

    return this.http.post<string>("http://localhost:8080/course/session/promptlet/answer", promptletResponse, {headers:this.getHeaders()})
      .subscribe((_: string) => {});
  }

  getUserResponse(userResponseIds: string[]){
    const userResponseRequest = {userResponseIds: userResponseIds};

    return this.http.put<UserResponse[]>("http://localhost:8080/course/session/promptlet/answers", userResponseRequest, {headers:this.getHeaders()})
      .subscribe((data: UserResponse[]) => {
        let localData = this.dataSource.getValue();
        localData.currentPromptlet.userResponses = data;
        this.changeData(localData);
      });
  }
  fetchUserResponse(){

    let stompClient = Stomp.over(new SockJS(`http://localhost:8080/socket`));

    stompClient.connect({}, frame => {
      stompClient.subscribe('/topic/notification/' + this.dataSource.getValue().currentPromptlet.id, (notification) => {
        // observer.next(notifications);
        const jsonBody = JSON.parse(notification.body)
        const userResponse : UserResponse = {id:jsonBody.id, profileId:jsonBody.profileId, profileName: jsonBody.profileName, response:jsonBody.responses};

        let localData = this.dataSource.getValue();
        console.log(jsonBody, userResponse);
        localData.currentPromptlet.userResponses.push(userResponse);
        // localData.currentPromptlet.actualUserResponses.push(userResponse);
        this.changeData(localData);

        console.log(userResponse);
      })
    })

  }
  // Subscribe Blocks
  loadSessionsByCurrentClassId(currentClass : ClassData){
    this.getSessionsData(currentClass.sessionIds)
      .subscribe((data: Session[]) => {
        let localData = this.dataSource.getValue();

        localData.currentClassSessions = data;
        this.changeData(localData);
        });
  }

  loadPromptletsByCurrentSessionId(){
    this.getPromptletData(this.dataSource.getValue().currentSession.promptletIds)
      .subscribe((data: any[]) => {
        let localData = this.dataSource.getValue();
        let promptlets : Promptlet[] = [];

        data.forEach(x => {
          const userResponses : UserResponse[] = x.userResponses.map(x => ({id:x, profileId:"", response:[]}));
          const promptlet : Promptlet = {id:x.id, prompt:x.prompt, promptlet_type:x.promptlet_type, answerPool: x.answerPool,
            correctAnswer:x.correctAnswer, userResponses: userResponses};
          promptlets.push(promptlet);
        });
        localData.currentSession.promptlets = promptlets;
        this.changeData(localData);
      });
  }
  fetchPromptletData(){

    let stompClient = Stomp.over(new SockJS(`http://localhost:8080/socket`));

    stompClient.connect({}, frame => {
      stompClient.subscribe('/topic/notification/' + this.dataSource.getValue().currentSession.id, (notification) => {
        // observer.next(notifications);
        const jsonBody = JSON.parse(notification.body)
        const studentPromptlet : Promptlet = {id:jsonBody.id, prompt:jsonBody.prompt, promptlet_type:jsonBody.promptletType,
          answerPool:jsonBody.responsePool, correctAnswer:[], userResponses:[]};

        let localData = this.dataSource.getValue();

        localData.currentSession.promptlets.push(studentPromptlet);
        this.changeData(localData);
      })
    })

  }

  loadPromptletsByActiveSession(){

    this.getSessionsData([this.dataSource.getValue().currentClass.activeSessionId])
      .subscribe((data: Session[]) => {
        let localData = this.dataSource.getValue();
        localData.currentSession = data[0];
        this.changeData(localData);

        this.getPromptletData(data[0].promptletIds)
          .subscribe((data: Promptlet[]) => {
            let localData = this.dataSource.getValue();

            localData.currentSession.promptlets = data;
            this.changeData(localData);
          });

      });

  }

  loadAllEnrolledClasses(){
    this.getEnrolledClassData()
      .subscribe((data: ClassData[]) => this.updateClasses(false, data));
  }
  loadAllOwnedClasses(){
    this.getOwnedClassData()
      .subscribe((data: ClassData[]) => this.updateClasses(true, data));
  }
  updateProfileAndClasses(){
    this.getProfile(this.dataSource.getValue().profile.id)
      .subscribe((data: Profile) =>
      {
        let localData = this.dataSource.getValue();
        localData.profile = data;
        this.changeData(localData);
        this.loadAllOwnedClasses();
        this.loadAllEnrolledClasses();
      });
  }

  updateClasses = (isOwnedClass: boolean, data: ClassData[]) => {
    let localData = this.dataSource.getValue();

    if (isOwnedClass){localData.ownedClasses = data.map(x => ({...x, owned:true}));}
    else {localData.enrolledClasses = data.map(x => ({...x, owned:false}));}


    this.changeData(localData);
    if (localData.currentClass != undefined && data.findIndex(x => x.id == localData.currentClass.id) >= 0){
      localData.currentClass = data[data.findIndex(x => x.id == localData.currentClass.id)];
      this.changeData(localData);
      if (isOwnedClass){
        this.loadSessionsByCurrentClassId(this.dataSource.getValue().currentClass);
      } else {
        this.loadSessionsByCurrentClassId(this.dataSource.getValue().currentClass);
      }

    }
  }

  // Data setters
  setCurrentSession(sessionId: string) {
    let localData = this.dataSource.getValue();
    localData.currentSession = this.dataSource.getValue().currentClassSessions.filter( item => item.id == sessionId)[0];
    this.changeData(localData);
  }
  setCurrentClass(clazz: ClassData) {
    let localData = this.dataSource.getValue();
    localData.currentClass = clazz;
    this.changeData(localData);
    this.updateProfileAndClasses();
  }
  setCurrentEnrolledClass(clazz: ClassData) {
    let localData = this.dataSource.getValue();
    localData.currentClass = clazz;

    this.changeData(localData);
  }
}

export type courseRequest = {
  ids: string[];
}

export type loginRequest = {
  username: string;
  password: string;
}

export type loginResponse = {
  jwt: string;
  userResponse: UserData;
}

export type idReturnType = {
  id: string;
}

export class SharedData {
  user: UserData;
  profile: Profile;
  ownedClasses: ClassData[];
  enrolledClasses: ClassData[];
  currentClass: ClassData;
  currentClassSessions: Session[];
  currentSession: Session;
  currentPromptlet: Promptlet;
  jwt: string;
  localData: { id: "123456789"; username: "RyanGreen105"; enabled: true; accountNonLocked: true; credentialsNonExpired: true; };
}

export type UserData = {
  id: string;
  username: string;
  profileId: string;
  enabled: boolean;
  accountNonExpired: boolean;
  accountNonLocked: boolean;
  credentialsNonExpired: boolean;
}

export type Profile = {
  id: string;
  username: string;
  coursesEnrolled: string[];
  coursesOwned: string[];
}

export type ClassData = {
  id: string;
  // code: string;
  className: string;
  sessionIds: string[];
  activeSessionId: string;
  owned: boolean;
}

export type Session = {
  id: string;
  sessionName: string;
  promptletIds: string[];
  promptlets: Promptlet[];
}

export type Promptlet = {
  id: string;
  prompt: string;
  promptlet_type: string;
  answerPool: string[];
  correctAnswer: string[];
  userResponses: UserResponse[];
}

export type UserResponse = {
  id: string;
  profileId: string;
  profileName: string;
  response: string[];
}

export type StudentPromptlet = {
  id: string;
  prompt: string;
  promptlet_type: string;
  answerPool: string[];
}
