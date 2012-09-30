package com.dotversion.services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.dotversion.models.Backup;
import com.dotversion.models.SalesforceOrg;
import com.dotversion.models.User;


public interface OrgBackupDataService {

	public User upsert(User usr) ;
	
	public SalesforceOrg upsert(SalesforceOrg org);
	

	public Backup upsert(Backup backup) ;

	public User findUser(String username);
	
	public User findUserById(String id);
	
	
	public SalesforceOrg findOrganizationById(String id) ;
	public SalesforceOrg findOrganizationByOrgId(String orgId) ;
	
	public String accessToken(String orgId,String orgType) ;
	
	public List<SalesforceOrg> getOrgsToBackup(int hourPst);
	
	public List<Backup> backupsForOrg(String orgId);

}
