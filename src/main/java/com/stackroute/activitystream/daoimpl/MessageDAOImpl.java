package com.stackroute.activitystream.daoimpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.activitystream.dao.CircleDAO;
import com.stackroute.activitystream.dao.MessageDAO;
import com.stackroute.activitystream.dao.UserCircleDAO;
import com.stackroute.activitystream.dao.UserDAO;
import com.stackroute.activitystream.model.Message;
import com.stackroute.activitystream.model.UserTag;


/*
* This class is implementing the MessageDAO interface. This class has to be annotated with 
* @Repository annotation.
* @Repository - is an annotation that marks the specific class as a Data Access Object, 
* thus clarifying it's role.
* @Transactional - The transactional annotation itself defines the scope of a single database 
* 					transaction. The database transaction happens inside the scope of a persistence 
* 					context.  
* */
@Repository("messageDAO")
@Transactional
public class MessageDAOImpl implements MessageDAO {

	

	/*
	 * Autowiring should be implemented for the SessionFactory. 
	 */
	
	@Autowired
	SessionFactory sessionFactory;

	/*
	 * Autowiring should be implemented for CircleDAO 
	 */
	
	@Autowired
	CircleDAO circleDAO;
	
	/*
	 * Autowiring should be implemented for UserDAO. 
	 */
	
	
	@Autowired
	UserDAO userDAO;
	
	
	/*
	 * Autowiring should be implemented for UserCircleDAO. 
	 */
	

	   @Autowired
	   UserCircleDAO userCircleDAO;
	
	/*
	 * Retrieve messages from a specific circle. For improved performace, we will
	 * implement retrieving the messages partially by implementing pagination
	 */
	public List<Message> getMessagesFromCircle(String circleName, int pageNumber) {
		
		   if(circleDAO.get(circleName)!=null)
		   {
			   return sessionFactory.getCurrentSession().createQuery("From Message where circleName=:circleName",Message.class)
					   .setParameter("circleName", circleName)
					   .setFetchSize(10)
					   .setFirstResult((pageNumber-1)*10)
					   .getResultList();
			   
		   }else
		   {
			   return null;
		   }
	}

	/*
	 * Retrieve messages between two users. Please note that in a one to one 
	 * conversation, both users can act sometimes as a sender and sometimes as a 
	 * recipient. For improved performace, we will implement retrieving the 
	 * messages partially by implementing pagination
	 */
	public List<Message> getMessagesFromUser(String username, String otherUsername, int pageNumber) {
		// TODO Auto-generated method stub
		  if(userDAO.get(username)!=null && userDAO.get(otherUsername)!=null)
		   {
			  try {
			   return sessionFactory.getCurrentSession().createQuery("From Message where senderName=:senderName and receiverId=:receiverId",Message.class)
					   .setParameter("senderName", username)
					   .setParameter("receiverId", otherUsername)
					   .setFetchSize(10)
					   .setFirstResult((pageNumber-1)*10)
					   .getResultList();
			  }catch (Exception e) {
				// TODO: handle exception
				  System.out.println(e);
				  return null;
			}
			   
		   }else
		   {
			   return null;
		   }
	}
	/*
	 * Retrieve messages from all circles subscribed by a specific user. For improved 
	 * performace, we will implement retrieving the messages partially by implementing pagination
	 */
	public List<Message> getMessages(String username, int pageNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * send messages from a specific circle. The posted message should have the current
	 * timestamp as the posted timestamp.
	 */
	public boolean sendMessageToCircle(String circleName, Message message) {
		// TODO Auto-generated method stub
		try
		{
			if(circleDAO.get(circleName)!=null && userDAO.get(message.getSenderName())!=null)
			{
				sessionFactory.getCurrentSession().save(message);
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
	 * Send message to a specific user
	 */
	public boolean sendMessageToUser(String username, Message message) {
		// TODO Auto-generated method stub
		if(userDAO.get(username)!=null && userDAO.get(message.getSenderName())!=null)
		{
			sessionFactory.getCurrentSession().save(message);
			return true;
		}else
		{
			return false;
		}
	}


	/*
	 * Retrieve all the tags available in the messages
	 */
	public List<String> listTags() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Retrieve all tags subscribed by a user
	 */
	public List<String> listMyTags(String username) {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 * Retrieve all messages containing a specific tag. For improved performace, we will
	 * implement retrieving the messages partially by implementing pagination
	 */
	public List<Message> showMessagesWithTag(String tag, int pageNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Subscribe user to a tag. Please implement validation to check whether the
	 * user and tag both exists.
	 */
	public boolean subscribeUserToTag(String username, String tag) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * Unsubscribe a user from a tag. Please implement validation to check whether
	 * the user has subscribed to the tag or not
	 */
	public boolean unsubscribeUserToTag(String username, String tag) {
		// TODO Auto-generated method stub
		return false;

	}
	/*
	 * Retrieve UserTag object for a username and a tag
	 */
	public UserTag getUserTag(String username, String tag) {
		// TODO Auto-generated method stub
		return null;
	}

}
