import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RegisteredListPage } from './registered-list.page';

describe('RegisteredListPage', () => {
  let component: RegisteredListPage;
  let fixture: ComponentFixture<RegisteredListPage>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(RegisteredListPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
