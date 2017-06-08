package manuscript.module.user.management.personaldata.settings.test;

import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import manuscript.module.user.management.academic.disciplines.AcademicDisciplinesDao;
import manuscript.module.user.management.personaldata.settings.PersonalDataSettings;
import manuscript.module.user.management.personaldata.settings.PersonalDataSettingsDao;
import manuscript.module.user.management.personaldata.settings.PersonalDataSettingsImpl;
import manuscript.test.annotation.IgnoreTestContext;

@IgnoreTestContext
@Configuration
public class PersonalDataSettingsContext {

	// @Bean
	// public SecurityContext getSecurityContext() {
	// SecurityContext securityContext = EasyMock.createMock(SecurityContext.class);
	// Authentication authentication = EasyMock.createMock(Authentication.class);
	// authentication.setAuthenticated(true);
	// EasyMock.replay(authentication);
	// EasyMock.expect(securityContext.getAuthentication()).andReturn(authentication);
	//
	// // securityContext.setAuthentication(authentication);
	//
	// EasyMock.replay(securityContext);
	//
	// SecurityContextHolder securityContextHolder = EasyMock.createMock(SecurityContextHolder.class);
	// EasyMock.expect(SecurityContextHolder.getContext()).andReturn(securityContext);
	// EasyMock.replay(securityContextHolder);
	//
	// return securityContext;
	// }

	@Bean
	public PersonalDataSettingsDao getPersonalDataSettingsDao() {
		return EasyMock.createMock(PersonalDataSettingsDao.class);
	}

	@Bean
	public AcademicDisciplinesDao getAcademicDisciplinesDao() {
		return EasyMock.createMock(AcademicDisciplinesDao.class);
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public PersonalDataSettings getPersonalDataSettings() {
		return new PersonalDataSettingsImpl(getPersonalDataSettingsDao(), getAcademicDisciplinesDao(), getPasswordEncoder());
	}
}
