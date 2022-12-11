package RamJongSuck.odongdong.DataInserter;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import RamJongSuck.odongdong.DataInserter.dataSet.DataSet;
import RamJongSuck.odongdong.DataInserter.dataconfig.producer.ConfigProducer;
import RamJongSuck.odongdong.DataInserter.dataloader.QueryRunner;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class DataInserterApplication implements CommandLineRunner {

	private final QueryRunner queryRunner;
	private final List<ConfigProducer> configProducerList;

	public static void main(String[] args) {
		SpringApplication.run(DataInserterApplication.class, args);
	}

	@Override
	public void run(String... args) {
		for (ConfigProducer configProducer : configProducerList) {
			DataSet dataSet = configProducer.getDataSet();
			String queryString = dataSet.getInsertQuery();
			queryRunner.runQuery(queryString);
		}
	}
}
