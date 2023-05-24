import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-distance-filter',
  templateUrl: './distance-filter.component.html',
  styleUrls: ['./distance-filter.component.scss'],
})
export class DistanceFilterComponent implements OnInit {
  @Output() filterValue = new EventEmitter<{ value: string }>();

  public currentFilterValue = '1km';
  public distanceOptions = ['500m', '1km', '2km'];

  constructor() {}

  ngOnInit() {}

  filterChanged(event: any) {
    this.filterValue.emit({ value: event.target?.value });
  }
}
