import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectBugsComponent } from './project-bugs.component';

describe('ProjectBugsComponent', () => {
  let component: ProjectBugsComponent;
  let fixture: ComponentFixture<ProjectBugsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProjectBugsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProjectBugsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
