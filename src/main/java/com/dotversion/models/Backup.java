package com.dotversion.models;

public class Backup {

	private String id;
	private Long nbrOfChanges;
	
	public Backup(String id, Long nbrOfChanges, String status, String statusMsg) {
		super();
		this.id = id;
		this.nbrOfChanges = nbrOfChanges;
		this.status = status;
		this.statusMsg = statusMsg;
	}

	public String getId() {
		return id;
	}
	
	public Long getNbrOfChanges() {
		return nbrOfChanges;
	}
	public void setNbrOfChanges(Long nbrOfChanges) {
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
	
	@Override
	public String toString() {
		return "Backup [id=" + id + ", nbrOfChanges=" + nbrOfChanges
				+ ", status=" + status + ", statusMsg=" + statusMsg + "]";
	}


	private String status;
	private String statusMsg;
}
