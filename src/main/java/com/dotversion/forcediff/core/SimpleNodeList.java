package com.dotversion.forcediff.core;

import java.util.Collections;
import java.util.List;

public class SimpleNodeList implements NodeList {

    private final List<Node> list;

    public SimpleNodeList(List<Node> inputList) {
        this.list = Collections.unmodifiableList(inputList);
    }

    @Override
    public List<Node> getNodes() {
        return list;
    }
    
}
