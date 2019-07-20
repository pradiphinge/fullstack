package unit;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.mpscstarter.utils.UserUtils;
import com.mpscstarter.web.controllers.ForgotMyPasswordController;


public class UserUtilsUnitTest {

	private MockHttpServletRequest mockRequest;
	@Before
	public void init() {
		mockRequest = new MockHttpServletRequest();
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
	
}
