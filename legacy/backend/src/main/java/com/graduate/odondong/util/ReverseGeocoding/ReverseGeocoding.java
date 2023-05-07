package com.graduate.odondong.util.ReverseGeocoding;

import org.json.JSONException;

import com.graduate.odondong.dto.CoordinateInfoDto;

public interface ReverseGeocoding {
	CoordinateInfoDto getAddressByCoordinate(Double x, Double y);
}
