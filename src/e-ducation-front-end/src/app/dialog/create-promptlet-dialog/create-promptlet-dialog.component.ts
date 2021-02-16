import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-create-promptlet-dialog',
  templateUrl: './create-promptlet-dialog.component.html',
  styleUrls: ['./create-promptlet-dialog.component.css']
})
export class CreatePromptletDialogComponent implements OnInit {
  
  promptletType: string = "MULTI_CHOICE";
  
  constructor(public dialogRef: MatDialogRef<CreatePromptletDialogComponent>) { }

  ngOnInit(): void {
  }
  
  onCreateClick(): void {
    this.dialogRef.close();
  }
  
  onCancelClick(): void {
    this.dialogRef.close();
  }

}
