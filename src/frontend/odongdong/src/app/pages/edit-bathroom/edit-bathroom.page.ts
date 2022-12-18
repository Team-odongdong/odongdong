import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';

@Component({
    selector: 'app-edit-bathroom',
    templateUrl: './edit-bathroom.page.html',
    styleUrls: ['./edit-bathroom.page.scss'],
})
export class EditBathroomPage implements OnInit {
    constructor(public navController: NavController) {}

    ngOnInit() {}

    goBack() {
        this.navController.navigateBack('/tabs/main');
    }
}
