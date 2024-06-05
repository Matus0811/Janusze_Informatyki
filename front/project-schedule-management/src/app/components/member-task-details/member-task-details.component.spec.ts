import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MemberTaskDetailsComponent } from './member-task-details.component';

describe('MemberTaskDetailsComponent', () => {
  let component: MemberTaskDetailsComponent;
  let fixture: ComponentFixture<MemberTaskDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MemberTaskDetailsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MemberTaskDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
