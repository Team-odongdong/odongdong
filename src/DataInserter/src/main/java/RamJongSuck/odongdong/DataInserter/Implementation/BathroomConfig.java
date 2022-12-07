package RamJongSuck.odongdong.DataInserter.Implementation;

import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Consumer;

import RamJongSuck.odongdong.DataInserter.Interface.DataConfig;
import RamJongSuck.odongdong.DataInserter.type.DatabaseType;
import RamJongSuck.odongdong.DataInserter.type.DomainType;
import RamJongSuck.odongdong.DataInserter.type.FileType;

public class BathroomConfig implements DataConfig {

	String fileName;
	Map<String, String> changeMap;
	Consumer<Map<String, String>> mapConsumer;
	DomainType domainType;
	Map<String, DatabaseType> databaseColumnTypeMap;

	public BathroomConfig(String fileName, Map<String, String> changeMap, DomainType domainType,
		Map<String, DatabaseType> databaseColumnTypeMap, Consumer<Map<String, String>> mapConsumer) {
		this.fileName = fileName;
		this.domainType = domainType;
		this.changeMap = changeMap;
		this.mapConsumer = mapConsumer;
		this.databaseColumnTypeMap = databaseColumnTypeMap;
	}

	@Override
	public String getFilePath() {
		return Paths.get("").toAbsolutePath().toString() + "\\src\\main\\resources\\BathroomFile\\" + fileName;
	}

	@Override
	public FileType getFileType() {
		String[] split = fileName.split("\\.");
		return FileType.getFileType(split[1]);
	}

	@Override
	public Map<String, String> getFileColumnToDatabaseColumnMap() {
		return changeMap;
	}

	@Override
	public DomainType getTargetClass() {
		return domainType;
	}

	@Override
	public Consumer<Map<String, String>> getMapConsumer() {
		return mapConsumer;
	}

	@Override
	public Map<String, DatabaseType> getDatabaseColumnTypeMap() {
		return databaseColumnTypeMap;
	}
}
