package com.dotversion.models;

import java.util.ArrayList;
import java.util.List;

public class User{

	private String id;
	private String username;
	private String email;
	private String firstName;
	private String lastName;
	
	List<SalesforceOrg> salesforceOrgs;

	public User(){
		super();
	}
	public User(String username, String email, String firstName, String lastName) {
		this();
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id=id;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<SalesforceOrg> getSalesforceOrgs() {
		return salesforceOrgs;
	}
	public void setSalesforceOrgs(List<SalesforceOrg> salesforceOrgs) {
		this.salesforceOrgs = salesforceOrgs;
	}
	
	public boolean add(SalesforceOrg arg0) {
		if(salesforceOrgs==null){ salesforceOrgs=new ArrayList<SalesforceOrg>() ;}
		return salesforceOrgs.add(arg0);
	}

	
	public boolean contains(SalesforceOrg o) {
		if(salesforceOrgs!=null){
			for(SalesforceOrg org:this.salesforceOrgs){
				if(org.getOrgId().equals(o.getOrgId())){
					return true;
				}
			}	
		}
		
		return false;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email
				+ ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
}
