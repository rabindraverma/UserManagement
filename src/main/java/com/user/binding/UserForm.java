package com.user.binding;

import java.time.LocalDate;
import lombok.Data;

@Data
public class UserForm {

	private String fName;

	private String lName;

	private String email;

	private Long phno;

	private LocalDate dob;

	private String gender;

	private Integer countryId;

	private Integer stateId;

	private Integer cityId;


}
