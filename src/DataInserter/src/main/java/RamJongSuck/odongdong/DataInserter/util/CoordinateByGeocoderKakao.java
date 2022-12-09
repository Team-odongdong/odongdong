package RamJongSuck.odongdong.DataInserter.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import RamJongSuck.odongdong.DataInserter.dto.AddressDetailInfoDto;
import RamJongSuck.odongdong.DataInserter.dto.AddressInfoDto;

@Component
public class CoordinateByGeocoderKakao {

	@Value("${kakao.REST_API_KEY}")
	private String REST_API_KEY;

	public AddressInfoDto getCoordinateByAddress(String address) throws JSONException {
		String urlString = String.format(
			"https://dapi.kakao.com/v2/local/search/address.json?analyze_type=similar&page=1&size=10&query=%s",
			address);
		WebClient webClient = WebClient.builder()
			.baseUrl(urlString)
			.defaultHeader("Authorization", "KakaoAK " + REST_API_KEY)
			.build();
		String result = webClient.get()
			.uri("")
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.bodyToMono(String.class)
			.block();
		JSONObject jsonObject = new JSONObject(result);
		if (jsonObject.getJSONArray("documents").isNull(0)) {
			AddressInfoDto nullAddressInfoDto = AddressInfoDto.builder()
				.address(address)
				.address_detail("")
				.longitude(0.0)
				.latitude(0.0)
				.build();
			return nullAddressInfoDto;
		}
		JSONObject document = jsonObject.getJSONArray("documents").getJSONObject(0);
		AddressInfoDto addressInfoDto = getAddressInfoDto(document);

		return addressInfoDto;
	}

	public AddressDetailInfoDto getAddressByCoordinate(Double x, Double y) {
		String urlString = String.format("https://dapi.kakao.com/v2/local/geo/coord2address.json?x=%s&y=%s", x, y);
		WebClient webClient = WebClient.builder()
			.baseUrl(urlString)
			.defaultHeader("Authorization", "KakaoAK " + REST_API_KEY)
			.build();
		String result = webClient.get()
			.uri("")
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.bodyToMono(String.class)
			.block();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(result);
			if (jsonObject.getJSONArray("documents").isNull(0)) {
				AddressDetailInfoDto nullAddressDetailInfoDto = AddressDetailInfoDto.builder().build();
				return nullAddressDetailInfoDto;
			}
			JSONObject document = jsonObject.getJSONArray("documents").getJSONObject(0);
			if (document.isNull("road_address")) {
				AddressDetailInfoDto nullAddressDetailInfoDto = AddressDetailInfoDto.builder().build();
				return nullAddressDetailInfoDto;
			}
			AddressDetailInfoDto addressDetailInfoDto = getCoordinateInfoDto(document);
			return addressDetailInfoDto;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	private AddressDetailInfoDto getCoordinateInfoDto(JSONObject document) throws JSONException {
		JSONObject road_address = document.getJSONObject("road_address");
		String address_name = (String)road_address.get("address_name");
		String address_detail = (String)road_address.get("building_name");
		AddressDetailInfoDto nullAddressDetailInfoDto = AddressDetailInfoDto.builder()
			.address_name(address_name)
			.address_detail(address_detail)
			.build();
		return nullAddressDetailInfoDto;
	}

	private AddressInfoDto getAddressInfoDto(JSONObject document) throws JSONException {
		Double longitude = Double.valueOf(((String)document.get("x")));
		Double latitude = Double.valueOf(((String)document.get("y")));
		String address_name = (String)document.get("address_name");
		String address_detail = null;
		if (!document.isNull("road_address")) {
			JSONObject road_address = document.getJSONObject("road_address");
			address_detail = (String)road_address.get("building_name");
		}
		AddressInfoDto addressInfoDto = AddressInfoDto.builder()
			.latitude(latitude)
			.longitude(longitude)
			.address(address_name)
			.address_detail(address_detail)
			.build();
		return addressInfoDto;
	}
}
