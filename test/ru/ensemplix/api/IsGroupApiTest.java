package ru.ensemplix.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IsGroupApiTest {
	
	@Test
	public void testIsGroup() throws Exception {		
		IsGroupApi groupApi = new IsGroupApi();
		groupApi.setId("enber");
		groupApi.execute();
		assertFalse(groupApi.isGroup());
		
		groupApi = new IsGroupApi();
		groupApi.setId("idiotikshow");
		groupApi.execute();
		assertTrue(groupApi.isGroup());
	}

}
