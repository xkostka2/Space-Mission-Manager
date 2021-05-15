import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AstronautsPageComponent } from './astronauts-page.component';

describe('AstronautsPageComponent', () => {
  let component: AstronautsPageComponent;
  let fixture: ComponentFixture<AstronautsPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AstronautsPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AstronautsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
