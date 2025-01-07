import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUserToProjectFormComponent } from './add-user-to-project-form.component';

describe('AddUserToProjectFormComponent', () => {
  let component: AddUserToProjectFormComponent;
  let fixture: ComponentFixture<AddUserToProjectFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddUserToProjectFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddUserToProjectFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
