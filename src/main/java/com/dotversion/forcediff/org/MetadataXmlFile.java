package com.dotversion.forcediff.org;
import java.io.*;
public class MetadataXmlFile {
	private String metadataType;
	private String name;
	private InputStream fileContents;
	public MetadataXmlFile(	String metadataType,String name,InputStream fileContents){
		super();
		this.metadataType = metadataType;
		this.name = name;
		this.fileContents = fileContents;
	}

	public String getMetadataType() {
		return metadataType;
	}
	public void setMetadataType(String metadataType) {
		this.metadataType = metadataType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public InputStream getFileContents() {
		return fileContents;
	}
	public void setFileContents(InputStream fileContents) {
		this.fileContents = fileContents;
	}
	
}
