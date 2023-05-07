import {
    Input,
    Output,
    QueryList,
    Component,
    ViewChildren,
    EventEmitter,
    AfterViewInit,
    ChangeDetectorRef,
} from '@angular/core';

@Component({
    selector: 'app-rating-component',
    templateUrl: './rating-component.component.html',
    styleUrls: ['./rating-component.component.scss'],
})
export class RatingComponentComponent implements AfterViewInit {
    @ViewChildren('star') starRef: QueryList<HTMLElement>;

    @Input() readonly: boolean;
    @Input() starSize: string;
    @Input() rating: number;

    @Output() ratingChanged = new EventEmitter<{ rate: number }>();

    public stars = [0, 0, 0, 0, 0];

    constructor(public changeDetectorRef: ChangeDetectorRef) {}

    ngAfterViewInit() {
        this.updateRate(this.rating - 1, true);
        this.setStarSize();
    }

    updateRate(idx: number, initial?: boolean) {
        this.rating = Math.ceil(idx + 1);
        this.setStars(this.rating);

        this.changeDetectorRef.detectChanges();
        this.setStarSize();

        if (!initial) {
            this.ratingChanged.emit({ rate: this.rating });
        }
    }

    setStars(rating: number) {
        const starsMap = {
            0: [0, 0, 0, 0, 0],
            1: [1, 0, 0, 0, 0],
            2: [1, 1, 0, 0, 0],
            3: [1, 1, 1, 0, 0],
            4: [1, 1, 1, 1, 0],
            5: [1, 1, 1, 1, 1],
        };

        this.stars = starsMap[rating];
    }

    /* eslint-disable */
    setStarSize() {
        this.starRef.map((starEl) => starEl['el'].style.setProperty('width', this.starSize));
    }
    /* eslint-enable */
}
