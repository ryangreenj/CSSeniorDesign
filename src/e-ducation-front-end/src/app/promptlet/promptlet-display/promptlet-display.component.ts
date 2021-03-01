import { Component, Input, OnInit } from '@angular/core';
import { DataService, Promptlet } from 'src/app/data.service';

interface CheckBox {
  choice: string;
  selected: boolean;
}

@Component({
  selector: 'app-promptlet-display',
  templateUrl: './promptlet-display.component.html',
  styleUrls: ['./promptlet-display.component.css']
})
export class PromptletDisplayComponent implements OnInit {

  @Input() promptlet: Promptlet;
  @Input() context: string;

  canSubmit: boolean = false;

  multiChoiceAnswer: string;
  checkBoxes: CheckBox[] = [];
  openResponseAnswer: string;
  sliderValue: string;

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    if (this.promptlet) {
      switch (this.promptlet.promptlet_type) {
        case "MULTI_CHOICE":
          break;
        case "MULTI_RESPONSE":
          this.canSubmit = true;
          this.promptlet.answerPool.forEach(choice => this.checkBoxes.push({"choice": choice, "selected": false}));
          break;
        case "OPEN_RESPONSE":
          break;
        case "SLIDER":
          break;
        default:
          console.log("Unknown promptlet type " + this.promptlet.promptlet_type);
          break;
      }
    }
  }

  checkCanSubmit() {
    switch (this.promptlet.promptlet_type) {
      case "MULTI_CHOICE":
        this.canSubmit = this.multiChoiceAnswer != "";
        break;
      case "OPEN_RESPONSE":
        this.canSubmit = this.openResponseAnswer != "";
        break;
      default:
        this.canSubmit = true;
        break;
    }
  }

  onSubmit() {
    let response = [];

    switch (this.promptlet.promptlet_type) {
      case "MULTI_CHOICE":
        response = [this.multiChoiceAnswer];
        break;
      case "MULTI_RESPONSE":
        this.checkBoxes.forEach(function(box) {
          if (box.selected == true) {
            response.push(box.choice);
          }
        });
        break;
      case "OPEN_RESPONSE":
        response = [this.openResponseAnswer];
        break;
      case "SLIDER":
        response = [this.sliderValue];
        break;
    }
    console.log(response);
    this.dataService.submitPromptletResponse(this.promptlet.id, response);
  }

}
