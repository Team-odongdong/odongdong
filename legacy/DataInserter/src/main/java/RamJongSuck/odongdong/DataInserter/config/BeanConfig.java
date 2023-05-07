package RamJongSuck.odongdong.DataInserter.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import RamJongSuck.odongdong.DataInserter.dataconfig.producer.ConfigProducer;
import RamJongSuck.odongdong.DataInserter.dataconfig.producer.JsonConfigProducer;
import RamJongSuck.odongdong.DataInserter.dataconfig.producer.XlsxConfigProducer;
import RamJongSuck.odongdong.DataInserter.util.CoordinateByGeocoderKakao;

@Configuration
public class BeanConfig {

	@Bean
	List<ConfigProducer> configProducerList(
		@Autowired CoordinateByGeocoderKakao coordinateByGeocoderKakao
	) {
		List<ConfigProducer> configList = new ArrayList<>();

		configList.add(new JsonConfigProducer(coordinateByGeocoderKakao, "해운대구_화장실.json"));
		configList.add(new XlsxConfigProducer(coordinateByGeocoderKakao, "test.xlsx"));

		return configList;
	}
}
