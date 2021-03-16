import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserResponseListComponent } from './user-response-list.component';

describe('UserResponseListComponent', () => {
  let component: UserResponseListComponent;
  let fixture: ComponentFixture<UserResponseListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserResponseListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserResponseListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
