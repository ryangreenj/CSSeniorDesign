import { stringify } from '@angular/compiler/src/util';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import {HttpClient, HttpHeaders} from "@angular/common/http";

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
    return { "id": promptletId, "prompt": "What is the correct answer?", "promptlet_type": "MULTI_CHOICE", "answerPool": ["a", "b", "c", "d"], "correctAnswer": ["b"], "userResponses": [] };
  }

  createPromptlet(prompt: string, promptlet_type: string, answerPool: string[], correctAnswer: string[]) {
    // POST - /course/session/promptlet
    console.log("prompt:", prompt);
    console.log("promptlet_type:", promptlet_type);
    console.log("answerPool:", answerPool);
    console.log("correctAnswer:", correctAnswer);
    const promptletRequest = {sessionId: this.dataSource.getValue().currentSessionId, prompt: prompt,
          promptlet_type: promptlet_type, answerPool: answerPool, correctAnswer: correctAnswer};
    return this.http.post<string>("http://localhost:8080/course/session/promptlet", promptletRequest, {headers:this.getHeaders()})
      .subscribe((_: string) => {});
  }

  // Subscribe Blocks
  loadSessionsByCurrentClassId(){
    this.getSessionsData(this.dataSource.getValue().currentClass.sessionIds)
      .subscribe((data: Session[]) => {
        let localData = this.dataSource.getValue();

        localData.currentClassSessions = data;
        this.changeData(localData);
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

    if (isOwnedClass){localData.ownedClasses = data;}
    else {localData.classes = data;}

    this.changeData(localData);
    if (localData.currentClass != undefined && data.findIndex(x => x.id == localData.currentClass.id) >= 0){
      localData.currentClass = data[data.findIndex(x => x.id == localData.currentClass.id)];
      this.changeData(localData);
      this.loadSessionsByCurrentClassId();
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
  classes: ClassData[];
  currentClass: ClassData;
  currentClassSessions: Session[];
  currentSessionId: string;
  currentSession: Session;
  currentPromptletId: string;
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
}

export type Session = {
  id: string;
  sessionName: string;
  promptletIds: string[];
}

export type Promptlet = {
  id: string;
  prompt: string;
  promptlet_type: string;
  answerPool: string[];
  correctAnswer: string[];
  userResponses: string[];
}
