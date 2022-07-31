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


public class AmazonSES {

	final String FROM = "andyhughes39@gmail.com";
	
	final String SUBJECT = "Hello from AWS SES";
	
	final String HTMLBODY = "<h1>Please reset your password</h1>"
			+ "<a href='http://localhost:8080/password-reset.html?token=$tokenValue>";
	
	final String TEXTBODY = "Please click the link below"
			+ "http://localhost:8080/password-reset.html?token=$tokenValue";
	
	

	public boolean sendPasswordResetRequest(String firstName, String email, String token)
	  {
	      boolean returnValue = false;
	 
	      AmazonSimpleEmailService client = 
	          AmazonSimpleEmailServiceClientBuilder.standard()
	            .withRegion(Regions.US_EAST_1).build();
	      
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

	      SendEmailResult result = client.sendEmail(request); 
	      if(result != null && (result.getMessageId()!=null && !result.getMessageId().isEmpty()))
	      {
	          returnValue = true;
	      }
	      
	      return returnValue;
	  }
	
}
