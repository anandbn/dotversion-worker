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
	private Map<String,List<CombinedNodeDifference>> childDifferencesMap;
	public CombinedNodeDifference(NodeDifference nodeDiff){
		this.nodeDifference=nodeDiff;
		this.childDifferencesMap = new HashMap<String,List<CombinedNodeDifference>>();
		combineDifferences();
	}
	
	public Map<String, List<CombinedNodeDifference>> getChildDifferencesMap() {
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
		NodeListDifference listDiff = this.nodeDifference.getChildNodeDifferences();
		NodeDifference nodeDiff;
		List<CombinedNodeDifference> finalList;
		if(listDiff!=null){
			if(listDiff.getLeftOnlyNodes()!=null){
				for(Node node:listDiff.getLeftOnlyNodes()){
					finalList = new ArrayList<CombinedNodeDifference>();
					nodeDiff = new NodeDifference(node.getKey(),null,null,null,NodeDifference.DifferenceType.LEFT_ONLY);
					finalList.add(new CombinedNodeDifference(nodeDiff));
					childDifferencesMap.put(nodeDiff.getNodeKey(),finalList);
				}
			}
			if(listDiff.getRightOnlyNodes()!=null){
				for(Node node:listDiff.getRightOnlyNodes()){
					finalList = new ArrayList<CombinedNodeDifference>();
					nodeDiff = new NodeDifference(node.getKey(),null,null,null,NodeDifference.DifferenceType.RIGHT_ONLY);
					finalList.add(new CombinedNodeDifference(nodeDiff));
					childDifferencesMap.put(nodeDiff.getNodeKey(),finalList);
				}
			}
			if(listDiff.getNodeDifferences()!=null){
				for(NodeDifference commonDiff :listDiff.getNodeDifferences()){
					finalList = new ArrayList<CombinedNodeDifference>();
					finalList.add(new CombinedNodeDifference(commonDiff));
					childDifferencesMap.put(commonDiff.getNodeKey(), finalList);
				}
				
			}
			
		}
		if(	this.nodeDifference.getChildNodeListDifferenceMap()!=null && 
			!this.nodeDifference.getChildNodeListDifferenceMap().isEmpty()){
			
			for(Map.Entry<String, NodeListDifference> entry : this.nodeDifference.getChildNodeListDifferenceMap().entrySet()){
				if(entry.getKey().equalsIgnoreCase("listViews")){
					System.out.println("");
				}
				finalList = new ArrayList<CombinedNodeDifference>();
				NodeListDifference childListDiff = entry.getValue();
				if(childListDiff!=null){
					for(Node node: childListDiff.getLeftOnlyNodes()){
						nodeDiff = new NodeDifference(node.getKey(),null,null,null,NodeDifference.DifferenceType.LEFT_ONLY);
						finalList.add(new CombinedNodeDifference(nodeDiff));
					}
					for(Node node: childListDiff.getRightOnlyNodes()){
						nodeDiff = new NodeDifference(node.getKey(),null,null,null,NodeDifference.DifferenceType.RIGHT_ONLY);
						finalList.add(new CombinedNodeDifference(nodeDiff));
					}
					for(NodeDifference childNodeDiff:childListDiff.getNodeDifferences()){
						finalList.add(new CombinedNodeDifference(childNodeDiff));
					}
					
				}
				childDifferencesMap.put(entry.getKey(), finalList);
				
			}
		}
	}
}
