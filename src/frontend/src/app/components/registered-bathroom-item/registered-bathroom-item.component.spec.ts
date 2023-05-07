import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { RegisteredBathroomItemComponent } from './registered-bathroom-item.component';

describe('RegisteredBathroomItemComponent', () => {
  let component: RegisteredBathroomItemComponent;
  let fixture: ComponentFixture<RegisteredBathroomItemComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ RegisteredBathroomItemComponent ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(RegisteredBathroomItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
