import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MessageHandlerComponent } from './message-handler.component';

describe('MessageHandlerComponent', () => {
  let component: MessageHandlerComponent;
  let fixture: ComponentFixture<MessageHandlerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MessageHandlerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MessageHandlerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
