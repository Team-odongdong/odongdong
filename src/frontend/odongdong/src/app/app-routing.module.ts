import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
    {
        path: '',
        loadChildren: () => import('./tabs/tabs.module').then((m) => m.TabsPageModule),
    },
    {
        path: 'oauth2/redirect',
        loadChildren: () =>
            import('./pages/kakao-login-redirect/kakao-login-redirect.module').then(
                (m) => m.KakaoLoginRedirectPageModule,
            ),
    },
    {
        path: 'edit-bathroom/:id',
        loadChildren: () =>
            import('./pages/edit-bathroom/edit-bathroom.module').then(
                (m) => m.EditBathroomPageModule,
            ),
    },
];
@NgModule({
    imports: [RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })],
    exports: [RouterModule],
})
export class AppRoutingModule {}
