package com.dotversion.forcediff.org;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.dotversion.forcediff.core.MetadataDirectoryNode;
import com.dotversion.forcediff.core.Node;
import com.dotversion.forcediff.core.NodeList;

public class OrgMetadata implements Node {
    
    private String orgName;
    private List<Node> metadataTopNodeList;

    public static void main(String args[]){
    	OrgMetadata current = new OrgMetadata(new DirectoryMetadataSource("current","/var/folders/_x/74kz_jr14pq10flqg1trfjqr0000gn/T/00D70000000JSvzEAG/unpackaged"));
    }
    public OrgMetadata(MetadataSource source) {
        this.orgName = source.getName();

        this.metadataTopNodeList = new ArrayList<Node>();
        MetadataXmlFile xmlData;
        metadataTopNodeList = new ArrayList<Node>();
        String currMetaType=null,prevMetaType=null;
        MetadataDirectoryNode currTypeNode=null;
        try{
	        while((xmlData = source.getNextMetadataObject())!=null){
        		currMetaType=xmlData.getMetadataType();
	        	if(prevMetaType==null){
	        		prevMetaType=xmlData.getMetadataType();
	        		currTypeNode = new MetadataDirectoryNode(currMetaType);
	        		
	        	}
	        	if(!currMetaType.equalsIgnoreCase(prevMetaType)){
	        		metadataTopNodeList.add(currTypeNode);
	        		currTypeNode = new MetadataDirectoryNode(currMetaType);
	        		prevMetaType=xmlData.getMetadataType();
	        	}
	        	currTypeNode.addChildNode(xmlData);
	        }
    		metadataTopNodeList.add(currTypeNode);
        }catch(Exception ex){
        	ex.printStackTrace();
        	throw new RuntimeException(ex);
        }
    }
    
    
    @Override
    public String getType() {
        return "org";
    }

    @Override
    public String getKey() {
        return "org";
    }

    @Override
    public String getShortDescription() {
        return orgName;
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
    public List<Node> getChildNodes() {
        return metadataTopNodeList;
    }

}
