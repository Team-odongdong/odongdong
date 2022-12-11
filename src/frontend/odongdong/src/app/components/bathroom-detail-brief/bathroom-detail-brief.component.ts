import { Component, Input, OnInit } from '@angular/core';

@Component({
    selector: 'app-bathroom-detail-brief',
    templateUrl: './bathroom-detail-brief.component.html',
    styleUrls: ['./bathroom-detail-brief.component.scss'],
})
export class BathroomDetailBriefComponent implements OnInit {
    @Input() imageUrl: string;

    constructor() {}

    ngOnInit() {}
}
