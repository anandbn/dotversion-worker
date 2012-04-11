package com.dotversion.common.util;

import com.sforce.soap.partner.GetUserInfoResult;
import com.sforce.ws.ConnectorConfig;

public class OrgInfo {

	public static enum OrgType {PRODUCTION, SANDBOX};
	
	private GetUserInfoResult userInfo;
	private ConnectorConfig metaConnConfig;
	private ConnectorConfig partConnConfig;
	private OrgType orgType; 
	private String logoutUrl;
	
	public OrgInfo(){
		super();
	}
	public OrgInfo(GetUserInfoResult userInfo, ConnectorConfig metaConnConfig,
			ConnectorConfig partConnConfig,OrgType type,String logoutUrl) {
		super();
		this.userInfo = userInfo;
		this.metaConnConfig = metaConnConfig;
		this.partConnConfig = partConnConfig;
		this.orgType=type;
		this.logoutUrl=logoutUrl;
	}
	public OrgType getOrgType() {
		return orgType;
	}
	public GetUserInfoResult getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(GetUserInfoResult userInfo) {
		this.userInfo = userInfo;
	}
	public ConnectorConfig getMetaConnConfig() {
		return metaConnConfig;
	}
	public void setMetaConnConfig(ConnectorConfig metaConnConfig) {
		this.metaConnConfig = metaConnConfig;
	}
	public ConnectorConfig getPartConnConfig() {
		return partConnConfig;
	}
	public void setPartConnConfig(ConnectorConfig partConnConfig) {
		this.partConnConfig = partConnConfig;
	}
	public String getLogoutUrl() {
		return logoutUrl;
	}
	
}
