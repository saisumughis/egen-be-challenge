package org.egen.challenge.controller;

import java.io.IOException;

import org.bson.Document;
import org.egen.challenge.service.UserService;
import org.egen.challenge.utils.Utility;
import org.egen.challenge.model.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;

import spark.Request;
import spark.Response;
import spark.Spark;

/**
 * @author sai.sumughi
 * Controller class to define the web services for user
 */
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	private UserService userService;
	
	/**Constructor to configure the services
	 * @param userService
	 */
	public UserController(UserService userService){
		this.userService = userService;
		
		this.logger.info("Configuring spark services");
		
		Spark.get("/", (req, res) -> Utility.prettyPrint(new ResponseMessage("Webservice for User management application", Utility.HTTP_SUCCESS)));
		Spark.post("/user", "application/json", (req, res) -> this.userService.createUser(req, res));
		Spark.put("/user/:id", "application/json", (req, res) -> this.userService.updateUser(req, res));
		Spark.get("/users", "application/json", (req, res) -> this.userService.getAllUsers(req, res));
		Spark.get("/user/:id", "application/json", (req, res) -> this.userService.findUserById(req, res));
		Spark.delete("/user/:id", "application/json", (req, res) -> this.userService.removeUser(req, res));
	}
	
	
}
