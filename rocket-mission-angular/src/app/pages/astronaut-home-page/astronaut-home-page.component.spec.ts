import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AstronautHomePageComponent } from './astronaut-home-page.component';

describe('AstronautHomePageComponent', () => {
  let component: AstronautHomePageComponent;
  let fixture: ComponentFixture<AstronautHomePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AstronautHomePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AstronautHomePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
