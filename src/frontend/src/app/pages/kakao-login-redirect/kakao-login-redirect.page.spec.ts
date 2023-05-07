import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { KakaoLoginRedirectPage } from './kakao-login-redirect.page';

describe('KakaoLoginRedirectPage', () => {
  let component: KakaoLoginRedirectPage;
  let fixture: ComponentFixture<KakaoLoginRedirectPage>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ KakaoLoginRedirectPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(KakaoLoginRedirectPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
