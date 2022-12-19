import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { TimeService } from 'src/app/services/time/time-service';

@Component({
    selector: 'app-time-picker',
    templateUrl: './time-picker.component.html',
    styleUrls: ['./time-picker.component.scss'],
})
export class TimePickerComponent implements OnInit {
    @Output() startTime = new EventEmitter<{ time: string }>();
    @Output() endTime = new EventEmitter<{ time: string }>();

    public currentStartTime;
    public currentEndTime;

    constructor(public timeService: TimeService) {}

    ngOnInit() {}

    onChangeStartTime(time) {
        const filteredStartTime = this.timeService.formatTimeToHourAndMinute(time.target.value);
        this.startTime.emit({ time: filteredStartTime });
    }

    onChangeEndTime(time) {
        const filteredEndTime = this.timeService.formatTimeToHourAndMinute(time.target.value);
        this.endTime.emit({ time: filteredEndTime });
    }
}
