package RamJongSuck.odongdong.DataInserter.dataconfig.producer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import RamJongSuck.odongdong.DataInserter.dataSet.DataSet;
import RamJongSuck.odongdong.DataInserter.dataSet.JsonDataSet;
import RamJongSuck.odongdong.DataInserter.dto.AddressInfoDto;
import RamJongSuck.odongdong.DataInserter.util.CoordinateByGeocoderKakao;
import RamJongSuck.odongdong.DataInserter.dataconfig.BathroomConfig;
import RamJongSuck.odongdong.DataInserter.dataconfig.DataConfig;
import RamJongSuck.odongdong.DataInserter.util.type.DatabaseType;
import RamJongSuck.odongdong.DataInserter.util.type.DomainType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JsonConfigProducer implements ConfigProducer {

	private final CoordinateByGeocoderKakao coordinateByGeocoderKakao;
	private final String fileName;

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
		return new JsonDataSet(getDataConfig());
	}

	private Consumer<Map<String, String>> makeMapConsumer() {
		return (map) -> {
			try {
				AddressInfoDto address = coordinateByGeocoderKakao.getCoordinateByAddress(map.get("address"));
				map.put("address_detail", address.getAddress_detail());
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		};
	}

	private Map<String, DatabaseType> makeDatabaseTypeMap() {
		Map<String, DatabaseType> databaseTypeMap = new HashMap<>();

		databaseTypeMap.put("title", DatabaseType.VARCHAR);
		databaseTypeMap.put("address", DatabaseType.VARCHAR);
		databaseTypeMap.put("latitude", DatabaseType.FLOAT);
		databaseTypeMap.put("longitude", DatabaseType.FLOAT);
		databaseTypeMap.put("address_detail", DatabaseType.VARCHAR);

		return databaseTypeMap;
	}

	private Map<String, String> makeBathroomMap() {
		Map<String, String> bathroomMap = new HashMap<>();

		bathroomMap.put("toiletName", "title");
		bathroomMap.put("address", "address");
		bathroomMap.put("lat", "latitude");
		bathroomMap.put("lng", "longitude");

		return bathroomMap;
	}
}
