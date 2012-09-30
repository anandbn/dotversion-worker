package com.dotversion.forcediff.core;

import java.util.Collections;
import java.util.List;


public class NodeListDifference {

    private final List<Node> leftOnlyNodes;
    private final List<Node> rightOnlyNodes;
    private final List<NodeDifference> nodeDifferences;

    public NodeListDifference(List<Node> leftOnly, List<Node> rightOnly, List<NodeDifference> differences) {
        this.leftOnlyNodes = Collections.unmodifiableList(leftOnly);
        this.rightOnlyNodes = Collections.unmodifiableList(rightOnly);
        this.nodeDifferences = Collections.unmodifiableList(differences);
    }

    public boolean hasDifferences() {
        return !leftOnlyNodes.isEmpty() || !rightOnlyNodes.isEmpty() || !nodeDifferences.isEmpty();
    }

    public List<Node> getLeftOnlyNodes() {
        return leftOnlyNodes;
    }

    public List<Node> getRightOnlyNodes() {
        return rightOnlyNodes;
    }

    public List<NodeDifference> getNodeDifferences() {
        return nodeDifferences;
    }

    public String toString() {
        return new StringBuilder().append(String.format("left-only nodes - %s\n", leftOnlyNodes))
                .append(String.format("right-only nodes - %s\n", rightOnlyNodes))
                .append(String.format("node differences - %s\n", nodeDifferences)).toString();
    }

}
