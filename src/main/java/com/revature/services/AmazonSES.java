package com.revature.services;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;

/**
 * This class uses AWS's SDK and AWS SES to send an email to a client when called. 
 * Sends a Jwt token as a query string in a link. 
 * For example /password-reset?token=$tokenValue
 * 
 * @author andrewhughes
 */
public class AmazonSES {

	// The sender of emails
	final String FROM = "andyhughes39@gmail.com";
	
	final String SUBJECT = "Hello from AWS SES";
	
	final String HTMLBODY = "<h1>Please reset your password</h1>"
			+ "<a href='http://localhost:4200/password-reset?token=$tokenValue>";
	
	final String TEXTBODY = "Please click the link below"
			+ "http://localhost:4200/password-reset?token=$tokenValue";
	
	
	/**
	 * Creates an AmazonSimpleEmailService using AWS sdk and .aws secret on the host's
	 * machine. 
	 * 
	 * Replaces the class variables with method parameters and attempts to send the 
	 * email with jwt token as query param.
	 * 
	 * @param firstName
	 * @param email
	 * @param token
	 * @return
	 */
	public boolean sendPasswordResetRequest(String firstName, String email, String token)
	  {
	      boolean returnValue = false;
	 
	      AmazonSimpleEmailService client = 
	          AmazonSimpleEmailServiceClientBuilder.standard()
	            .withRegion(Regions.US_EAST_1).build();
	      
	      // replace all variables with method parameters
	      String htmlBodyWithToken = HTMLBODY.replace("$tokenValue", token);
	             htmlBodyWithToken = htmlBodyWithToken.replace("$firstName", firstName);
	        
	      String textBodyWithToken = TEXTBODY.replace("$tokenValue", token);
	             textBodyWithToken = textBodyWithToken.replace("$firstName", firstName);
	      
	      
	      SendEmailRequest request = new SendEmailRequest()
	          .withDestination(
	              new Destination().withToAddresses( email ) )
	          .withMessage(new Message()
	              .withBody(new Body()
	                  .withHtml(new Content()
	                      .withCharset("UTF-8").withData(htmlBodyWithToken))
	                  .withText(new Content()
	                      .withCharset("UTF-8").withData(textBodyWithToken)))
	              .withSubject(new Content()
	                  .withCharset("UTF-8").withData(SUBJECT)))
	          .withSource(FROM);

	      // send the email and obtain a result
	      SendEmailResult result = client.sendEmail(request); 
	      if(result != null && (result.getMessageId()!=null && !result.getMessageId().isEmpty()))
	      {
	          returnValue = true;
	      }
	      
	      // return the result
	      return returnValue;
	  }
	
}
