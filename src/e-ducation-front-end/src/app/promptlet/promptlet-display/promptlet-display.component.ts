import { Component, Input, OnInit } from '@angular/core';
import {DataService, Promptlet, UserResponse} from 'src/app/data.service';

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
          if (this.promptlet.submitted && this.promptlet.userResponses[0].response[0] != undefined){
            this.multiChoiceAnswer = this.promptlet.userResponses[0].response[0];
          }
          break;
        case "MULTI_RESPONSE":
          if (this.promptlet.submitted && this.promptlet.userResponses[0] != undefined){
            this.promptlet.answerPool.forEach(choice => {
              if (this.promptlet.userResponses[0].response.indexOf(choice) > -1){
                this.checkBoxes.push({"choice": choice, "selected": true})
              } else {
                this.checkBoxes.push({"choice": choice, "selected": false})
              }
            });
          } else {
            this.canSubmit = true;
            this.promptlet.answerPool.forEach(choice => this.checkBoxes.push({"choice": choice, "selected": false}));
          }

          break;
        case "OPEN_RESPONSE":
          if (this.promptlet.submitted && this.promptlet.userResponses[0].response[0] != undefined){
            this.openResponseAnswer = this.promptlet.userResponses[0].response[0];
          }
          break;
        case "SLIDER":
          if (this.promptlet.submitted && this.promptlet.userResponses[0].response[0] != undefined){
            this.sliderValue = this.promptlet.userResponses[0].response[0];
          }
          break;
        default:
          console.log("Unknown promptlet type " + this.promptlet.promptlet_type);
          break;
      }
    }
  }

  checkCanSubmit(choice: string) {
    switch (this.promptlet.promptlet_type) {
      case "MULTI_CHOICE":
        if (this.promptlet.userResponses.length > 0){
          this.canSubmit = this.multiChoiceAnswer != "" && choice != this.promptlet.userResponses[0].response[0];
        } else {
          this.canSubmit = this.multiChoiceAnswer != ""
        }
        break;
      case "OPEN_RESPONSE":
        if (this.promptlet.userResponses.length > 0){
          this.canSubmit = this.openResponseAnswer != "" && choice != this.promptlet.userResponses[0].response[0];
        } else {
          this.canSubmit = this.openResponseAnswer != ""
        }
        break;
      case "MULTI_RESPONSE":
        if (this.promptlet.userResponses.length > 0) {
          let tempCanSubmit = true;
          for (let check of this.checkBoxes){
            if (check.selected){
              if (this.promptlet.userResponses[0].response.indexOf(check.choice) < 0 ||
                (check.choice == choice[choice.length - 1] && choice[0] == "-")){
                tempCanSubmit = false;
                break;
              }
            } else {
              if (this.promptlet.userResponses[0].response.indexOf(check.choice) > -1 ||
                (check.choice == choice[choice.length - 1] && choice[0] != "-")){
                tempCanSubmit = false;
                break;
              }
            }
          }

          this.canSubmit = !tempCanSubmit;
        }

        break;
      case "SLIDER":
        if (this.promptlet.userResponses.length > 0){
          this.canSubmit = this.sliderValue != "" && choice != this.promptlet.userResponses[0].response[0];
        } else {
          this.canSubmit = this.sliderValue != ""
        }
        break;
      default:
        this.canSubmit = true;
        break;
    }
  }

  onSubmit() {
    if (this.context != "student") {
      return;
    }

    let response = [];

    switch (this.promptlet.promptlet_type) {
      case "MULTI_CHOICE":
        response = [this.multiChoiceAnswer];
        if (this.promptlet.userResponses.length > 0){
          this.promptlet.userResponses[0].response[0] = this.multiChoiceAnswer;
        }
        break;
      case "MULTI_RESPONSE":
        if (this.promptlet.userResponses.length > 0){
          this.promptlet.userResponses[0].response = [];
        }
        for (let box of this.checkBoxes){
          if (box.selected == true) {
            response.push(box.choice);
            if (this.promptlet.userResponses.length > 0){
              this.promptlet.userResponses[0].response.push(box.choice);
            }
          }
        }
        break;
      case "OPEN_RESPONSE":
        if (this.promptlet.userResponses.length > 0){
          this.promptlet.userResponses[0].response[0] = this.openResponseAnswer;
        }
        response = [this.openResponseAnswer];
        break;
      case "SLIDER":
        response = [this.sliderValue];
        if (this.promptlet.userResponses.length > 0){
          this.promptlet.userResponses[0].response[0] = this.sliderValue;
        }
        break;
    }
    this.canSubmit = false;
    this.promptlet.submitted = true;
    this.dataService.submitPromptletResponse(this.promptlet.id, response);
  }

}
