import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AstronautDetailPageComponent } from './astronaut-detail-page.component';

describe('AstronautDetailPageComponent', () => {
  let component: AstronautDetailPageComponent;
  let fixture: ComponentFixture<AstronautDetailPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AstronautDetailPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AstronautDetailPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
