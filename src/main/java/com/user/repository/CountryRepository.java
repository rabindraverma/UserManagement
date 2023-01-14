package com.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entities.CountryMaster;

public interface CountryRepository extends JpaRepository<CountryMaster, Integer> {

}
