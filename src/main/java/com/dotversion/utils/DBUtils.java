package com.dotversion.utils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;


import com.dotversion.common.util.ConnectionUtils;
import com.dotversion.models.SalesforceOrg;
import com.dotversion.models.User;
import com.sforce.soap.partner.*;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.bind.XmlObject;
public class DBUtils {
	private static String LOGIN_URL="https://login.salesforce.com/services/Soap/u/24.0";
	private static String SOQL_USER_FIND = 	" select Id,Email__c,First_Name__c,Last_Name__c,Username__c,"+
																	" (SELECT Id,Access_Token__c,AM_or_PM__c,Dot_Version_User__c,"+
																	"  Frequency__c,Metadata_Types__c,Org_Id__c,"+
																	" Org_Type__c,Status_Message__c,Status__c,Time_of_Day__c,Timezone__c, "+
																	" API_Endpoint__c " +
																	" from Org_Backup__r ) "+
																	" from Dot_Version_User__c where Username__c='%s' limit 1";
	private static String SOQL_USER_FIND_BYID= 	" select Id,Email__c,First_Name__c,Last_Name__c,Username__c,"+
																			" (SELECT Id,Access_Token__c,AM_or_PM__c,Dot_Version_User__c,"+
																			"  Frequency__c,Metadata_Types__c,Org_Id__c,"+
																			" Org_Type__c,Status_Message__c,Status__c,Time_of_Day__c,Timezone__c," +
																			" API_Endpoint__c  "+
																			" from Org_Backup__r ) "+
																			" from Dot_Version_User__c where Id='%s' limit 1";
	private static String SOQL_BACKUP_FIND = "SELECT Id,Access_Token__c,AM_or_PM__c,Dot_Version_User__c,"+
																	"  Frequency__c,Metadata_Types__c,Org_Id__c,"+
																	" Org_Type__c,Status_Message__c,Status__c,Time_of_Day__c,Timezone__c,API_Endpoint__c "+
																	" from Org_Backup__c where Id='%s'";
	
	private static String METADATA_TYPES = "select Name from Metadata_Types__c";

	private static  List<String> availableMetadataTypes;
	public static User upsert(User usr) throws Exception{
		
		PartnerConnection pconn = ConnectionUtils.getPartnerConnection(System.getenv("SFDC_USERNAME"),
																										System.getenv("SFDC_PASSWORD"),
																										LOGIN_URL);
		UpsertResult[] upsertResults = pconn.upsert("Username__c", new SObject[]{asSObject(usr)});
		usr.setId(upsertResults[0].getId());
		List<SalesforceOrg> orgs= usr.getSalesforceOrgs();
		SObject[] orgsToCreate = new SObject[usr.getSalesforceOrgs().size()];
		System.out.println(">>>>>>>>>>>>>> usr:"+usr);
		Integer orgIdx=0; 
		for(SalesforceOrg org: orgs){
			System.out.println(">>>>>>>>>>org-BFR:"+org);
			org.setDotVersionOwner(usr.getId());
			orgsToCreate[orgIdx++]=asSObject(org);
		}
		upsertResults = pconn.upsert("id",orgsToCreate);
		orgIdx=0;
		for(UpsertResult result:upsertResults){
			usr.getSalesforceOrgs().get(orgIdx++).setId(result.getId());
		}
		return usr;
	}
	
	
	public static User findUser(String username) throws Exception{

		PartnerConnection pconn = ConnectionUtils.getPartnerConnection( System.getenv("SFDC_USERNAME"),
																		System.getenv("SFDC_PASSWORD"),
																		LOGIN_URL);

		com.sforce.soap.partner.QueryResult result = pconn.query(String.format(SOQL_USER_FIND,username));
		User usr=null;
		if(result.getRecords().length >0){
			usr = fromSObjectToUser( result.getRecords()[0]);
			Iterator<XmlObject> backups= result.getRecords()[0].evaluate("Org_Backup__r/records");
			while(backups.hasNext()){
				usr.add(fromSObjectToOrg(backups.next()));
			}
		}
		return usr;
	}
	
	public static User findUserById(String id) throws Exception{

		PartnerConnection pconn = ConnectionUtils.getPartnerConnection(System.getenv("SFDC_USERNAME"),
																										System.getenv("SFDC_PASSWORD"),
																										LOGIN_URL);

		com.sforce.soap.partner.QueryResult result = pconn.query(String.format(SOQL_USER_FIND_BYID,id));
		User usr=null;
		if(result.getRecords().length >0){
			usr = fromSObjectToUser( result.getRecords()[0]);
			Iterator<XmlObject> backups= result.getRecords()[0].evaluate("Org_Backup__r/records");
			while(backups.hasNext()){
				usr.add(fromSObjectToOrg(backups.next()));
			}
		}
		return usr;
	}
	
	
	public static SalesforceOrg findBackup(String id) throws Exception{
		PartnerConnection pconn = ConnectionUtils.getPartnerConnection(System.getenv("SFDC_USERNAME"),
																										System.getenv("SFDC_PASSWORD"),
																										LOGIN_URL);

		com.sforce.soap.partner.QueryResult result = pconn.query(String.format(SOQL_BACKUP_FIND,id));
		SalesforceOrg org=null;
		if(result.getRecords().length >0){
			org = fromSObjectToOrg( result.getRecords()[0]);
			
		}
		return org;																				
		
	}
	
