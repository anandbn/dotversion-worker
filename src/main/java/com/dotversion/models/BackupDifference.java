package com.dotversion.models;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.dotversion.forcediff.core.CombinedNodeDifference;
import com.dotversion.forcediff.core.NodeDifference;

public class BackupDifference {

	private NodeDifference diffDetail;

	private Map<String,Map<String,String>> diffSmry;
	
	
	public BackupDifference(NodeDifference differences,
			Map<String, Map<String, String>> differenceSummary) {
		super();
		this.diffDetail = differences;
		this.diffSmry = differenceSummary;
	}
	public NodeDifference getDiffDetail() {
		return diffDetail;
	}
	public void setDiffDetail(NodeDifference differences) {
		this.diffDetail = differences;
	}
	public Map<String, Map<String, String>> getDiffSmry() {
		return diffSmry;
	}
	public void setDiffSmry(
			Map<String, Map<String, String>> differenceSummary) {
		this.diffSmry = differenceSummary;
	}
	
}
