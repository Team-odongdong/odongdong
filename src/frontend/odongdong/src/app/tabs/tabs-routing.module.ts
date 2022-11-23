import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { environment } from 'src/environments/environment';
import { ProfilePage } from './profile-tab/profile.page';
import { TabsPage } from './tabs.page';

const routes: Routes = [
  {
    path: 'tabs',
    component: TabsPage,
    children: [
      {
        path: 'main',
        loadChildren: () => import('./main-tab/main.module').then(m => m.MainPageModule)
      },
      {
        path: 'profile',
        loadChildren: () => import('./profile-tab/profile.module').then(m => m.ProfilePageModule)
      },
      {
        path: 'setting',
        loadChildren: () => import('./setting-tab/setting.module').then(m => m.SettingPageModule)
      },
      {
        path: '',
        redirectTo: '/tabs/main',
        pathMatch: 'full'
      }
    ]
  },
  {
    path: '',
    redirectTo: '/tabs/main',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
})
export class TabsPageRoutingModule {}
