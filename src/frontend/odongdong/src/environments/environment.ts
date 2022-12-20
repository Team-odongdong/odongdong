// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
    production: false,
    appVersion: {
        name: '1.3.0',
        major: 1,
        minor: 3,
        build: 0,
    },

    // apiUrl: 'http://localhost:9003',
    // apiUrl: 'http://13.125.245.177:9003',
    // apiUrl: 'http://odongdong.site:9003',
    // apiUrl: 'https://odongdong.site',
    apiUrl: 'https://prod.odongdong.site',
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
