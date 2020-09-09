import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApiManageComponent } from './api-manage.component';

describe('ApiManageComponent', () => {
  let component: ApiManageComponent;
  let fixture: ComponentFixture<ApiManageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ApiManageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ApiManageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
