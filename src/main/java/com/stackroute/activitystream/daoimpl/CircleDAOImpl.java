package com.stackroute.activitystream.daoimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.activitystream.dao.CircleDAO;
import com.stackroute.activitystream.dao.UserDAO;
import com.stackroute.activitystream.model.Circle;
import com.stackroute.activitystream.model.UserCircle;

/*
* This class is implementing the CircleDAO interface. This class has to be annotated with 
* @Repository annotation.
* @Repository - is an annotation that marks the specific class as a Data Access Object, 
* thus clarifying it's role.
* @Transactional - The transactional annotation itself defines the scope of a single database 
* 					transaction. The database transaction happens inside the scope of a persistence 
* 					context.  
* */
@Transactional
@Repository("circleDAO")
public class CircleDAOImpl implements CircleDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.
	 */

	@Autowired
	SessionFactory sessionFactory;

	/*
	 * Autowiring should be implemented for UserDAO.
	 */

	@Autowired
	UserDAO userDAO;

	/*
	 * Create a new circle
	 */
	public boolean save(Circle circle) {
		// TODO Auto-generated method stub
		try {

			if (userDAO.get(circle.getCreatorId()) != null) {

				sessionFactory.getCurrentSession().save(circle);

				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	/*
	 * Update an existing circle
	 */
	public boolean update(Circle circle) {
		// TODO Auto-generated method stub
		try {
			sessionFactory.getCurrentSession().update(circle);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;

		}
	}

	/*
	 * delete an existing circle
	 */
	public boolean delete(Circle circle) {
		// TODO Auto-generated method stub
		try {
			sessionFactory.getCurrentSession().delete(circle);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	/*
	 * Retrieve a specific circle
	 */
	public Circle get(String circleName) {
		// TODO Auto-generated method stub
		try {
			Circle circle = sessionFactory.getCurrentSession().createQuery("From Circle where circleName=:circleName",Circle.class)
					.setParameter("circleName", circleName)
					.getSingleResult();

			return circle;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

	/*
	 * retrieving all circles
	 */
	public List<Circle> getAllCircles() {
		// TODO Auto-generated method stub
		try {
			return sessionFactory.getCurrentSession().createQuery("From Circle", Circle.class).getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	/*
	 * Retrieving all circles that matches a search string
	 */
	@SuppressWarnings("unchecked")
	public List<Circle> getAllCircles(String searchString) {
		// TODO Auto-generated method stub
		try {
			return sessionFactory.getCurrentSession()
					.createQuery("From Circle where circlename like :searchstring", Circle.class)
					.setParameter("searchstring", searchString).getResultList();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

}
