package com.dotversion.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DifferenceSummary {
	private List<DifferenceSummaryDetail> details;

	public boolean add(DifferenceSummaryDetail arg0) {
		if(details==null){
			details = new ArrayList<DifferenceSummaryDetail>();
		}
		return details.add(arg0);
	}


	public boolean addAll(Collection<? extends DifferenceSummaryDetail> c) {
		if(details==null){
			details = new ArrayList<DifferenceSummaryDetail>();
		}
		return details.addAll(c);
	}


	public List<DifferenceSummaryDetail> getDetails() {
		return details;
	}

	public void setDetails(List<DifferenceSummaryDetail> details) {
		this.details = details;
	}

}
