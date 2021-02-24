import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { DataService } from 'src/app/data.service';

@Component({
  selector: 'app-create-session-dialog',
  templateUrl: './create-session-dialog.component.html',
  styleUrls: ['./create-session-dialog.component.css']
})
export class CreateSessionDialogComponent implements OnInit {
  
  sessionName: string;
  
  constructor(public dialogRef: MatDialogRef<CreateSessionDialogComponent>, private dataService: DataService) { }

  ngOnInit(): void {
  }
  
  onCreateClick(): void {
    this.dialogRef.close();
    this.dataService.createSession(this.sessionName);
  }
  
  onCancelClick(): void {
    this.dialogRef.close();
  }

}
