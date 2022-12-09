package RamJongSuck.odongdong.DataInserter.dataconfig.producer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.json.JSONException;
import org.springframework.stereotype.Component;

import RamJongSuck.odongdong.DataInserter.dataSet.DataSet;
import RamJongSuck.odongdong.DataInserter.dataSet.XlsxDataSet;
import RamJongSuck.odongdong.DataInserter.dto.AddressInfoDto;
import RamJongSuck.odongdong.DataInserter.util.CoordinateByGeocoderKakao;
import RamJongSuck.odongdong.DataInserter.dataconfig.BathroomConfig;
import RamJongSuck.odongdong.DataInserter.dataconfig.DataConfig;
import RamJongSuck.odongdong.DataInserter.util.type.DatabaseType;
import RamJongSuck.odongdong.DataInserter.util.type.DomainType;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class XlsxConfigProducer implements ConfigProducer {

	private final CoordinateByGeocoderKakao coordinateByGeocoderKakao;
	private final String fileName = "test.xlsx";

	@Override
	public DataConfig getDataConfig() {
		return BathroomConfig.builder()
			.fileName(fileName)
			.changeMap(makeBathroomMap())
			.domainType(DomainType.bathroom)
			.databaseColumnTypeMap(makeDatabaseTypeMap())
			.mapConsumer(makeMapConsumer())
			.build();
	}

	@Override
	public DataSet getDataSet() {
		return new XlsxDataSet(getDataConfig());
	}

	private Consumer<Map<String, String>> makeMapConsumer() {
		return (map) -> {
			try {
				AddressInfoDto address = coordinateByGeocoderKakao.getCoordinateByAddress(map.get("address"));
				map.put("latitude", address.getLatitude().toString());
				map.put("longitude", address.getLongitude().toString());
				map.put("address_detail", address.getAddress_detail());
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		};
	}

	private static Map<String, DatabaseType> makeDatabaseTypeMap() {
		Map<String, DatabaseType> databaseTypeMap = new HashMap<>();

		databaseTypeMap.put("title", DatabaseType.VARCHAR);
		databaseTypeMap.put("address", DatabaseType.VARCHAR);
		databaseTypeMap.put("is_unisex", DatabaseType.BOOLEAN);
		databaseTypeMap.put("operation_time", DatabaseType.VARCHAR);
		databaseTypeMap.put("latitude", DatabaseType.FLOAT);
		databaseTypeMap.put("longitude", DatabaseType.FLOAT);
		databaseTypeMap.put("address_detail", DatabaseType.VARCHAR);

		return databaseTypeMap;
	}

	private static Map<String, String> makeBathroomMap() {
		Map<String, String> bathroomMap = new HashMap<>();

		bathroomMap.put("화장실명", "title");
		bathroomMap.put("소재지도로명주소", "address");
		bathroomMap.put("소재지지번주소", "address");
		bathroomMap.put("남녀공용화장실여부", "is_unisex");
		bathroomMap.put("개방시간", "operation_time");

		return bathroomMap;
	}
}
