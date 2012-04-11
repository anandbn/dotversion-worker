package com.dotversion.forcediff.org;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.jibx.runtime.JiBXException;

import com.dotversion.forcediff.core.CombinedNodeDifference;
import com.dotversion.forcediff.core.MetadataFactory;
import com.dotversion.forcediff.core.MetadataReader;
import com.dotversion.forcediff.core.NodeDifference;
import com.dotversion.forcediff.core.NodeDifferenceFinder;
import com.dotversion.common.util.Configuration;
import com.dotversion.common.util.OrgInfo;
import com.dotversion.common.util.PackageHelper;
import com.salesforce.forcetools.jibx.CustomObject;
import com.salesforce.forcetools.jibx.Metadata;
import com.sforce.soap.metadata.AsyncRequestState;
import com.sforce.soap.metadata.AsyncResult;
import com.sforce.soap.metadata.FileProperties;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.metadata.RetrieveMessage;
import com.sforce.soap.metadata.RetrieveRequest;
import com.sforce.soap.metadata.Package;
import com.sforce.soap.metadata.RetrieveResult;
import com.sforce.ws.ConnectorConfig;

public class OrgDifferenceFinder {
	private OrgInfo org1Info;
	private OrgInfo org2Info;
	public OrgDifferenceFinder(OrgInfo org1Conn, OrgInfo org2Conn) {
		super();
		this.org1Info = org1Conn;
		this.org2Info = org2Conn;
	}
	
	public OrgInfo getOrg1Info() {
		return org1Info;
	}

	public void setOrg1Info(OrgInfo org1Info) {
		this.org1Info = org1Info;
	}

	public OrgInfo getOrg2Info() {
		return org2Info;
	}

	public void setOrg2Info(OrgInfo org2Info) {
		this.org2Info = org2Info;
	}

	public static void main(String[] args) throws Exception{
		OrgDifferenceFinder diffFinder = new OrgDifferenceFinder(null,null);
		byte[] pdfData = diffFinder.generateDifferencesAsPDF(args[0],args[1]);
	}

	public byte[] generateDifferencesAsPDF(String zip1,String zip2) throws IOException,JiBXException,Exception{
		return null;
	}

	public byte[] generateDifferencesAsPDF() throws IOException,JiBXException{
		return null;
		
	}
    private List<CombinedNodeDifference> getDifferences() throws FileNotFoundException,JiBXException{
    	try{
	        MetadataReader reader = new MetadataReader("objects");
	        OrgMetadata org1 = retreiveMetadata(this.org1Info.getMetaConnConfig());
	        OrgMetadata org2 = retreiveMetadata(this.org2Info.getMetaConnConfig());
	    	NodeDifferenceFinder finder = NodeDifferenceFinder.getInstance();
	        NodeDifference diff = finder.findDifferences(org1,org2);
			System.out.println(">>>>>>>>>>>>>>>>>Differences:"+diff);
	        CombinedNodeDifference combinedDifference = new CombinedNodeDifference(diff);
	        List<CombinedNodeDifference> retList = new ArrayList<CombinedNodeDifference>();
	        retList.add(combinedDifference);
	        List<CombinedNodeDifference> objDiff = combinedDifference.getChildDifferencesMap().get("objects");
	        for(Map.Entry<String, List<CombinedNodeDifference>> entry : objDiff.get(0).getChildDifferencesMap().entrySet()){
	        	retList.addAll(entry.getValue());
	        }
	        return retList;
    	}catch(Exception ex){
    		if(ex instanceof FileNotFoundException) {
    			throw (FileNotFoundException)ex;
    		}else if(ex instanceof JiBXException){
    			throw (JiBXException)ex;
    		}else{	
    			throw new RuntimeException(ex);
    		}
    	}
        
    }
    
    private static List<CombinedNodeDifference> getDifferencesFromFile(String zipFile1,String zipFile2) throws Exception{
        OrgMetadata org1 = new OrgMetadata(new ZipArchiveMetadataSource("org1", new ZipInputStream(new FileInputStream(zipFile1))));
        OrgMetadata org2 = new OrgMetadata(new ZipArchiveMetadataSource("org2", new ZipInputStream(new FileInputStream(zipFile2))));
        NodeDifferenceFinder finder = NodeDifferenceFinder.getInstance();
        NodeDifference diff = finder.findDifferences(org1, org2);
        CombinedNodeDifference combinedDifference = new CombinedNodeDifference(diff);
        List<CombinedNodeDifference> retList = new ArrayList<CombinedNodeDifference>(); 
        List<CombinedNodeDifference> objDiff = combinedDifference.getChildDifferencesMap().get("objects");
        for(Map.Entry<String, List<CombinedNodeDifference>> entry : objDiff.get(0).getChildDifferencesMap().entrySet()){
        	retList.addAll(entry.getValue());
        }
        return retList;
        
    }

	public OrgMetadata retreiveMetadata(ConnectorConfig metaConfig) throws Exception{
		RetrieveRequest retReq = new RetrieveRequest();
		Package pkgCfg = PackageHelper.getPackageConfig("ObjectsOnlyConfig");
		retReq.setApiVersion(Double.valueOf(pkgCfg.getVersion()));
		retReq.setUnpackaged(pkgCfg);
		Integer maxPolls = Integer.valueOf(Configuration.getProperty("forcetools","forcetools.metadataPollMax"));
		Integer pollWaitSec = Integer.valueOf(Configuration.getProperty("forcetools","forcetools.metadataPollingInterval"));
		long waitTimeMillis = pollWaitSec*1000;
		Integer currPollCnt=0;
		MetadataConnection metaConn = null;
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

	private void logMessagesAndWarnings(RetrieveResult result){
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
	
	private OrgMetadata extractContents(RetrieveResult result) throws Exception{
    	ZipInputStream zipIn=null;
    	zipIn = initializeInMemoryZip(result);
	    return new OrgMetadata(new ZipArchiveMetadataSource("org1", zipIn));
	}

	private ZipInputStream initializeInMemoryZip(RetrieveResult result) throws Exception{
	    ByteArrayInputStream bais = new ByteArrayInputStream(result.getZipFile());
	    return new ZipInputStream(new BufferedInputStream(bais));
	}


}
