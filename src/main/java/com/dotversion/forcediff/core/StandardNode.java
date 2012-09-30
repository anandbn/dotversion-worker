package com.dotversion.forcediff.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.salesforce.metadata.Metadata;
import com.salesforce.metadata.MiniLayout;
import com.salesforce.metadata.ObjectRelationship;
import com.salesforce.metadata.Picklist;
import com.salesforce.metadata.SearchLayouts;
import com.salesforce.metadata.CustomField;
import com.salesforce.metadata.SharedTo;
import com.salesforce.metadata.SummaryLayout;
import com.salesforce.metadata.WorkflowEmailRecipient;

public class StandardNode implements Node {
    private static enum NodeChildType {NODELIST, NODE, ATTRIBUTE};
    
    private final String key;
    private final String shortDescription;
    private Map<String, String> attributeMapCache;
    private Map<String, NodeList> childNodeListMap;
    private List<Node> childNodes;
    
    protected StandardNode(Object metadataObj, String key, String shortDescKey) {
        this.key = key;
        this.attributeMapCache = readAttributes(metadataObj);
        this.childNodeListMap = readChildNodeLists(metadataObj);
        this.childNodes = readChildNodes(metadataObj);
        this.shortDescription = this.attributeMapCache.get(shortDescKey);
    }
    
    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getType() {
        // TODO: Make this an enum?
        return "label";
    }

    @Override
    public String getShortDescription() {
        return shortDescription;
    }

    @Override
    public Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(attributeMapCache);
    }

    @Override
    public String getAttributeValue(String name) {
        return attributeMapCache.get(name);
    }

    @Override
    public String toString() {
        return this.getKey();
    }

    private Map<String, String> readAttributes(Object metadataObj) {
        Map<String, String> attributes = new HashMap<String, String>();
        Class<? extends Object> cls = metadataObj.getClass();
        for (Method accessorMethod: getAccessorMethods(cls)) {
            NodeChildType childType = inferChildType(accessorMethod.getReturnType());
            if (childType == NodeChildType.ATTRIBUTE) {
                Object returnObj = null;
                try {
                    returnObj = accessorMethod.invoke(metadataObj);
                } catch (Exception e) {
                    // TODO: Fix exception handling
                    throw new RuntimeException(e);
                }
                String name = parseAccessorVariableName(accessorMethod.getName());
                if (returnObj != null) {
                    // TODO: string conversion, remove -map should be able to hold boolean and other values
                    String attrValue = returnObj.toString();
                    attributes.put(name, attrValue);
                }
            }
        }
        return attributes;
     
    }

    private Map<String, NodeList> readChildNodeLists(Object metadataObj) {
        Map<String, NodeList> childNodesMap = new HashMap<String, NodeList>();
        Class<? extends Object> cls = metadataObj.getClass();
        for (Method accessorMethod: getAccessorMethods(cls)) {
            NodeChildType childType = inferChildType(accessorMethod.getReturnType());
            if (childType == NodeChildType.NODELIST) {
                Object returnObj = null;
                try {
                    returnObj = accessorMethod.invoke(metadataObj);
                } catch (Exception e) {
                    // TODO: Fix exception handling
                    throw new RuntimeException(e);
                }

                String childNodeName = parseAccessorVariableName(accessorMethod.getName());
                List childList = (List) returnObj;
                NodeList childNodeList = MetadataFactory.createNodeListInstance(childList);

                if (childNodeList != null) {
                    childNodesMap.put(childNodeName, childNodeList);
                }
                else {
                    childNodesMap.put(childNodeName, MetadataFactory.createNodeListInstance(Collections.emptyList()));
                }
            }
            
        }
        return childNodesMap;
     
    }
    
    // TODO: REMOVE duplication across readattribute, readchildnodelist and this method.
    private List<Node> readChildNodes(Object metadataObj) {
        List<Node> childNodes = new ArrayList<Node>();
        Class<? extends Object> cls = metadataObj.getClass();
        for (Method accessorMethod: getAccessorMethods(cls)) {
            NodeChildType childType = inferChildType(accessorMethod.getReturnType());
            if (childType == NodeChildType.NODE) {
                Object returnObj = null;
                try {
                    returnObj = accessorMethod.invoke(metadataObj);
                } catch (Exception e) {
                    // TODO: Fix exception handling
                    throw new RuntimeException(e);
                }

                String childNodeName = parseAccessorVariableName(accessorMethod.getName());
                Node childNode = null;
                if (returnObj != null) {
                    if (returnObj instanceof Metadata) {
                        Metadata metadata = (Metadata) returnObj;
                        if (metadata != null) {
                            childNode = MetadataFactory.getNodeInstance(metadata, childNodeName);                            
                        }
                    }
                    else {
                        assert returnObj instanceof SearchLayouts;
                        childNode = MetadataFactory.getNodeInstance(returnObj, childNodeName);
                    }
                }

                if (childNode != null) {
                    childNodes.add(childNode);
                }
            }
            
        }
        return childNodes;
     
    }
    
    private NodeChildType inferChildType(Class cls) {
        if (cls.equals(List.class)) {
            return NodeChildType.NODELIST;
        }
        else if (	Metadata.class.isAssignableFrom(cls) || SearchLayouts.class.equals(cls) || 
        			Picklist.class.equals(cls) || SharedTo.class.equals(cls) ||
        			MiniLayout.class.equals(cls) || SummaryLayout.class.equals(cls) || ObjectRelationship.class.equals(cls) ||
        			WorkflowEmailRecipient.class.equals(cls)) {
            return NodeChildType.NODE;
        }
        else {
            return NodeChildType.ATTRIBUTE;
        }
        
    }
    
    private List<Method> getAccessorMethods(Class<? extends Object> cls) {
        Method methods[] = cls.getDeclaredMethods();
        List<Method> accessorMethods = new ArrayList<Method>();
        for (Method method: methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get") || methodName.startsWith("is")) {
                if (method.getParameterTypes().length == 0) {
                    accessorMethods.add(method);
                }
            }
        }
        return accessorMethods;

    }
    
    private String parseAccessorVariableName(String methodName) {
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

    @Override
    public boolean hasChildNodes() {
        return !this.childNodeListMap.isEmpty() || !this.childNodes.isEmpty();
    }

    @Override
    public List<Node> getChildNodes() {
        return this.childNodes;
    }

    @Override
    public NodeList getChildNodeList(String childNodeType) {
        return this.childNodeListMap.get(childNodeType);
    }

    @Override
    public Collection<String> getSupportedChildNodeListTypes() {
        return this.childNodeListMap.keySet();
    }
}
