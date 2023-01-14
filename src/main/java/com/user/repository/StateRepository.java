package com.user.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entities.StateMaster;


public interface StateRepository extends JpaRepository<StateMaster, Serializable> {
	
	//@Query(value ="SELECT s FROM StateMaster s WHERE s.countryId = ?1")
	public List<StateMaster> findByCountryId(Integer countryId);
}
