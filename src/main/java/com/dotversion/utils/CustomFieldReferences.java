package com.dotversion.utils;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class CustomFieldReferences {
	private static PrintWriter fileOut;
	private static final String METHODS_RETURNING_LISTS_STR = "public java\\.util\\.List<(.*)> (.*)()";
	private static final Pattern METHODS_RETURNING_LISTS = Pattern.compile(METHODS_RETURNING_LISTS_STR);
	private static final String METHODS_RETURNING_METADATA_STR="public (.*) (.*)()";
	private static final Pattern METHODS_RETURNING_METADATA = Pattern.compile(METHODS_RETURNING_METADATA_STR);
	private static final String STATIC_METHODS_STR = "public static .*";
	private static final Pattern STATIC_METHODS = Pattern.compile(STATIC_METHODS_STR);
	public static void main(String args[]){
		fileOut=null;
		try{
			CustomFieldReferences refs = new CustomFieldReferences();
			fileOut = new PrintWriter(new File("methodTree.json"));
			Map<String,List<MethodsToInvoke>> clzAndMethods = refs.initFieldReferences(args[0]);
			ObjectMapper objectMapper =new ObjectMapper();
			objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
			String jsonStr = objectMapper.writeValueAsString(clzAndMethods);
			fileOut.println(jsonStr);
			fileOut.flush();
			
		}catch(Exception ex){
			fileOut.println(ex.getMessage());
		}finally{
			fileOut.close();
		}

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
	public Map<String, List<MethodsToInvoke>> initFieldReferences(String baseDir) throws Exception{
		@SuppressWarnings("unchecked")
		Collection<File> fileList = FileUtils.listFiles(new File(baseDir),null,true);
		Iterator<File> fileItr = fileList.iterator();
		Map<String,List<MethodsToInvoke>> clzAndMethods  = new HashMap<String,List<MethodsToInvoke>>();
		List<MethodsToInvoke> methods;
		while(fileItr.hasNext()){
			String fileName = fileItr.next().getName();
			fileName = fileName.substring(0, fileName.length()-5);
			String metaClassName = String.format("com.salesforce.metadata.%s",fileName);
			if(!fileName.equals("DataCategory")){
				methods = initFieldReferencesForClass(metaClassName);
				if(methods!= null){
					clzAndMethods.put(metaClassName, methods);
				}
			}
			
		}
		return clzAndMethods;

	}
	
	private  List<MethodsToInvoke> initFieldReferencesForClass(String metaClassName) throws ClassNotFoundException{
		@SuppressWarnings("unchecked")
		Class<? extends Metadata> cls = (Class<? extends Metadata>) Class.forName(metaClassName);
		List<Method> methods=null;
        return getAccessorMethods(cls);
		
	}
	
    private  List<MethodsToInvoke> getAccessorMethods(Class<? extends Object> cls) throws ClassNotFoundException {
        Method methods[] = cls.getDeclaredMethods();
        List<MethodsToInvoke> accessorMethods = new ArrayList<MethodsToInvoke>();
        for (Method method: methods) {
            String methodName = method.getName();
            //Skip static methods
            if(!Pattern.matches(STATIC_METHODS_STR, method.toGenericString())){
                if ((methodName.startsWith("get") || methodName.startsWith("is")) && method.getReturnType().equals(String.class)) {
                	fileOut.println(String.format("%s;%s;%s",cls.getName(),method.getName(),method.getReturnType().getName()));
                	fileOut.flush();
                	if (method.getParameterTypes().length == 0) {
                        accessorMethods.add(new MethodsToInvoke(method,null,false));
                    }
                }else{
                	if(!method.getReturnType().equals(void.class)){
                		Matcher m = METHODS_RETURNING_LISTS.matcher(method.toGenericString());
                		if(m.matches()){
                			String retType = m.group(1);
                			if(retType.startsWith("com.salesforce.metadata") && retType.indexOf("DataCategory")==-1){
                				//List returning another metadata class
                				List<MethodsToInvoke> innerMethodsToInv = getAccessorMethods(Class.forName(retType));
                				if(!innerMethodsToInv.isEmpty())
                					accessorMethods.add(new MethodsToInvoke(method,innerMethodsToInv,true));
                			}
                		}
                		m=METHODS_RETURNING_METADATA.matcher(method.toGenericString());
                		if(m.matches()){
                			String retType = m.group(1);
                			if(retType.startsWith("com.salesforce.metadata") && retType.indexOf("DataCategory")==-1){
                				List<MethodsToInvoke> innerMethodsToInv = getAccessorMethods(Class.forName(retType));
                				if(!innerMethodsToInv.isEmpty())
                					accessorMethods.add(new MethodsToInvoke(method,innerMethodsToInv,false));
                			}
                			
                		}
                	}
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
