package RamJongSuck.odongdong.DataInserter.dataSet;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import RamJongSuck.odongdong.DataInserter.reader.JsonReader;
import RamJongSuck.odongdong.DataInserter.util.InsertTargetListMaker;
import RamJongSuck.odongdong.DataInserter.util.QueryMaker;
import RamJongSuck.odongdong.DataInserter.dataconfig.DataConfig;

public class JsonDataSet implements DataSet {

	private List<Map<String, String>> insertTargetList;
	private DataConfig dataConfig;

	public JsonDataSet(DataConfig dataConfig) {
		this.dataConfig = dataConfig;
		setDataSetList(dataConfig, dataConfig.getMapConsumer());
	}

	@Override
	public void setDataSetList(DataConfig dataConfig, Consumer<Map<String, String>> consumer) {
		insertTargetList = InsertTargetListMaker.setInsertTargetList(dataConfig, consumer,
			new JsonReader(dataConfig.getFilePath(), dataConfig.getFileColumnToDatabaseColumnMap()));
	}

	@Override
	public String getInsertQuery() {
		return QueryMaker.makeInsertQuery(insertTargetList, dataConfig.getTargetClass(),
			dataConfig.getDatabaseColumnTypeMap());
	}
}
