package com.dotversion.common.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ServiceLoader;
import java.util.zip.ZipInputStream;

import com.dotversion.common.DotVersionConstants;
import com.dotversion.forcediff.core.JaxbUnmarshallerCache;
import com.dotversion.forcediff.core.MetadataFactory;
import com.dotversion.forcediff.core.NodeDifference;
import com.dotversion.forcediff.core.NodeDifferenceFinder;
import com.dotversion.forcediff.core.StandardNode;
import com.dotversion.forcediff.org.DirectoryMetadataSource;
import com.dotversion.forcediff.org.OrgMetadata;
import com.dotversion.forcediff.org.ZipArchiveMetadataSource;
import com.dotversion.git.BackupStatus;
import com.dotversion.git.GitUtils;
import com.dotversion.models.Backup;
import com.dotversion.models.BackupDifference;
import com.dotversion.models.SalesforceOrg;
import com.dotversion.models.User;
import com.dotversion.services.OrgBackupDataService;
import com.dotversion.services.impl.DBUtils;
import com.dotversion.utils.ZipUtils;
import com.dotversion.forcediff.core.CombinedNodeDifference;
import com.salesforce.metadata.Layout;
import com.salesforce.metadata.LayoutSection;
import com.salesforce.metadata.Metadata;
import com.salesforce.metadata.ReportType;
import com.salesforce.metadata.Workflow;
import com.sforce.soap.metadata.AsyncRequestState;
import com.sforce.soap.metadata.AsyncResult;
import com.sforce.soap.metadata.FileProperties;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.metadata.Package;
import com.sforce.soap.metadata.RetrieveMessage;
import com.sforce.soap.metadata.RetrieveRequest;
import com.sforce.soap.metadata.RetrieveResult;
import com.sforce.ws.ConnectorConfig;
import com.sforce.ws.wsdl.Collection;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.eclipse.jgit.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
public class OrgMetadataUtils {
	private static Logger logger = LoggerFactory.getLogger(OrgMetadataUtils.class);
	private static JedisPoolFactory poolFactory = new JedisPoolFactory();

	private static final OrgBackupDataService dbUtils;
	static{
		dbUtils = ServiceLoader.load(OrgBackupDataService.class).iterator().next();
	}
	public static void main(String[] args) throws Exception{
		/*
		OrgMetadata current = new OrgMetadata(new DirectoryMetadataSource("current","/Users/anandbashyamnarasimhan/scratchpad/dotversion/00DA0000000YjLPMA0-c/unpackaged"));
		OrgMetadata latest = new OrgMetadata(new DirectoryMetadataSource("latest","/Users/anandbashyamnarasimhan/scratchpad/dotversion/00DA0000000YjLPMA0-l/unpackaged"));
		NodeDifferenceFinder finder = NodeDifferenceFinder.getInstance();
        NodeDifference diff = finder.findDifferences(latest,current);
        ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
		objectMapper.writeValue(new File("/Users/anandbashyamnarasimhan/scratchpad/dotversion/diff.json"),diff);
		*/
		/*
		Class<? extends com.salesforce.metadata.Metadata> clsName = MetadataFactory.getMetadataClass("layouts");
		File layout = new File("/Users/anandbashyamnarasimhan/scratchpad/dotversion/00DA0000000YjLPMA0-c/unpackaged/workflows/Position__c.workflow");
        Workflow obj1 = (Workflow) JaxbUnmarshallerCache.unmarshallerFor(clsName)
				  											  .unmarshal(layout);
        
        layout = new File("/Users/anandbashyamnarasimhan/scratchpad/dotversion/00DA0000000YjLPMA0-l/unpackaged/workflows/Position__c.workflow");
        Workflow obj2 = (Workflow) JaxbUnmarshallerCache.unmarshallerFor(clsName)
				  											  .unmarshal(layout);
        
        StandardNode node1 =(StandardNode) MetadataFactory.getNodeInstance(obj1);
        StandardNode node2 =(StandardNode) MetadataFactory.getNodeInstance(obj2);
        
        NodeDifference diff =NodeDifferenceFinder.getInstance().findDifferences(node1, node2);
        ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
		objectMapper.writeValue(new File("/Users/anandbashyamnarasimhan/scratchpad/dotversion/diff.json"),diff);
		
		User usr = dbUtils.findUser("");
		//SalesforceOrg org = dbUtils.findOrganizationByOrgId("00DA0000000YjLPMA0");
		backupOrg(usr.getSalesforceOrgs().get(0));
		*/
		
	}
	
