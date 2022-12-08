package RamJongSuck.odongdong.DataInserter.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import RamJongSuck.odongdong.DataInserter.Implementation.Util.CheckDataSet;
import RamJongSuck.odongdong.DataInserter.Interface.DataConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
@Transactional
public class QueryRunner {

	@PersistenceContext
	EntityManager em;

	@Autowired
	CheckDataSet checkDataSet;

	public void runQuery(DataConfig dataConfig) {
		int changeNum = excuteQuery(checkDataSet.getDataSetByConfig(dataConfig).getInsertQuery());
		System.out.println(String.format("총 %d개의 데이터가 업데이트 되었습니다.", changeNum));
	}

	int excuteQuery(String insertQuery) {
		em.joinTransaction();
		return em.createNativeQuery(insertQuery).executeUpdate();
	}
}
