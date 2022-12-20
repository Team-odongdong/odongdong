import { Component, OnInit } from '@angular/core';
import { ModalController, NavController } from '@ionic/angular';
import { SocialLoginComponent } from 'src/app/modals/social-login/social-login.component';
import { LoginService } from 'src/app/services/auth/login-service';
import { ImageService } from 'src/app/services/image/image-service';

@Component({
    selector: 'app-profile',
    templateUrl: 'profile.page.html',
    styleUrls: ['profile.page.scss'],
})
export class ProfilePage implements OnInit {
    public userName: string;
    public userImage: string;
    public userEmail: string;

    constructor(
        public loginService: LoginService,
        public imageService: ImageService,
        public navController: NavController,
        public modalController: ModalController,
    ) {}

    ngOnInit(): void {}

    ionViewDidEnter() {
        this.getProfile();
    }

    onClickBackButton() {
        this.navController.navigateBack('/tabs/main');
    }

    async openSocialLogin() {
        const modal = await this.modalController.create({
            component: SocialLoginComponent,
        });
        await modal.present();
    }

    async getProfile() {
        try {
            const { data } = await this.loginService.getUserProfile();

            if (data.code === 1000) {
                await this.setProfile(data.result);
            } else {
                this.openSocialLogin();
            }
        } catch (error) {
            this.openSocialLogin();
        }
    }

    async setProfile(userData) {
        this.userName = userData.name;
        this.userEmail = userData.email;
        this.userImage = await this.imageService.httpToHttps(userData.picture);
    }

    goToRegisterList() {
        this.navController.navigateForward('/register-list');
    }
}
