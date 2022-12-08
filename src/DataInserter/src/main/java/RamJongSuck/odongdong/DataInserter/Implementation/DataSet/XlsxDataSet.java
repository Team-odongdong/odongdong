package RamJongSuck.odongdong.DataInserter.Implementation.DataSet;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import RamJongSuck.odongdong.DataInserter.Implementation.Reader.XlsxReader;
import RamJongSuck.odongdong.DataInserter.Implementation.Util.InsertTargetListMaker;
import RamJongSuck.odongdong.DataInserter.Implementation.Util.QueryMaker;
import RamJongSuck.odongdong.DataInserter.Interface.DataConfig;
import RamJongSuck.odongdong.DataInserter.Interface.DataSet;

public class XlsxDataSet implements DataSet {

	private List<Map<String, String>> insertTargetList;
	private DataConfig dataConfig;

	public XlsxDataSet(DataConfig dataConfig) {
		this.dataConfig = dataConfig;
		setDataSetList(dataConfig, dataConfig.getMapConsumer());
	}

	@Override
	public void setDataSetList(DataConfig dataConfig, Consumer<Map<String, String>> consumer) {
		insertTargetList = InsertTargetListMaker.setInsertTargetList(dataConfig, consumer,
			new XlsxReader(dataConfig.getFilePath(), dataConfig.getFileColumnToDatabaseColumnMap()));
	}

	@Override
	public String getInsertQuery() {
		return QueryMaker.makeInsertQuery(insertTargetList, dataConfig.getTargetClass(),
			dataConfig.getDatabaseColumnTypeMap());
	}
}
