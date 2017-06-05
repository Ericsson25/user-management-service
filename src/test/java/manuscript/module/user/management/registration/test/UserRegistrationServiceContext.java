package manuscript.module.user.management.registration.test;

import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import manuscript.module.user.management.academic.disciplines.AcademicDisciplinesDao;
import manuscript.module.user.management.registration.UserRegistrationDao;
import manuscript.module.user.management.registration.UserRegistrationService;
import manuscript.module.user.management.registration.UserRegistrationServiceImpl;
import manuscript.test.annotation.IgnoreTestContext;

@IgnoreTestContext
@Configuration
public class UserRegistrationServiceContext {

	@Bean
	public UserRegistrationService getUserRegistrationService() {
		return new UserRegistrationServiceImpl(getUserRegistrationDao(), getAcademicDisciplinesDao());
	}

	@Bean
	public UserRegistrationDao getUserRegistrationDao() {
		return EasyMock.createMock(UserRegistrationDao.class);
	}

	@Bean
	public AcademicDisciplinesDao getAcademicDisciplinesDao() {
		return EasyMock.createMock(AcademicDisciplinesDao.class);
	}
}
