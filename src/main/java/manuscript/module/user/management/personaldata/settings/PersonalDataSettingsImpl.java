package manuscript.module.user.management.personaldata.settings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import manuscript.module.user.management.academic.disciplines.AcademicDisciplinesDao;
import manuscript.module.user.management.exception.UserIsNotAuthenticatedException;
import manuscript.module.user.management.request.ChangePasswordRequest;
import manuscript.module.user.management.request.SavePersonalDataRequest;
import manuscript.module.user.management.request.UpdateAcademicDisciplinesRequest;
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
		String userId = null;

		if (ManuscriptSecurityContext.getContext() != null) {
			userId = ManuscriptSecurityContext.getContext().getUserId();
		} else {
			throw new UserIsNotAuthenticatedException("User is not authenticated!");
		}

		response.setUser(personalDataSettingsDao.getUserData(userId));
		response.setAcademicDisciplines(academicDisciplinesDao.getDisciplinesByUserId(userId));

		return null;
	}

	@Override
	public SavePersonalDataResponse savePersonalData(SavePersonalDataRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UpdateAcademicDisciplinesResponse updateAcademicDisciplines(UpdateAcademicDisciplinesRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChangePasswordResponse changePassword(ChangePasswordRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	// public PersonalDataSettingsPreloadResponse preload() {
	// PersonalDataSettingsPreloadResponse response = new PersonalDataSettingsPreloadResponse();
	//
	// AuthenticatedUser authenticatedUser = getAuthenticatedUser();
	//
	// response.setUser(personalDataSettingsDao.getUserData(authenticatedUser));
	// response.setAcademicDisciplines(personalDataSettingsDao.getAcademicDisciplinesByUserId(authenticatedUser.getUserId()));
	//
	// return response;
	// }
	//
	// @Override
	// public SavePersonalDataResponse savePersonalData(SavePersonalDataRequest request) {
	// SavePersonalDataResponse response = new SavePersonalDataResponse();
	// personalDataSettingsDao.updatePersonalData(request, getAuthenticatedUser().getUserId());
	// response.setSuccessMessage("Your personal data has been updated succesfully!");
	// return response;
	// }
	//
	// @Override
	// public ChangePasswordResponse changePassword(ChangePasswordRequest request) {
	// ChangePasswordResponse response = new ChangePasswordResponse();
	//
	// if (!isGivenPasswordAreTheSame(request.getPassword())) {
	// throw new PasswordValidationException("The given password are not matched");
	// }
	//
	// if (!isOldPasswordIsTheSameAsSaved(request.getOldPassword())) {
	// throw new GivenOldPasswordIsIncorrectException("Your password is worng...");
	// }
	//
	// UpdatePassword updatePassword = new UpdatePassword();
	// updatePassword.setUserId(getAuthenticatedUser().getUserId());
	// updatePassword.setEncryptedPassword(getEncodedPassword(request.getPassword().getPassword()));
	//
	// personalDataSettingsDao.updatePassword(updatePassword);
	//
	// response.setSuccessMessage("Your password has changed successfully!");
	// return response;
	// }
	//
	// private AuthenticatedUser getAuthenticatedUser() {
	// if ((AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {
	// return (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	// }
	// throw new RuntimeException(); // FIX IT
	// }
	//
	// private boolean isGivenPasswordAreTheSame(Password password) {
	// if (password.getPassword().equals(password.getPasswordAgain())) {
	// return true;
	// }
	// return false;
	// }
	//
	// private boolean isOldPasswordIsTheSameAsSaved(String oldPassword) {
	// if (passwordEncoder.matches(oldPassword, personalDataSettingsDao.getPasswordByUserId(getAuthenticatedUser().getUserId()))) {
	// return true;
	// }
	// return false;
	// }
	//
	// private String getEncodedPassword(String password) {
	// return new BCryptPasswordEncoder().encode(password);
	// }
	//
	// @Override
	// public UpdateAcademicDisciplinesResponse updateAcademicDisciplines(UpdateAcademicDisciplinesRequest request) {
	// UpdateAcademicDisciplinesResponse response = new UpdateAcademicDisciplinesResponse();
	// personalDataSettingsDao.updateAcademicDisciplinesByUserId(request, getAuthenticatedUser().getUserId());
	// response.setSuccessMessage("Your disciplines has been updated successfully.");
	// return response;
	// }

}
