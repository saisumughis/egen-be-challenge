package org.egen.challenge.service;

import java.io.IOException;
import java.util.List;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;

import spark.Request;
import spark.Response;

public interface UserService {

	
	public String createUser(Request req, Response res) throws JsonProcessingException, ProcessingException, IOException;
	public String getAllUsers(Request req, Response res) throws JsonProcessingException;
	public String updateUser(Request req, Response res) throws JsonProcessingException, ProcessingException, IOException;
	public String removeUser(Request req, Response res) throws JsonProcessingException, ProcessingException, IOException;
	public String findUserById(Request req, Response res) throws JsonProcessingException, ProcessingException, IOException, Exception;

}
