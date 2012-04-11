package com.dotversion.common.util;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
public class Configuration {
	private static Map<String,Properties> allConfigs;
	static{
		
		allConfigs = new HashMap<String,Properties>();
		Properties props = new Properties();
		try{
			props.load(ClassLoader.getSystemResourceAsStream("forcetools.properties"));
			allConfigs.put("forcetools",props);
			System.out.println(">>>>>>>>>>>>>>>>>Loaded forcetools.properties");
			props = new Properties();
			props.load(ClassLoader.getSystemResourceAsStream("jasper.properties"));
			allConfigs.put("jasper",props);
			System.out.println(">>>>>>>>>>>>>>>>>Loaded jasper.properties");
		}catch(IOException ioEx){
			System.out.println("Exception loading jasperreportmapping.properties:"+ioEx.getMessage());
		}
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
			props.load(ClassLoader.getSystemResourceAsStream(type+".properties"));
			allConfigs.put(type,props);
		}catch(IOException ioEx){
			System.out.println("Exception loading jasperreportmapping.properties:"+ioEx.getMessage());
		}
		System.out.println(String.format(">>>>>>>>>>>>>>>>>Loaded %s.properties",type));
		
	}
	
}
