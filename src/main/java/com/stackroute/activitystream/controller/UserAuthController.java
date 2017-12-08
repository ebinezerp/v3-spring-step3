package com.stackroute.activitystream.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.activitystream.dao.UserDAO;
import com.stackroute.activitystream.model.User;


/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */

public class UserAuthController {

	/*
	 * Autowiring should be implemented for the UserDAO. Please note that 
	 * we should not create any object using the new keyword 
	 */
	
	@Autowired
	UserDAO userDAO;
	


	/* Define a handler method which will authenticate a user by reading the Serialized user
	 * object from request body containing the username and password and validating the same. Post login, the 
	 * username will have to be stored into session object, so that we can check whether the user is logged in for all
	 * other services. 
	 * This handler method should return any one of the status messages basis on different
	 * situations:
	 * 1. 200(OK) - If login is successful
	 * 2. 500(INTERNAL SERVER ERROR) - If login is not successful
	 * 
	 * This handler method should map to the URL "/api/authenticate" using HTTP POST method
	*/
	
	@PostMapping("/api/authenticate")
	public ResponseEntity<User> login(@RequestBody User user,HttpSession session)
	{
		 boolean auth=userDAO.validate(user.getUsername(), user.getPassword());
		
		 
		 if(auth)
		 {
			 user=userDAO.get(user.getUsername());
			 session.setAttribute("username", user.getUsername());
			return new ResponseEntity<User>(user,HttpStatus.OK);
		 }
		 else
		 {
			 return new ResponseEntity<User>(user,HttpStatus.UNAUTHORIZED);
		 }
	}
	
	

	/* Define a handler method which will perform logout. Post logout, the user session is to be destroyed.
	 * This handler method should return any one of the status messages basis on different
	 * situations:
	 * 1. 200(OK) - If logout is successful
	 * 2. 400(BAD REQUEST) - If logout has failed
	 * 
	 * This handler method should map to the URL "/api/logout" using HTTP PUT method
	*/ 
	
	@PutMapping("/api/logout")
	public ResponseEntity<User> logout(HttpSession session)
	{
		String username=(String)session.getAttribute("username");
		User user=userDAO.get(username);
		if(user!=null)
		{
			session.removeAttribute("username");
			session.invalidate();
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}else
		{
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
	}
	

}
