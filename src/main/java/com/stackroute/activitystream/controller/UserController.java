package com.stackroute.activitystream.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.stackroute.activitystream.dao.UserDAO;
import com.stackroute.activitystream.model.Message;
import com.stackroute.activitystream.model.User;


/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
public class UserController {
	/*
	 * Autowiring should be implemented for the UserDAO. 
	 * Please note that we should not create any object using the new keyword 
	 */
	
	@Autowired
	UserDAO userDAO;
	
  
	/* Define a handler method which will list all the available users.
	 * This handler method should return any one of the status messages basis on different
	 * situations:
	 * 1. 200(OK) - If login is successful
	 * 2. 401(UNAUTHORIZED) - If the user is not logged in
	 * 
	 * This handler method should map to the URL "/api/user" using HTTP GET method
	*/

	@GetMapping("/api/user")
	public ResponseEntity<List<User>> usersList(HttpSession session)
	{
		if(session.getAttribute("username")!=null)
		{
		List<User> users=userDAO.list();
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
		}else
		{
			return new ResponseEntity<List<User>>(HttpStatus.UNAUTHORIZED);
		}
		
	}
	

	/* Define a handler method which will show details of a specific user.
	 * This handler method should return any one of the status messages basis on different
	 * situations:
	 * 1. 200(OK) - If login is successful
	 * 2. 401(UNAUTHORIZED) - If the user is not logged in
	 * 3. 404(NOT FOUND) - If the user with the searched for is not found
	 * This handler method should map to the URL "/api/user/{username}" using HTTP GET method
	 * where "username" should be replaced by a username without {}
	*/
	@GetMapping("/api/user/{username}")
	public ResponseEntity<User> aSpecifiedUser(@PathVariable("username") String username,HttpSession session)
	{
		System.out.println("entered");
		System.out.println(username);
		if(session.getAttribute("username")!=null)
		{
			User user=userDAO.get((String)session.getAttribute("username"));
			if(user!=null)
			{
				System.out.println("entered in usernot null");
				return new ResponseEntity<User>(user,HttpStatus.OK);
				
			}else
			{
				return new ResponseEntity<User>(user,HttpStatus.NOT_FOUND);
			}
		}else
		{
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	

	/* Define a handler method which will create a specific user by reading the 
	 * Serialized object from request body and save the user details in user table 
	 * in database. This handler method should return any one of the status messages
	 *  basis on different situations:
	 * 1. 201(CREATED) - If the user is successfully created
	 * 2. (CONFLICT) - If the username conflicts with any existing user
	 * 
	 * Note:
	 * ------
	 * This method can be called without being logged in as well as when a new user will
	 * use the app, he will register himself first before login.
	 * This handler method should map to the URL "/api/user" using HTTP POST method
	*/
	
	@PostMapping("/api/user")
	public ResponseEntity<User> createUser(@RequestBody User user)
	{
		User userExisted=userDAO.get(user.getUsername());
		if(userExisted!=null)
		{
			return new ResponseEntity<User>(userExisted,HttpStatus.CONFLICT);
		}else
		{
			userDAO.save(user);
		return new ResponseEntity<User>(user,HttpStatus.CREATED);
		}
	}

	/* Define a handler method which will update an specific user by reading the 
	 * Serialized object from request body and save the updated user details in user table 
	 * in database. This handler method should return any one of the status messages
	 *  basis on different situations:
	 * 1. 200(OK) - If the user is successfully updated
	 * 2. 404(NOT FOUND) - If the user with specified username is not found
	 * 3. (UNAUTHORIZED) - If the user trying to perform the action has not logged in
	 * 
	 * This handler method should map to the URL "/api/user" using HTTP PUT method
	*/
	
	@PutMapping("/api/user/{username}")
	public ResponseEntity<User> updateUser(@PathVariable("username") String username,@RequestBody User user,HttpSession session)
	{
		if(session.getAttribute("username")!=null && !((String)session.getAttribute("username")).equals("username"))
		{
			
			
			if(userDAO.get(username)!=null)
			{
				userDAO.update(user);
				return new ResponseEntity<User>(user,HttpStatus.OK);
			}else
			{
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
			
		}else
		{
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		}
	}
	
}
