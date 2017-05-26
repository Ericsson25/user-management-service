package manuscript.module.user.management.registration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import manuscript.module.user.management.academic.disciplines.AcademicDisciplinesDao;
import manuscript.module.user.management.bean.AdditionalData;
import manuscript.module.user.management.bean.Password;
import manuscript.module.user.management.bean.Role;
import manuscript.module.user.management.exception.NameAlreadyReservedException;
import manuscript.module.user.management.exception.PasswordValidationException;
import manuscript.module.user.management.exception.SaveUserException;
import manuscript.module.user.management.request.UserRegistrationRequest;
import manuscript.module.user.management.response.UserRegistrationPreloadResponse;
import manuscript.module.user.management.response.UserRegistrationResponse;

/**
 * 
 * @author Balazs Kovacs
 *
 */
@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

	private final static Logger LOGGER = LoggerFactory.getLogger(UserRegistrationServiceImpl.class);

	private UserRegistrationDao userRegistrationDao;
	private AcademicDisciplinesDao academicDisciplinesDao;

	public UserRegistrationServiceImpl(UserRegistrationDao userRegistrationDao, AcademicDisciplinesDao academicDisciplinesDao) {
		this.userRegistrationDao = userRegistrationDao;
		this.academicDisciplinesDao = academicDisciplinesDao;
	}

	@Override
	public UserRegistrationPreloadResponse userRegistrationPreload() {
		UserRegistrationPreloadResponse response = new UserRegistrationPreloadResponse();
		response.setAcademicDisciplines(academicDisciplinesDao.getDisciplinesAsList());
		return response;
	}

	@Override
	public UserRegistrationResponse createRegistration(UserRegistrationRequest request) throws NameAlreadyReservedException {
		UserRegistrationResponse response = new UserRegistrationResponse();

		isUsernameReserved(request.getUser().getUserName());
		isPasswordValid(request.getPassword());

		AdditionalData additionalData = new AdditionalData();
		additionalData.setDefaultRoles(getDefaultRoles());

		try {
			userRegistrationDao.createRegistration(request, additionalData);
			LOGGER.debug("Registration success. Created user is {}", request.getUser());
		} catch (Exception e) {
			throw new SaveUserException("Registration has failed. Please try again later.");
		}

		response.setSuccessMessage("Your registration has been finished successfully. Please try to login.");
		return null;
	}

	private void isUsernameReserved(String userName) {
		if (userRegistrationDao.isNameReserved(userName)) {
			LOGGER.error("{} is already used.", userName);
			throw new NameAlreadyReservedException("Username is already used!");
		}
	}

	private void isPasswordValid(Password password) {
		if (password.getPassword().equals(password.getPasswordAgain())) {
			LOGGER.debug("Given passwords are valid");

			password.setPassword(getEncodedPassword(password.getPassword()));
		} else {
			LOGGER.debug("Password validation failed: ", password);
			throw new PasswordValidationException("Password validation failed. The given passwords are not mached.");
		}

	}

	private String getEncodedPassword(String password) {
		String encodedPassword = new BCryptPasswordEncoder().encode(password);
		LOGGER.debug("Encoded password is {}", encodedPassword);

		return encodedPassword;
	}

	private List<Role> getDefaultRoles() {
		return userRegistrationDao.getDefaultRole();
	}

}
