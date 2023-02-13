package com.splitmoney.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.splitmoney.model.Expanse;
import com.splitmoney.model.User;

@Repository
public interface ExpanseRepository extends JpaRepository<Expanse, Long> {
	
	public List<Expanse> findBylendID(User user);
	public List<Expanse> findByoweID(User user);
	
	@Query(value = "SELECT * FROM expanse e WHERE e.lendid = ?1 and e.oweid = ?2", nativeQuery = true)
	List<Expanse> findUserByLendidToOweid(long lendid,long oweid);
	
}
