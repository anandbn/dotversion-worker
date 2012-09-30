package com.dotversion.services.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringEscapeUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;


import com.dotversion.common.util.ConnectionUtils;
import com.dotversion.models.Backup;
import com.dotversion.models.SalesforceOrg;
import com.dotversion.models.User;
import com.dotversion.services.OrgBackupDataService;
import com.force.api.http.Http;
import com.force.api.http.HttpRequest;
import com.force.api.http.HttpResponse;
import com.sforce.soap.partner.*;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.bind.XmlObject;
public class DBUtils implements OrgBackupDataService{
	//2012-09-05T15:53:51.000Z
	private static SimpleDateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	private String LOGIN_URL="https://login.salesforce.com/services/Soap/u/24.0";
	private String SOQL_USER_FIND = 	" select Id,Email__c,First_Name__c,Last_Name__c,Username__c,"+
																	" (SELECT Id,Access_Token__c,AM_or_PM__c,Dot_Version_User__c,"+
																	"  Frequency__c,Metadata_Types__c,Org_Id__c,"+
																	" Org_Type__c,Status_Message__c,Status__c,Time_of_Day__c,Timezone__c, "+
																	" API_Endpoint__c,Refresh_token__c,Backup_Count__c,LastModifiedDate " +
																	" from Organization__r ) "+
																	" from Dot_Version_User__c where Username__c='%s' limit 1";
	private String SOQL_USER_FIND_BYID= 	" select Id,Email__c,First_Name__c,Last_Name__c,Username__c,"+
																			" (SELECT Id,Access_Token__c,AM_or_PM__c,Dot_Version_User__c,"+
																			"  Frequency__c,Metadata_Types__c,Org_Id__c,"+
																			" Org_Type__c,Status_Message__c,Status__c,Time_of_Day__c,Timezone__c," +
																			" API_Endpoint__c,Refresh_token__c,Backup_Count__c,LastModifiedDate  "+
																			" from Organization__r ) "+
																			" from Dot_Version_User__c where Id='%s' limit 1";
	private String SOQL_BACKUP_FIND = "SELECT Id,Access_Token__c,AM_or_PM__c,Dot_Version_User__c,"+
																	"  Frequency__c,Metadata_Types__c,Org_Id__c,"+
																	" Org_Type__c,Status_Message__c,Status__c,Time_of_Day__c,Timezone__c,API_Endpoint__c,Refresh_token__c,"+
																	" Backup_Count__c"+
																	" from Organization__c where Id='%s'";
	private String SOQL_BACKUP_FIND_BY_ORGID = 	"SELECT Id,Access_Token__c,AM_or_PM__c,Dot_Version_User__c,"+
												"  Frequency__c,Metadata_Types__c,Org_Id__c,"+
												" Org_Type__c,Status_Message__c,Status__c,Time_of_Day__c,Timezone__c,API_Endpoint__c,Refresh_token__c,"+
												" Backup_Count__c"+
												" from Organization__c where Org_Id__c='%s'";
	
	private String SOQL_BACKUPS_FOR_ORG = "SELECT CreatedDate,Git_Version_Id__c,Id,Organization__c,Status_Message__c,Status__c,Version__c,Changes__c " +
										 "FROM Backup__c where Organization__r.Org_Id__c='%s'";
	
	private String ORGS_TO_BACKUP =  "select Id,Org_Id__c from Organization__c where Hour_Of_Day_PST__c=%s";
	
