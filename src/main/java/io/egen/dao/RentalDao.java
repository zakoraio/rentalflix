package io.egen.dao;

import java.util.List;

import io.egen.rentalflix.RentalRecord;

public interface RentalDao {

	RentalRecord findById(int id);

	void createRentalRecord(RentalRecord RentalRecord);
	
	RentalRecord updateRentalRecord(RentalRecord RentalRecord);
	
	RentalRecord deleteRentalRecord(int id);
	
	List<RentalRecord> findAllRentalRecords();

	List<RentalRecord> findRentalRecordByName(String name);
	
	RentalRecord findRentalRecordByMovieID(Integer movieId);

}
