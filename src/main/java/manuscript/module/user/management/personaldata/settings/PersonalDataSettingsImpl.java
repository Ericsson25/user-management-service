package manuscript.module.user.management.personaldata.settings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import manuscript.module.user.management.academic.disciplines.AcademicDisciplinesDao;
import manuscript.module.user.management.bean.Password;
import manuscript.module.user.management.exception.ChangePasswordException;
import manuscript.module.user.management.exception.DisciplinesUpdateException;
import manuscript.module.user.management.exception.PasswordValidationException;
import manuscript.module.user.management.exception.PersonalDataSettingsException;
import manuscript.module.user.management.exception.UserIsNotAuthenticatedException;
import manuscript.module.user.management.request.ChangePasswordRequest;
import manuscript.module.user.management.request.SavePersonalDataRequest;
import manuscript.module.user.management.request.UpdateAcademicDisciplinesRequest;
import manuscript.module.user.management.request.UpdatePassword;
import manuscript.module.user.management.response.ChangePasswordResponse;
import manuscript.module.user.management.response.PersonalDataSettingsPreloadResponse;
import manuscript.module.user.management.response.SavePersonalDataResponse;
import manuscript.module.user.management.response.UpdateAcademicDisciplinesResponse;
import manuscript.system.security.holder.ManuscriptSecurityContext;

/**
 * 
 * @author Balazs Kovacs
 *
 */
@Service
public class PersonalDataSettingsImpl implements PersonalDataSettings {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonalDataSettingsImpl.class);

	private PersonalDataSettingsDao personalDataSettingsDao;
	private AcademicDisciplinesDao academicDisciplinesDao;
	private PasswordEncoder passwordEncoder;

	public PersonalDataSettingsImpl(PersonalDataSettingsDao personalDataSettingsDao, AcademicDisciplinesDao academicDisciplinesDao,
			PasswordEncoder passwordEncoder) {
		this.personalDataSettingsDao = personalDataSettingsDao;
		this.academicDisciplinesDao = academicDisciplinesDao;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public PersonalDataSettingsPreloadResponse preload() {
		PersonalDataSettingsPreloadResponse response = new PersonalDataSettingsPreloadResponse();

		String userId = getUserIdFromContext();

		response.setUser(personalDataSettingsDao.getUserData(userId));
		response.setAcademicDisciplines(academicDisciplinesDao.getDisciplinesByUserId(userId));

		return response;
	}

	@Override
	public SavePersonalDataResponse savePersonalData(SavePersonalDataRequest request) {
		SavePersonalDataResponse response = new SavePersonalDataResponse();
		try {
			personalDataSettingsDao.updatePersonalData(request, getUserIdFromContext());
		} catch (Exception e) {
			throw new PersonalDataSettingsException("Can't modify your personal data. Please try again later.");
		}
		response.setSuccessMessage("Your personal data has updated successfully.");
		return response;
	}

	@Override
	public UpdateAcademicDisciplinesResponse updateAcademicDisciplines(UpdateAcademicDisciplinesRequest request) {
		UpdateAcademicDisciplinesResponse response = new UpdateAcademicDisciplinesResponse();

		String userId = getUserIdFromContext();

		try {
			academicDisciplinesDao.updateDisciplinesByUserId(userId, request.getAcademicDisciplines());
		} catch (Exception exception) {
			throw new DisciplinesUpdateException("Your academic disciplnes has not been updated. Please try again later.");
		}

		response.setSuccessMessage("Your academic disciplines has been updated successfully.");
		return response;
	}

	@Override
	public ChangePasswordResponse changePassword(ChangePasswordRequest request) {
		ChangePasswordResponse response = new ChangePasswordResponse();

		String userId = getUserIdFromContext();

		if (!isGivenPasswordAreTheSame(request.getPassword())) {
			throw new PasswordValidationException("The given password are not matched");
		}

		if (!isOldPasswordIsTheSameAsSaved(request.getOldPassword())) {
			throw new PasswordValidationException("The given old password and the stored are not matched.");
		}

		UpdatePassword updatePassword = new UpdatePassword();
		updatePassword.setUserId(userId);
		updatePassword.setEncryptedPassword(getEncodedPassword(request.getPassword().getPassword()));

		try {
			personalDataSettingsDao.updatePassword(updatePassword);
		} catch (Exception e) {
			throw new ChangePasswordException("Can not change your password. Please try again later.");
		}

		response.setSuccessMessage("Your password has been changed successfully.");
		return response;
	}

	private String getUserIdFromContext() {
		String userId = null;

		if (ManuscriptSecurityContext.getContext() != null) {
			userId = ManuscriptSecurityContext.getContext().getUserId();
		} else {
			throw new UserIsNotAuthenticatedException("User is not authenticated!");
		}

		return userId;
	}

	private boolean isGivenPasswordAreTheSame(Password password) {
		return password.getPassword().equals(password.getPasswordAgain()) ? true : false;
	}

	private boolean isOldPasswordIsTheSameAsSaved(String oldPassword) {
		return passwordEncoder.matches(oldPassword, personalDataSettingsDao.getPasswordByUserId(getUserIdFromContext())) ? true : false;
	}

	private String getEncodedPassword(String password) {
		return new BCryptPasswordEncoder().encode(password);
	}
}
