import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ThreadModalComponent } from './thread-modal.component';

describe('ThreadModalComponent', () => {
  let component: ThreadModalComponent;
  let fixture: ComponentFixture<ThreadModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ThreadModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ThreadModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
