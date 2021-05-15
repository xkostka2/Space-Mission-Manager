import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MissionDetailPageComponent } from './mission-detail-page.component';

describe('MissionDetailPageComponent', () => {
  let component: MissionDetailPageComponent;
  let fixture: ComponentFixture<MissionDetailPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MissionDetailPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MissionDetailPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
