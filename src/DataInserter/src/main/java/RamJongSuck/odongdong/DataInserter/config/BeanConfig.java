package RamJongSuck.odongdong.DataInserter.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import RamJongSuck.odongdong.DataInserter.dataconfig.producer.ConfigProducer;
import RamJongSuck.odongdong.DataInserter.dataconfig.producer.JsonConfigProducer;
import RamJongSuck.odongdong.DataInserter.dataconfig.producer.XlsxConfigProducer;

@Configuration
public class BeanConfig {

	@Bean
	List<ConfigProducer> configProducerList(
		@Autowired JsonConfigProducer jsonConfigProducer,
		@Autowired XlsxConfigProducer xlsxConfigProducer
	) {
		List<ConfigProducer> configList = new ArrayList<>();

		configList.add(jsonConfigProducer);
		configList.add(xlsxConfigProducer);

		return configList;
	}
}
