import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { DataService, Promptlet } from 'src/app/data.service';

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

  previewPromptlet: Promptlet = {id: "NULL", prompt: "", promptlet_type: "MULTI_CHOICE", answerPool: [], correctAnswer: [], userResponses: [], visible:false };

  constructor(public dialogRef: MatDialogRef<CreatePromptletDialogComponent>, private dataService: DataService) { }

  ngOnInit(): void {
  }

  onUpdate(): void {
    this.previewPromptlet.prompt = this.prompt;
    this.previewPromptlet.promptlet_type = this.promptletType;
    this.previewPromptlet.answerPool = (this.answerPool != undefined ? this.answerPool.split("\n") : []);
  }

  onCreateClick(): void {
    this.dialogRef.close();

    this.dataService.createPromptlet(this.prompt, this.promptletType, (this.answerPool != undefined ? this.answerPool.split("\n") : []), (this.correctAnswer != undefined ? this.correctAnswer.split("\n") : []))
  }

  onCancelClick(): void {
    this.dialogRef.close();
  }

}