	public User upsert(User usr){
		
		PartnerConnection pconn;
		try {
			pconn = ConnectionUtils.getPartnerConnection(System.getenv("SFDC_USERNAME"),
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
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public SalesforceOrg upsert(SalesforceOrg org){
		PartnerConnection pconn = null;
		try{	
			pconn = ConnectionUtils.getPartnerConnection(System.getenv("SFDC_USERNAME"),
														 System.getenv("SFDC_PASSWORD"),
														 LOGIN_URL);
			UpsertResult[] upsertResults ;
			SObject[] orgsToCreate = new SObject[1];
			orgsToCreate[0]=asSObject(org);
			upsertResults = pconn.upsert("id",orgsToCreate);
			if(upsertResults[0].getSuccess()){
				org.setId(upsertResults[0].getId());
			}else{
				throw new RuntimeException("Error saving Organization__c:"+upsertResults[0].getErrors());
			}
			
			
			return org;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

	public Backup upsert(Backup backup){
		PartnerConnection pconn;
		try {
			pconn = ConnectionUtils.getPartnerConnection(System.getenv("SFDC_USERNAME"),
																			System.getenv("SFDC_PASSWORD"),
																			LOGIN_URL);
			UpsertResult[] upsertResults ;
			SObject[] toCreate = new SObject[1];
			toCreate[0]=asSObject(backup);
			upsertResults = pconn.upsert("id",toCreate);
			if(upsertResults[0].getSuccess()){
				backup.setId(upsertResults[0].getId());
			}else{
				throw new RuntimeException("Error saving Backup__c:"+upsertResults[0].getErrors());
			}
			
			return backup;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public User findUser(String username){

		PartnerConnection pconn;
		try {
			pconn = ConnectionUtils.getPartnerConnection( System.getenv("SFDC_USERNAME"),
																			System.getenv("SFDC_PASSWORD"),
																			LOGIN_URL);

			com.sforce.soap.partner.QueryResult result = pconn.query(String.format(SOQL_USER_FIND,username));
			User usr=null;
			if(result.getRecords().length >0){
				usr = fromSObjectToUser( result.getRecords()[0]);
				Iterator<XmlObject> backups= result.getRecords()[0].evaluate("Organization__r/records");
				while(backups.hasNext()){
					usr.add(fromSObjectToOrg(backups.next()));
				}
			}else{
				String partialUsrName = username.substring(0,username.lastIndexOf('.'));
				result = pconn.query(String.format(SOQL_USER_FIND,partialUsrName));
				if(result.getRecords().length >0){
					usr = fromSObjectToUser( result.getRecords()[0]);
					Iterator<XmlObject> backups= result.getRecords()[0].evaluate("Organization__r/records");
					while(backups.hasNext()){
						usr.add(fromSObjectToOrg(backups.next()));
					}
				}
			}
			return usr;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public User findUserById(String id){

		PartnerConnection pconn;
		try {
			pconn = ConnectionUtils.getPartnerConnection(System.getenv("SFDC_USERNAME"),
																											System.getenv("SFDC_PASSWORD"),
																											LOGIN_URL);
			com.sforce.soap.partner.QueryResult result = pconn.query(String.format(SOQL_USER_FIND_BYID,id));
			User usr=null;
			if(result.getRecords().length >0){
				usr = fromSObjectToUser( result.getRecords()[0]);
				Iterator<XmlObject> backups= result.getRecords()[0].evaluate("Organization__r/records");
				while(backups.hasNext()){
					usr.add(fromSObjectToOrg(backups.next()));
				}
			}
			return usr;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
	
	public SalesforceOrg findOrganizationById(String id){
		PartnerConnection pconn;
		try {
			pconn = ConnectionUtils.getPartnerConnection(System.getenv("SFDC_USERNAME"),
																											System.getenv("SFDC_PASSWORD"),
																											LOGIN_URL);
			com.sforce.soap.partner.QueryResult result = pconn.query(String.format(SOQL_BACKUP_FIND,id));
			SalesforceOrg org=null;
			if(result.getRecords().length >0){
				org = fromSObjectToOrg( result.getRecords()[0]);
				
			}
			return org;																				
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		
	}

	public SalesforceOrg findOrganizationByOrgId(String orgId){
		PartnerConnection pconn;
		try {
			pconn = ConnectionUtils.getPartnerConnection(System.getenv("SFDC_USERNAME"),
															System.getenv("SFDC_PASSWORD"),
															LOGIN_URL);
			com.sforce.soap.partner.QueryResult result = pconn.query(String.format(SOQL_BACKUP_FIND_BY_ORGID,orgId));
			SalesforceOrg org=null;
			if(result.getRecords().length >0){
				org = fromSObjectToOrg( result.getRecords()[0]);
				
			}
			return org;																				
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		
	}

	public String accessToken(String orgId,String orgType){
		Map<String,Object> postParams = new HashMap<String,Object>();
		
		PartnerConnection pconn;
		try {
			pconn = ConnectionUtils.getPartnerConnection( System.getenv("SFDC_USERNAME"),
																			System.getenv("SFDC_PASSWORD"),
																			LOGIN_URL);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		
		postParams.put("client_id", System.getenv("FORCE_OAUTH_KEY"));
		postParams.put("client_secret", System.getenv("FORCE_OAUTH_SECRET"));
		postParams.put("redirect_uri",System.getenv("APP_URI")+"/_auth");
		postParams.put("orgType",orgType);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString;
		try {
			jsonString = mapper.writeValueAsString(postParams);
		} catch (JsonGenerationException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		HttpRequest req = new HttpRequest();
		req.method("POST");
		HttpResponse response = Http.send(req.header("Authorization", "OAuth "+pconn.getConfig().getSessionId())
		   									 .header("Accept", "application/json")
		   									 .header("Content-Type", "application/json; charset=UTF-8")
		   									 .url("https://na9-api.salesforce.com/services/apexrest/dotversion/accessToken/"+orgId)
		   									 .content(jsonString.getBytes()));
		InputStream content = response.getStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(content));
		
		String respStr;
		try {
			respStr = StringEscapeUtils.unescapeJava(reader.readLine());
		
			Map<String,String> accessTokenResp = mapper.readValue(	respStr.substring(1,respStr.length()-1),
																	new TypeReference<Map<String, String>>() {});
			return accessTokenResp.get("access_token");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

	
	public SObject asSObject(User user){
		SObject sobj = new SObject();
		sobj.setType("Dot_Version_User__c");
		sobj.setField("First_Name__c",user.getFirstName());
		sobj.setField("Last_Name__c",user.getLastName());
		sobj.setField("Username__c",user.getUsername());
		sobj.setField("Email__c",user.getEmail());
		sobj.setId(user.getId());
		return sobj;
	}
	

	public SObject asSObject(Backup bkp){
		SObject sobj = new SObject();
		sobj.setType("Backup__c");
		sobj.setField("Organization__c",bkp.getOrganization().getId());
		sobj.setField("Status__c",bkp.getStatus());
		sobj.setField("Status_Message__c",bkp.getStatusMsg());
		sobj.setField("Git_Version_Id__c",bkp.getGitVersion());
		sobj.setField("Changes__c",bkp.getNbrOfChanges());
		sobj.setField("Version__c",bkp.getVersion());
		return sobj;
	}

	public User fromSObjectToUser(SObject sobj){
		return new User(	(String)sobj.getField("Username__c"),
								(String)sobj.getField("Email__c"),
								(String)sobj.getField("First_Name__c"),
								(String)sobj.getField("Last_Name__c"));
	}
	
	public Backup fromSObjectToBackup(SObject sobj) throws NumberFormatException, ParseException{
		
		Backup bkp= new Backup(sobj.getField("Changes__c")!=null?Double.valueOf((String) sobj.getField("Changes__c")).intValue():0,
							  (String)sobj.getField("Status__c"),
							  (String)sobj.getField("Status_Message__c"),
							  (String)sobj.getField("Git_Version_Id__c"),
							  null,
							  (((Double)Double.parseDouble((String)sobj.getField("Version__c"))).intValue()),
							  sobj.getField("CreatedDate")!=null?ISO_DATE_FORMAT.parse((String)sobj.getField("CreatedDate")):null);
		bkp.setId(sobj.getId());
		return bkp;
	}
	
	public SalesforceOrg fromSObjectToOrg(XmlObject sobj) throws ParseException{
			
		SalesforceOrg org = new  SalesforceOrg(	(String)sobj.getField("Id"),
																	(String)sobj.getField("Org_Id__c"),
																	(String)sobj.getField("Org_Type__c"),
																	(String)sobj.getField("Frequency__c"),
																	sobj.getField("Time_of_Day__c")!=null?Integer.parseInt((String)sobj.getField("Time_of_Day__c")):null,
																	(String)sobj.getField("AM_or_PM__c"),
																	(String)sobj.getField("Metadata_Types__c"),
																	(String)sobj.getField("Access_Token__c"),
																	(String)sobj.getField("Refresh_Token__c"),
																	((String)sobj.getField("Timezone__c")==null)?"America/Los_Angeles":(String)sobj.getField("Timezone__c"),
																	(String)sobj.getField("API_Endpoint__c"));
		
		
		org.setDotVersionOwner((String)sobj.getField("Dot_Version_User__c"));
		org.setLastBackupStatus((String)sobj.getField("Status__c"));
		org.setLastBackupStatusMsg((String)sobj.getField("Status_Message__c"));
		org.setBackupCount(sobj.getField("Backup_Count__c")!=null?Double.valueOf((String) sobj.getField("Backup_Count__c")).intValue():null);
		org.setLastBackupDate(sobj.getField("LastModifiedDate")!=null?ISO_DATE_FORMAT.parse((String)sobj.getField("LastModifiedDate")):null);
		return org;
	}
	
	public SObject asSObject(SalesforceOrg org){
		SObject sobj = new SObject();
		sobj.setType("Organization__c");
		sobj.setField("Access_Token__c",org.getAccessToken());
		sobj.setField("Refresh_Token__c",org.getRefreshToken());
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
		sobj.setField("Status__c", org.getLastBackupStatus());
		sobj.setField("Status_Message__c",org.getLastBackupStatusMsg());
		sobj.setId(org.getId());
		return sobj;
	}
	
	private int getPSTTimeOfDay(String timeZoneStr,int timeOfDay,String amPm){
		GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone(timeZoneStr));
		cal.set(Calendar.HOUR_OF_DAY, (amPm.equalsIgnoreCase("AM")?0:12)+timeOfDay);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		GregorianCalendar local = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		local.setTimeInMillis(cal.getTimeInMillis());
		return local.get(Calendar.HOUR_OF_DAY);
	}

	@Override
	public List<SalesforceOrg> getOrgsToBackup(int hourPst) {
		List<SalesforceOrg> orgsToBkup = new ArrayList<SalesforceOrg>();
		PartnerConnection pconn;
		try {
			pconn = ConnectionUtils.getPartnerConnection( System.getenv("SFDC_USERNAME"),
																			System.getenv("SFDC_PASSWORD"),
																			LOGIN_URL);

			com.sforce.soap.partner.QueryResult result = pconn.query(String.format(ORGS_TO_BACKUP,hourPst));
			if(result.getRecords().length >0){
				for(SObject obj: result.getRecords()){
					orgsToBkup.add(fromSObjectToOrg(obj));
				}
			}
			return orgsToBkup;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<Backup> backupsForOrg(String orgId){
		
		PartnerConnection pconn;
		try {
			List<Backup> retList = new ArrayList<Backup>();
			pconn = ConnectionUtils.getPartnerConnection( System.getenv("SFDC_USERNAME"),
																			System.getenv("SFDC_PASSWORD"),
																			LOGIN_URL);

			com.sforce.soap.partner.QueryResult result = pconn.query(String.format(SOQL_BACKUPS_FOR_ORG,orgId));
			if(result.getRecords().length >0){
				for(SObject obj: result.getRecords()){
					retList.add(fromSObjectToBackup(obj));
				}
			}
			return retList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
