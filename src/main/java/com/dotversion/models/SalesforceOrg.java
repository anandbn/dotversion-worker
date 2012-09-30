package com.dotversion.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalesforceOrg {
	private String id;
	private String orgId;
	
	//Sandbox vs. Production
	private String orgType;
	
	private String lastBackupStatus;
	private Date lastBackupDate;
	private String lastBackupStatusMsg;
	
	private String frequency;
	private Integer timeOfDay;
	private String accessToken;
	private String refreshToken;
	
	private String dotVersionOwner;
	private String metadataTypes;
	private String amOrPm;
	private String timezone;
	private String apiEndpoint;
	private Integer backupCount;
	private List<Backup> backups;
	
	public SalesforceOrg(){
		super();
	}
	public SalesforceOrg(String orgId, String orgType, String frequency,Integer timeOfDay, String dayNight, String metadataElements, String accessToken,String refreshToken,String timezone,String apiEndpoint) {
		this();
		this.orgId = orgId;
		this.orgType = orgType;
		this.timeOfDay = timeOfDay;
		this.amOrPm = dayNight;
		this.metadataTypes = metadataElements;
		this.accessToken = accessToken;
		this.refreshToken=refreshToken;
		this.frequency=frequency;
		this.timezone=timezone;
		this.apiEndpoint=apiEndpoint;
	}
	
	public SalesforceOrg(String id,String orgId, String orgType, String frequency,
									Integer timeOfDay, String dayNight, String metadataElements, String accessToken,String refreshToken,String timezone,String apiEndpoint) {
		
		this(orgId,orgType, frequency,timeOfDay, dayNight, metadataElements, accessToken,refreshToken,timezone,apiEndpoint);
		this.id=id;
		
	}


	public SalesforceOrg(String orgId, String orgType,String apiEndpoint,String accessToken,String refreshToken) {
		this.orgId = orgId;
		this.orgType = orgType;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.apiEndpoint=apiEndpoint;
	}

	public String getApiEndpoint() {
		return apiEndpoint;
	}
	public void setApiEndpoint(String apiEndpoint) {
		this.apiEndpoint = apiEndpoint;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public String getLastBackupStatusMsg() {
		return lastBackupStatusMsg;
	}
	public void setLastBackupStatusMsg(String lastBackupStatusMsg) {
		this.lastBackupStatusMsg = lastBackupStatusMsg;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id=id;
		
	}
	public String getDotVersionOwner() {
		return dotVersionOwner;
	}
	public void setDotVersionOwner(String dotVersionOwner) {
		this.dotVersionOwner = dotVersionOwner;
	}
	public String getMetadataTypes() {
		return metadataTypes;
	}
	public void setMetadataTypes(String metadataTypes) {
		this.metadataTypes = metadataTypes;
	}
	public String getAmOrPm() {
		return amOrPm;
	}
	public void setAmOrPm(String amOrPm) {
		this.amOrPm = amOrPm;
	}

	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public Integer getTimeOfDay() {
		return timeOfDay;
	}
	public void setTimeOfDay(Integer timeOfDay) {
		this.timeOfDay = timeOfDay;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getLastBackupStatus() {
		return lastBackupStatus;
	}
	public void setLastBackupStatus(String lastBackStatus) {
		this.lastBackupStatus = lastBackStatus;
	}
	public Date getLastBackupDate() {
		return lastBackupDate;
	}
	public void setLastBackupDate(Date lastBackupDate) {
		this.lastBackupDate = lastBackupDate;
	}

	public Integer getBackupCount() {
		return backupCount==null?0:backupCount;
	}
	public void setBackupCount(Integer backupCount) {
		this.backupCount = backupCount;
	}
	public List<Backup> getBackups() {
		return backups;
	}
	public void setBackups(List<Backup> backups) {
		this.backups = backups;
	}
	
	
 	public boolean add(Backup arg0) {
 		if(backups==null){
 			this.backups=new ArrayList<Backup>();
 		}
		return backups.add(arg0);
	}
	public boolean remove(Object arg0) {
		return backups.remove(arg0);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalesforceOrg other = (SalesforceOrg) obj;
		if (orgId == null) {
			if (other.orgId != null)
				return false;
		} else if (!orgId.equals(other.orgId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SalesforceOrg [id=" + id + ", orgId=" + orgId + ", orgType="
				+ orgType + ", lastBackupStatus=" + lastBackupStatus
				+ ", lastBackupDate=" + lastBackupDate
				+ ", lastBackupStatusMsg=" + lastBackupStatusMsg
				+ ", frequency=" + frequency + ", timeOfDay=" + timeOfDay
				+ ", refreshToken=" + refreshToken + ", dotVersionOwner="
				+ dotVersionOwner + ", metadataTypes=" + metadataTypes
				+ ", amOrPm=" + amOrPm + ", timezone=" + timezone
				+ ", apiEndpoint=" + apiEndpoint + "]";
	}
	
	
}
