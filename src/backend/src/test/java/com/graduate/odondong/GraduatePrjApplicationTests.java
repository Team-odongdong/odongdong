package com.graduate.odondong;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.dto.CoordinateInfoDto;
import com.graduate.odondong.service.BathroomService.BathroomService;
import com.graduate.odondong.service.BathroomService.BulkInsertPublicBathroom;
import com.graduate.odondong.util.ReverseGeocoding.ChangeByGeocoderKakao;
import com.graduate.odondong.util.ReverseGeocoding.ChangeByGeocoderNaver;
import com.graduate.odondong.util.type.BulkInsertEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class GraduatePrjApplicationTests {

	@Autowired
	ChangeByGeocoderKakao changeByGeocoder;
	@Autowired
	BulkInsertPublicBathroom bulkInsertPublicBathroom;
	@Autowired
	BathroomService bathroomService;

	@Autowired
	ChangeByGeocoderNaver changeByGeocoderNaver;

	@Test
	void 테스트() {
		List<Bathroom> kmByLongitudeLatitude = bathroomService.get1kmByLongitudeLatitude(126.966182351829, 37.5911968710618);
		return;
	}

	@Test
	void naver지오코딩테스트() {
		System.out.println(LocalTime.now());
		CoordinateInfoDto addressByCoordinate1 = changeByGeocoder.getAddressByCoordinate(126.91298945203502,37.574952579149084 );
		System.out.println(LocalTime.now());
		CoordinateInfoDto addressByCoordinate = changeByGeocoderNaver.getAddressByCoordinate(126.91298945203502,37.574952579149084 );
		System.out.println(LocalTime.now());
		return;
	}
	
	@Test
	void 벌크인서트() {
		bulkInsertPublicBathroom.BulkInsert("12_04_01_E_공중화장실정보.xlsx", new HashMap<>() {{
//		bulkInsertPublicBathroom.BulkInsert("test.xlsx", new HashMap<>() {{
			put("화장실명", BulkInsertEnum.title);
			put("소재지도로명주소", BulkInsertEnum.address);
			put("소재지지번주소", BulkInsertEnum.addressDetail);
			put("남녀공용화장실여부", BulkInsertEnum.isUnisex);
			put("개방시간", BulkInsertEnum.operationTime);
		}});
	}
}
