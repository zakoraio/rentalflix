package io.egen.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import io.egen.rentalflix.RentalRecord;

@Repository("rentalDao")
public class RentalDaoImpl extends AbstractDao<Integer, RentalRecord> implements RentalDao {

	public RentalRecord findById(int id) {
		return getByKey(id);
	}

	@Override
	public RentalRecord updateRentalRecord(RentalRecord entity) {
		RentalRecord RentalRecord = getByKey(entity.getId());
		if (null != RentalRecord) {
			RentalRecord.setUsername(entity.getUsername());
			RentalRecord.setMovieId(entity.getMovieId());
			update(RentalRecord);
			return RentalRecord;
		}

		return null;
	}

	public void createRentalRecord(RentalRecord RentalRecord) {
		persist(RentalRecord);
	}

	public RentalRecord deleteRentalRecord(int id) {
		RentalRecord RentalRecord = getByKey(id);
		if(null!=RentalRecord){
			delete(RentalRecord);
			return RentalRecord;
		}
		return null;
		
	}

	@SuppressWarnings("unchecked")
	public List<RentalRecord> findAllRentalRecords() {
		Criteria criteria = createEntityCriteria();
		return (List<RentalRecord>) criteria.list();
	}

	public List<RentalRecord> findRentalRecordByName(String userName) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("userName", userName).ignoreCase());
		List<RentalRecord> RentalRecordList = (List<RentalRecord>) criteria.list();
		return RentalRecordList;
	}

	@Override
	public RentalRecord findRentalRecordByMovieID(Integer movieId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("movieId", movieId));
		List<RentalRecord> RentalRecordList = (List<RentalRecord>) criteria.list();
		if(RentalRecordList.size()>0)
		return RentalRecordList.get(0);
		return null;
	}
}
