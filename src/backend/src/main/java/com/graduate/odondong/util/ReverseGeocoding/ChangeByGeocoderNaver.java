package com.graduate.odondong.util.ReverseGeocoding;

import java.util.function.Consumer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.graduate.odondong.dto.CoordinateInfoDto;

@Service
public class ChangeByGeocoderNaver implements ReverseGeocoding {

	@Value("${naver.REST_API_KEY}")
	private String REST_API_KEY;

	@Value("${naver.REST_API_KEY_ID}")
	private String REST_API_KEY_ID;

	@Override
	public CoordinateInfoDto getAddressByCoordinate(Double x, Double y) {
		String result = getResultByNaverReverseGeoCoding(x, y);
		try {
			return ExtractCoordinateByJson(result);
		} catch (JSONException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String getResultByNaverReverseGeoCoding(Double x, Double y) {
		String urlString = String.format(
			"https://naveropenapi.apigw.ntruss.com:443/map-reversegeocode/v2/gc?coords=%s,%s&output=json",
			x, y);
		WebClient webClient = WebClient.builder().baseUrl(urlString).defaultHeaders(httpHeaders -> {
			httpHeaders.add("X-NCP-APIGW-API-KEY-ID", REST_API_KEY_ID);
			httpHeaders.add("X-NCP-APIGW-API-KEY", REST_API_KEY);
		}).build();
		String result = webClient.get()
			.uri("")
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.bodyToMono(String.class)
			.block();
		return result;
	}

	private CoordinateInfoDto ExtractCoordinateByJson(String result) throws Exception {

		JSONObject jsonObject = new JSONObject(result);
		if ((int)jsonObject.getJSONObject("status").get("code") != 0)
			throw new Exception("잘못된 NAVER_REST_API 요청");
		JSONArray results = jsonObject.getJSONArray("results");
		JSONObject region = results.getJSONObject(0).getJSONObject("region");
		String city  = (String) region.getJSONObject("area1").get("name");
		String county  = (String) region.getJSONObject("area2").get("name");
		String dong = (String) region.getJSONObject("area3").get("name");
		String detail = (String) region.getJSONObject("area4").get("name");
		return CoordinateInfoDto.builder()
			.address_name(String.format("%s %s %s %s", city, county, dong, detail))
			.build();
	}
}
