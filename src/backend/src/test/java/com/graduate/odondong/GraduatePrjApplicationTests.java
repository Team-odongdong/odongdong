package com.graduate.odondong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.graduate.odondong.service.BathroomService.BathroomService;
import com.graduate.odondong.service.BathroomService.BulkInsertPublicBathroom;
import com.graduate.odondong.util.ReverseGeocoding.ChangeByGeocoderKakao;
import com.graduate.odondong.util.ReverseGeocoding.ChangeByGeocoderNaver;

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

	// @Test
	// void 테스트() {
	// 	Map<String, BulkInsertEnum> bulkInsertEnumMap = new HashMap<>();
	// 	bulkInsertEnumMap.put("화장실명", BulkInsertEnum.title);
	// 	bulkInsertEnumMap.put("소재지도로명주소", BulkInsertEnum.address);
	// 	bulkInsertEnumMap.put("남녀공용화장실여부", BulkInsertEnum.isUnisex);
	// 	bulkInsertEnumMap.put("소재지지번주소", BulkInsertEnum.addressDetail);
	// 	bulkInsertEnumMap.put("개방시간", BulkInsertEnum.operationTime);
	//
	//
	// 	bulkInsertPublicBathroom.BulkInsert("경기도공중화장실현황(개방표준).xlsx", bulkInsertEnumMap);
	// }

// 	@Test
// 	void naver지오코딩테스트() {
// 		System.out.println(LocalTime.now());
// 		CoordinateInfoDto addressByCoordinate1 = changeByGeocoder.getAddressByCoordinate(126.91298945203502,37.574952579149084 );
// 		System.out.println(LocalTime.now());
// 		CoordinateInfoDto addressByCoordinate = changeByGeocoderNaver.getAddressByCoordinate(126.91298945203502,37.574952579149084 );
// 		System.out.println(LocalTime.now());
// 		return;
// 	}
	
}
