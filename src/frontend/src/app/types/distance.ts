export type DistanceOptions = '500m' | '1km' | '2km';
export type DistanceOptionValues = 0.5 | 1 | 2;

export const Distance: Record<DistanceOptions, DistanceOptionValues> = {
  '500m': 0.5,
  '1km': 1,
  '2km': 2,
};
Object.freeze(Distance);
