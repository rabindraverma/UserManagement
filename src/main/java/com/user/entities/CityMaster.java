package com.user.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "CITY_MASTER")
public class CityMaster {
	
	@Id
	private Integer cityId;
	private String cityName;
	private Integer stateId;

}
