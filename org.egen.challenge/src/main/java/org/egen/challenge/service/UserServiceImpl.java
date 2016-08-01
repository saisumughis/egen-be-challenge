package org.egen.challenge.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.egen.challenge.dao.UserDAO;
import org.egen.challenge.model.ResponseMessage;
import org.egen.challenge.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;

public class UserServiceImpl implements UserService{
	
	private UserDAO userDAO;
	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	public UserServiceImpl(UserDAO userDAO){
		this.userDAO = userDAO;
	}
	

	
	public String createUser(Request req, Response res) throws ProcessingException, IOException{
    	  	
		String json = req.body();
		this.logger.info("Validating against json schema");
	    if(Utility.validate(json)){
	    	this.logger.info("Valid input json");
	    	Document doc = this.userDAO.createUser(json);
	    	res.status(Utility.HTTP_SUCCESS);
	    	if(null != doc){
	    		 return Utility.prettyPrint(new ResponseMessage("User already exists", Utility.HTTP_SUCCESS));
	    	}
	    }
	    else{
	    		this.logger.info("Invalid input json");
	    	  res.status(Utility.HTTP_BAD_REQUEST);
	    	  return Utility.prettyPrint(new ResponseMessage("Invalid Json", Utility.HTTP_BAD_REQUEST));
	    }
	    this.logger.info("User inserted into the database");
	    return Utility.prettyPrint(new ResponseMessage("User record inserted", Utility.HTTP_SUCCESS));
	}
	
//	private boolean validate(String json) {
//		
//		ObjectMapper objectMapper = new ObjectMapper();
//		User user = null;
//		 try {
//			user = objectMapper.readValue(json, User.class);
//		} catch (JsonParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// TODO Auto-generated method stub
//		if(user.isValid())
//			return true;
//		return false;
//	}

	public String getAllUsers(Request req, Response res) throws JsonProcessingException{
		List<Document> docs = this.userDAO.getAllUsers();
		List<String> users = null;
		
		if(docs.size() == 0){
			this.logger.warn("Database empty");
			res.status(Utility.HTTP_NOT_FOUND);
			return Utility.prettyPrint(new ResponseMessage("Database Empty", Utility.HTTP_NOT_FOUND));
		}else{
			this.logger.info("Fetching all users");
			res.status(Utility.HTTP_SUCCESS);
			users = new ArrayList<String>();
			for(Document doc:docs){
				users.add(Utility.prettyPrint(doc));
			}
		}
		
		return users.stream().collect(Collectors.joining(","));
		
	}
	
	public String updateUser(Request req, Response res) throws ProcessingException, IOException{
		String json = req.body();
		if(Utility.validate(json)){
		Document doc = this.userDAO.updateUser(req.body());
		if(null == doc){
			this.logger.warn("No user found");
			res.status(Utility.HTTP_NOT_FOUND);
			return Utility.prettyPrint(new ResponseMessage("No user found", Utility.HTTP_NOT_FOUND));
		}
		
		this.logger.warn("User record updated");
		res.status(Utility.HTTP_SUCCESS);
		return Utility.prettyPrint(new ResponseMessage("User record Updated", Utility.HTTP_SUCCESS));
	}
		this.logger.warn("Invalid json");
		res.status(Utility.HTTP_BAD_REQUEST);
		return Utility.prettyPrint(new ResponseMessage("Invalid Json", Utility.HTTP_BAD_REQUEST));

	
	}

	public String removeUser(Request req, Response res)
			throws JsonProcessingException, ProcessingException, IOException {
		String id = req.params(":id");
		Document doc = this.userDAO.removeUser(id);
		if(null == doc){
			this.logger.warn("User not found");
			res.status(Utility.HTTP_NOT_FOUND);
			return Utility.prettyPrint(new ResponseMessage("User record not found", Utility.HTTP_NOT_FOUND));
		}
		
		this.logger.warn("User record deleted");
		res.status(Utility.HTTP_SUCCESS);
		return Utility.prettyPrint(new ResponseMessage("User recored deleted", Utility.HTTP_SUCCESS));
	}

	public String findUserById(Request req, Response res)
			throws JsonProcessingException, ProcessingException, IOException {
		String id = req.params(":id");
		Document doc = this.userDAO.findUserById(id);
		if(null == doc){
			this.logger.warn("User not found");
			res.status(Utility.HTTP_NOT_FOUND);
			return Utility.prettyPrint(new ResponseMessage("User record not found", Utility.HTTP_NOT_FOUND));
		}
		
		this.logger.warn("Returning user record");
		res.status(Utility.HTTP_SUCCESS);
		return Utility.prettyPrint(doc);
	}
	
	
	
	

}
