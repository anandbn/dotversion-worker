package com.dotversion.forcediff.core;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EmptyNodeImpl implements Node {
    private String key;
    
    public EmptyNodeImpl(String key) {
        this.key = key;
    }

    @Override
    public String getType() {
        // TODO: Re-visit
        return "EMPTY_NODE";
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getShortDescription() {
        return key;
    }

    @Override
    public Map<String, String> getAttributes() {
        return Collections.emptyMap();
    }

    @Override
    public String getAttributeValue(String name) {
        return null;
    }

    @Override
    public boolean hasChildNodes() {
        return false;
    }

    @Override
    public NodeList getChildNodeList(String childNodeType) {
        return null;
    }

    @Override
    public Collection<String> getSupportedChildNodeListTypes() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return this.getKey();
    }

    @Override
    public List<Node> getChildNodes() {
        return Collections.emptyList();
    }
}
