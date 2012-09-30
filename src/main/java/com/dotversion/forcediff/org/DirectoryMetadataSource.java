package com.dotversion.forcediff.org;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class DirectoryMetadataSource implements MetadataSource {
    
    private String sourceName;
    private File metadataBaseDir;
    private List<String> metadataTypes;
    private Iterator<File> metadataFiles;
    
    private String currMetadataType;
    private static final String TYPES_WITH_CONTENT="components;classes;pages;triggers;staticResources;";
   
    
    public static void main(String args[]) throws Exception{
    	DirectoryMetadataSource src = new DirectoryMetadataSource("current","/var/folders/_x/74kz_jr14pq10flqg1trfjqr0000gn/T/00D70000000JSvzEAG/unpackaged");
    	MetadataXmlFile file = src.getNextMetadataObject();
    	while(file!=null){
    		System.out.println(String.format("type:%s,name=%s",file.getMetadataType(),file.getName()));
    		file = src.getNextMetadataObject();
    	}
    }
    @SuppressWarnings("unchecked")
	public DirectoryMetadataSource(String sourceName, String baseDir) {
        this.sourceName = sourceName;
        metadataBaseDir = new File(baseDir);
        assert metadataBaseDir.isDirectory();
        @SuppressWarnings("rawtypes")
		Collection allFiles = FileUtils.listFiles(this.metadataBaseDir, null, true);
        metadataFiles = allFiles.iterator();
    }
    
    @Override
    public String getName() {
        return this.sourceName;
    }

    @Override
    public List<String> getSupportedMetadataTypes() {
        return this.metadataTypes;
    }

    @Override
    public List<File> getContentList(String metadataType) {
        assert this.metadataTypes.contains(metadataType);
        File nodeDir = new File(this.metadataBaseDir + File.separator + metadataType);
        assert nodeDir.isDirectory();
        List<File> contentList = new ArrayList<File>();
        for (File f: nodeDir.listFiles()) {
            contentList.add(f);
        }
        return contentList;
    }

    
    @Override
    public MetadataXmlFile getNextMetadataObject() throws Exception{
    	if(this.metadataFiles.hasNext()){
    		File currFile = this.metadataFiles.next();
            MetadataXmlFile xmlFile=null;
            //Skip package.xml
            while(currFile!=null && (currFile.getName().indexOf("package.xml")>-1 || currFile.isDirectory() || currFile.getName().indexOf("DS_Store")>-1)){
            	if(this.metadataFiles.hasNext()){
            		currFile = this.metadataFiles.next(); 
            	}else{
            		return null;
            	}
            }
            if(currFile!=null){
        		this.currMetadataType=getMetadatTypeFromPath(currFile.getPath());
        	    if(TYPES_WITH_CONTENT.indexOf(this.currMetadataType)>-1){
        	    	currFile = this.metadataFiles.next(); 
        		}
        		xmlFile = new MetadataXmlFile(	this.currMetadataType,
            									getNameFromZipEntry(currFile.getName()),
            									extractXMLFromFile(currFile));
            	
            }else{
            	return null;
            }
            return xmlFile;

    	
    	}
    	return null;
    }
    
    
    private String getMetadatTypeFromPath(String filePath){
		String objName="";
		//Strip of "unpackaged"
		objName = filePath.substring(filePath.indexOf("unpackaged/")+"unpackaged/".length());
		//Return the metadata type 
		objName = objName.substring(0,objName.indexOf('/'));
		return objName;
	}
	private String getNameFromZipEntry(String zipEntryName){
		String objName="";
		if(zipEntryName.indexOf('.')>-1){
			//Strip of "unpackaged"
			objName = zipEntryName.substring(zipEntryName.indexOf('/')+1);
			if(objName.indexOf('/')>-1){
				objName = objName.substring(objName.indexOf('/'));
			}
			objName = objName.substring(0,objName.indexOf("."));
		}
		return objName;
	}
	
	private ByteArrayInputStream extractXMLFromFile(File fileToRead) throws Exception{
		return new ByteArrayInputStream(FileUtils.readFileToByteArray(fileToRead));
	}

}
