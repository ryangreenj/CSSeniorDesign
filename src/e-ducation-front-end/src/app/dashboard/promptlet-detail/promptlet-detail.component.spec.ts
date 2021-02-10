import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PromptletDetailComponent } from './promptlet-detail.component';

describe('PromptletDetailComponent', () => {
  let component: PromptletDetailComponent;
  let fixture: ComponentFixture<PromptletDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PromptletDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PromptletDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
