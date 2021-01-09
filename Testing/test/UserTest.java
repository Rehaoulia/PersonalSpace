package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.main.User;

public class UserTest {
	
	String expu = "username";
	String expp = "password";
	@Test
	public void testUsername() throws ClassNotFoundException{
		

		User user = new User(expu, expp);
		 String str = user.getUsername();
		assertTrue(str!="" && str!=null);
		assertFalse(str=="" || str == null);
		assertEquals(expu, str);
	}
	
	@Test
	public void testPassword() throws ClassNotFoundException{
		

		User user = new User(expu, expp);
		 String str = user.getPassword();
		assertTrue(str!="" && str!=null);
		assertFalse(str=="" || str == null);
		assertEquals(expp, str);
	}
}
