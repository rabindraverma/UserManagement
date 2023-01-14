package com.user.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entities.CityMaster;

public interface CityRepository extends JpaRepository<CityMaster, Serializable> {
	
	public List<CityMaster> findByStateId(Integer stateId);

}
