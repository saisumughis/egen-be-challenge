package org.egen.challenge.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.egen.challenge.model.User;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;

/** Helper class for validating the json input
 * @author sai.sumughi
 *
 */
public class Utility {
	
	 public static final int HTTP_BAD_REQUEST = 400;
	 public static final int HTTP_SUCCESS = 200;
	 public static final int HTTP_NOT_FOUND = 404;
	 
	
	 public static boolean jsonValidate() throws IOException{
		 
		 byte[] json = Files.readAllBytes(Paths.get("src/main/resources", "user.txt"));
			ObjectMapper objectMapper = new ObjectMapper();
			User user = null;
			 try {
				user = objectMapper.readValue(json, User.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// TODO Auto-generated method stub

			return false;		 
	 }
	 
	 
	 public static String getSampleJson(String filename) throws IOException{
		 
		 byte[] json = Files.readAllBytes(Paths.get("src/main/resources", filename));
			ObjectMapper objectMapper = new ObjectMapper();
			User user = null;
			 try {
				user = objectMapper.readValue(json, User.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// TODO Auto-generated method stub
		 return	objectMapper.writeValueAsString(user);	 
	 }
	 
	 public static String prettyPrint(Object object) throws JsonProcessingException{
		 ObjectMapper objectMapper = new ObjectMapper();
		 return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		
		 
	 }
	 

	public static String readFile(String fileName) throws IOException {
		    BufferedReader br = new BufferedReader(new FileReader(fileName));
		    try {
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();

		        while (line != null) {
		            sb.append(line);
		            //sb.append("\n");
		            line = br.readLine();
		        }
		        return sb.toString();
		    } finally {
		        br.close();
		    }
		}
	
	public static boolean validateForTest(String name) throws ProcessingException, IOException{
		 
		 	byte[] json = Files.readAllBytes(Paths.get("src/main/resources", "user.json"));
			ObjectMapper objectMapper = new ObjectMapper();
			User user = objectMapper.readValue(json, User.class);
			
			String jsonData = Utility.prettyPrint(user);			

			
			String jsonSchema = Utility.readFile("src/main/resources/schema.json");
			
			

	       final JsonNode data = JsonLoader.fromString(jsonData);
	       final JsonNode schema = JsonLoader.fromString(jsonSchema);

	       final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
	       JsonValidator validator = factory.getValidator();

	       ProcessingReport report = validator.validate(schema, data);
	       return report.isSuccess();
			
	 }
	
	public static boolean validate(String json) throws ProcessingException, IOException{
		 
	 	
		ObjectMapper objectMapper = new ObjectMapper();
		User user = objectMapper.readValue(json, User.class);
		
		String jsonData = Utility.prettyPrint(user);			
		//System.out.println(jsonData);
		
		//from jsonschema.net
		String jsonSchema = Utility.readFile("src/main/resources/schema.json");
		
		//System.out.println(jsonSchema);

       final JsonNode data = JsonLoader.fromString(jsonData);
       final JsonNode schema = JsonLoader.fromString(jsonSchema);

       final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
       JsonValidator validator = factory.getValidator();

       ProcessingReport report = validator.validate(schema, data);
       //System.out.println(report.isSuccess());
       return report.isSuccess();
		
 }



}
