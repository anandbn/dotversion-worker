package com.dotversion.forcediff.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dotversion.common.util.CommonUtil;

public class NodeDifferenceFinder {
	private static final NodeDifferenceFinder SINGLETON_INSTANCE = new NodeDifferenceFinder();
	
	public static NodeDifferenceFinder getInstance() {
		return SINGLETON_INSTANCE;
	}
	
	private NodeDifferenceFinder() {
	}

	public NodeDifference findDifferences(Node left, Node right) {
		assert left.getKey().equals(right.getKey());
		String nodeKey = left.getKey();
		
		Map<String, String> attributes = left.getAttributes();
		List<AttributeDifference> attributeDifferences = new ArrayList<AttributeDifference>();
		for (String attrName: attributes.keySet()) {
			String leftValue = attributes.get(attrName);
			String rightValue = right.getAttributeValue(attrName);
			if (!CommonUtil.equalsWithNullCheck(leftValue, rightValue)) {
				attributeDifferences.add(new AttributeDifference(attrName, leftValue, rightValue));
			}
		}
		
		NodeListDifference childNodeDifferences = NodeListDifferenceFinder.getInstance().findDifferences(left.getChildNodes(), right.getChildNodes());
		
        Map<String, NodeListDifference> childNodeListDifferencesMap = new HashMap<String, NodeListDifference>();
		if (left.hasChildNodes()) {
			assert right.hasChildNodes();
			for (String childNodeType: left.getSupportedChildNodeListTypes()) {
		        NodeListDifference childNodeListDifference = NodeListDifferenceFinder.getInstance().findDifferences(left.getChildNodeList(childNodeType), right.getChildNodeList(childNodeType));
		        if (childNodeListDifference != null && childNodeListDifference.hasDifferences()) {
	                childNodeListDifferencesMap.put(childNodeType, childNodeListDifference);
		        }
			}
		}
		
		if (attributeDifferences.isEmpty() && childNodeListDifferencesMap.isEmpty() && (childNodeDifferences  == null || !childNodeDifferences.hasDifferences())) {
			return null;
		}
		else {
			return new NodeDifference(nodeKey, attributeDifferences, childNodeDifferences, childNodeListDifferencesMap);
		}
	}
	
}