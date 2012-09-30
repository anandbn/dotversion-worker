package com.dotversion.forcediff.core;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class JaxbUnmarshallerCache {

	private static Map<String,Unmarshaller> unmarshallers;
	static{
		unmarshallers = new HashMap<String,Unmarshaller>();
	}
	public static Unmarshaller unmarshallerFor(Class clz) throws JAXBException{
		Unmarshaller unmarshaller = unmarshallers.get(clz.getName());
		if(unmarshaller ==null){
			JAXBContext context = JAXBContext.newInstance(clz);
			unmarshaller = context.createUnmarshaller();
			unmarshallers.put(clz.getName(),unmarshaller );
		}
		return unmarshaller;
		
	}
	
}
