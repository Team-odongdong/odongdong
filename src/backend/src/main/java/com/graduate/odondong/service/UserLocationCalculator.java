package com.graduate.odondong.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.graduate.odondong.dto.BathroomResponseDto;
import com.graduate.odondong.dto.Point;

import lombok.Data;

@Data
public class UserLocationCalculator {

	private static final double EARTH_RADIUS = 6371.0;
	Point userPoint;
	double distance;

	public UserLocationCalculator(Point point, Double distance) {
		this.userPoint = point;
		this.distance = distance;
	}

	public double getLatitudeMinus() {
		return userPoint.getLatitude() - 0.0091 * distance;
	}

	public double getLatitudePlus() {
		return userPoint.getLatitude() + 0.0091 * distance;
	}

	public double getLongitudeMinus() {
		return userPoint.getLongitude() - 0.011269 * distance;
	}

	public double getLongitudePlus() {
		return userPoint.getLongitude() + 0.011269 * distance;
	}

	public List<BathroomResponseDto.BathroomInfo> calculateDistanceAndRemove(List<BathroomResponseDto.BathroomInfo> bathroomInfoList) {
		return bathroomInfoList.stream()
			.map((info) -> {
				double calculateDistance = calculateDistance(new Point(info.getLatitude(), info.getLongitude()));
				return calculateDistance <= distance ? info.withDistance(calculateDistance) : null;
			})
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}

	private double calculateDistance(Point targetPoint) {
		double dLat = Math.toRadians(targetPoint.getLatitude() - userPoint.getLatitude());
		double dLon = Math.toRadians(targetPoint.getLongitude() - userPoint.getLongitude());

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
			+ Math.cos(Math.toRadians(targetPoint.getLatitude())) * Math.cos(Math.toRadians(userPoint.getLatitude()))
			* Math.sin(dLon / 2) * Math.sin(dLon / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return EARTH_RADIUS * c;
	}
}
