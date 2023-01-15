package com.user.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.binding.LoginForm;
import com.user.binding.UnlockAccountForm;
import com.user.binding.UserForm;
import com.user.entities.CityMaster;
import com.user.entities.CountryMaster;
import com.user.entities.StateMaster;
import com.user.entities.User;
import com.user.repository.CityRepository;
import com.user.repository.CountryRepository;
import com.user.repository.StateRepository;
import com.user.repository.UserRepository;
import com.user.util.EmailUtils;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private CountryRepository countryRepo;
	@Autowired
	private StateRepository stateRepo;
	@Autowired
	private CityRepository cityRepo;
	@Autowired
	private EmailUtils emailUtil;

	private Random random = new Random();

	@Override
	public String checkEmail(String email) {
		User user = userRepo.findByEmail(email);
		return user == null ? "UNIQUE" : "DUBLICATE";

	}

	@Override
	public Map<Integer, String> getCountries() {
		List<CountryMaster> countries = countryRepo.findAll();
		Map<Integer, String> countryMap = new HashMap<>();
		countries.forEach(country ->
			countryMap.put(country.getCountryId(), country.getCountryName())
		);
		return countryMap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		List<StateMaster> states = stateRepo.findByCountryId(countryId);
		Map<Integer, String> countryMap = new HashMap<>();
		states.forEach(c ->
			countryMap.put(c.getStateId(), c.getStateName())
		);
		return countryMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		List<CityMaster> cities = cityRepo.findByStateId(stateId);
		Map<Integer, String> cityMap = new HashMap<>();
		cities.forEach(c ->
			cityMap.put(c.getCityId(), c.getCityName())
		);
		return cityMap;
	}

	@Override
	public String registerUser(UserForm userForm) throws Exception {

		// copy data from binding object to entity object
		User entity = new User();
		BeanUtils.copyProperties(userForm, entity);
		// generate and Set random Password
		entity.setUserPwd(generateRandomPassword());

		// set account status as locked
		entity.setAccStatus("LOCKED");

		userRepo.save(entity);

		// send email to unlock account
		String to = userForm.getEmail();
		String subject = "Registration Email";
		String body = readEmailBody("REG_EMAIL_BODY.txt", entity);

		emailUtil.sendEmail(to, subject, body);

		return "User Account Created";
	}

	@Override
	public String unlockAccount(UnlockAccountForm unlockAccForm) {
		String email = unlockAccForm.getEmail();
		User user = userRepo.findByEmail(email);

		if (user != null && user.getUserPwd().equals(unlockAccForm.getTempPwd())) {
			user.setUserPwd(unlockAccForm.getNewPwd());
			user.setAccStatus("UNLOCKED");
			userRepo.save(user);
			return "Account Unlocked";
		}
		return "Invalid Temporary Password";
	}

	@Override
	public String login(LoginForm loginForm) {
		User user = userRepo.findByEmailAndUserPwd(loginForm.getEmail(), loginForm.getPwd());

		if (user == null) {
			return "Invalid Credentials";
		}
		if (user.getAccStatus().equals("LOCKED")) {
			return "Account Locked";
		}
		return "SUCESS";
	}

	@Override
	public String forgotPwd(String email) throws Exception {
		User user = userRepo.findByEmail(email);

		if (user == null) {
			return "No account found";
		}

		// send email to user with pwd
		String subject = "Recover Password";

		String body = readEmailBody("FORGOT_PWD_EMAIL_BODY.txt", user);

		emailUtil.sendEmail(email, subject, body);

		return "Password sent to your Registered email";
	}

	private String generateRandomPassword() {
		String text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";


		StringBuilder sb = new StringBuilder();

		for (int i = 1; i <= 6; i++) {
			int index = random.nextInt(text.length());
			sb.append(text.charAt(index));
		}
		return sb.toString();
	}

	private String readEmailBody(String fileName, User user) {
		StringBuilder sb = new StringBuilder();
		try (Stream<String> lines = Files.lines(Paths.get(fileName))) {

			lines.forEach(line -> {
				line = line.replace("${FNAME}", user.getFName());
				line = line.replace("${LNAME}", user.getLName());
				line = line.replace("${TEMP_PWD}", user.getUserPwd());
				line = line.replace("${EMAIL}", user.getEmail());
				line = line.replace("${PWD}", user.getUserPwd());
				sb.append(line);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
