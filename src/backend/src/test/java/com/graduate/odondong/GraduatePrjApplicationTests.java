package com.graduate.odondong;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.dto.CoordinateInfoDto;
import com.graduate.odondong.service.BathroomService.BathroomService;
import com.graduate.odondong.service.BathroomService.BulkInsertPublicBathroom;
import com.graduate.odondong.util.ReverseGeocoding.ChangeByGeocoderKakao;
import com.graduate.odondong.util.ReverseGeocoding.ChangeByGeocoderNaver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
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

//	@Test
//	void 테스트() {
//		List<Bathroom> kmByLongitudeLatitude = bathroomService.get1kmByLongitudeLatitude(126.966182351829, 37.5911968710618);
//		return;
//	}

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
