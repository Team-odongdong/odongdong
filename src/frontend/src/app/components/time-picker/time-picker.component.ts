import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { timeToHourAndMinute } from 'src/app/utils/formatting';

@Component({
  selector: 'app-time-picker',
  templateUrl: './time-picker.component.html',
  styleUrls: ['./time-picker.component.scss'],
})
export class TimePickerComponent implements OnInit {
  @Output() startTime = new EventEmitter<{ time: string }>();
  @Output() endTime = new EventEmitter<{ time: string }>();

  constructor() {}

  ngOnInit() {}

  onChangeStartTime(time: any) {
    const filteredStartTime = timeToHourAndMinute(time.target.value) ?? '';

    this.startTime.emit({ time: filteredStartTime });
  }

  onChangeEndTime(time: any) {
    const filteredEndTime = timeToHourAndMinute(time.target.value) ?? '';

    this.endTime.emit({ time: filteredEndTime });
  }
}
