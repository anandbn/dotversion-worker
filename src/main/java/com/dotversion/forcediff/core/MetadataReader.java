package com.dotversion.forcediff.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

import com.salesforce.forcetools.jibx.Metadata;

public class MetadataReader {
    IUnmarshallingContext metadataUnmarshallContext;
    
    public MetadataReader(String metadataType) throws JiBXException {
        Class<? extends Metadata> metadataCls = MetadataFactory.getMetadataClass(metadataType);
        initialize(metadataCls);
    }
    
    public MetadataReader(Class<? extends Metadata> metadataCls) throws JiBXException {
        initialize(metadataCls);
    }
    
    private void initialize(Class<? extends Metadata> metadataCls) throws JiBXException {
        IBindingFactory bfact = BindingDirectory.getFactory(metadataCls);
        this.metadataUnmarshallContext = bfact.createUnmarshallingContext();
    }
    
    public Metadata readMetadataFromFile(File file) throws JiBXException, FileNotFoundException {
    	return readMetadataFromStream(new FileInputStream(file));
    }
    public Metadata readMetadataFromStream(InputStream in) throws JiBXException, FileNotFoundException {
        Metadata metadataIn = (Metadata)this.metadataUnmarshallContext.unmarshalDocument(in, null);
        return metadataIn;
    }

}
