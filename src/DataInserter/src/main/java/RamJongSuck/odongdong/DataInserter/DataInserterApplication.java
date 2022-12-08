package RamJongSuck.odongdong.DataInserter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.json.JSONException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import RamJongSuck.odongdong.DataInserter.Dto.AddressInfoDto;
import RamJongSuck.odongdong.DataInserter.Implementation.BathroomConfig;
import RamJongSuck.odongdong.DataInserter.Implementation.QueryRunner;
import RamJongSuck.odongdong.DataInserter.Implementation.Util.CoordinateByGeocoderKakao;
import RamJongSuck.odongdong.DataInserter.Interface.DataConfig;
import RamJongSuck.odongdong.DataInserter.type.DatabaseType;
import RamJongSuck.odongdong.DataInserter.type.DomainType;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class DataInserterApplication implements CommandLineRunner {

	private final QueryRunner queryRunner;
	private final CoordinateByGeocoderKakao coordinateByGeocoderKakao;

	public static void main(String[] args) {
		SpringApplication.run(DataInserterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// xlsxFileInsert();
		jsonFileInsert();
	}

	private void jsonFileInsert() {
		Map<String, String> bathroomMap = new HashMap<>();
		bathroomMap.put("toiletName", "title");
		bathroomMap.put("address", "address");
		bathroomMap.put("lat", "latitude");
		bathroomMap.put("lng", "longitude");

		Map<String, DatabaseType> databaseTypeMap = new HashMap<>();
		databaseTypeMap.put("title", DatabaseType.VARCHAR);
		databaseTypeMap.put("address", DatabaseType.VARCHAR);
		databaseTypeMap.put("latitude", DatabaseType.FLOAT);
		databaseTypeMap.put("longitude", DatabaseType.FLOAT);
		databaseTypeMap.put("address_detail", DatabaseType.VARCHAR);

		Consumer<Map<String, String>> mapConsumer = (map) -> {
			try {
				AddressInfoDto address = coordinateByGeocoderKakao.getCoordinateByAddress(map.get("address"));
				map.put("address_detail", address.getAddress_detail());
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		};

		DataConfig dataConfig = new BathroomConfig("해운대구_화장실.json", bathroomMap, DomainType.bathroom, databaseTypeMap, mapConsumer);
		queryRunner.runQuery(dataConfig);
	}

	private void xlsxFileInsert() {
		Map<String, String> bathroomMap = new HashMap<>();
		bathroomMap.put("화장실명", "title");
		bathroomMap.put("소재지도로명주소", "address");
		bathroomMap.put("소재지지번주소", "address");
		bathroomMap.put("남녀공용화장실여부", "is_unisex");
		bathroomMap.put("개방시간", "operation_time");

		Map<String, DatabaseType> databaseTypeMap = new HashMap<>();
		databaseTypeMap.put("title", DatabaseType.VARCHAR);
		databaseTypeMap.put("address", DatabaseType.VARCHAR);
		databaseTypeMap.put("is_unisex", DatabaseType.BOOLEAN);
		databaseTypeMap.put("operation_time", DatabaseType.VARCHAR);
		databaseTypeMap.put("latitude", DatabaseType.FLOAT);
		databaseTypeMap.put("longitude", DatabaseType.FLOAT);
		databaseTypeMap.put("address_detail", DatabaseType.VARCHAR);

		Consumer<Map<String, String>> mapConsumer = (map) -> {
			try {
				AddressInfoDto address = coordinateByGeocoderKakao.getCoordinateByAddress(map.get("address"));
				map.put("latitude", address.getLatitude().toString());
				map.put("longitude", address.getLongitude().toString());
				map.put("address_detail", address.getAddress_detail());
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		};

		DataConfig dataConfig = new BathroomConfig("test.xlsx", bathroomMap, DomainType.bathroom, databaseTypeMap, mapConsumer);
		queryRunner.runQuery(dataConfig);
	}
}
