package com.dotversion.git;

public class BackupStatus {

	private String versionId;
	private String statusMsg;
	private boolean success;
	private Integer changeCount;
	private String changesJson;
	
	public BackupStatus(String versionId, String statusMsg, boolean success,Integer fileCount) {
		super();
		this.versionId = versionId;
		this.statusMsg = statusMsg;
		this.success = success;
		this.changeCount=fileCount;
	}
	public Integer getChangeCount() {
		return changeCount;
	}
	public void setChangeCount(Integer changeCount) {
		this.changeCount = changeCount;
	}
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getChangesJson() {
		return changesJson;
	}
	public void setChangesJson(String changesJson) {
		this.changesJson = changesJson;
	}
	
}
