package com.stackroute.activitystream.daoimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.activitystream.dao.UserDAO;
import com.stackroute.activitystream.model.User;

/*
* This class is implementing the UserCircleDAO interface. This class has to be annotated with 
* @Repository annotation.
* @Repository - is an annotation that marks the specific class as a Data Access Object, 
* thus clarifying it's role.
* @Transactional - The transactional annotation itself defines the scope of a single database 
* 					transaction. The database transaction happens inside the scope of a persistence 
* 					context.  
* */
@Repository("userDAO")
@Transactional
public class UserDAOImpl implements UserDAO{
	/*
	 * Autowiring should be implemented for the SessionFactory. 
	 */
	
@Autowired
private SessionFactory sessionFactory;
	
	/*
	 * Create a new user 
	 */
	public boolean save(User user) {
		// TODO Auto-generated method stub
		try {
			sessionFactory.getCurrentSession().save(user);
			return true;
		}catch(Exception e)
		{
			return false;
		}
	}

	/*
	 * Update an existing user 
	 */
	public boolean update(User user) {
		// TODO Auto-generated method stub
		try {
			sessionFactory.getCurrentSession().update(user);
			return true;
		}catch(Exception e)
		{
			return false;
		}
	}

	
	/*
	 * Remove an existing user 
	 */
	public boolean delete(User user) {
		// TODO Auto-generated method stub
		try {
			sessionFactory.getCurrentSession().delete(user);
			return true;
		}catch(Exception e)
		{
		return false;
		}
	}

	/*
	 * Retrieve all available user 
	 */
	public List<User> list() {
		// TODO Auto-generated method stub
		try
		{
		return	sessionFactory.getCurrentSession().createQuery("From User",User.class).getResultList();
		}catch(Exception e)
		{
			return null;
		}
	}

	/*
	 * validate an user 
	 */
	public boolean validate(String id, String password) {
		// TODO Auto-generated method stub
		try
		{
			sessionFactory.getCurrentSession().createQuery("From User where username=:id and password=:password",User.class)
			.setParameter("id", id)
			.setParameter("password", password)
			.getSingleResult();
			return true;
		}catch(Exception e)
		{
			return false;
		}
	
	}

	/*
	 * Retrieve details of an user 
	 */
	public User get(String id) {
		// TODO Auto-generated method stub
		try
		{
			return sessionFactory.getCurrentSession().createQuery("From User where username=:id",User.class)
					.setParameter("id", id)
					.getSingleResult();
		}catch(Exception e)
		{
			return null;
		}
			
	}
	/*
	 * check whether a user exists with a given userId 
	 */
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		try
		{
			sessionFactory.getCurrentSession().createQuery("From User where username=:id",User.class)
			.setParameter("id", id)
			.getSingleResult();
			return true;
		}catch(Exception e)
		{
			return false;
		}
	}

}
