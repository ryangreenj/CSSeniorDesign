import { Component, Input, OnInit } from '@angular/core';
import { Promptlet } from 'src/app/data.service';

@Component({
  selector: 'app-promptlet-display',
  templateUrl: './promptlet-display.component.html',
  styleUrls: ['./promptlet-display.component.css']
})
export class PromptletDisplayComponent implements OnInit {
  
  @Input() promptlet: Promptlet;
  @Input() instructorContext: boolean;
  
  questionType: string;
  questionInstruction: string;
  
  multiChoiceAnswer: string;
  
  constructor() { }

  ngOnInit(): void {
    if (this.promptlet) {
      this.promptlet.promptlet_type = "SLIDER";
      
      switch (this.promptlet.promptlet_type) {
        case "MULTI_CHOICE":
          this.questionType = "Multiple Choice";
          this.questionInstruction = "Choose one of the following"
          break;
        case "MULTI_RESPONSE":
          this.questionType = "Multiple Response";
          this.questionInstruction = "Choose one or more of the following"
          break;
        case "OPEN_RESPONSE":
          this.questionType = "Open Response";
          this.questionInstruction = "Type your answer and submit"
          break;
        case "SLIDER":
          this.questionType = "Slider Response";
          this.questionInstruction = "Use the Slider to respond"
          break;
        default:
          this.questionType = "Unknown";
          this.questionInstruction = "Unknown type";
          break;
      }
    }
  }

}