	public static void backupOrg(SalesforceOrg org) throws Exception{
		logger.info(String.format("[dv-org-usr] - %s",org.toString()));
		org.setLastBackupStatus("In Progress");
		dbUtils.upsert(org);
		try{
			Backup bkp = doBackup(org);
			if(bkp!=null){
				bkp.setOrganization(org);
				dbUtils.upsert(bkp);
				logger.debug("[dv-org-bkp] - Created new Backup record");
				org.setLastBackupStatus("Success");
				dbUtils.upsert(org);
				logger.debug("[dv-org-bkp] - Updated org record");
			}else{
				logger.debug("[dv-org-bkp] - No changes to backup. Exiting now");
				org.setLastBackupStatus("Success");
				dbUtils.upsert(org);
				logger.debug("[dv-org-bkp] - Updated org record");
	
			}
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("[dv-org-bkp] - Backup failed with error: "+ex.getMessage());
			logger.error("[dv-org-bkp] - Backup failed. Updating status to 'Failed'");
			org.setLastBackupStatus("Failed");
			org.setLastBackupStatus(ex.getMessage());
			dbUtils.upsert(org);
			
		}

	}
	public static Backup doBackup(SalesforceOrg org) throws Exception{
		String accessToken = dbUtils.accessToken(org.getId(),org.getOrgType().toLowerCase());
		
		RetrieveRequest retReq = new RetrieveRequest();
		Package pkgCfg = PackageHelper.getPackageConfig(org.getMetadataTypes());
		retReq.setApiVersion(Double.valueOf(pkgCfg.getVersion()));
		retReq.setUnpackaged(pkgCfg);
		Integer maxPolls = Integer.valueOf(System.getenv("MAX_POLLS")==null?"100":System.getenv("MAX_POLLS"));
		Integer pollWaitSec = Integer.valueOf(System.getenv("POLL_WAIT")==null?"10":System.getenv("POLL_WAIT"));
		long waitTimeMillis = pollWaitSec*1000;
		Integer currPollCnt=0;
		MetadataConnection metaConn = null;
		ConnectorConfig metaConfig = new ConnectorConfig();
		metaConfig.setServiceEndpoint(String.format("%s/services/Soap/m/%s/%s",
													org.getApiEndpoint(),
													System.getenv("SFDC_API_VERSION")==null?"23.0":System.getenv("SFDC_API_VERSION"),
													org.getOrgId()
												)
									);
		
		metaConfig.setSessionId(accessToken);
		metaConn = com.sforce.soap.metadata.Connector.newConnection(metaConfig);

        try{
            AsyncResult asyncResult = metaConn.retrieve(retReq);
    		logger.info(String.format("[dv-meta-fetch] - Async Request ID = %s",asyncResult.getId()));
	        while (!asyncResult.isDone()) {
	        	
	        	Thread.sleep(waitTimeMillis);
		        // double the wait time for the next iteration  
	        	waitTimeMillis *= 2;
		        if (currPollCnt++ > maxPolls) {
			    	//logger.error("Maximum poll limit exceeded. Unable to fetch metadata. Reduce the components in your package configuration and try again");
		        	throw new Exception("Maximum poll limit exceeded. Unable to fetch metadata. Reduce the components in your package configuration and try again");
			    }
	        	try{
			        asyncResult = metaConn.checkStatus(
			        		new String[] {asyncResult.getId()})[0];
	        	}catch(Exception ex){
			    	//logger.error(ex);
		        	throw ex;
	        	}
		    }
		    if (asyncResult.getState() != AsyncRequestState.Completed) {
		    	//logger.error(asyncResult.getStatusCode() + " msg: " +asyncResult.getMessage());
		        throw new Exception(asyncResult.getStatusCode() + " msg: " +asyncResult.getMessage());
		    }
		    RetrieveResult result = metaConn.checkRetrieveStatus(asyncResult.getId());
		    logMessagesAndWarnings(result);
		    return extractContents(org,result);
		    // Print out any warning messages
        }catch(Exception ex){
        	throw new RuntimeException(ex);
        }
		    
	}
	
	private static Backup extractContents(SalesforceOrg org,RetrieveResult result) throws Exception{
    	ZipInputStream zipIn=null;
    	
    	String zipFileName=System.getProperty("java.io.tmpdir") + File.separator + org.getOrgId()+".zip";
    	String tempGitDir=null;
        try{
        	FileOutputStream fileOut = new FileOutputStream(zipFileName);
	        fileOut.write(result.getZipFile());
	        fileOut.close();
	        zipIn = ZipUtils.initializeInMemoryZip(result.getZipFile());
	    	BackupStatus sts=null;
	    	if( org.getBackupCount()==0){
	    		sts = GitUtils.createOrgMetadataRepo(org.getOrgId(),zipIn);
	    	}else{
	    		tempGitDir = GitUtils.pullFromRemote(org.getOrgId());
	    		BackupDifference bkpDiff = getDifferences(org,zipIn,tempGitDir);
	    		Map<String, Map<String, String>> whatsChanged = bkpDiff.getDiffSmry();
	    		if(!whatsChanged.isEmpty()){
		    		zipIn = ZipUtils.initializeInMemoryZip(result.getZipFile());
		    		sts = GitUtils.updateOrgMetadataRepo(org.getOrgId(),zipIn,tempGitDir,whatsChanged);
		    		ObjectMapper objectMapper =new ObjectMapper();
		    		objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
		    		String jsonStr = objectMapper.writeValueAsString(bkpDiff.getDiffDetail());
		    		writeChangesToRedis(sts.getVersionId(),jsonStr);
	    		}
	    	}
	    	if(sts!=null){
	    		return new Backup(	sts.getChangeCount(),
									sts.isSuccess()?"Success":"Failed",
									sts.getStatusMsg(),
									sts.getVersionId(), 
									org,
									org.getBackupCount()+1,
									new Date());
	    	}else{
	    		return null;
	    	}
        }finally{
        	File zipFile = new File(zipFileName);
        	zipFile.delete();
        	if(tempGitDir!=null){
        		FileUtils.delete(new File(tempGitDir),FileUtils.RECURSIVE);
        	}
        }
	}


