package com.splitmoney.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.splitmoney.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	public List<User> findByfirstName(String firstName);
	public User findById(long id);
}
