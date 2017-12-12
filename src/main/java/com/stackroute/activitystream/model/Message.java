package com.stackroute.activitystream.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;


/*
 * The class "Message" will be acting as the data model for the message Table in the database. Please
 * note that this class is annotated with @Entity annotation. Hibernate will scan all package for 
 * any Java objects annotated with the @Entity annotation. If it finds any, then it will begin the 
 * process of looking through that particular Java object to recreate it as a table in your database.
 */

@Component
@Entity
public class Message {

	/*
	 * This class should have eight fields
	 * (messageId,senderName,receiverId,circleName,postedDate,streamType,message,tag). Out of these four fields, the
	 * field messageId should be auto-generated. This class should also contain
	 * the getters and setters for the fields. The value of postedDate should
	 * not be accepted from the user but should be always initialized with the
	 * system date
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int messageId;
	

	
	private String senderName;
	
	private String receiverId; //object
	
	private String circleName; //object
	
	private Date postedDate; //object 
	
	private String streamType;
	
	
	private String message;
	
	private String tag;  //Object

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate() {
		this.postedDate = new Date();
	}

	public String getStreamType() {
		return streamType;
	}

	public void setStreamType(String streamType) {
		this.streamType = streamType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
	public Message()
	{
		
	}
	

	public Message(String string, Object object, String string2, Object object2, String string3, String string4, String string5)
	{
		
	}
	
	
	/*public Message(String senderName, String circleName, Date postedDate, String message, String receiverId, String streamType,
			String tag) {
		// TODO Auto-generated constructor stub
	}*/

	/*public void setMessage(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setStreamType(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setSenderName(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setTag(String string) {
		// TODO Auto-generated method stub
		
	}*/

}
