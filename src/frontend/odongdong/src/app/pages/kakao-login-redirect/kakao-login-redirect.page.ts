import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { LoginService } from 'src/app/services/auth/login-service';

@Component({
  selector: 'app-kakao-login-redirect',
  templateUrl: './kakao-login-redirect.page.html',
  styleUrls: ['./kakao-login-redirect.page.scss'],
}) 
export class KakaoLoginRedirectPage implements OnInit {

  public redirectUrl: string;
  public kakaoUserKey: string;

  constructor(
    public router: Router,
    public loginService: LoginService,
  ) { }

  ngOnInit() {
    this.redirectUrl = this.router.url;

    this.kakaoUserKey = this.redirectUrl.split('=')[1];
  }

  ionViewDidEnter() {
    this.setCookie("JSESSIONID", this.redirectUrl, 60);
  }

  setCookie(cookie_name, value, miuntes) {
    const exdate = new Date();
    exdate.setMinutes(exdate.getMinutes() + miuntes);
    const cookie_value = escape(value) + ((miuntes == null) ? '' : '; expires=' + exdate.toUTCString()) + "; path=/;";
    document.cookie = cookie_name + '=' + cookie_value;
  }

  // cookieTest() {
  //   this.loginService.cookieTest(this.temp);
  // }
}
