package unit;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.mpscstarter.backend.persistence.domain.backend.User;
import com.mpscstarter.utils.UserUtils;
import com.mpscstarter.web.controllers.ForgotMyPasswordController;
import com.mpscstarter.web.domain.frontend.BasicAccountPayload;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


public class UserUtilsUnitTest {

	private MockHttpServletRequest mockRequest;
	private PodamFactory podamFactory;
	
	@Before
	public void init() {
		mockRequest = new MockHttpServletRequest();
		podamFactory = new PodamFactoryImpl();
	}

	@Test
	public void testPasswordResetUrlConstruction()throws Exception{
		
		mockRequest.setServerPort(8080);
		String token =UUID.randomUUID().toString();
		long userId = 123456;
		
		String expectedUrl = "http://localhost:8080"+
							 ForgotMyPasswordController.CHANGE_PASSWORD_PATH+
							 "?id="+userId+
							 "&token="+token;
		
		String actualUrl = UserUtils.createPasswordResetUrl(mockRequest,userId,token);
		
		Assert.assertEquals(expectedUrl, actualUrl);
							 
	} 

	
	@Test
	public void mapWebUserToDomainUser() {
		BasicAccountPayload webUser = podamFactory.manufacturePojoWithFullData(BasicAccountPayload.class);
		webUser.setEmail("me@Example.com");

		User user = UserUtils.fromWebUserToDomainUser(webUser);
		Assert.assertNotNull(user);
		
		Assert.assertEquals(webUser.getUsername(), user.getUsername());
		Assert.assertEquals(webUser.getPassword(), user.getPassword());
		Assert.assertEquals(webUser.getFirstName(), user.getFirstname());
		Assert.assertEquals(webUser.getLastName(), user.getLastname());
		Assert.assertEquals(webUser.getEmail(), user.getEmail());
		Assert.assertEquals(webUser.getCountry(), user.getCountry());
		Assert.assertEquals(webUser.getDescription(), user.getDescription());
		Assert.assertEquals(webUser.getPhoneNumber(), user.getPhonenumber());
		
		
		
		
	}
	
	
	
}
