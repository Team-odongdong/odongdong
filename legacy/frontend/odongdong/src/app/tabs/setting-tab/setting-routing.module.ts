import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SettingPage } from './setting.page';

const routes: Routes = [
  {
    path: '',
    component: SettingPage,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SettingPageRoutingModule {}