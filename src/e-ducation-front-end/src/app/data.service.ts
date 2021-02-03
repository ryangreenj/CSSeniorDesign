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
  
  getSessionData(sessionId: string) {
    // Load session data from ID
    return { "courseId": "12334123", "sessionName": sessionId + " Session" };
  }
}

export class SharedData {
  // Temporarily fill, this service is where we can load and store shared class data
  classes: ClassData[] = [
    { "id": "100", "code": "CS101", "className": "Computer Science 1", "status": false, "sessionIds": [] },
    { "id": "101", "code": "EECE3093C", "className": "Software Engineering", "status": true, "sessionIds": [] },
    { "id": "102", "code": "COMM1017", "className": "Intro to Public Speaking", "status": false, "sessionIds": [] },
    { "id": "103", "code": "SPN1001", "className": "Spanish 1", "status": true, "sessionIds": [] },
    { "id": "104", "code": "MATH1062", "className": "Calculus 2", "status": false, "sessionIds": ["123", "234", "456"] }
  ];
  
  currentClass: ClassData;
  currentSessionId: string;
  currentSession: Session;
}

export type ClassData = {
  id: string
  code: string
  className: string
  status: boolean
  sessionIds: string[]
}

export type Session = {
  courseId: string
  sessionName: string
}