import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { KakaoLoginRedirectPageRoutingModule } from './kakao-login-redirect-routing.module';

import { KakaoLoginRedirectPage } from './kakao-login-redirect.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    KakaoLoginRedirectPageRoutingModule
  ],
  declarations: [KakaoLoginRedirectPage]
})
export class KakaoLoginRedirectPageModule {}
