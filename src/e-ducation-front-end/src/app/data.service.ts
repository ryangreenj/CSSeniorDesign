import { stringify } from '@angular/compiler/src/util';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  
  private dataSource = new BehaviorSubject<SharedData>(new SharedData());
  currentData = this.dataSource.asObservable();
  
  constructor() { }
  
  changeData(data: SharedData) {
    this.dataSource.next(data);
  }
  
  loadClassData() {
    // Load class data for this user user.id
    return [
      { "id": "100", "code": "CS101", "className": "Computer Science 1", "status": false, "sessionIds": [] },
      { "id": "101", "code": "EECE3093C", "className": "Software Engineering", "status": true, "sessionIds": [] },
      { "id": "102", "code": "COMM1017", "className": "Intro to Public Speaking", "status": false, "sessionIds": [] },
      { "id": "103", "code": "SPN1001", "className": "Spanish 1", "status": true, "sessionIds": [] },
      { "id": "104", "code": "MATH1062", "className": "Calculus 2", "status": false, "sessionIds": ["123", "234", "456"] }
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
}

export class SharedData {
  user: UserData;
  classes: ClassData[];
  currentClass: ClassData;
  currentSessionId: string;
  currentSession: Session;
  currentPromptletId: string;
  currentPromptlet: Promptlet;
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
  code: string;
  className: string;
  status: boolean;
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