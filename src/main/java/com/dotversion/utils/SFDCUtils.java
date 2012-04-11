package com.dotversion.utils;

import java.util.regex.Pattern;

public class SFDCUtils {

	public static String getOrgType(String apiEndpoint){
		if(apiEndpoint.startsWith("https://na")){
			return "Production";
		}else if (apiEndpoint.startsWith("https://cs")){
			return "Sandbox";
		}else if (apiEndpoint.indexOf("my.salesforce.com")>-1){
			return "Production";
		}else{
			return "Production";
		}
	}
}
