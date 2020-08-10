package com.macys.mst.transportation.functional.stepdefinitions;


import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.steps.context.StepsContext;

import com.macys.mst.transportation.functional.util.TibjmsSSLConnection;
import com.macys.mtp.runners.BaseServiceTest;
import io.qameta.allure.Step;
public class EMSConnectionAndValidationSteps extends BaseServiceTest{
	
	private static Logger log = Logger.getLogger(EMSConnectionAndValidationSteps.class.getName());
	public static TibjmsSSLConnection tibjmssslconnection;
    TextMessage message;
    List<Message> messages;
	
	public String PK_PO_NBR;
	public String PK_SYS_TYP_NBR;
	public String PK_ZL_DIVN_NBR;
	private StepsContext stepsContext;
	
	public EMSConnectionAndValidationSteps(StepsContext stepsContext) {
		this.stepsContext = stepsContext;
	}
	
	@Given("EMS Connection")
	@Step("Given EMS Connection")
	public void givenEMSConnection() throws Exception {
		 /* String serverUrl = FileConfig.getInstance().getStringConfigValue("ems.server.url");
			FileConfig from MTP not working*/	
	    tibjmssslconnection=TibjmsSSLConnection.getinstance();
						
	}

	@Then("receive message from Queue")
	@Step("Then receive message from Queue")
	public void thenReceiveMessageFromQueue() throws Exception {
		
		Calendar cal = Calendar.getInstance(); 
		Date d = cal.getTime();
        long timestamp = d.getTime(); 
        Thread.sleep(10000);
		
        messages=tibjmssslconnection.receiveMessageFromQueue("M.LGS.TMS.TRANSACTION.GG.POSTAT.FOM.QA", "JMSTimestamp > "+ timestamp );
      // Message messageFromQueue=TibjmsSSLConnection.messageFromQueue;
       log.info("The Message received is : messageFromQueue" + messages);
	
       /*if(messageFromQueue!=null) {
       Enumeration<String> e = (Enumeration<String>) message.getPropertyNames();
  	   while (e.hasMoreElements()) {
  	   String name = e.nextElement();
  	    if(name.equals("PK_PO_NBR"))
		   PK_PO_NBR=message.getStringProperty(name);
  	    System.out.println(PK_PO_NBR);
  	   }
       }*/
	
	}
	
	@Then("verify message")
	@Step("And verify message")
	public void thenVerifyMessage() throws JMSException {

		for(Message message:messages)
		{
		 Enumeration<String> e = (Enumeration<String>) message.getPropertyNames();
		 while (e.hasMoreElements()) {
		   String name = e.nextElement();
		  //System.out.println(name + "=" + message.getStringProperty(name));
		   if(name.equals("PK_PO_NBR"))
			   PK_PO_NBR=message.getStringProperty(name);
		    else if(name.equals("PK_SYS_TYP_NBR"))
			   PK_SYS_TYP_NBR=message.getStringProperty(name);
			else if(name.equals("PK_ZL_DIVN_NBR"))
			{
				PK_ZL_DIVN_NBR=message.getStringProperty(name);
		        org.testng.Assert.assertEquals(PK_ZL_DIVN_NBR, "71");
		   }
		   
		 }
		 log.info("PK_PO_NBR"+PK_PO_NBR+"PK_SYS_TYP_NBR"+PK_SYS_TYP_NBR+"PK_ZL_DIVN_NBR"+PK_ZL_DIVN_NBR);
		}
		
	  
	}

}
