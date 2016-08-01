package org.egen.challenge.service;

import java.util.ArrayList;

import org.egen.challenge.dao.UserDAO;
import org.egen.challenge.dao.UserDAOMongoDBImpl;
import org.egen.challenge.model.ResponseMessage;
import org.egen.challenge.spark.Application;
import org.egen.challenge.utils.Utility;
import org.junit.runner.RunWith;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import spark.Request;
import spark.Response;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
	
    private static Request request;
    private static Response response;
    private static UserService userService;
    private static UserDAO userDAO;
    private static String jsonInput1;
    private static String jsonInput2;
    private static String collection = "testUsers";

    
    @BeforeClass
    public static final void setUp() throws Exception {
    	userDAO = new UserDAOMongoDBImpl(Application.mongoConnection(), collection);
    	userService = new UserServiceImpl(userDAO);
    	request = Mockito.mock(Request.class);
    	response = Mockito.mock(Response.class);
    	jsonInput1 = Utility.getSampleJson("user.json");
    	jsonInput2 = Utility.getSampleJson("user2.json");
    }
    
    @Test
    public void create() throws Exception {
        // Setup mock for request
        
        Mockito.when(request.body()).thenReturn(jsonInput1);
        String result = userService.createUser(request, response);
        String expectedResult = Utility.prettyPrint(new ResponseMessage("User record inserted", 200));
        Assert.assertEquals("Result matches", expectedResult, result);
        userService.removeUser(request, response);
    }
    
    
    @Test
    public void findUserById() throws Exception {
        // Setup mock for request
        
        Mockito.when(request.body()).thenReturn(jsonInput2);
        String result = userService.findUserById(request, response);
        String expectedResult = Utility.prettyPrint(new ResponseMessage("User record not found", Utility.HTTP_NOT_FOUND));
        Assert.assertEquals("No user found", expectedResult, result);
    }
    
    @Test
    public void getAllUsers() throws Exception {

        String result = userService.getAllUsers(request, response);
        String expectedResult = Utility.prettyPrint(new ResponseMessage("Database Empty", Utility.HTTP_NOT_FOUND));
        Assert.assertNotEquals("Database not empty", expectedResult, result);
    }

    

}
