package com.graduate.odondong;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.service.BathroomService.BathroomService;
import com.graduate.odondong.service.BathroomService.BulkInsertPublicBathroom;
import com.graduate.odondong.util.ChangeByGeocoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class GraduatePrjApplicationTests {

	@Autowired
	ChangeByGeocoder changeByGeocoder;
	@Autowired
	BulkInsertPublicBathroom bulkInsertPublicBathroom;
	@Autowired
	BathroomService bathroomService;

	@Test
	void 테스트() {
		List<Bathroom> kmByLongitudeLatitude = bathroomService.get1kmByLongitudeLatitude(126.966182351829, 37.5911968710618);
		return;
	}
	
	@Test
	void 테스트2() {
		bulkInsertPublicBathroom.BulkInsert("12_04_01_E_공중화장실정보.xlsx");
	}

}