	public static List<String> getMetadataTypes() throws Exception{
		if(availableMetadataTypes ==null){
			initializeMetadataTypes();
		}
		return availableMetadataTypes;
	}
	public static void initializeMetadataTypes() throws Exception{
		PartnerConnection pconn = ConnectionUtils.getPartnerConnection(System.getenv("SFDC_USERNAME"), 
																										System.getenv("SFDC_PASSWORD"),
																										LOGIN_URL);

		com.sforce.soap.partner.QueryResult result = pconn.query(METADATA_TYPES);
		availableMetadataTypes = new ArrayList<String>();
		if (result.getRecords().length > 0) {
			for(SObject sobj :result.getRecords()){
				availableMetadataTypes.add((String)sobj.getField("Name"));
			}
		}
		
	}
	public static void main(String[] args) throws Exception{
		//User usr = new User("test@dotv.com","test@dotv.com","Test","Test");
		//SalesforceOrg org = new SalesforceOrg("orgId","Production","Daily",6,"AM","Custom Object;Apex;","accessToken");
		//usr.add(org);
		//create(usr);
		//User user1 = findUser("admin@0128_anarasimhan.com");
		//System.out.println(">>>>>>>>>>>>>>>>"+user1);
		//upsert(user1);
		//getPSTTimeOfDay("America/Los_Angeles");
	}
	
	
	public static SObject asSObject(User user){
		SObject sobj = new SObject();
		sobj.setType("Dot_Version_User__c");
		sobj.setField("First_Name__c",user.getFirstName());
		sobj.setField("Last_Name__c",user.getLastName());
		sobj.setField("Username__c",user.getUsername());
		sobj.setField("Email__c",user.getEmail());
		sobj.setId(user.getId());
		return sobj;
	}
	
	public static User fromSObjectToUser(SObject sobj){
		return new User(	(String)sobj.getField("Username__c"),
								(String)sobj.getField("Email__c"),
								(String)sobj.getField("First_Name__c"),
								(String)sobj.getField("Last_Name__c"));
	}
	
	public static SalesforceOrg fromSObjectToOrg(XmlObject sobj){
			
		SalesforceOrg org = new  SalesforceOrg(	(String)sobj.getField("Id"),
																	(String)sobj.getField("Org_Id__c"),
																	(String)sobj.getField("Org_Type__c"),
																	(String)sobj.getField("Frequency__c"),
																	Integer.parseInt((String)sobj.getField("Time_of_Day__c")),
																	(String)sobj.getField("AM_or_PM__c"),
																	(String)sobj.getField("Metadata_Types__c"),
																	(String)sobj.getField("Access_Token__c"),
																	((String)sobj.getField("Timezone__c")==null)?"America/Los_Angeles":(String)sobj.getField("Timezone__c"),
																	(String)sobj.getField("API_Endpoint__c"));
		
		
		org.setDotVersionOwner((String)sobj.getField("Dot_Version_User__c"));
		org.setLastBackupStatus((String)sobj.getField("Status__c"));
		//org.setLastBackupDate((Date)sobj.get
		org.setLastBackupStatusMsg((String)sobj.getField("Status_Message__c"));
		return org;
	}
	
	public static SObject asSObject(SalesforceOrg org){
		SObject sobj = new SObject();
		sobj.setType("Org_Backup__c");
		sobj.setField("Access_Token__c",org.getAccessToken());
		sobj.setField("AM_or_PM__c",org.getAmOrPm());
		sobj.setField("Dot_Version_User__c",org.getDotVersionOwner());
		sobj.setField("Frequency__c",org.getFrequency());
		sobj.setField("Org_Id__c",org.getOrgId());
		sobj.setField("Org_Type__c",org.getOrgType());
		sobj.setField("Time_of_Day__c",String.valueOf(org.getTimeOfDay()));
		sobj.setField("Metadata_Types__c",org.getMetadataTypes());
		sobj.setField("Timezone__c",org.getTimezone());
		sobj.setField("Hour_of_Day_PST__c", getPSTTimeOfDay(org.getTimezone(),org.getTimeOfDay(),org.getAmOrPm()));
		sobj.setField("API_Endpoint__c", org.getApiEndpoint());	
		sobj.setId(org.getId());
		return sobj;
	}
	
	private static int getPSTTimeOfDay(String timeZoneStr,int timeOfDay,String amPm){
		GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone(timeZoneStr));
		cal.set(Calendar.HOUR_OF_DAY, (amPm.equalsIgnoreCase("AM")?0:12)+timeOfDay);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		GregorianCalendar local = new GregorianCalendar();
		local.setTimeInMillis(cal.getTimeInMillis());
		return local.get(Calendar.HOUR_OF_DAY);
	}
}
