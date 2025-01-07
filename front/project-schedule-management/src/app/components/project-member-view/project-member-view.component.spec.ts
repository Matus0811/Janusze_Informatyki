import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectMemberViewComponent } from './project-member-view.component';

describe('ProjectMemberViewComponent', () => {
  let component: ProjectMemberViewComponent;
  let fixture: ComponentFixture<ProjectMemberViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProjectMemberViewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProjectMemberViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
