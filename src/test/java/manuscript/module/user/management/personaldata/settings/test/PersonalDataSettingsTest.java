package manuscript.module.user.management.personaldata.settings.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import manuscript.module.user.management.academic.disciplines.AcademicDisciplinesDao;
import manuscript.module.user.management.personaldata.settings.PersonalDataSettings;
import manuscript.module.user.management.personaldata.settings.PersonalDataSettingsDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersonalDataSettingsContext.class)
@Ignore
public class PersonalDataSettingsTest {

	@Autowired
	private PersonalDataSettingsDao personalDataSettingsDao;

	@Autowired
	private AcademicDisciplinesDao academicDisciplinesDao;

	// @Autowired
	// private PasswordEncoder passwordEncoder;

	@Autowired
	private PersonalDataSettings personalDataSettings;

	@Before
	public void before() {

	}

	@After
	public void after() {

	}

}
