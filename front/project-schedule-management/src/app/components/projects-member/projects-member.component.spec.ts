import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectsMemberComponent } from './projects-member.component';

describe('ProjectsMemberComponent', () => {
  let component: ProjectsMemberComponent;
  let fixture: ComponentFixture<ProjectsMemberComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProjectsMemberComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProjectsMemberComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
