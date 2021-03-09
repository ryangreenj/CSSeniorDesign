import { Component, Input, OnInit } from '@angular/core';
import { DataService, Promptlet, SharedData } from 'src/app/data.service';

@Component({
  selector: 'app-results-display',
  templateUrl: './results-display.component.html',
  styleUrls: ['./results-display.component.css']
})
export class ResultsDisplayComponent implements OnInit {

  sharedData: SharedData;

  @Input() promptlet: Promptlet;
  visible: boolean = false;

  processedResults: Result[] = [];

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.dataService.currentData.subscribe(data => this.dataChanged(data));
  }

  dataChanged(data: SharedData): void {
    this.sharedData = data;
    this.handleResults(this.sharedData.currentPromptlet);
  }

  handleResults(promptlet: Promptlet): void {

    this.processedResults = []
    switch (promptlet.promptlet_type) {
      case "MULTI_CHOICE":
        this.visible = true;
        this.handleResultsMultiResponse(promptlet);
        break;
      case "MULTI_RESPONSE":
        this.visible = true;
        this.handleResultsMultiResponse(promptlet);
        break;
      case "SLIDER":
        this.visible = true;
        this.handleResultsSlider(promptlet, 0, 100, 10);
        break;
    }
  }


  handleResultsMultiResponse(promptlet: Promptlet): void {
    if (promptlet) {
      for (const answer of promptlet.answerPool) {
        this.processedResults.push({"name": answer, "value": 0});
      }

      // for (const result of this.processedResults){
      //     result.value = 0;
      // }
      for (const response of promptlet.userResponses) {
        for (const r of response.response) {
          this.processedResults.find(element => element.name == r).value += 1;
        }
      }
    }
  }

  handleResultsSlider(promptlet: Promptlet, min: number, max: number, interval: number): void {
    if (promptlet) {
      for (let i = 0; i < max; i += interval) {
        this.processedResults.push({"name": i + " - " + (i + interval), "value": 0});
      }

      for (var response of promptlet.userResponses) {
        for (var r of response.response) {
          let index = Math.min(Math.floor((parseInt(r)) / interval), 9);
          this.processedResults[index].value += 1;
        }
      }
    }
  }

}

interface Result {
  name: string;
  value: number;
};
