package RamJongSuck.odongdong.DataInserter.Implementation.DataSet;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import RamJongSuck.odongdong.DataInserter.Implementation.Reader.XlsxReader;
import RamJongSuck.odongdong.DataInserter.Implementation.Util.QueryMaker;
import RamJongSuck.odongdong.DataInserter.Interface.DataConfig;
import RamJongSuck.odongdong.DataInserter.Interface.DataSet;
import lombok.Getter;

@Getter
public class XlsxDataSet implements DataSet {

	private List<Map<String, String>> insertTargetList;
	private DataConfig dataConfig;


	public XlsxDataSet(DataConfig dataConfig) {
		setDataSetList(dataConfig, dataConfig.getMapConsumer());
	}

	public XlsxDataSet(DataConfig dataConfig, Consumer<Map<String, String>> consumer) {
		setDataSetList(dataConfig, consumer);
	}


	private void setDataSetList(DataConfig dataConfig, Consumer<Map<String, String>> consumer) {
		this.dataConfig = dataConfig;
		Map<String, String> fileToDatabaseMap = dataConfig.getFileColumnToDatabaseColumnMap();
		XlsxReader xlsxReader = new XlsxReader(dataConfig.getFilePath(), dataConfig.getFileColumnToDatabaseColumnMap());
		List<Map<String, String>> mapList = xlsxReader.getMapList();
		mapList.forEach(consumer);
		insertTargetList = mapList;
	}


	@Override
	public String getInsertQuery() {
		return QueryMaker.makeInsertQuery(insertTargetList, dataConfig.getTargetClass(), dataConfig.getDatabaseColumnTypeMap());
	}
}
