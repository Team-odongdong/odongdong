import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class TimeService {
    constructor() {}

    formatTimeToHourAndMinute(time) {
        const regexp = /([01]?[0-9]|2[0-3]):[0-5][0-9]?/g;

        return time.match(regexp)[0];
    }
}
