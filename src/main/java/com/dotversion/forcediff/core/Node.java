package com.dotversion.forcediff.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface Node {
    public String getType();

    public String getKey();
	
    public String getShortDescription();

    public Map<String, String> getAttributes();
	
	public String getAttributeValue(String name);
	
	public boolean hasChildNodes();
	
	public NodeList getChildNodeList(String childNodeType);
	
	public Collection<String> getSupportedChildNodeListTypes();

    List<Node> getChildNodes();
}