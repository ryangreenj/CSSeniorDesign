import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ClassData} from "../../../data.service";

@Component({
  selector: 'app-class-list-display',
  templateUrl: './class-list-display.component.html',
  styleUrls: ['./class-list-display.component.css']
})
export class ClassListDisplayComponent {

  @Input()
  clazzList: ClassData[];

  @Output() routeToClassEvent = new EventEmitter<ClassData>();

  routeToClass(value: ClassData){
    this.routeToClassEvent.emit(value);
  }

}
