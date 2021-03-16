import {Component, Input, OnInit} from '@angular/core';
import {UserResponse} from "../../data.service";

@Component({
  selector: 'app-user-response-list',
  templateUrl: './user-response-list.component.html',
  styleUrls: ['./user-response-list.component.scss']
})
export class UserResponseListComponent implements OnInit {

  @Input()
  userResponses: UserResponse[];

  constructor() { }

  ngOnInit(): void {
  }

}
