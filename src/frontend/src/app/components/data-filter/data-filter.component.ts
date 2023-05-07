import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
    selector: 'app-data-filter',
    templateUrl: './data-filter.component.html',
    styleUrls: ['./data-filter.component.scss'],
})
export class DataFilterComponent implements OnInit {
    @Output() filterValue = new EventEmitter<{ value: string }>();

    public currentFilterValue = 'bathroom';

    constructor() {}

    ngOnInit() {}

    filterChanged(event) {
        this.filterValue.emit({ value: event.target.value });
    }
}
