package manuscript.module.user.management.registration.test;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import manuscript.module.user.management.academic.disciplines.AcademicDisciplinesDao;
import manuscript.module.user.management.bean.AcademicDisciplines;
import manuscript.module.user.management.bean.Password;
import manuscript.module.user.management.bean.Role;
import manuscript.module.user.management.bean.User;
import manuscript.module.user.management.exception.NameAlreadyReservedException;
import manuscript.module.user.management.exception.PasswordValidationException;
import manuscript.module.user.management.registration.UserRegistrationDao;
import manuscript.module.user.management.registration.UserRegistrationService;
import manuscript.module.user.management.request.UserRegistrationRequest;
import manuscript.module.user.management.response.UserRegistrationPreloadResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UserRegistrationServiceContext.class)
public class UserRegistrationServiceTest {

	@Autowired
	private UserRegistrationService userRegistrationService;

	@Autowired
	private UserRegistrationDao userRegistrationDao;

	@Autowired
	private AcademicDisciplinesDao academicDisciplinesDao;

	@Before
	public void before() {
		EasyMock.reset(userRegistrationDao);
		EasyMock.reset(academicDisciplinesDao);
	}

	@After
	public void after() {
		EasyMock.reset(userRegistrationDao);
		EasyMock.reset(academicDisciplinesDao);
	}

	@Test
	public void userRegistrationPreload_test() {
		UserRegistrationPreloadResponse response = new UserRegistrationPreloadResponse();

		EasyMock.expect(academicDisciplinesDao.getDisciplinesAsList()).andReturn(getAcademicDisciplines());
		EasyMock.replay(academicDisciplinesDao);

		response = userRegistrationService.userRegistrationPreload();

		Assert.assertNotNull(response);
		Assert.assertTrue(response.getAcademicDisciplines().size() > 0);
	}

	@Test(expected = NameAlreadyReservedException.class)
	public void createRegistration_reservedUsername_test() {
		UserRegistrationRequest request = new UserRegistrationRequest();
		request.setUser(getUser(null, null, null, "dummy", null, null));

		EasyMock.expect(userRegistrationDao.isNameReserved(request.getUser().getUserName())).andReturn(true);
		EasyMock.replay(userRegistrationDao);

		userRegistrationService.createRegistration(request);

	}

	@Test(expected = PasswordValidationException.class)
	public void createRegistration_invalidPassword_test() {
		UserRegistrationRequest request = new UserRegistrationRequest();
		request.setUser(getUser(null, null, null, "dummy", null, null));
		request.setPassword(getPassword("password", "anotherPassword"));

		EasyMock.expect(userRegistrationDao.isNameReserved(request.getUser().getUserName())).andReturn(false);
		EasyMock.replay(userRegistrationDao);

		userRegistrationService.createRegistration(request);
	}

	// @Test
	// public void createRegistration_validPassword_test() {
	// UserRegistrationRequest request = new UserRegistrationRequest();
	// request.setUser(getUser(null, null, null, "dummy", null, null));
	// request.setPassword(getPassword("password", "password"));
	//
	// AdditionalData additionalData = new AdditionalData();
	// additionalData.setDefaultRoles(getRoles());
	//
	// EasyMock.expect(userRegistrationDao.isNameReserved(request.getUser().getUserName())).andReturn(false);
	// EasyMock.expect(userRegistrationDao.getDefaultRole()).andReturn(getRoles());
	// EasyMock.expect(userRegistrationDao.createRegistration(request, additionalData)).andReturn(request.getUser());
	// EasyMock.replay(userRegistrationDao);
	//
	// userRegistrationService.createRegistration(request);
	// }

	private List<AcademicDisciplines> getAcademicDisciplines() {
		List<AcademicDisciplines> list = new ArrayList<AcademicDisciplines>();
		list.add(getDisciplines("01", "disciplines1"));

		return list;
	}

	private AcademicDisciplines getDisciplines(String id, String name) {
		AcademicDisciplines disciplines = new AcademicDisciplines();
		disciplines.setAcademicDisciplinesId(id);
		disciplines.setAcademicDisciplinesName(name);
		return disciplines;
	}

	private User getUser(String email, String firstName, String lastName, String userName, String job, String title) {
		User user = new User();
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setJob(job);
		user.setTitle(title);
		user.setUserName(userName);
		return user;
	}

	private Password getPassword(String password, String passwordAgain) {
		Password pass = new Password();
		pass.setPassword(password);
		pass.setPasswordAgain(passwordAgain);
		return pass;
	}

	private List<Role> getRoles() {
		List<Role> roles = new ArrayList<Role>();
		Role role = new Role();
		role.setRoleId(1);
		role.setRole("role1");

		roles.add(role);

		return roles;
	}

}
