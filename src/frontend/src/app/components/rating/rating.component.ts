import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  Output,
  QueryList,
  ViewChildren,
} from '@angular/core';
import { Rating } from 'src/app/types/rating';

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.scss'],
})
export class RatingComponent implements AfterViewInit {
  @ViewChildren('star') starRef: QueryList<HTMLElement>;

  @Input() readonly: boolean;
  @Input() starSize: string;
  @Input() rating: Rating;

  @Output() ratingChanged = new EventEmitter<{ rate: number }>();

  public stars = [0, 0, 0, 0, 0];

  constructor(private changeDetectorRef: ChangeDetectorRef) {}

  ngAfterViewInit() {
    this.updateRate(this.rating - 1, true);
    this.setStarSize();
  }

  updateRate(idx: number, initial?: boolean) {
    this.rating = Math.ceil(idx + 1) as Rating;
    this.setStars(this.rating);

    this.changeDetectorRef.detectChanges();
    this.setStarSize();

    if (!initial) {
      this.ratingChanged.emit({ rate: this.rating });
    }
  }

  setStars(rating: Rating) {
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

  setStarSize() {
    this.starRef.map((starEl: any) =>
      starEl['el'].style.setProperty('width', this.starSize)
    );
  }
}
