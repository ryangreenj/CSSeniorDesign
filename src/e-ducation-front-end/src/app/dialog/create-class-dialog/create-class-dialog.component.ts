import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { DataService } from 'src/app/data.service';

@Component({
  selector: 'app-create-class-dialog',
  templateUrl: './create-class-dialog.component.html',
  styleUrls: ['./create-class-dialog.component.css']
})
export class CreateClassDialogComponent implements OnInit {
  
  classCode: string; // Currently unused
  className: string;
  
  constructor(public dialogRef: MatDialogRef<CreateClassDialogComponent>, private dataService: DataService) { }

  ngOnInit(): void {
  }
  
  onCreateClick(): void {
    this.dialogRef.close();
    this.dataService.createClass(this.className);
  }
  
  onCancelClick(): void {
    this.dialogRef.close();
  }
  
}
