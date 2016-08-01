package org.egen.challenge.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.egen.challenge.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

/** Mongo DB implementation for user management
 * @author sai.sumughi
 *
 */
public class UserDAOMongoDBImpl implements UserDAO{
	private final Logger logger = LoggerFactory.getLogger(UserDAOMongoDBImpl.class);
	private MongoDatabase database;	
	private MongoCollection<Document> collection;
	
	/** Constructor to get the collection
	 * @param database - name of the database connected
	 * @param collectionName - name of the collection to connect 
	 */
	public UserDAOMongoDBImpl(MongoDatabase database, String collectionName){
		this.database = database;
		this.logger.info("Getting collection:" + collectionName);
		this.collection = database.getCollection(collectionName);	
	}

	/** create a user record in the database
	 * @param jsonInput - input json from request body
	 * @ereturn Document - record of user
	 */
	public Document createUser(String jsonInput) {
	
		Document doc = Document.parse(jsonInput);
		String id = doc.getString("id");
		Document docUser = findUserById(id);
		if(null == docUser || docUser.isEmpty()){
			this.logger.info("Inserting user record");
		    this.collection.insertOne(doc);
		}
		return docUser;
	 
	}
	
	/** To find a user in the database
	 * @param id - id of the user
	 * @return document - user record if found
	 */
	public Document findUserById(String id){
		this.logger.info("Finding user record");
		Document user = this.collection.find(Filters.eq("id", id)).projection(Projections.excludeId()).first();
		return user;
	}

	public List<Document> getAllUsers() {
		// TODO Auto-generated method stub
		this.logger.info("Getting the list of all users");
		List<Document> documents = new ArrayList<>();
	    MongoCursor<Document> cursor = this.collection.find().projection(Projections.excludeId()).iterator();
		while (cursor.hasNext()) {
		       documents.add(cursor.next());
		}
		return documents;
	}

	public Document updateUser(String jsonInput) {
		// TODO Auto-generated method stub
		this.logger.info("Updating user record");
		Document doc = Document.parse(jsonInput);
		String id = doc.getString("id");
		Document user = findUserById(id);
		if(null == user || user.isEmpty()){
			return user;
		}
		this.collection.updateOne(user, new Document("$set", doc));
		return doc;
	}
	
	public Document removeUser(String id) {
		this.logger.info("Removing user record");
		Document user = findUserById(id);
		if(null == user || user.isEmpty()){
			return user;
		}
		this.collection.deleteOne(user);
		return user;
	}
	

}
