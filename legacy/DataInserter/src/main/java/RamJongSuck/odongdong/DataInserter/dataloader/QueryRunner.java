package RamJongSuck.odongdong.DataInserter.dataloader;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
@Transactional
public class QueryRunner {

	@PersistenceContext
	EntityManager em;

	public void runQuery(String queryString) {
		int changeNum = excuteQuery(queryString);
		System.out.println(String.format("총 %d개의 데이터가 업데이트 되었습니다.", changeNum));
	}

	int excuteQuery(String insertQuery) {
		em.joinTransaction();
		return em.createNativeQuery(insertQuery).executeUpdate();
	}
}
