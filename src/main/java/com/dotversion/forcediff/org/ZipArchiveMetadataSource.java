package com.dotversion.forcediff.org;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.dotversion.common.util.Configuration;
import com.salesforce.metadata.ApexClass;
import com.salesforce.metadata.ApexComponent;
import com.salesforce.metadata.ApexPage;
import com.salesforce.metadata.ApexTrigger;
import com.salesforce.metadata.Document;
import com.salesforce.metadata.EmailTemplate;
import com.salesforce.metadata.Metadata;
import com.salesforce.metadata.MetadataWithContent;
import com.salesforce.metadata.Scontrol;
import com.salesforce.metadata.StaticResource;

public class ZipArchiveMetadataSource  implements MetadataSource {
	private String sourceName;
    private List<String> metadataTypes;
    private ZipInputStream zipIn;
	public String getName(){return this.sourceName;}
    private String currMetadataType;
    private static final String TYPES_WITH_CONTENT="components;classes;pages;triggers;staticResources;";
	@Override
    public List<String> getSupportedMetadataTypes() {
        return this.metadataTypes;
    } 
    public List<File> getContentList(String metadataType){
    	return null;
    }
    
    public ZipArchiveMetadataSource(String sourceName, ZipInputStream zipIn) throws Exception {
        this.sourceName = sourceName;
        this.zipIn=zipIn;
    }

    @Override
    public MetadataXmlFile getNextMetadataObject() throws Exception{
        ZipEntry currEntry= zipIn.getNextEntry();
        MetadataXmlFile xmlFile=null;
        //Skip package.xml
        while(currEntry!=null && (currEntry.getName().indexOf("package.xml")>-1 || currEntry.isDirectory())){
        	currEntry = zipIn.getNextEntry();
        }
        if(currEntry!=null){
    		this.currMetadataType=getMetadatTypeFromZipEntry(currEntry.getName());
    	    if(TYPES_WITH_CONTENT.indexOf(this.currMetadataType)>-1){
    	    	currEntry = zipIn.getNextEntry();
    		}
    		xmlFile = new MetadataXmlFile(	this.currMetadataType,
        									getNameFromZipEntry(currEntry.getName()),
											extractXMLFromZip());
        	
        }else{
        	zipIn.close();
        }
        return xmlFile;

    }
    
	private ByteArrayInputStream extractXMLFromZip() throws Exception{
		int count;
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream(); 
        byte data[] = new byte[2048];
        while ((count = zipIn.read(data, 0, 2048)) != -1) {
        	byteOut.write(data, 0, count);
        }		
        zipIn.closeEntry();
		return new ByteArrayInputStream(byteOut.toByteArray());
	}
	
	private String getMetadatTypeFromZipEntry(String zipEntryName){
		String objName="";
		//Strip of "unpackaged"
		objName = zipEntryName.substring(zipEntryName.indexOf('/')+1);
		//Return the metadata type 
		objName = objName.substring(0,objName.indexOf('/'));
		return objName;
	}
	private String getNameFromZipEntry(String zipEntryName){
		String objName="";
		if(zipEntryName.indexOf('.')>-1){
			//Strip of "unpackaged"
			objName = zipEntryName.substring(zipEntryName.indexOf('/')+1);
			objName = objName.substring(objName.indexOf('/'));
			objName = objName.substring(1,objName.indexOf("."));
		}else{
			//Strip of "unpackaged"
			objName = zipEntryName.substring(zipEntryName.indexOf('/'));
			objName=zipEntryName.substring(0,objName.length()-1);
		}
		return objName;
	}

}
