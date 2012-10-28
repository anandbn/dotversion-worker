package com.dotversion.utils;

import org.apache.commons.io.FileUtils;

import com.dotversion.forcediff.core.MetadataDirectoryNode;
import com.dotversion.forcediff.core.MetadataReader;
import com.dotversion.forcediff.core.Node;
import com.dotversion.forcediff.org.DirectoryMetadataSource;
import com.dotversion.forcediff.org.MetadataXmlFile;
import com.dotversion.forcediff.org.OrgMetadata;
import com.salesforce.metadata.Metadata;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
public class CustomFieldReferences {
	private PrintWriter fileOut;
	public static void main(String args[]) throws Exception{
		
		CustomFieldReferences refs = new CustomFieldReferences();
		Map<String,List<Method>> clzAndMethods = refs.initFieldReferences(args[0]);
		/*
		DirectoryMetadataSource source = new DirectoryMetadataSource("current","/Users/anarasimhan/tmp/fdc-00dt0000000gmupmao/unpackaged");
        MetadataXmlFile xmlData;
        MetadataReader reader;
        
        try{
	        while((xmlData = source.getNextMetadataObject())!=null){
        		String currMetaType = xmlData.getMetadataType();
        		reader = new MetadataReader(currMetaType);
        		Metadata metadata = reader.readMetadataFromStream(xmlData.getFileContents());
        		refs.findFieldReferences(metadata,currMetaType,clzAndMethods);
	        }
        }catch(Exception ex){
        	ex.printStackTrace();
        	throw new RuntimeException(ex);
        }*/

	}
	
	public CustomFieldReferences() throws Exception{
		fileOut = new PrintWriter(new File("methodsAndArgs.txt"));

	}
	
	public void findFieldReferences(Metadata metadata,String metadataType,Map<String,List<Method>> clzAndMethods) throws Exception{
		String clzName = metadata.getClass().getName();
		if(clzAndMethods.containsKey(clzName)){
			for(Method mthd: clzAndMethods.get(clzName)){
				Object value = mthd.invoke(metadata);
				System.out.println(String.format(	"Metadata Object:%s,Class:%s,methodName:%s, value=%s",
													metadata.getFullName(),clzName,mthd.getName(),value));
			}
		}else{
			System.out.println(String.format("Class:%s has no 'field' references",clzName));
		}
	}
	public Map<String, List<Method>> initFieldReferences(String baseDir) throws Exception{
		@SuppressWarnings("unchecked")
		Collection<File> fileList = FileUtils.listFiles(new File(baseDir),null,true);
		Iterator<File> fileItr = fileList.iterator();
		Map<String,List<Method>> clzAndMethods  = new HashMap<String,List<Method>>();
		List<Method> methods;
		while(fileItr.hasNext()){
			String fileName = fileItr.next().getName();
			fileName = fileName.substring(0, fileName.length()-5);
			String metaClassName = String.format("com.salesforce.metadata.%s",fileName);
			
			methods = initFieldReferencesForClass(metaClassName);
			if(methods!= null){
				clzAndMethods.put(metaClassName, methods);
			}
			
		}
		return clzAndMethods;

	}
	
	private  List<Method> initFieldReferencesForClass(String metaClassName) throws ClassNotFoundException{
		@SuppressWarnings("unchecked")
		Class<? extends Metadata> cls = (Class<? extends Metadata>) Class.forName(metaClassName);
		List<Method> methods=null;
        for (Method accessorMethod: getAccessorMethods(cls)) {
            String name = parseAccessorVariableName(accessorMethod.getName());
            if(name.indexOf("field")>=0 || name.indexOf("Field")>=0){
            	if(methods ==null){
            		methods = new ArrayList<Method>();
            	}
            	methods.add(accessorMethod);
            	//System.out.println(String.format("%s.%s() has a  'field' reference.",metaClassName,name,accessorMethod.getName()));
            }else{
            	//System.out.println(String.format("%s.%s() has no 'field' references",metaClassName,name,accessorMethod.getName()));
            	
            }
        }
        return methods;
		
	}
	
    private  List<Method> getAccessorMethods(Class<? extends Object> cls) {
        Method methods[] = cls.getDeclaredMethods();
        List<Method> accessorMethods = new ArrayList<Method>();
 	        for (Method method: methods) {
	            String methodName = method.getName();
	            if ((methodName.startsWith("get") || methodName.startsWith("is")) && method.getReturnType().equals(String.class)) {
	            	fileOut.println(String.format("%s;%s;%s",cls.getName(),method.getName(),method.getReturnType().getName()));
	            	if (method.getParameterTypes().length == 0) {
	                    accessorMethods.add(method);
	                }
	            }else{
	            	fileOut.println(String.format("%s;%s;%s",cls.getName(),method.getName(),method.getReturnType().getName()));
	            	if(method.getName().equalsIgnoreCase("getFields")){
	            		Class clz = method.getReturnType().getClass();
	            		
	            		System.out.println("");
	     
	            	}
	            }
	        }
        return accessorMethods;

    }
    
    private  String parseAccessorVariableName(String methodName) {
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
