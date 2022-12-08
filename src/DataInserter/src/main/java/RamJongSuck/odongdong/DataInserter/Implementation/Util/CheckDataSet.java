package RamJongSuck.odongdong.DataInserter.Implementation.Util;

import org.springframework.stereotype.Component;

import RamJongSuck.odongdong.DataInserter.Implementation.DataSet.JsonDataSet;
import RamJongSuck.odongdong.DataInserter.Implementation.DataSet.XlsxDataSet;
import RamJongSuck.odongdong.DataInserter.Interface.DataConfig;
import RamJongSuck.odongdong.DataInserter.Interface.DataSet;
import RamJongSuck.odongdong.DataInserter.type.FileType;

@Component
public class CheckDataSet {

	public DataSet getDataSetByConfig(DataConfig dataConfig) {
		if(dataConfig.getFileType().equals(FileType.XLSX)) {
			return new XlsxDataSet(dataConfig);
		} else if (dataConfig.getFileType().equals(FileType.JSON)) {
			return new JsonDataSet(dataConfig);
		} else if (dataConfig.getFileType().equals(FileType.XML)) {
			return null;
		}
		throw new RuntimeException("확장자가 잘못되었습니다.");
	}
}
