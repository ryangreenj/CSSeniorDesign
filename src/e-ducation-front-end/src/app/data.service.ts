import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  private storageName: string = "Settings";
  private ipAddr: string = "localhost";

  private dataSource = new BehaviorSubject<SharedData>(new SharedData());
  currentData = this.dataSource.asObservable();

  private stompClientUserResponse;
  private stompClientPromptlets;
  constructor(private http: HttpClient) {

    if (this.getUserSettings("jwtToken") != null && this.getUserSettings("userResponse") != null){
      let localData = this.dataSource.getValue();
      localData.jwt = this.getUserSettings("jwtToken");
      localData.user = this.getUserSettings("userResponse");
      this.changeData(localData);

      this.getProfile(localData.user.profileId)
        .subscribe((data: Profile) =>
        {
          let localData = this.dataSource.getValue();
          localData.profile = data;
          this.changeData(localData);

          if (this.getUserSettings("currentSession") != null){
            let localData = this.dataSource.getValue();
            localData.currentSession = this.getUserSettings("currentSession");
            this.changeData(localData);
          }
          if (this.getUserSettings("currentPromptlet") != null){
            let localData = this.dataSource.getValue();
            localData.currentPromptlet = this.getUserSettings("currentPromptlet");
            this.changeData(localData);
          }

          if (this.getUserSettings("currentClass") != null){
            this.setCurrentClass(this.getUserSettings("currentClass"));
          } else {
            this.updateProfileAndClasses();
          }
        });
    }
  }

  getHeaders(){
    return new HttpHeaders().set('Content-Type', 'application/json' ).set('Authorization','Bearer ' + this.dataSource.getValue().jwt);
  }

  changeData(data: SharedData) {
    this.dataSource.next(data);
  }

  setSettings(storageName: string, data: any) {
    localStorage.setItem(storageName, JSON.stringify(data));
  }
  getUserSettings(storageName: string) {
    let data = localStorage.getItem(storageName);
    return JSON.parse(data);
  }
  clearUserSettings() {
    localStorage.removeItem(this.storageName);
  }
  cleanAll() {
    localStorage.clear()
  }

  /** HTTP CALLS -- return observable **/
  createProfile (username: string) {
    let newProfileRequest = {username:username};
    return this.http.post<idReturnType>("http://" + this.ipAddr + ":8080/profile",newProfileRequest);
  }
  getProfile(profileId: String){
    let getProfileRequest = {id: profileId};
    return this.http.put<Profile>("http://" + this.ipAddr + ":8080/profile",getProfileRequest);
  }

  createUser(username: string, password: string, profileId: string) {
    let newUserRequest = {username:username, password:password, profileId: profileId};
    return this.http.post("http://" + this.ipAddr + ":8080/user",newUserRequest);
  }
  authorizeUser(username: string, password: string) {
    // POST - /authorize
    let l : loginRequest = {username:username, password:password};

    return this.http.post<loginResponse>("http://" + this.ipAddr + ":8080/authenticate",l);
  }

  enrollClass(courseId: string) {
    // POST - /profile/join
    let enrollClassRequest = {profileId: this.dataSource.getValue().profile.id, courseId: courseId};
    return this.http.post<string>("http://" + this.ipAddr + ":8080/profile/join",enrollClassRequest, {headers:this.getHeaders()})
      .subscribe((_: string) => {});
  }
  getEnrolledClassData(){
    let c : courseRequest = {ids: this.dataSource.getValue().profile.coursesEnrolled};
    return this.http.put<ClassData[]>("http://" + this.ipAddr + ":8080/course/", c,{headers: this.getHeaders()});
  }
  createClass(courseName: string) {
    // POST - /course/create
    const newCourseRequest = {courseName:courseName};
    return this.http.post<idReturnType>("http://" + this.ipAddr + ":8080/course", newCourseRequest, {headers:this.getHeaders()})
      .subscribe((courseId:idReturnType) => {
        const hostCourseRequest = {profileId:this.dataSource.getValue().profile.id, courseId:courseId.id};
        this.http.post<string>("http://" + this.ipAddr + ":8080/profile/create",hostCourseRequest, {headers:this.getHeaders()})
          .subscribe((_: string) => {});
      });
  }
  getOwnedClassData(){
    const c : courseRequest = {ids: this.dataSource.getValue().profile.coursesOwned};
    return this.http.put<ClassData[]>("http://" + this.ipAddr + ":8080/course/", c,{headers: this.getHeaders()});
  }

  getSessionsData(sessions: string[]) {
    // Load session data from ID
    let getSessionsRequest = {sessionIds: sessions};
    return this.http.put<Session[]>("http://" + this.ipAddr + ":8080/course/session",getSessionsRequest, {headers:this.getHeaders()});
  }
  createSession(sessionName: string) {
    // POST - /course/session
    const courseId = this.dataSource.getValue().currentClass.id;
    const newSessionRequest = {courseId:courseId, sessionName:sessionName};
    return this.http.post<string>("http://" + this.ipAddr + ":8080/course/session",newSessionRequest, {headers:this.getHeaders()})
      .subscribe((_: string) => {});
  }
  setActiveSession(sessionId: string, fetchData: boolean){
    const courseId = this.dataSource.getValue().currentClass.id;
    const activeSessionRequest = {courseId:courseId, sessionId:sessionId};
    return this.http.post<string>("http://" + this.ipAddr + ":8080/course/activeSession",activeSessionRequest, {headers:this.getHeaders()})
      .subscribe((_: string) => {
        this.updateProfileAndClasses();
        if (fetchData){
          // this.fetchPromptletData();
        }
      });
  }

  createPromptlet(prompt: string, promptlet_type: string, answerPool: string[], correctAnswer: string[]) {
    // POST - /course/session/promptlet
    const promptletRequest = {sessionId: this.dataSource.getValue().currentSession.id, prompt: prompt,
          promptlet_type: promptlet_type, answerPool: answerPool, correctAnswer: correctAnswer};
    return this.http.post<string>("http://" + this.ipAddr + ":8080/course/session/promptlet", promptletRequest, {headers:this.getHeaders()})
      .subscribe((_: string) => {});
  }
  getPromptletData(promptletIds : string[]) {
    // Load promptlet data from ID
    let getPromptletRequest = {ids: promptletIds};
    return this.http.put<Promptlet[]>("http://" + this.ipAddr + ":8080/course/session/promptlet",getPromptletRequest, {headers:this.getHeaders()});
  }
  submitPromptletResponse(promptletId: string, response: string[]) {
    const profileId = this.dataSource.getValue().user.profileId;
    // Unsure if this should be UserData or Profile
    // Perhaps we can concatenate user ID as the first line of 'response' so we only send one string per response
    let promptletResponse = {promptletId: promptletId, profileId: profileId, response: response};

    return this.http.post<string>("http://" + this.ipAddr + ":8080/course/session/promptlet/answer", promptletResponse, {headers:this.getHeaders()})
      .subscribe((_: string) => {});
  }

  getUserResponse(userResponseIds: string[]){
    const userResponseRequest = {userResponseIds: userResponseIds};

    return this.http.put<UserResponse[]>("http://" + this.ipAddr + ":8080/course/session/promptlet/answers", userResponseRequest, {headers:this.getHeaders()})
      .subscribe((data: UserResponse[]) => {
        let localData = this.dataSource.getValue();
        localData.currentPromptlet.userResponses = data;
        this.changeData(localData);
      });
  }
  fetchUserResponse(){

    this.stompClientUserResponse = Stomp.over(new SockJS("http://" + this.ipAddr + ":8080/socket"));

    this.stompClientUserResponse.connect({}, frame => {
      this.stompClientUserResponse.subscribe('/topic/notification/' + this.dataSource.getValue().currentPromptlet.id, (notification) => {
        // observer.next(notifications);
        const jsonBody = JSON.parse(notification.body)
        const userResponse : UserResponse = {id:jsonBody.id, profileId:jsonBody.profileId, profileName: jsonBody.profileName, response:jsonBody.responses};

        let localData = this.dataSource.getValue();
        localData.currentPromptlet.userResponses = localData.currentPromptlet.userResponses.filter(x => x.profileId != userResponse.profileId);
        localData.currentPromptlet.userResponses.push(userResponse);
        this.changeData(localData);
      })
    })
  }
  disconnectUserResponse(){
    this.stompClientUserResponse.disconnect({});
  }

  updatePromptletStatus(promptletId : string, status : boolean){
    const getPromptletActiveRequest = {sessionId: this.dataSource.getValue().currentSession.id,promptletId: promptletId, status:status};
    this.http.post<string>("http://" + this.ipAddr + ":8080/course/session/promptlet/active", getPromptletActiveRequest, {headers:this.getHeaders()})
      .subscribe((_: string) => {
          this.loadPromptletsByCurrentSessionId(false);
      });
  }
  // Subscribe Blocks
  loadSessionsByCurrentClassId(currentClass : ClassData, possibleHide : boolean){
    this.getSessionsData(currentClass.sessionIds)
      .subscribe((data: Session[]) => {
          let localData = this.dataSource.getValue();

          localData.currentClassSessions = data;
          this.changeData(localData);

          if (localData.currentSession != undefined && data.findIndex(x => x.id == localData.currentSession.id) >= 0) {

            let localData = this.dataSource.getValue();
            localData.currentSession = data[data.findIndex(x => x.id == localData.currentSession.id)];
            this.changeData(localData);
            this.loadPromptletsByCurrentSessionId(possibleHide);

          }
        });
  }

  loadPromptletsByCurrentSessionId(possibleHide : boolean){
    this.getPromptletData(this.dataSource.getValue().currentSession.promptletIds)
      .subscribe((data: any[]) => {
        let localData = this.dataSource.getValue();
        let promptlets : Promptlet[] = [];

        data.forEach(x => {
          const userResponses : UserResponse[] = x.userResponses.map(x => ({id:x, profileId:"", response:[]}));
          const promptlet : Promptlet = {id:x.id, prompt:x.prompt, promptlet_type:x.promptlet_type, answerPool: x.answerPool,
            correctAnswer:x.correctAnswer, userResponses: userResponses, visible:x.visible};
          if (promptlet.visible || !possibleHide){
            promptlets.push(promptlet);
          }

          if (localData.currentPromptlet != undefined && localData.currentPromptlet.id == promptlet.id){
            localData.currentPromptlet = promptlet;
            this.changeData(localData);
            this.getUserResponse(x.userResponses);
          }
        });
        localData.currentSession.promptlets = promptlets;
        this.changeData(localData);
      });
  }
  fetchPromptletData(){

    if (this.stompClientPromptlets == undefined || !this.stompClientPromptlets.isConnected){

      this.stompClientPromptlets = Stomp.over(new SockJS("http://" + this.ipAddr + ":8080/socket"));
      this.stompClientPromptlets.connect({}, frame => {
        this.stompClientPromptlets.subscribe('/topic/notification/' +
          (this.dataSource.getValue().currentSession == undefined ||  this.dataSource.getValue().currentClass.activeSessionId == "" ? this.dataSource.getValue().currentClass.id : this.dataSource.getValue().currentSession.id), (notification) => {
          // observer.next(notifications);
          const jsonBody = JSON.parse(notification.body)
          if (jsonBody.notificationType == "PROMPTLET"){
            const studentPromptlet : Promptlet = {id:jsonBody.id, prompt:jsonBody.prompt, promptlet_type:jsonBody.promptletType,
              answerPool:jsonBody.responsePool, correctAnswer:[], userResponses:[], visible:jsonBody.visible};

            let localData = this.dataSource.getValue();
            if (studentPromptlet.visible == true){
              localData.currentSession.promptlets.push(studentPromptlet);
            } else {
              localData.currentSession.promptlets = localData.currentSession.promptlets.filter(x => x.id != studentPromptlet.id);
            }
            this.changeData(localData);
          } else if (jsonBody.notificationType == "SESSION") {

            this.setActiveSession(jsonBody.newSessionId, true);
            this.stompClientPromptlets.disconnect({});
          }
        })
      })
    }
  }
  disconnectPromptlets(){
    if (this.stompClientPromptlets != undefined)
    {

      this.stompClientPromptlets.disconnect({});
    }
  }

  loadPromptletsByActiveSession(){
    if (this.dataSource.getValue().currentClass.activeSessionId != ""){
      this.getSessionsData([this.dataSource.getValue().currentClass.activeSessionId])
        .subscribe((data: Session[]) => {
          let localData = this.dataSource.getValue();
          localData.currentSession = data[0];
          this.changeData(localData);

          this.getPromptletData(data[0].promptletIds)
            .subscribe((data: Promptlet[]) => {
              let localData = this.dataSource.getValue();

              localData.currentSession.promptlets = data.filter(x => x.visible);
              this.changeData(localData);
              this.disconnectPromptlets()
              this.fetchPromptletData();
            });

        });
    } else if (this.dataSource.getValue().currentClass != undefined) {
      let localData = this.dataSource.getValue();
      localData.currentSession = {id:"", sessionName:"", promptletIds: [], promptlets:[]}
      this.changeData(localData);
      this.disconnectPromptlets()
      this.fetchPromptletData();
    } else {
    }
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
        this.loadSessionsByCurrentClassId(this.dataSource.getValue().currentClass, false);
      } else {
        this.loadPromptletsByActiveSession();
      }

    }
  }

  // Data setters
  setCurrentPromptlet(promptletId: string) {
    // Load promptlet data from ID

    let localData = this.dataSource.getValue();
    localData.currentPromptlet = this.dataSource.getValue().currentSession.promptlets.filter( item => item.id == promptletId)[0];

    this.setSettings("currentPromptlet", localData.currentPromptlet);
    this.changeData(localData);
  }
  setCurrentSession(sessionId: string) {
    let localData = this.dataSource.getValue();
    localData.currentSession = this.dataSource.getValue().currentClassSessions.filter( item => item.id == sessionId)[0];

    this.setSettings("currentSession", localData.currentSession);
    this.changeData(localData);
  }
  setCurrentClass(clazz: ClassData) {
    let localData = this.dataSource.getValue();
    localData.currentClass = clazz;
    this.setSettings("currentClass", localData.currentClass);
    this.changeData(localData);
    this.updateProfileAndClasses();
  }
  setCurrentEnrolledClass(clazz: ClassData) {
    let localData = this.dataSource.getValue();
    localData.currentClass = clazz;
    this.setSettings("currentClass", localData.currentClass);
    this.changeData(localData);
    this.updateProfileAndClasses();
  }

  login(username : string, password : string, router: Router){
    this.authorizeUser(username,password)
      .subscribe((authResponse: loginResponse) =>
      {
        let localData = this.dataSource.getValue();
        localData.jwt = authResponse.jwt;
        localData.user = authResponse.userResponse;

        this.setSettings("jwtToken", authResponse.jwt);
        this.setSettings("userResponse", authResponse.userResponse);
        this.getProfile(authResponse.userResponse.profileId)
          .subscribe((data: Profile) =>
          {
            let localData = this.dataSource.getValue();
            localData.profile = data;
            this.changeData(localData);
            router.navigate(["dashboard/classlist"]);
          });
      });
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
  visible: boolean;
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
