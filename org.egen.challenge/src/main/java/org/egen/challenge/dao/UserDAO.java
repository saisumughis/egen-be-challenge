package org.egen.challenge.dao;

import java.util.List;

import org.bson.Document;

/** Interface for User DAO operations
 * @author sai.sumughi
 *
 */
public interface UserDAO {
	
	public Document createUser(String jsonInput);
	public List<Document> getAllUsers();
	public Document updateUser(String jsonInput);
	public Document findUserById(String id);
	public Document removeUser(String jsonInput);

}
