import { IonicModule } from '@ionic/angular';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProfilePage } from './profile.page';

import { ProfilePageRoutingModule } from './profile-routing.module';
import { SocialLoginComponent } from 'src/app/modals/social-login/social-login.component';
import { SocialLoginComponentModule } from 'src/app/modals/social-login/social-login.module';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ProfilePageRoutingModule,
    SocialLoginComponentModule
  ],
  declarations: [ProfilePage],
  entryComponents: [SocialLoginComponent]
})
export class ProfilePageModule {}