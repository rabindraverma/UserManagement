package com.user.entities;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "USER_ACCOUNT")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String fName;

    private String lName;

    private String email;

    private Long phno;

    private LocalDate dob;

    private String gender;

    private Integer countryId;

    private Integer stateId;

    private Integer cityId;

    private String userPwd;

    private String accStatus;
}
