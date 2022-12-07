package RamJongSuck.odongdong.DataInserter.Interface;

import java.util.Map;
import java.util.function.Consumer;

import RamJongSuck.odongdong.DataInserter.type.DatabaseType;
import RamJongSuck.odongdong.DataInserter.type.DomainType;
import RamJongSuck.odongdong.DataInserter.type.FileType;

public interface DataConfig {
	String getFilePath();
	FileType getFileType();
	Map<String,String> getFileColumnToDatabaseColumnMap();
	DomainType getTargetClass();

	Consumer<Map<String, String>> getMapConsumer();
	Map<String, DatabaseType> getDatabaseColumnTypeMap();
}
