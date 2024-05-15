import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUsersToTaskComponent } from './add-users-to-task.component';

describe('AddUsersToTaskComponent', () => {
  let component: AddUsersToTaskComponent;
  let fixture: ComponentFixture<AddUsersToTaskComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddUsersToTaskComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddUsersToTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
