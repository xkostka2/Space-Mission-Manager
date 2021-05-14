import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyMissionsPageComponent } from './my-missions-page.component';

describe('MyMissionsPageComponent', () => {
  let component: MyMissionsPageComponent;
  let fixture: ComponentFixture<MyMissionsPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyMissionsPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyMissionsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
