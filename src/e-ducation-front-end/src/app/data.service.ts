import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  
  private dataSource = new BehaviorSubject<SharedData>(new SharedData())
  currentData = this.dataSource.asObservable();
  
  constructor() { }
  
  changeData(data: SharedData) {
    this.dataSource.next(data);
  }
}

export class SharedData {
  // Temporarily fill, this service is where we can load and store shared class data
  classes: ClassData[] = [
    { "id": '100', "code": "CS101", "name": "Computer Science 1", "status": false },
    { "id": '101', "code": "EECE3093C", "name": "Software Engineering", "status": false },
    { "id": '102', "code": "COMM1017", "name": "Intro to Public Speaking", "status": false },
    { "id": '103', "code": "SPN1001", "name": "Spanish 1", "status": true }
  ];
  
  activeClass: ClassData
}

export type ClassData = {
  id: string
  code: string
  name: string
  status: boolean
}