import { Injectable } from '@angular/core';

declare let naver;

@Injectable({
  providedIn: 'root',
})
export class KakaoMapService {
  constructor() {}

  createMarkerImage(markerImage, width: number, height: number) {
    // const createdMarker = new naver.maps.ImageIcon(
    //   markerImage,
    //   new naver.maps.Size(width, height),
    //   {
    //     alt: alt ? alt : 'marker',
    //   }
    // );
    // return createdMarker;

    return {
      url: markerImage,
      scaledSize: new naver.maps.Size(width, height),
    };
  }

  deleteAllMarkers(markerList) {
    markerList.forEach((marker) => {
      marker.setMap(null);
    });
  }

  setCameraMovement(level: number) {
    const levels = [
      100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 0.05, 0.03, 0.01, 0.0055, 0.003,
      0.0015, 0.0008, 0.0004, 0.0002, 0.0001,
    ];

    return levels[level];
  }
}
