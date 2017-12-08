package com.stackroute.activitystream.daoimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.activitystream.dao.CircleDAO;
import com.stackroute.activitystream.dao.UserCircleDAO;
import com.stackroute.activitystream.dao.UserDAO;
import com.stackroute.activitystream.model.UserCircle;

/*
* This class is implementing the UserCircleDAO interface. This class has to be annotated with 
* @Repository annotation.
* @Repository - is an annotation that marks the specific class as a Data Access Object, 
* thus clarifying it's role.
* @Transactional - The transactional annotation itself defines the scope of a single database 
* 					transaction. The database transaction happens inside the scope of a persistence 
* 					context.  
* */
@Repository("userCircleDAO")
@Transactional
public class UserCircleDAOImpl implements UserCircleDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.
	 */
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	CircleDAO circleDAO;

	/*
	 * Add a user to a circle
	 */
	public boolean addUser(String username, String circleName) {
		
		try {
			if(userDAO.get(username)!=null && circleDAO.get(circleName)!=null)
			{
			sessionFactory.getCurrentSession().createQuery("insert into UserCircle(username,circleName) select c.creatorId,c.circleName from Circle c where c.creatorId=:id and c.circleName=:cname")
		.setParameter("id", username)
		.setParameter("cname", circleName)
		.executeUpdate();
		return true;
			}else
			{
				return false;
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		

	}

	/*
	 * Remove a user from a circle
	 */
	public boolean removeUser(String username, String circleName) {
		// TODO Auto-generated method stub
		try {
			
		int i=	sessionFactory.getCurrentSession().createQuery("delete from UserCircle where username=:username and circleName=:circleName")
			.setParameter("username", username)
			.setParameter("circleName", circleName)
			.executeUpdate();
			if(i>0)
			{
			return true;
			}
			else
			{
				return false;
			}
		}catch(Exception e)
		{
			return false;
		}
		
	}

	/*
	 * Retrieve unique UserCircle object which contains a specific username and
	 * circleName
	 */
	public UserCircle get(String username, String circleName) {
		// TODO Auto-generated method stub
		try {
			return sessionFactory.getCurrentSession().createQuery("From UserCircle where username=:username and circleName=:circleName",UserCircle.class)
			.setParameter("username", username)
			.setParameter("circleName", circleName)
			.getSingleResult();
		}catch(Exception e)
		{
			return null;
		}
	}

	/*
	 * Retrieve all subscribed circles by a user
	 */
	@SuppressWarnings("unchecked")
	public List<String> getMyCircles(String username) {
		// TODO Auto-generated method stub
		try
		{
			List<String> circles=sessionFactory.getCurrentSession().createQuery("select circleName from UserCircle where username=:username")
			.setParameter("username", username)
			.getResultList();
			return circles;
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

}
