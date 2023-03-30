package RamJongSuck.odongdong.DataInserter.dataconfig;

import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Consumer;

import RamJongSuck.odongdong.DataInserter.util.type.DatabaseType;
import RamJongSuck.odongdong.DataInserter.util.type.DomainType;
import RamJongSuck.odongdong.DataInserter.util.type.FileType;
import lombok.Builder;

public class BathroomConfig implements DataConfig {

	String fileName;
	Map<String, String> changeMap;
	Consumer<Map<String, String>> mapConsumer;
	DomainType domainType;
	Map<String, DatabaseType> databaseColumnTypeMap;

	@Builder //이 케이스는 각 Param 들이 어떤 내용을 의미하는지 명확하게 보이지 않아서 Builder가 적합해 보임
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

	@Override
	public Boolean isTypeOf(FileType fileType) {
		return getFileType().equals(fileType);
	}
}
