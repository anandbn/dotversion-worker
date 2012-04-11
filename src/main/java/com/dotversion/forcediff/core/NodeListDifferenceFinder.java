package com.dotversion.forcediff.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class NodeListDifferenceFinder {
	
	private static final NodeListDifferenceFinder SINGLETON_INSTANCE = new NodeListDifferenceFinder();

	public static NodeListDifferenceFinder getInstance() {
		return SINGLETON_INSTANCE;
	}
	
	private NodeListDifferenceFinder() {
	}

    public NodeListDifference findDifferences(
            NodeList leftNodeList, NodeList rightNodeList) {
        if (leftNodeList == null && rightNodeList == null) return null;
        final List<Node> leftNodes = new ArrayList<Node>(leftNodeList.getNodes());
        final List<Node> rightNodes = new ArrayList<Node>(rightNodeList.getNodes());
        return findDifferences(leftNodes, rightNodes);
    }
	
	public NodeListDifference findDifferences(
			List<Node> leftNodes, List<Node> rightNodes) {

		Collections.sort(leftNodes, NodeKeyComparator.getInstance());
		Collections.sort(rightNodes, NodeKeyComparator.getInstance());
		
		final Iterator<Node> leftNodeIterator = leftNodes.iterator();
		final Iterator<Node> rightNodeIterator = rightNodes.iterator();
		
		final List<Node> leftOnlyNodes = new ArrayList<Node>();
		final List<Node> rightOnlyNodes = new ArrayList<Node>();
		final List<NodeDifference> nodeDifferences = new ArrayList<NodeDifference>();
		
		Node leftElement = null; 
		Node rightElement = null;
		
		boolean moveLeft = true;
		boolean moveRight = true;
		while ((!moveLeft || (moveLeft && leftNodeIterator.hasNext()))
		        && 
		        (!moveRight || (moveRight && rightNodeIterator.hasNext()))) {
            if (moveLeft) leftElement = leftNodeIterator.next();
            if (moveRight) rightElement = rightNodeIterator.next();
            int compareResult = NodeKeyComparator.getInstance().compare(leftElement, rightElement);
            if (compareResult == 0) {
                NodeDifference nodeDifference = NodeDifferenceFinder.getInstance().findDifferences(leftElement, rightElement);
                if (nodeDifference != null) {
                    nodeDifferences.add(nodeDifference);
                }
            }
            else if (compareResult > 0) {
                rightOnlyNodes.add(rightElement);
            }
            else {
                leftOnlyNodes.add(leftElement);
            }
            
            moveLeft = (compareResult <= 0);
            moveRight = (compareResult >= 0);
		}
		
		/**
		while (comparisonsRequired) {
			int compareResult = NodeKeyComparator.getInstance().compare(leftElement, rightElement);
			
            if (compareResult == 0) {
                NodeDifference nodeDifference = NodeDifferenceFinder.getInstance().findDifferences(leftElement, rightElement);
                if (nodeDifference != null) {
                    nodeDifferences.add(nodeDifference);
                }
            }
			
            if (compareResult > 0) rightOnlyNodes.add(rightElement);

            if (compareResult < 0) leftOnlyNodes.add(leftElement);

            if (compareResult >= 0) {
				if (rightNodeIterator.hasNext()) {
					rightElement = rightNodeIterator.next();
				}
				else {
				    if (compareResult > 0) leftOnlyNodes.add(leftElement);
					comparisonsRequired = false;
				}
			}
			
			if (compareResult <= 0) {
				if (leftNodeIterator.hasNext()) {
					leftElement = leftNodeIterator.next();
				}
				else {
                    if (compareResult < 0) rightOnlyNodes.add(rightElement);
					comparisonsRequired = false;
				}
			}
			
		}
		**/
		
        if (!moveLeft) leftOnlyNodes.add(leftElement);
        if (!moveRight) rightOnlyNodes.add(rightElement);
		
        while (leftNodeIterator.hasNext()) {
            leftOnlyNodes.add(leftNodeIterator.next());
        }
        while (rightNodeIterator.hasNext()) {
            rightOnlyNodes.add(rightNodeIterator.next());
        }
		
		if (leftOnlyNodes.isEmpty() && rightOnlyNodes.isEmpty() && nodeDifferences.isEmpty()) {
			return null;
		}
		else {
			return new NodeListDifference(leftOnlyNodes, rightOnlyNodes, nodeDifferences);
		}
	}
}