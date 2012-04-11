package com.dotversion.forcediff.org;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import com.dotversion.forcediff.core.CombinedNodeDifference;
import com.dotversion.forcediff.core.NodeDifference;
import com.dotversion.forcediff.core.NodeDifferenceFinder;
import com.dotversion.forcediff.core.NodeListDifference;
import com.dotversion.common.util.Configuration;

public class OrgMetadataMain {
    
    public static void main(String[] args) {
    		String zipEntryName = "unpackaged/objects/Error_Message__c.object";
    		String objName="";
    		if(zipEntryName.indexOf('.')>-1){
    			//Strip of "unpackaged"
    			
    			objName = zipEntryName.substring(zipEntryName.indexOf('/')+1);
    			String metaType = objName.substring(0,objName.indexOf('/'));
    			objName = objName.substring(objName.indexOf('/'));
    			objName = objName.substring(1,objName.indexOf("."));
    		}else{
    			//Strip of "unpackaged"
    			objName = zipEntryName.substring(zipEntryName.indexOf('/'));
    			objName=zipEntryName.substring(0,objName.length()-1);
    		}
    	
		
    }
    private static List<CombinedNodeDifference> getDifferences(String zipFile1,String zipFile2) throws Exception{
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

    
    private static List<CombinedNodeDifference> getDifferences(){
        OrgMetadata org1 = new OrgMetadata(new DirectoryMetadataSource("org1", Configuration.getProperty("forcetools", "forcetools.forcediff.sourcefile")));
        OrgMetadata org2 = new OrgMetadata(new DirectoryMetadataSource("org2", Configuration.getProperty("forcetools", "forcetools.forcediff.destFile")));
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
}
