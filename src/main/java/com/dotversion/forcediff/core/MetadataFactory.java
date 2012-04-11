package com.dotversion.forcediff.core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;


import com.salesforce.forcetools.jibx.*;

public class MetadataFactory {
    
    private static final Map<String, IUnmarshallingContext> unmarshallingContextMap = new HashMap<String, IUnmarshallingContext>(); 
    
    public static Class<? extends Metadata> getMetadataClass(String metadataType) {
        if (metadataType.equals("objects")) {
            return CustomObject.class;
        }
        else if (metadataType.equals("applications")) {
            return CustomApplication.class;
        }
        else if (metadataType.equals("tabs")) {
            return CustomTab.class;
        }
        else if (metadataType.equals("labels")) {
            return CustomLabel.class;
        }
        else if (metadataType.equals("components")) {
            return ApexComponent.class;
        }
        else {
            return null;
        }
    }

    public static NodeList createMetadataNodeListInstance(Map<String, Metadata> metadataMap) {
        List<Node> nodes = new ArrayList<Node>();
        for (Entry<String, Metadata> metadataEntry: metadataMap.entrySet()) {
            nodes.add(MetadataFactory.getNodeInstance(metadataEntry.getValue(), metadataEntry.getKey()));
        }
        return new SimpleNodeList(nodes);
    }
    
    public static NodeList createNodeListInstance(List metadataList) {
        List<Node> nodes = new ArrayList<Node>();
        if (metadataList != null) {
            for (Object metadataObj : metadataList) {
                Node node = getNodeInstance(metadataObj);
                if (node != null) {
                    nodes.add(node);
                }
            }
        }
        return new SimpleNodeList(nodes);
    }
    
    public static Node getNodeInstance(Object metadataObj) {
        
        if (metadataObj instanceof Metadata) {
            Metadata metadata = (Metadata) metadataObj;
            if (metadata instanceof CustomLabel) {
                return new StandardNode(metadataObj, metadata.getFullName(), "shortDescription");
            }
            else {
                return new StandardNode(metadataObj, metadata.getFullName(), "label");
            }
        }
        else if (metadataObj instanceof String) {
            return new EmptyNodeImpl((String) metadataObj);
        }
        else if (metadataObj instanceof ActionOverride) {
            ActionOverride override = (ActionOverride) metadataObj;
            return new StandardNode(metadataObj, override.getActionName(), "actionName");
        }else if (metadataObj instanceof ListViewFilter) {
            	ListViewFilter listViewFilter = (ListViewFilter) metadataObj;
                return new StandardNode(metadataObj, listViewFilter.getField(), "fieldName");
            }
        else {
            System.err.println("Unsupported node type - " + metadataObj.getClass());
            return null;
        }
        
    }
    
    
    public static Node getNodeInstance(Object node, String metadataKey) {
        return new StandardNode(node, metadataKey, "label");
    }
    
    public static NodeList getNodeInstance(final String baseDir, final String metadataType) {
        File nodeDir = new File(baseDir + File.pathSeparator + metadataType);
        assert nodeDir.isDirectory();
        IUnmarshallingContext context = getUnmarshallingContext(metadataType);
        List<Node> list = new ArrayList<Node>();
        for (File f: nodeDir.listFiles()) {
            Metadata metadata;
            try {
                metadata = (Metadata)context.unmarshalDocument(
                        new BufferedInputStream(new FileInputStream(f)), null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            list.add(getNodeInstance(metadata));
        }
        return new SimpleNodeList(list);
    }
    
    public static IUnmarshallingContext getUnmarshallingContext(String metadataType) {
        if (unmarshallingContextMap.containsKey(metadataType)) return unmarshallingContextMap.get(metadataType);
        Class<? extends Metadata> metadataClass = getMetadataClass(metadataType);
        IBindingFactory bindingFactory;
        try {
            bindingFactory = BindingDirectory.getFactory(metadataClass);
            IUnmarshallingContext context = bindingFactory.createUnmarshallingContext();
            unmarshallingContextMap.put(metadataType, context);
            return context;
        } catch (JiBXException e) {
            throw new RuntimeException(e);
        }
    }

}
