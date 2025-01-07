import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskBugsComponent } from './task-bugs.component';

describe('TaskBugsComponent', () => {
  let component: TaskBugsComponent;
  let fixture: ComponentFixture<TaskBugsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TaskBugsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TaskBugsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
