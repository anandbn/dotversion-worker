package com.dotversion.utils;

import org.apache.commons.io.FileUtils;

import com.salesforce.metadata.Metadata;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;
public class CustomFieldReferences {

	public static void main(String args[]) throws ClassNotFoundException{
		@SuppressWarnings("unchecked")
		Collection<File> fileList = FileUtils.listFiles(new File(args[0]),null,true);
		Iterator<File> fileItr = fileList.iterator();
		while(fileItr.hasNext()){
			String fileName = fileItr.next().getName();
			fileName = fileName.substring(0, fileName.length()-5);
			
			printFieldReferences(fileName);
			
		}
		
	}
	
	private static void printFieldReferences(String clsName) throws ClassNotFoundException{
		String metaClassName = String.format("com.salesforce.metadata.%s",clsName);
		@SuppressWarnings("unchecked")
		Class<? extends Metadata> cls = (Class<? extends Metadata>) Class.forName(metaClassName);
        Map<String, String> attributes = new HashMap<String, String>();
       
        for (Method accessorMethod: getAccessorMethods(cls)) {
            String name = parseAccessorVariableName(accessorMethod.getName());
            if(name.indexOf("field")>=0 || name.indexOf("Field")>=0){
            	System.out.println(String.format("%s,%s",clsName,name));
            }
        }

		
	}
	
    private static List<Method> getAccessorMethods(Class<? extends Object> cls) {
        Method methods[] = cls.getDeclaredMethods();
        List<Method> accessorMethods = new ArrayList<Method>();
        for (Method method: methods) {
            String methodName = method.getName();
            if ((methodName.startsWith("get") || methodName.startsWith("is")) && method.getReturnType().equals(String.class)) {
               
            	if (method.getParameterTypes().length == 0) {
                    accessorMethods.add(method);
                }
            }
        }
        return accessorMethods;

    }
    
    private static String parseAccessorVariableName(String methodName) {
        int startIndex;
        if (methodName.startsWith("get")) {
            startIndex = 3;
        }
        else if (methodName.startsWith("is")) {
            startIndex = 2;
        }
        else {
            throw new UnsupportedOperationException("Method name is not an accessor");
        }
        String variableName = methodName.substring(startIndex);
        char firstChar = variableName.charAt(0);
        if (!Character.isLowerCase(firstChar)) {
            variableName = Character.toLowerCase(variableName.charAt(0)) + (variableName.length() > 1 ? variableName.substring(1):"");  
        }
        return variableName;
    }

}
