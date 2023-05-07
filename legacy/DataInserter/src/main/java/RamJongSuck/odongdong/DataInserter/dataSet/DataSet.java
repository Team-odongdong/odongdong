package RamJongSuck.odongdong.DataInserter.dataSet;

import java.util.Map;
import java.util.function.Consumer;

import RamJongSuck.odongdong.DataInserter.dataconfig.DataConfig;

public interface DataSet {
	void setDataSetList(DataConfig dataConfig, Consumer<Map<String, String>> consumer);

	String getInsertQuery();
}
