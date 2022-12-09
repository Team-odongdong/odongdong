package RamJongSuck.odongdong.DataInserter.dataconfig;

import java.util.Map;
import java.util.function.Consumer;

import RamJongSuck.odongdong.DataInserter.util.type.DatabaseType;
import RamJongSuck.odongdong.DataInserter.util.type.DomainType;
import RamJongSuck.odongdong.DataInserter.util.type.FileType;

public interface DataConfig {
	String getFilePath();

	FileType getFileType();

	Map<String, String> getFileColumnToDatabaseColumnMap();

	DomainType getTargetClass();

	Consumer<Map<String, String>> getMapConsumer();

	Map<String, DatabaseType> getDatabaseColumnTypeMap();

	Boolean isTypeOf(FileType fileType);
}
