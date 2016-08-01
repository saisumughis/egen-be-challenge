package org.egen.challenge.model;

import java.time.LocalDateTime;
import java.util.Optional;


@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode(of= {"firstName", "lastName"})
@lombok.ToString(callSuper = true, of = {"firstName", "lastName"})
public class User {
	
	
	public String id;
	public String firstName;
	public String lastName;
	public String email;
	public Address address;
	public String dateCreated;
	public Company company;
	public String profilePic;
	
	//Simple validation instead of using json-schema-validator
//	public boolean isValid(){ 
//		 Optional optUser = Optional.ofNullable(this);
//		 if(!optUser.isPresent())
//			 return false;		 
//		 Optional optAddress = Optional.ofNullable(this.address);
//		 Optional optCompany = Optional.ofNullable(this.company);
//		 if(!optAddress.isPresent() || !optCompany.isPresent())
//			return false;
//		return true;
//	}
//	
	

}
