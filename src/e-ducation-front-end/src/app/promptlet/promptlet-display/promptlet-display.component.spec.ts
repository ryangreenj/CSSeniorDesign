import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PromptletDisplayComponent } from './promptlet-display.component';

describe('PromptletDisplayComponent', () => {
  let component: PromptletDisplayComponent;
  let fixture: ComponentFixture<PromptletDisplayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PromptletDisplayComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PromptletDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