	private static BackupDifference getDifferences(SalesforceOrg org,ZipInputStream zipIn,String currentGitDir) throws Exception{
		OrgMetadata latest = new OrgMetadata(new ZipArchiveMetadataSource("latest", zipIn));
		Map<String, Map<String, String>> whatsChanged = new HashMap<String,Map<String,String>>();
		Map<String,String> 	changes = new HashMap<String,String>(),
							adds = new HashMap<String,String>(), 
							deletes = new HashMap<String,String>();
		OrgMetadata current = new OrgMetadata(new DirectoryMetadataSource("current", currentGitDir+File.separator+"unpackaged"));
		NodeDifferenceFinder finder = NodeDifferenceFinder.getInstance();
        NodeDifference diff = finder.findDifferences(latest,current);
        CombinedNodeDifference combinedDifference = new CombinedNodeDifference(diff);
        String fileName;
        for(Entry<String, CombinedNodeDifference> folder: combinedDifference.getChildDifferencesMap().entrySet()){
        	logger.debug(String.format(">>>>>>>>>>>>>>> Folder - :%s",folder.getKey()));
         		for(Entry<String, CombinedNodeDifference> metaDiff: folder.getValue().getChildDifferencesMap().entrySet()){
        			fileName=	"unpackaged"+File.separator+folder.getKey()+
    						File.separator+metaDiff.getKey()+"."+
    						Configuration.fileExtensionFor(folder.getKey());

        			if(metaDiff.getValue().getLeftOnlyDifference()){
        				adds.put(fileName,currentGitDir+File.separator+fileName);
                		logger.debug(String.format(">>>>>>>>>>>>>>>>>>>>>>> Metadata ADDED - %s",fileName));
        			}else if(metaDiff.getValue().getRightOnlyDifference()){
        				deletes.put(fileName,currentGitDir+File.separator+fileName);
                		logger.debug(String.format(">>>>>>>>>>>>>>>>>>>>>>> Metadata DELETED - %s",fileName));
        				
        			}else{
            			changes.put(fileName, currentGitDir+File.separator+fileName);
                		logger.debug(String.format(">>>>>>>>>>>>>>>>>>>>>>> Metadata MODIFIED - %s",fileName));
        			}
            	}
        }
        if(!changes.isEmpty()){
        	whatsChanged.put("MODIFIED", changes);
        }
        if(!adds.isEmpty()){
        	whatsChanged.put("ADDED", adds);
        }
        if(!deletes.isEmpty()){
        	whatsChanged.put("DELETED", deletes);
        }
        
		return new BackupDifference(diff,whatsChanged);
	}
	private static void logMessagesAndWarnings(RetrieveResult result){
	    StringBuilder buf = new StringBuilder();
	    if (result.getMessages() != null) {
	        for (RetrieveMessage rm : result.getMessages()) {
	        	buf.append(rm.getFileName() + " - " + rm.getProblem());
	        }
	        buf.append('\n');
	        for(FileProperties fileProp:result.getFileProperties()){
	        	buf.append("Name:"+fileProp.getFileName());
	        	buf.append('\n');
	        	buf.append("Full Name:"+fileProp.getFullName());
	        	buf.append('\n');
	        }
	    }
	    if (buf.length() > 0) {
	    	//logger.info("Retrieve warnings:\n" + buf);
	    	logger.info(String.format("[dv-meta-complete] - %s","Retrieve warnings:\n" + buf));
	    }	
		
	}
	
	private static void writeChangesToRedis(String gitVersionId,String changesJson){
		JedisPool pool=null;
		Jedis jedis=null;
		try {

			pool = poolFactory.getPool();
			jedis = pool.getResource();
			jedis.hset(gitVersionId, "changes",changesJson);
		} finally {
			if(pool!=null && jedis!=null){
				pool.returnResource(jedis);
			}

		}
	
		
	}
}
