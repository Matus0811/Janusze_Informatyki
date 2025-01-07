import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectStatusDetailsComponent } from './project-status-details.component';

describe('ProjectStatusDetailsComponent', () => {
  let component: ProjectStatusDetailsComponent;
  let fixture: ComponentFixture<ProjectStatusDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProjectStatusDetailsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProjectStatusDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
