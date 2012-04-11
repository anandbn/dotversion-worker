package com.dotversion.forcediff.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NodeDifference {

	public enum DifferenceType { LEFT_ONLY, RIGHT_ONLY, BOTH }

    private final String nodeKey;
    private final List<AttributeDifference> attributeDifferences;
    private final Map<String, NodeListDifference> childNodeListDifferenceMap;
    private final NodeListDifference childNodeDifferences;
    private final DifferenceType diffType;

    public NodeDifference(String key, List<AttributeDifference> attrDifferences, NodeListDifference childNodeDiff, Map<String, NodeListDifference> childNodeListDiffMap) {
        this.nodeKey = key;
        this.attributeDifferences = attrDifferences!=null?Collections.unmodifiableList(attrDifferences):null;
        this.childNodeListDifferenceMap = childNodeListDiffMap!=null?Collections.unmodifiableMap(childNodeListDiffMap):null;
        this.childNodeDifferences = childNodeDiff;
        this.diffType=DifferenceType.BOTH;
    }

    public NodeDifference(	String key, List<AttributeDifference> attrDifferences, NodeListDifference childNodeDiff, 
    						Map<String, NodeListDifference> childNodeListDiffMap,DifferenceType typeOfDiff) {
        this.nodeKey = key;
        this.attributeDifferences = attrDifferences!=null?Collections.unmodifiableList(attrDifferences):null;
        this.childNodeListDifferenceMap = childNodeListDiffMap!=null?Collections.unmodifiableMap(childNodeListDiffMap):null;
        this.childNodeDifferences = childNodeDiff;
        this.diffType=typeOfDiff;
    }

    public boolean hasDifferences() {
        if (!attributeDifferences.isEmpty()) return true;
        if (childNodeDifferences != null && childNodeDifferences.hasDifferences()) return true;
        for (NodeListDifference childNodeListDifference: childNodeListDifferenceMap.values()) {
            if (childNodeListDifference.hasDifferences()) return true;
        }
        return false;
    }

    public boolean getLeftOnlyDifference(){
    	return this.diffType.equals(DifferenceType.LEFT_ONLY)||this.diffType.equals(DifferenceType.BOTH);
    }
    public boolean getRightOnlyDifference(){
    	return this.diffType.equals(DifferenceType.RIGHT_ONLY)||this.diffType.equals(DifferenceType.BOTH);
    }
    
    public List<AttributeDifference> getAttributeDifferences() {
        return attributeDifferences;
    }

    public NodeListDifference getChildNodeDifferences() {
        return this.childNodeDifferences;
    }


    public Map<String, NodeListDifference> getChildNodeListDifferenceMap() {
		return childNodeListDifferenceMap;
	}

    public NodeListDifference getChildNodeListDifference(String childType) {
        NodeListDifference diff = childNodeListDifferenceMap.get(childType);
        return diff;
    }

    public NodeListDifference getChildNodeListDifference() {
        Set<String> keySet = childNodeListDifferenceMap.keySet();
        assert keySet.size() <= 1;
        if (keySet.size() == 0) return null;
        return childNodeListDifferenceMap.get(keySet.iterator().next());
    }
    
    public String getNodeKey(){
    	return this.nodeKey;
    }
    
    @Override
    public String toString() {
        return String.format("Node name - %s, Attribute differences - %s, ChildNodeDifference - %s, ChildNodeListDifferences - %s", 
                nodeKey,
                attributeDifferences.toString(),
                childNodeDifferences == null ? "NULL" : childNodeDifferences.toString(),
                childNodeListDifferenceMap == null ? "NULL" : childNodeListDifferenceMap.toString());
    }
}