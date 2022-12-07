package RamJongSuck.odongdong.DataInserter.Implementation.Util;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import RamJongSuck.odongdong.DataInserter.Interface.DataConfig;
import RamJongSuck.odongdong.DataInserter.Interface.DataReader;

public class InsertTargetListMaker {

	public static List<Map<String, String>> setInsertTargetList(DataConfig dataConfig,
		Consumer<Map<String, String>> consumer, DataReader dataReader) {
		List<Map<String, String>> mapList = dataReader.getMapList();
		if (consumer != null)
			mapList.forEach(consumer);
		return mapList;
	}
}
