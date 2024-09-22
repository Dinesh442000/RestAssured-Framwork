package api.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	Faker faker;
	User userPayload;
	public Logger logger;
	
	@BeforeClass
	public void setup() {
		faker  = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
	

		
		logger = LogManager.getLogger(this.getClass());
		
		logger.debug("debugging.....");
	}
	
	@Test(priority=1)
	public void testpostUser() {
		logger.info("");
	Response response=	UserEndPoints.createUser(userPayload);
	response.then().log().all();
	Assert.assertEquals(response.getStatusCode(),200);
		
	}
	
	
	@Test(priority=2)
	public void testGetUserByName() {
		
	Response response=	UserEndPoints.readUser(this.userPayload.getUsername());
	response.then().log().all();
	Assert.assertEquals(response.getStatusCode(),200);
		
	}
	
	@Test(priority=3)
	public void testUpdateUserByName() {
		//update data here
		System.out.println("Before Update"+userPayload.getFirstName());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		System.out.println("After Update"+userPayload.getFirstName());
		
		Response response=	UserEndPoints.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().body();
		Assert.assertEquals(response.getStatusCode(),200);
		
		Response responseAfterupdate=UserEndPoints.readUser(this.userPayload.getUsername());
		Assert.assertEquals(responseAfterupdate.getStatusCode(),200);
	}
	
	
	@Test(priority=4)
	public void testDeleteUserByName() {
		
	Response response=	UserEndPoints.deleteUser(this.userPayload.getUsername());
	response.then().log().all();
	Assert.assertEquals(response.getStatusCode(),200);
		
	}
}
