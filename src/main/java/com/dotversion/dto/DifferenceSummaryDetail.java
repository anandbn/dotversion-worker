package com.dotversion.dto;

import java.util.ArrayList;
import java.util.List;

public class DifferenceSummaryDetail {
	public enum ChangeType {
	    ADD,MODIFY,DELETE
	}
	private String name;
	private String type;
	private ChangeType changeType;
	private List<String> changes;
	private String attributeName;
	private String attrPrev,attrCurr;
	
	public DifferenceSummaryDetail(String name, String type,
			ChangeType changeType, List<String> changes) {
		super();
		this.name = name;
		this.type = type;
		this.changeType = changeType;
		this.changes = changes;
	}

	public DifferenceSummaryDetail(String name, String type,
			ChangeType changeType, List<String> changes,String attributeName,String attrPrev,String attrCurr) {
		super();
		this.name = name;
		this.type = type;
		this.changeType = changeType;
		this.changes = changes;
		this.attributeName=attributeName;
		this.attrCurr=attrCurr;
		this.attrPrev=attrPrev;
	}

	public boolean addChange(String e) {
		if(changes==null){
			changes = new ArrayList<String>();
		}
		return changes.add(e);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ChangeType getChangeType() {
		return changeType;
	}
	public void setChangeType(ChangeType changeType) {
		this.changeType = changeType;
	}
	public List<String> getChanges() {
		return changes;
	}
	public void setChanges(List<String> changes) {
		this.changes = changes;
	}
	
	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttrPrev() {
		return attrPrev;
	}

	public void setAttrPrev(String attrPrev) {
		this.attrPrev = attrPrev;
	}

	public String getAttrCurr() {
		return attrCurr;
	}

	public void setAttrCurr(String attrCurr) {
		this.attrCurr = attrCurr;
	}

	@Override
	public String toString() {
		return "DifferenceSummaryDetail [name=" + name + ", type=" + type
				+ ", changeType=" + changeType + ", changes=" + changes + "]";
	}

}
