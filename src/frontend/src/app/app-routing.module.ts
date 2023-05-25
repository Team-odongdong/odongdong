import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { RegisteredBathroomItemComponent } from './components/registered-bathroom-item/registered-bathroom-item.component';

const routes: Routes = [
  {
    path: '',
    loadChildren: () =>
      import('./tabs/tabs.module').then((m) => m.TabsPageModule),
  },
  {
    path: 'edit-bathroom',
    loadChildren: () =>
      import('./pages/edit-bathroom/edit-bathroom.module').then(
        (m) => m.EditBathroomPageModule
      ),
  },
  {
    path: 'registered-list',
    loadChildren: () =>
      import('./pages/registered-list/registered-list.module').then(
        (m) => m.RegisteredListPageModule
      ),
  },
];
@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules }),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
