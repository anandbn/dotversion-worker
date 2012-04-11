package com.dotversion.common.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.zip.ZipInputStream;

import com.dotversion.forcediff.org.OrgMetadata;
import com.dotversion.forcediff.org.ZipArchiveMetadataSource;
import com.dotversion.models.SalesforceOrg;
import com.dotversion.models.User;
import com.dotversion.utils.DBUtils;
import com.sforce.soap.metadata.AsyncRequestState;
import com.sforce.soap.metadata.AsyncResult;
import com.sforce.soap.metadata.FileProperties;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.metadata.Package;
import com.sforce.soap.metadata.RetrieveMessage;
import com.sforce.soap.metadata.RetrieveRequest;
import com.sforce.soap.metadata.RetrieveResult;
import com.sforce.ws.ConnectorConfig;

public class OrgMetadataUtils {

	public static void main(String[] args) throws Exception{
		User usr = DBUtils.findUser("admin@0128_anarasimhan.com");
		SalesforceOrg org = usr.getSalesforceOrgs().get(0);
		retreiveMetadata(org);
	}
	public static OrgMetadata retreiveMetadata(SalesforceOrg org) throws Exception{
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
		metaConfig.setSessionId(org.getAccessToken());
		metaConn = com.sforce.soap.metadata.Connector.newConnection(metaConfig);

        try{
            AsyncResult asyncResult = metaConn.retrieve(retReq);
            System.out.println("Async Request ID:"+asyncResult.getId());
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
		    return extractContents(result);
		    // Print out any warning messages
        }catch(Exception ex){
        	throw new RuntimeException(ex);
        }
		    
	}
	
	private static OrgMetadata extractContents(RetrieveResult result) throws Exception{
    	ZipInputStream zipIn=null;
    	zipIn = initializeInMemoryZip(result);
	    return new OrgMetadata(new ZipArchiveMetadataSource("org1", zipIn));
	}

	private static ZipInputStream initializeInMemoryZip(RetrieveResult result) throws Exception{
	    ByteArrayInputStream bais = new ByteArrayInputStream(result.getZipFile());
	    return new ZipInputStream(new BufferedInputStream(bais));
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
	    	System.out.println("Retrieve warnings:\n" + buf);
	    }	
		
	}
}
