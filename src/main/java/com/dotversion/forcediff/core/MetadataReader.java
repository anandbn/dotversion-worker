package com.dotversion.forcediff.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.bind.JAXBException;

import com.salesforce.metadata.Metadata;

public class MetadataReader {
    Class<? extends Metadata> metadataCls;
    
    public MetadataReader(String metadataType) throws ClassNotFoundException, JAXBException {
        Class<? extends com.salesforce.metadata.Metadata> clsName = MetadataFactory.getMetadataClass(metadataType);
        initialize(clsName);
    }
    
    public MetadataReader(Class<? extends Metadata> metadataCls) throws JAXBException {
        initialize(metadataCls);
    }
    
    private void initialize(Class<? extends Metadata> metadataCls) throws JAXBException {
        this.metadataCls=metadataCls;
    }
    
    public Metadata readMetadataFromFile(File file) throws FileNotFoundException, JAXBException {
    	return readMetadataFromStream(new FileInputStream(file));
    }
    public Metadata readMetadataFromStream(InputStream in) throws FileNotFoundException, JAXBException {
        
        Metadata metadataIn = (Metadata) JaxbUnmarshallerCache.unmarshallerFor(this.metadataCls)
        													  .unmarshal(in);
        return metadataIn;
    }

}
