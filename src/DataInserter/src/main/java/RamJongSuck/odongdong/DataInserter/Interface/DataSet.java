package RamJongSuck.odongdong.DataInserter.Interface;

import java.util.Map;
import java.util.function.Consumer;

public interface DataSet {
	void setDataSetList(DataConfig dataConfig, Consumer<Map<String, String>> consumer);

	String getInsertQuery();
}
