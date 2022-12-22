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
        this.rating = idx + 1;

        for (let i = 0; i <= idx; i++) {
            this.stars[i] = 1;
        }
        for (let i = idx + 1; i < 5; i++) {
            this.stars[i] = 0;
        }

        this.changeDetectorRef.detectChanges();
        this.setStarSize();

        if (!initial) {
            this.ratingChanged.emit({ rate: this.rating });
        }
    }

    /* eslint-disable */
    setStarSize() {
        this.starRef.map((starEl) => starEl['el'].style.setProperty('width', this.starSize));
    }
    /* eslint-enable */
}
