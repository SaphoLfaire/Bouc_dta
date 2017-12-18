package bouc_web_service.controllertests;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import bouc_web_service.utils.JsonHelper;
import fr.dta.App;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = App.class)
@TestPropertySource("classpath:application-test.properties")
@Transactional
public abstract class IntegrationTests {

	@Autowired
    protected WebApplicationContext wac;

	@Autowired
    protected JsonHelper jsonHelper;
	
    protected MockMvc mockMvc;

    @Before
    public void initMockMcv() {
        mockMvc = MockMvcBuilders
        		.webAppContextSetup(wac)
        		//.apply(SecurityMockMvcConfigurers.springSecurity())
        		.build();
    }

}
