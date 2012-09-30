package com.dotversion.forcediff.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.dotversion.forcediff.org.MetadataXmlFile;
import com.salesforce.metadata.Metadata;

public class MetadataDirectoryNode implements Node {
    
    private String metadataType;
    private List<Node> childNodes;
    private MetadataReader reader;
    
    public MetadataDirectoryNode(String metadataType, List<File> contentList) {
        this.metadataType = metadataType;
        
        try {
            MetadataReader reader = new MetadataReader(metadataType);
            
            childNodes = new ArrayList<Node>();
            for (File f: contentList) {
                String fileName = f.getName();
                String metadataKey = fileName.substring(0, fileName.lastIndexOf("."));
                Metadata metadata = reader.readMetadataFromFile(f);
                childNodes.add(MetadataFactory.getNodeInstance(metadata, metadataKey));
            }
        }
        catch (Exception e) {
            // TODO: handle exception better.
            throw new RuntimeException(e);
        }
        
        
        
    }
    
    public MetadataDirectoryNode(String metadataType){
        this.metadataType = metadataType;
        childNodes = new ArrayList<Node>();
        try {
            this.reader = new MetadataReader(metadataType);
        }
        catch (Exception e) {
            // TODO: handle exception better.
            throw new RuntimeException(e);
        }
    	
    }
    
    public void addChildNode(MetadataXmlFile metadataXml) throws Exception{
    	Metadata metadata = reader.readMetadataFromStream(metadataXml.getFileContents());
    	childNodes.add(MetadataFactory.getNodeInstance(metadata, metadataXml.getName()));
    	
    }

    @Override
    public String getType() {
        return this.metadataType;
    }

    @Override
    public String getKey() {
        return metadataType;
    }

    @Override
    public String getShortDescription() {
        return metadataType;
    }

    @Override
    public Map<String, String> getAttributes() {
        return Collections.emptyMap();
    }

    @Override
    public String getAttributeValue(String name) {
        return null;
    }

    @Override
    public boolean hasChildNodes() {
        return true;
    }

    @Override
    public NodeList getChildNodeList(String childNodeType) {
        return null;

    }

    @Override
    public Collection<String> getSupportedChildNodeListTypes() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return this.getKey();
    }

    @Override
    public List<Node> getChildNodes() {
        return this.childNodes;
    }

}
