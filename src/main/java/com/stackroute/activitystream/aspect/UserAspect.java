package com.stackroute.activitystream.aspect;

import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAspect {
	
 private Logger userLogger;
	public UserAspect() {
		// TODO Auto-generated constructor stub
		userLogger=Logger.getLogger("userLogger");
	}
	
	@After("execution(public boolean save(*))")
	public void create()
	{
		userLogger.info("created");
		System.out.println("User Noted");
		/*System.out.println("fksdfkaskffjasklfklasjfklaskls"+session.getAttribute("username").toString());
		System.out.println(session.getAttribute("username").toString()+" is user created");*/
	}

}
