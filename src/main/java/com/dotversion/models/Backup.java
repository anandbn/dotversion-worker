package com.dotversion.models;

import java.util.Date;

public class Backup {

	private String id;
	private Integer nbrOfChanges;
	private String status;
	private String statusMsg;
	private String gitVersion;
	private SalesforceOrg organization;
	private Integer version;
	private Date createdDate;
	
	public Backup(String id, Integer nbrOfChanges, String status,
			String statusMsg, String gitVersion,SalesforceOrg organization,Integer version) {
		super();
		this.id = id;
		this.nbrOfChanges = nbrOfChanges;
		this.status = status;
		this.statusMsg = statusMsg;
		this.gitVersion = gitVersion;
		this.organization=organization;
		this.version=version;
	}

	public Backup(Integer nbrOfChanges, String status,
			String statusMsg, String gitVersion,SalesforceOrg organization,Integer version,Date createdDate) {
		super();
		this.nbrOfChanges = nbrOfChanges;
		this.status = status;
		this.statusMsg = statusMsg;
		this.gitVersion = gitVersion;
		this.organization=organization;
		this.version=version;
		this.createdDate=createdDate;
	}

	public String getId() {
		return id;
	}
	
	public Integer getNbrOfChanges() {
		return nbrOfChanges;
	}
	public void setNbrOfChanges(Integer nbrOfChanges) {
		this.nbrOfChanges = nbrOfChanges;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	
	public String getGitVersion() {
		return gitVersion;
	}

	public void setGitVersion(String gitVersion) {
		this.gitVersion = gitVersion;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SalesforceOrg getOrganization() {
		return organization;
	}

	public void setOrganization(SalesforceOrg organization) {
		this.organization = organization;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Override
	public String toString() {
		return "Backup [id=" + id + ", nbrOfChanges=" + nbrOfChanges
				+ ", status=" + status + ", statusMsg=" + statusMsg + "]";
	}

}
