import { stringify } from '@angular/compiler/src/util';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  private dataSource = new BehaviorSubject<SharedData>(new SharedData());
  currentData = this.dataSource.asObservable();

  constructor(private http: HttpClient) { }

  changeData(data: SharedData) {
    this.dataSource.next(data);
  }
  
  loginUser(username: string, password: string) {
    // POST - /authorize
    
    let userResponse: { "id": "123456789", "username": "RyanGreen105", "enabled": true, "accountNonExpired": true, "accountNonLocked": true, "credentialsNonExpired": true };
    
    let localData = this.dataSource.getValue();
    localData.user = userResponse;
    this.changeData(localData);
    
    return true;
  }
  
  createUser(username: string, password: string) {
     // POST - /user
  }

  loadClassData() {
    // Load class data for this user user.id
    return [
      { "id": "100","className": "Computer Science 1","sessionIds": [] },
      { "id": "101", "className": "Software Engineering", "sessionIds": [] },
      { "id": "102", "className": "Intro to Public Speaking", "sessionIds": [] },
      { "id": "103", "className": "Spanish 1",  "sessionIds": [] },
      { "id": "104", "className": "Calculus 2",  "sessionIds": ["123", "234", "456"] }
    ];
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

  loadClassData2(){


  }

  getAllClassData() {
    const token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKYWtlIiwiZXhwIjoxNjEzNjg0NDcwLCJpYXQiOjE2MTM1OTgwNzB9.-0dL36EO_D2wotl--i9kOMPvOAHL_TOAb63rroTuMYaHqVIBkB9opeK_-lq0qOrjtxihCOVjCyyHvyBS99OTQQ";
    let c : courseRequest = {ids:["60187006aef5fb30cac9ad61"]};
    return this.http.get<ClassData[]>("http://localhost:8080/course/all/");
  }
}

export type courseRequest = {
  ids: string[];
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
