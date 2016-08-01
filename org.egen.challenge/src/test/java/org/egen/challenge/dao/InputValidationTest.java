package org.egen.challenge.dao;

import java.io.IOException;


import org.egen.challenge.utils.Utility;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;


@RunWith(MockitoJUnitRunner.class)
public class InputValidationTest {
	
	private static String jsonInput1;
	private static String jsonInput2;
	
	@BeforeClass
    public static final void setUp() throws Exception {
    	jsonInput1 = Utility.getSampleJson("user.json");
    	jsonInput2 = Utility.getSampleJson("invaliduser.json");
	}
	
	
	@Test
	public void validInput() throws ProcessingException, IOException{
		boolean res = Utility.validate(jsonInput1);
		Assert.assertEquals("Expected true", true, res);
		
		
		
	}
	
	@Test
	public void invalidInput() throws ProcessingException, IOException{
		boolean res = Utility.validate(jsonInput2);
		Assert.assertEquals("Expected false", false, res);
	}

}
