import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EditBathroomPage } from './edit-bathroom.page';

describe('EditBathroomPage', () => {
  let component: EditBathroomPage;
  let fixture: ComponentFixture<EditBathroomPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(EditBathroomPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
