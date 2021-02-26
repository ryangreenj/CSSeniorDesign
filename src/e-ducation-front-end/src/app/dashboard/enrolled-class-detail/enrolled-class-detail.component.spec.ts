import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EnrolledClassDetailComponent } from './enrolled-class-detail.component';

describe('EnrolledClassDetailComponent', () => {
  let component: EnrolledClassDetailComponent;
  let fixture: ComponentFixture<EnrolledClassDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EnrolledClassDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EnrolledClassDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
