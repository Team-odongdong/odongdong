export const timeToHourAndMinute = (time: string) => {
  const regexp = /([01]?[0-9]|2[0-3]):[0-5][0-9]?/g;

  return time.match(regexp)?.[0];
};
