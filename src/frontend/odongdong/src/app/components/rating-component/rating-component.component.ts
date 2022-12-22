import { Component, Input, OnInit } from '@angular/core';

@Component({
    selector: 'app-rating-component',
    templateUrl: './rating-component.component.html',
    styleUrls: ['./rating-component.component.scss'],
})
export class RatingComponentComponent implements OnInit {
    @Input() readonly: boolean;
    @Input() rating: number;

    public stars = [0, 0, 0, 0, 0];

    constructor() {}

    ngOnInit() {
        this.readonly = false;
    }

    onRatingChange(idx: number) {
        this.rating = idx + 1;

        this.stars = [0, 0, 0, 0, 0];
        for (let i = 0; i <= idx; i++) {
            this.stars[i] = 1;
        }
    }
}
