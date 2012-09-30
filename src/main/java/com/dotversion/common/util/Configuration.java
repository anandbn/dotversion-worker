package com.dotversion.common.util;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.salesforce.metadata.Metadata;
public class Configuration {
	private static Map<String,Properties> allConfigs;
	private static List<String> availableTypes;
	private static Map<String,String> folderToType;
	private static Map<String,String> folderToFileExtension;
	
	static{
		allConfigs = new HashMap<String,Properties>();
	}
	public static String getProperty(String propType,String propKey){
		if(allConfigs.containsKey(propType)){
			return allConfigs.get(propType).getProperty(propKey);
		}else{
			loadProperties(propType);
			return getProperty(propType, propKey);

		}
	}
	
	private static void loadProperties(String type){
		Properties props = new Properties();
		try{
			props.load(Configuration.class.getResourceAsStream("/"+type+".properties"));
			allConfigs.put(type,props);
		}catch(IOException ioEx){
			System.out.println(String.format("Exception loading %s.properties: %s",type,ioEx.getMessage()));
		}
	}
	
	public static List<String> availableMetadataTypes(){
		if(availableTypes==null){
			loadMetadataTypes();
		}
		return availableTypes;
		
		
	}
	
	@SuppressWarnings("unchecked")
	public static Class<? extends Metadata>  metadataClassFor(String folderName) throws ClassNotFoundException{
		if(availableTypes==null){
			loadMetadataTypes();
		}
		return (Class<? extends Metadata>)Class.forName(folderToType.get(folderName));
		
		
	}
	
	public static String  fileExtensionFor(String folderName) throws ClassNotFoundException{
		if(availableTypes==null){
			loadMetadataTypes();
		}
		return folderToFileExtension.get(folderName);
		
		
	}
	private static void loadMetadataTypes(){
		availableTypes = new ArrayList<String>();
		folderToType = new HashMap<String,String>();
		folderToFileExtension = new HashMap<String,String>();
		loadProperties("metadata_types");
		Properties props = allConfigs.get("metadata_types");
		for(String type:props.getProperty("availableTypes").split(",")){
			availableTypes.add(type);
			folderToType.put(props.getProperty(type+".folderName"),
							props.getProperty(type+".metadataClass"));
			folderToFileExtension.put(	props.getProperty(type+".folderName"),
										props.getProperty(type+".fileExtension"));
		}
	}
	
	public static String publicKey() throws IOException{
		String keyStr;
		BufferedReader reader=null;
		try{
			reader = new BufferedReader(new InputStreamReader(Configuration.class.getResourceAsStream("/id_rsa_pub.txt")));
			keyStr = reader.readLine();
			
		}catch(IOException ioEx){
			System.out.println("Could not load public key");
			throw ioEx;
		}finally{
			if(reader!=null){
				reader.close();
			}
		}
		return keyStr;
	}
	
	public static String privateKey() throws IOException{
		String keyStr;
		BufferedReader reader=null;
		try{
			reader = new BufferedReader(new InputStreamReader(Configuration.class.getResourceAsStream("/id_rsa.txt")));
			keyStr = reader.readLine();
			
		}catch(IOException ioEx){
			System.out.println("Could not load public key");
			throw ioEx;
		}finally{
			if(reader!=null){
				reader.close();
			}
		}
		return keyStr;
	}
	
	public static String knownHosts() throws IOException{
		String keyStr;
		BufferedReader reader=null;
		try{
			reader = new BufferedReader(new InputStreamReader(Configuration.class.getResourceAsStream("/known_hosts")));
			keyStr = reader.readLine();
			
		}catch(IOException ioEx){
			System.out.println("Could not load known_hosts");
			throw ioEx;
		}finally{
			if(reader!=null){
				reader.close();
			}
		}
		return keyStr;
	}
	
	public static String templatePOM() throws IOException{
		String pomStr = "";;
		String currLine;
		BufferedReader reader=null;
		try{
			reader = new BufferedReader(new InputStreamReader(Configuration.class.getResourceAsStream("/sample_pom.txt")));
			while((currLine = reader.readLine())!=null){
				pomStr+=currLine;
			}
			
		}catch(IOException ioEx){
			System.out.println("Could not template pom.xml");
			throw ioEx;
		}finally{
			if(reader!=null){
				reader.close();
			}
		}
		return pomStr;
	}
	
}
