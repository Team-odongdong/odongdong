import { Injectable } from "@angular/core";

declare let kakao;

@Injectable({
    providedIn: 'root',
})
export class KakaoMapService {
    constructor() {}

    createMarkerImage(markerImage, width: number, height: number, alt?: string) {
        const createdMarker = new kakao.maps.MarkerImage(
            markerImage,
            new kakao.maps.Size(width, height),
            {
                alt: alt? alt: 'marker',
            }
        );

        return createdMarker;
    }

    deleteAllMarkers(markerList) {
        markerList.forEach((marker) => {
            marker.setMap(null);
        });
    }

    setCameraMovement(level: number) {
        const levels = [0.00035, 0.0007, 0.0013, 0.003, 0.005, 0.01];
    
        if(level > 7) {
            return 0.01;
        } else {
            return levels[level-1];
        }
    }

}