package com.dotversion.forcediff.core;

import java.util.Comparator;


public class NodeKeyComparator implements Comparator<Node> {
	private static final NodeKeyComparator SINGLETON_INSTANCE = new NodeKeyComparator();
	
	public static NodeKeyComparator getInstance() {
		return SINGLETON_INSTANCE;
	}
	
	private NodeKeyComparator() {
	}

	@Override
	public int compare(Node left, Node right) {
		return left.getKey().compareTo(right.getKey());
	}
}