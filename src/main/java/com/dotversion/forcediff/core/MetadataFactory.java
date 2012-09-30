package com.dotversion.forcediff.core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;


import com.dotversion.common.util.Configuration;
import com.salesforce.metadata.*;

public class MetadataFactory {
    static Logger logger = Logger.getLogger(MetadataFactory.class);
     
    public static Class<? extends com.salesforce.metadata.Metadata> getMetadataClass(String metadataType) throws ClassNotFoundException {
        return Configuration.metadataClassFor(metadataType);
    	
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
        }else if(metadataObj instanceof ProfileFieldLevelSecurity){
        	ProfileFieldLevelSecurity fldSec = (ProfileFieldLevelSecurity)metadataObj;
        	return new StandardNode(metadataObj, fldSec.getField(), "fieldName");
        }else if(metadataObj instanceof ProfileRecordTypeVisibility){
        	ProfileRecordTypeVisibility recTypeSec = (ProfileRecordTypeVisibility)metadataObj;
        	return new StandardNode(metadataObj, recTypeSec.getRecordType(), "recordType");
        }else if(metadataObj instanceof ProfileObjectPermissions){
        	ProfileObjectPermissions objSec = (ProfileObjectPermissions)metadataObj;
        	return new StandardNode(metadataObj, objSec.getObject(), "objectName");
        }else if(metadataObj instanceof RecordTypePicklistValue){
        	RecordTypePicklistValue recType = (RecordTypePicklistValue)metadataObj;
        	return new StandardNode(metadataObj, recType.getPicklist(), "picklistName");
        }else if(metadataObj instanceof RelatedListItem){
        	RelatedListItem obj = (RelatedListItem)metadataObj;
        	return new StandardNode(metadataObj, obj.getRelatedList(), "relatedListName");
        }else if(metadataObj instanceof RelatedListItem){
        	RelatedListItem obj = (RelatedListItem)metadataObj;
        	return new StandardNode(metadataObj, obj.getRelatedList(), "relatedListName");
        }else if(metadataObj instanceof LayoutSection){
        	LayoutSection obj = (LayoutSection)metadataObj;
        	if(obj.getStyle()==LayoutSectionStyle.CUSTOM_LINKS){
        		return new StandardNode(metadataObj, "customLinks", "label");
        	}else if(obj.getLabel()==null){
        		return new StandardNode(metadataObj, obj.getStyle().name(), "label");
        	}else{
        		return new StandardNode(metadataObj, obj.getLabel(), "label");
        	}
        }else if(metadataObj instanceof ProfileLayoutAssignment){
        	ProfileLayoutAssignment obj = (ProfileLayoutAssignment)metadataObj;
        	return new StandardNode(metadataObj, obj.getLayout(), "layoutName");
        }else if(metadataObj instanceof ReportLayoutSection){
        	ReportLayoutSection obj = (ReportLayoutSection)metadataObj;
        	return new StandardNode(metadataObj, obj.getMasterLabel(), "masterLabel");
        }else if(metadataObj instanceof FilterItem){
        	FilterItem obj = (FilterItem)metadataObj;
        	return new StandardNode(metadataObj, obj.getField(), "fieldName");
        }else if(metadataObj instanceof WorkflowTimeTrigger){
        	WorkflowTimeTrigger obj = (WorkflowTimeTrigger)metadataObj;
        	return new StandardNode(metadataObj, obj.getOffsetFromField(), "offsetFromField");
        }else if(metadataObj instanceof WorkflowActionReference){
        	WorkflowActionReference obj = (WorkflowActionReference)metadataObj;
        	return new StandardNode(metadataObj, obj.getName(), "name");
        }else if(metadataObj instanceof ProfileApplicationVisibility){
        	ProfileApplicationVisibility obj = (ProfileApplicationVisibility)metadataObj;
        	return new StandardNode(metadataObj, obj.getApplication(), "application");
        }else if(metadataObj instanceof ProfileTabVisibility){
        	ProfileTabVisibility obj = (ProfileTabVisibility)metadataObj;
        	return new StandardNode(metadataObj, obj.getTab(), "tabName");
        }else if(metadataObj instanceof ReportTypeColumn){
        	ReportTypeColumn obj = (ReportTypeColumn)metadataObj;
        	return new StandardNode(metadataObj, obj.getField(), "fieldName");
        }else if(metadataObj instanceof MiniLayout){
        	MiniLayout obj = (MiniLayout)metadataObj;
        	return new StandardNode(metadataObj, obj.getFields().toString(), "minilayout");
        }else if(metadataObj instanceof LayoutColumn){
        	LayoutColumn obj = (LayoutColumn)metadataObj;
        	String key="";
        	for(LayoutItem item:obj.getLayoutItems()){
        		if(item.getField()!=null){
        			key+=item.getField()+";";
        		}
        		if(item.getScontrol()!=null){
        			key+=item.getScontrol()+";";
        		}
        		if(item.getCustomLink()!=null){
        			key+=item.getCustomLink()+";";
        		}
           	}
        	return new StandardNode(metadataObj, key, "fieldNames");
        }else if(metadataObj instanceof LayoutItem){
        	LayoutItem obj = (LayoutItem)metadataObj;
        	if(obj.getField()!=null){
        		return new StandardNode(metadataObj, obj.getField(), "label");
        	}else if(obj.getCustomLink()!=null){
        		return new StandardNode(metadataObj, obj.getCustomLink(), "label");
           	}else if(obj.getScontrol()!=null){
        		return new StandardNode(metadataObj, obj.getScontrol(), "label");
        	}else if(obj.getPage()!=null){
        		return new StandardNode(metadataObj, obj.getPage(), "label");
        	}else if(obj.isEmptySpace()!=null && obj.isEmptySpace()){
        		return new StandardNode(metadataObj, "emptySpace", "label");
        	}else if(obj.isEmptySpace()!=null && obj.isEmptySpace()){
        		return new StandardNode(metadataObj, "emptySpace", "label");
        	}else{
        		logger.error("Unsupported LayoutItem");
        		return null;
        	}
        }else if(metadataObj instanceof LayoutHeader){
        	return new StandardNode(metadataObj,((LayoutHeader)metadataObj).value(),"value");
        }else if(metadataObj instanceof CustomFieldTranslation){
        	return new StandardNode(metadataObj,((CustomFieldTranslation)metadataObj).getLabel(),"label");
        }else if(metadataObj instanceof SharingReasonTranslation){
        	return new StandardNode(metadataObj,((SharingReasonTranslation)metadataObj).getLabel(),"label");
        }else if(metadataObj instanceof ValidationRuleTranslation){
        	return new StandardNode(metadataObj,((ValidationRuleTranslation)metadataObj).getName(),"name");
        }else if(metadataObj instanceof ObjectNameCaseValue){
        	return new StandardNode(metadataObj,((ObjectNameCaseValue)metadataObj).getValue(),"value");
        }else if(metadataObj instanceof LayoutTranslation){
        	return new StandardNode(metadataObj,((LayoutTranslation)metadataObj).getLayout(),"layout");
        }else if(metadataObj instanceof WorkflowTaskTranslation){
        	return new StandardNode(metadataObj,((WorkflowTaskTranslation)metadataObj).getName(),"name");
        }else if(metadataObj instanceof AnalyticSnapshotMapping){
        	return new StandardNode(metadataObj,((AnalyticSnapshotMapping)metadataObj).getSourceField(),"sourceField");
        }else if(metadataObj instanceof PicklistValueTranslation){
        	return new StandardNode(metadataObj,((PicklistValueTranslation)metadataObj).getMasterLabel(),"masterLabel");
        }else if(metadataObj instanceof LayoutSectionTranslation){
        	return new StandardNode(metadataObj,((LayoutSectionTranslation)metadataObj).getLabel(),"label");
        }else if(metadataObj instanceof RecordTypeTranslation){
        	return new StandardNode(metadataObj,((RecordTypeTranslation)metadataObj).getLabel(),"label");
        }else if(metadataObj instanceof WorkflowEmailRecipient){
        	WorkflowEmailRecipient obj = (WorkflowEmailRecipient)metadataObj;
        	if(obj.getRecipient()!=null){
        		return new StandardNode(metadataObj,obj.getRecipient(),"label");
        	}else if(obj.getField()!=null){
        		return new StandardNode(metadataObj,obj.getField(),"label");
        	}else{
        		return new StandardNode(metadataObj,obj.getType().name(),"label");
        	}
        }else if(metadataObj instanceof ObjectRelationship){
        	return new StandardNode(metadataObj,getObjectRelationshipName((ObjectRelationship)metadataObj),"label");
        }else{
        	if( metadataObj!=null)
        		logger.error("Unsupported node type - " + metadataObj.getClass());
            return null;
        }
        
    }
    
    
    public static Node getNodeInstance(Object node, String metadataKey) {
        return new StandardNode(node, metadataKey, "label");
    }
    
    public static String getObjectRelationshipName(ObjectRelationship objRel){
    	
    	String relName="";
    	if(objRel.getJoin()!=null){
    		relName+="."+getObjectRelationshipName(objRel.getJoin());
    	}else{
    		relName+= objRel.getRelationship();
    	}
    	return relName;
    }
}
