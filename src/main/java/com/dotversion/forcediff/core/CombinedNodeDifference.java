package com.dotversion.forcediff.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 * This is a decorator to the <code>NodeDifference</code> class that will
 * combine all differences into a map that can be used in output
 * generators
 */
public class CombinedNodeDifference {
	private NodeDifference nodeDifference;
	private Map<String,CombinedNodeDifference> childDifferencesMap;
	public CombinedNodeDifference(NodeDifference nodeDiff){
		this.nodeDifference=nodeDiff;
		this.childDifferencesMap = new HashMap<String,CombinedNodeDifference>();
		combineDifferences();
	}
	
	@Override
	public String toString() {
		return "CombinedNodeDifference [nodeKey =" + getNodeKey() +
				", attribute-differences=" + (getAttributeDifferences().isEmpty() || getAttributeDifferences()==null?0:getAttributeDifferences().size())
				+ ", childDifferences=" + (getChildDifferencesMap().isEmpty()|| getChildDifferencesMap()==null?0:getChildDifferencesMap().size())
				+ "]";
	}

	public Map<String, CombinedNodeDifference> getChildDifferencesMap() {
		return childDifferencesMap;
	}

	public List<AttributeDifference> getAttributeDifferences(){
		return this.nodeDifference.getAttributeDifferences();
	}
	
	public String getNodeKey(){
		return this.nodeDifference.getNodeKey();
	}
	
    public boolean getLeftOnlyDifference(){
    	return this.nodeDifference.getLeftOnlyDifference();
    }
    public boolean getRightOnlyDifference(){
    	return this.nodeDifference.getRightOnlyDifference();
    }

    private void combineDifferences(){
		/*
		 * Get the node level differences and combine them into the HashMap
		 */
    	if(this.nodeDifference!=null){
			NodeListDifference listDiff = this.nodeDifference.getChildNodeDifferences();
			NodeDifference nodeDiff;
			List<CombinedNodeDifference> finalList;
			if(listDiff!=null){
				if(listDiff.getLeftOnlyNodes()!=null && !listDiff.getLeftOnlyNodes().isEmpty()){
					for(Node node:listDiff.getLeftOnlyNodes()){
						nodeDiff = new NodeDifference(node.getKey(),null,null,null,NodeDifference.DifferenceType.LEFT_ONLY);
						childDifferencesMap.put(nodeDiff.getNodeKey(),new CombinedNodeDifference(nodeDiff));
					}
				}
				if(listDiff.getRightOnlyNodes()!=null &&  !listDiff.getRightOnlyNodes().isEmpty()){
					for(Node node:listDiff.getRightOnlyNodes()){
						nodeDiff = new NodeDifference(node.getKey(),null,null,null,NodeDifference.DifferenceType.RIGHT_ONLY);
						childDifferencesMap.put(nodeDiff.getNodeKey(),new CombinedNodeDifference(nodeDiff));
					}
				}
				if(listDiff.getNodeDifferences()!=null){
					for(NodeDifference commonDiff :listDiff.getNodeDifferences()){
						childDifferencesMap.put(commonDiff.getNodeKey(),new CombinedNodeDifference(commonDiff));
					}
					
				}
				
			}
			
			if(	this.nodeDifference.getChildNodeListDifferenceMap()!=null && 
				!this.nodeDifference.getChildNodeListDifferenceMap().isEmpty()){
				
				for(Map.Entry<String, NodeListDifference> entry : this.nodeDifference.getChildNodeListDifferenceMap().entrySet()){
					finalList = new ArrayList<CombinedNodeDifference>();
					NodeListDifference childListDiff = entry.getValue();
					if(childListDiff!=null){
						for(Node node: childListDiff.getLeftOnlyNodes()){
							nodeDiff = new NodeDifference(node.getKey(),null,null,null,NodeDifference.DifferenceType.LEFT_ONLY);
							childDifferencesMap.put(node.getKey(), new CombinedNodeDifference(nodeDiff));
						}
						for(Node node: childListDiff.getRightOnlyNodes()){
							nodeDiff = new NodeDifference(node.getKey(),null,null,null,NodeDifference.DifferenceType.RIGHT_ONLY);
							childDifferencesMap.put(node.getKey(), new CombinedNodeDifference(nodeDiff));
						}
						for(NodeDifference childNodeDiff:childListDiff.getNodeDifferences()){
							childDifferencesMap.put(childNodeDiff.getNodeKey(), new CombinedNodeDifference(childNodeDiff));
						}
						
					}
					
					
				}
			}
    	}
	}
}
