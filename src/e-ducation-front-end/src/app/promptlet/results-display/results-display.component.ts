import { Component, Input, OnInit } from '@angular/core';
import { DataService, Promptlet, SharedData } from 'src/app/data.service';
import { interval, Subscription } from 'rxjs';

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
  
  subscription: Subscription;
  
  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.dataService.currentData.subscribe(data => this.dataChanged(data));
    
    //this.handleResults(this.sharedData.currentPromptlet);
    
    // Refresh every 5 seconds
    //const source = interval(5000);
    //this.subscription = source.subscribe(val => this.handleResults(this.sharedData.currentPromptlet));
  }
  
  ngOnDestroy(): void {
    //this.subscription.unsubscribe();
  }
  
  dataChanged(data: SharedData): void {
    this.sharedData = data;
    this.handleResults(this.sharedData.currentPromptlet);
  }
  
  handleResults(promptlet: Promptlet): void {
    console.log('called');
    
    this.processedResults = []
    switch (promptlet.promptlet_type) {
      case "MULTI_CHOICE":
      case "MULTI_RESPONSE":
        this.visible = true;
        this.handleResultsMulti(promptlet);
      case "SLIDER":
        this.visible = true;
        this.handleResultsSlider(promptlet, 0, 100, 10);
    }
  }
  
  handleResultsMulti(promptlet: Promptlet): void {
    if (promptlet) {
      for (var answer of promptlet.answerPool) {
        this.processedResults.push({"name": answer, "value": 0});
      }
      
      for (var response of promptlet.userResponses) {
        for (var r of response.response) {
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