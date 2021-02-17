import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-create-session-dialog',
  templateUrl: './create-session-dialog.component.html',
  styleUrls: ['./create-session-dialog.component.css']
})
export class CreateSessionDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<CreateSessionDialogComponent>) { }

  ngOnInit(): void {
  }
  
  onCreateClick(): void {
    this.dialogRef.close();
  }
  
  onCancelClick(): void {
    this.dialogRef.close();
  }

}
