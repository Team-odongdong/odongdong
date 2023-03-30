package RamJongSuck.odongdong.DataInserter.dataconfig.producer;

import RamJongSuck.odongdong.DataInserter.dataSet.DataSet;
import RamJongSuck.odongdong.DataInserter.dataconfig.DataConfig;

public interface ConfigProducer {
	DataConfig getDataConfig();

	DataSet getDataSet();
}
