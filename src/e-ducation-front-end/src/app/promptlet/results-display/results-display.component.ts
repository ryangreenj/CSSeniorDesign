import { Component, Input, OnInit } from '@angular/core';
import { Promptlet } from 'src/app/data.service';

@Component({
  selector: 'app-results-display',
  templateUrl: './results-display.component.html',
  styleUrls: ['./results-display.component.css']
})
export class ResultsDisplayComponent implements OnInit {
  
  @Input() promptlet: Promptlet;
  visible: boolean = false;
  
  processedResults: Result[] = [];
  
  constructor() { }

  ngOnInit(): void {
    this.visible = this.promptlet.promptlet_type == "MULTI_CHOICE"
    this.processResults();
  }
  
  processResults(): void {
    for (var answer of this.promptlet.answerPool) {
      this.processedResults.push({"name": answer, "value": 0});
    }
    console.log(this.processedResults);
    for (var response of this.promptlet.userResponses) {
      this.processedResults.find(element => element.name == response.response[0]).value += 1;
    }
  }

}

interface Result {
  name: string;
  value: number;
};