import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DecodeModalComponent } from './decode-modal.component';

describe('DecodeModalComponent', () => {
  let component: DecodeModalComponent;
  let fixture: ComponentFixture<DecodeModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DecodeModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DecodeModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
