declare let naver: any;

export const createMarkerImage = (
  markerImage: string,
  width: number,
  height: number
) => {
  return {
    url: markerImage,
    scaledSize: new naver.maps.Size(width, height),
  };
};

export const deleteAllMarkers = (markerList: any[]) => {
  markerList.forEach((marker) => {
    marker.setMap(null);
  });
};

export const setCameraMovement = (level: number) => {
  const levels = [
    100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 0.05, 0.03,
    0.01, 0.0055, 0.003, 0.0015, 0.0008, 0.0004, 0.0002, 0.0001,
  ];

  return levels[level];
};
