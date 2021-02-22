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

  private jwt: string;

  constructor(private http: HttpClient) {
    this.jwt = "";
  }

  getHeaders(){
    return new HttpHeaders().set('Content-Type', 'application/json' ).set('Authorization','Bearer ' + this.jwt);
  }

  changeData(data: SharedData) {
    this.jwt = data.jwt;
    this.dataSource.next(data);
  }

  loginUser(username: string, password: string) {
    // POST - /authorize
    let hh =  new HttpHeaders({ 'Content-Type': 'application/json'});
    let l : loginRequest = {username:username, password:password};

    // let userResponse: { "id": "123456789", "username": "RyanGreen105", "enabled": true, "accountNonExpired": true, "accountNonLocked": true, "credentialsNonExpired": true };

    // let localData = this.dataSource.getValue();
    // localData.user = userResponse;
    // this.changeData(localData);

    return this.http.post<loginResponse>("http://localhost:8080/authenticate",l);
  }


  createUser(username: string, password: string) {
     // POST - /user
  }

  loadSessionData(sessionId: string) {
    // Load session data from ID
    return { "id": sessionId, "sessionName": sessionId + " Session", "promptletIds": ["eeee", "4345", "asdf"] };
  }

  loadPromptletData(promptletId: string) {
    // Load promptlet data from ID
    return { "id": promptletId, "prompt": "What is the correct answer?", "promptlet_type": "MULTI_CHOICE", "answerPool": ["a", "b", "c", "d"], "correctAnswer": ["b"], "userResponses": [] };
  }

  enrollClass(courseId: string) {
    // POST - /profile/join
    console.log("Tried to enroll in " + courseId);

    // Need to re-get and update data after this request
  }

  createClass(courseName: string) {
    // POST - /course
    console.log("Tried to create class " + courseName);
  }

  createSession(sessionName: string) {
    // POST - /course/session
    let courseId = this.dataSource.getValue().currentClass.id;

    console.log("Tried to create session " + sessionName + " for " + courseId);
  }

  createPromptlet(prompt: string, promptlet_type: string, answerPool: string[], correctAnswer: string[]) {
    // POST - /course/session/promptlet
    let sessionId = this.dataSource.getValue().currentSessionId;
  }

  getClassData(){
    let c : courseRequest = {ids:["60187006aef5fb30cac9ad61","6019a975b3e5ba58746caeca","60341d4d8dbb8a3cb14b181c"]};
    return this.http.put<ClassData[]>("http://localhost:8080/course/", c,{headers: this.getHeaders()});
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



export class SharedData {
  user: UserData;
  ownedClasses: ClassData[];
  classes: ClassData[];
  currentClass: ClassData;
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
  enabled: boolean;
  accountNonExpired: boolean;
  accountNonLocked: boolean;
  credentialsNonExpired: boolean;
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
