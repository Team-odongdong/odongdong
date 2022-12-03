import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { KakaoLoginRedirectPage } from './kakao-login-redirect.page';

const routes: Routes = [
  {
    path: '',
    component: KakaoLoginRedirectPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class KakaoLoginRedirectPageRoutingModule {}
