import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RightAsideComponent } from './right-aside.component';

describe('RightAsideComponent', () => {
  let component: RightAsideComponent;
  let fixture: ComponentFixture<RightAsideComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RightAsideComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RightAsideComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
