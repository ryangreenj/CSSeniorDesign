import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { DataService } from 'src/app/data.service';

@Component({
  selector: 'app-create-promptlet-dialog',
  templateUrl: './create-promptlet-dialog.component.html',
  styleUrls: ['./create-promptlet-dialog.component.css']
})
export class CreatePromptletDialogComponent implements OnInit {
  
  prompt: string;
  promptletType: string = "MULTI_CHOICE";
  answerPool: string;
  correctAnswer: string;
  
  constructor(public dialogRef: MatDialogRef<CreatePromptletDialogComponent>, private dataService: DataService) { }

  ngOnInit(): void {
  }
  
  onCreateClick(): void {
    this.dialogRef.close();
    
    this.dataService.createPromptlet(this.prompt, this.promptletType, this.answerPool.split("\n"), this.correctAnswer.split("\n"))
  }
  
  onCancelClick(): void {
    this.dialogRef.close();
  }

}
