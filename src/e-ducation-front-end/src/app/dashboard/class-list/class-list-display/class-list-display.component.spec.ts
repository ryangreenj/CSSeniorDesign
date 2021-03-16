import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassListDisplayComponent } from './class-list-display.component';

describe('ClassListDisplayComponent', () => {
  let component: ClassListDisplayComponent;
  let fixture: ComponentFixture<ClassListDisplayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClassListDisplayComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClassListDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
