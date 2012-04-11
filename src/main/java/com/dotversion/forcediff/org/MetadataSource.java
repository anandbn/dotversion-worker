package com.dotversion.forcediff.org;

import java.io.File;
import java.util.List;

public interface MetadataSource {
    
    public String getName();
    
    public List<String> getSupportedMetadataTypes();
    
    public List<File> getContentList(String metadataType);
    
    public MetadataXmlFile getNextMetadataObject() throws Exception;

}
