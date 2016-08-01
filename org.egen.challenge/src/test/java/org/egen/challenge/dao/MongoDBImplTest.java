package org.egen.challenge.dao;


import org.bson.Document;
import org.egen.challenge.spark.Application;
import org.egen.challenge.utils.Utility;
import org.junit.runner.RunWith;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import spark.Request;
import spark.Response;

@RunWith(MockitoJUnitRunner.class)
public class MongoDBImplTest {
	
	private static UserDAO userDAO;
	private static String jsonInput1;
	private static String jsonInput2;
	private static MongoCollection<Document> dbCollection;
	private static String collection = "testUsers";
	
	@BeforeClass
    public static final void setUp() throws Exception {
		MongoDatabase mongoDB = Application.mongoConnection();
		if(null == mongoDB){
			Assert.fail("Cannot connect to database");
		}
		dbCollection = mongoDB.getCollection(collection);
    	userDAO = new UserDAOMongoDBImpl(mongoDB, collection);
    	jsonInput1 = Utility.getSampleJson("user.json");
    	jsonInput2 = Utility.getSampleJson("user2.json");
	}
	
	@Test
	public void createUser() {
		userDAO.createUser(jsonInput1);
		userDAO.createUser(jsonInput2);
	    Assert.assertEquals("Expected to get 2 users", 2, userDAO.getAllUsers().size());
	    userDAO.removeUser("1");
	    userDAO.removeUser("2");
	    Assert.assertEquals("Expected to get empty list of users", 0, userDAO.getAllUsers().size());
	}
	
	@Test
	public void createDuplicateUser() {
		userDAO.createUser(jsonInput1);
		userDAO.createUser(jsonInput1);
	    Assert.assertEquals("Expected to only 1 user", 1, userDAO.getAllUsers().size());
	    userDAO.removeUser(jsonInput1);
	}
	
	
	@Test
	public void removeInvalidUser() {
		Document doc = userDAO.removeUser("Invalid");
	    Assert.assertEquals("User not found", null, doc);

	}
	
	@Test
	public void updateUser(){
		userDAO.createUser(jsonInput1);
		userDAO.updateUser(jsonInput1);
		Assert.assertEquals("Expected to only 1 user", 1, userDAO.getAllUsers().size());
		userDAO.removeUser(jsonInput1);
	}
	
	
	@Test
	public void findInvalidUserById() {
		Document doc = userDAO.findUserById("invaliduser");
	    Assert.assertEquals("User not found", null, doc);
	}
}

