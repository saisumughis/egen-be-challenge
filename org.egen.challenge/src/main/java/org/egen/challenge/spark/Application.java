package org.egen.challenge.spark;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.egen.challenge.controller.UserController;
import org.egen.challenge.dao.UserDAOMongoDBImpl;
import org.egen.challenge.model.User;
import org.egen.challenge.service.UserService;
import org.egen.challenge.service.UserServiceImpl;
import org.egen.challenge.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import spark.Spark;

public class Application {
	
	 private static final Logger logger = LoggerFactory.getLogger(Application.class);
	 private static final String DATABASE_NAME = "users";
	 private static final String COLLECTION_NAME = "egen";
	 
	 
	 public Application() throws IOException, ProcessingException{
		 
		 logger.info("Spark server running on default port 4567");
		 new UserController(new UserServiceImpl(new UserDAOMongoDBImpl(mongoConnection(), COLLECTION_NAME)));
		 
	 }

	 public static MongoDatabase mongoConnection(){
		 logger.info("Connecting to database: " + DATABASE_NAME);
		 MongoClient mongoClient = null;
		 try{
			 mongoClient = new MongoClient("localhost" , 27017);
		 }catch(Exception e){
			 logger.error("Error connecting to database at port 27017");
		 }
		 
		 return mongoClient.getDatabase(DATABASE_NAME);
	 }
	 
	public static void main(String[] args) throws IOException, ProcessingException {
		// TODO Auto-generated method stub
		new Application();
		
	}

}
