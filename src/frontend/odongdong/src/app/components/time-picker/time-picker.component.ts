import { Component, OnInit } from '@angular/core';
import { TimeService } from 'src/app/services/time/time-service';

@Component({
    selector: 'app-time-picker',
    templateUrl: './time-picker.component.html',
    styleUrls: ['./time-picker.component.scss'],
})
export class TimePickerComponent implements OnInit {
    public startTime;
    public endTime;

    constructor(public timeService: TimeService) {}

    ngOnInit() {}

    onChangeStartTime(time) {
        this.startTime = time.target.value;
        console.log('st', this.startTime);
    }

    onChangeEndTime(time) {
        this.endTime = time.target.value;
        console.log('et', this.endTime, this.timeService.formatTimeToHourAndMinute(this.endTime));
    }

    clic() {
        console.log(this.startTime, this.endTime);
    }
}
