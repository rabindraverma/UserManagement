package com.user.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.user.binding.LoginForm;
import com.user.binding.UnlockAccountForm;
import com.user.binding.UserForm;
import com.user.service.UserMgmtService;

@RestController
public class UserRestController {

	@Autowired
	private UserMgmtService userMgmtService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginForm form) {
		String status = userMgmtService.login(form);
		return new ResponseEntity<>(status, HttpStatus.OK);

	}

	@GetMapping("/country")
	public Map<Integer, String> loadCountry() {
		return userMgmtService.getCountries();
	}

	@GetMapping("/states/{countryId}")
	public Map<Integer, String> loadState(@PathVariable Integer countryId) {
		return userMgmtService.getStates(countryId);
	}

	@GetMapping("/cities/{stateId}")
	public Map<Integer, String> loadCities(@PathVariable Integer stateId) {
		return userMgmtService.getCities(stateId);
	}

	@GetMapping("/email/{email}")
	public String checkEmail(@PathVariable String email) {
		return userMgmtService.checkEmail(email);
	}

	@PostMapping("/user")
	public ResponseEntity<String> registerUser(@RequestBody UserForm userForm) throws Exception {
		return new ResponseEntity<>(userMgmtService.registerUser(userForm), HttpStatus.CREATED);

	}

	@PostMapping("/unlock")
	public ResponseEntity<String> unlockAccount(@RequestBody UnlockAccountForm unlockAccForm) {
		String status = userMgmtService.unlockAccount(unlockAccForm);
		return new ResponseEntity<>(status, HttpStatus.OK);

	}

	@GetMapping("/forgotpwd/{email}")
	public ResponseEntity<String> forgetPwd(@PathVariable String email) throws Exception {
		String status = userMgmtService.forgotPwd(email);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}
}
