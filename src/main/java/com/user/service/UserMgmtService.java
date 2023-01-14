package com.user.service;

import java.util.Map;

import com.user.binding.LoginForm;
import com.user.binding.UnlockAccountForm;
import com.user.binding.UserForm;

public interface UserMgmtService {
	
	public String checkEmail (String email);

	public Map<Integer, String> getCountries ( ) ;

	public Map<Integer, String> getStates (Integer countryId);

	public Map<Integer, String> getCities (Integer stateId);

	public String registerUser (UserForm userForm) throws Exception;

	public String unlockAccount (UnlockAccountForm unlockAccForm);

	public String login (LoginForm loginForm);

	public String forgotPwd (String email) throws Exception;
	


}
