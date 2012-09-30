package com.dotversion.common.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.node.ArrayNode;

import com.dotversion.dto.DifferenceSummary;
import com.dotversion.dto.DifferenceSummaryDetail;

public class DifferenceUtils {

	public static DifferenceSummary summarizeDifferences(String jsonStr) throws JsonProcessingException, IOException{
		DifferenceSummary smry = new DifferenceSummary();
		ObjectMapper mapper = new ObjectMapper();
		if(jsonStr!=null){
			JsonNode rootNode = mapper.readTree(jsonStr);
			ArrayNode orgDiffNode = (ArrayNode) rootNode.get("childNodeDifferences").get("nodeDifferences");
			Iterator<JsonNode> metadataTypes = orgDiffNode.getElements();
			while(metadataTypes.hasNext()){
				JsonNode topLevel = metadataTypes.next();
				smry.addAll(summarizeChildDiffs(topLevel));
				
			}
		}
		return smry;
	}
	

	//CustomObject(s),Profile(s) etc.
	private static List<DifferenceSummaryDetail> summarizeChildDiffs(JsonNode topLevel){
		
		List<DifferenceSummaryDetail> summary = new ArrayList<DifferenceSummaryDetail>();
		if(topLevel.get("childNodeDifferences")!=null){
			Iterator<JsonNode> changedTypes = null;
			if(topLevel.get("childNodeDifferences").get("leftOnlyNodes")!=null){
				changedTypes = ((ArrayNode) topLevel.get("childNodeDifferences").get("leftOnlyNodes")).iterator();
				while(changedTypes.hasNext()){
					JsonNode added = changedTypes.next();
					summary.add(new DifferenceSummaryDetail(added.get("key").asText(),
							topLevel.get("nodeKey").asText(),
							DifferenceSummaryDetail.ChangeType.ADD,
							new ArrayList<String>())
					);
					System.out.println(String.format("%s:'%s' added.",
												topLevel.get("nodeKey").asText(),
												added.get("key").asText()
											)
					);
				}
				
			}
			if(topLevel.get("childNodeDifferences").get("rightOnlyNodes")!=null){
				changedTypes = ((ArrayNode) topLevel.get("childNodeDifferences").get("rightOnlyNodes")).iterator();
				while(changedTypes.hasNext()){
					JsonNode removed = changedTypes.next();
					summary.add(new DifferenceSummaryDetail(removed.get("key").asText(),
											topLevel.get("nodeKey").asText(),
											DifferenceSummaryDetail.ChangeType.DELETE,
											new ArrayList<String>())
									);
					System.out.println(String.format("%s:'%s' deleted.",
												topLevel.get("nodeKey").asText(),
												removed.get("key").asText()
											)
					);
				}
				
			}
			if(topLevel.get("childNodeDifferences").get("nodeDifferences")!=null){
				changedTypes = ((ArrayNode) topLevel.get("childNodeDifferences").get("nodeDifferences")).iterator();
				while(changedTypes.hasNext()){
					JsonNode childDiff = changedTypes.next();
					summary.addAll(summarizeAttributeDiffs(childDiff,topLevel.get("nodeKey").asText()));
				}
				
			}
			
		}
		return summary;
		
	}
	
	
	private static List<DifferenceSummaryDetail> summarizeAttributeDiffs(JsonNode mdataDiff,String mdataType){
		List<DifferenceSummaryDetail> summary = new ArrayList<DifferenceSummaryDetail>();
		if(mdataDiff.get("attributeDifferences")!=null){
			Iterator<JsonNode> attrbDiffItr = ((ArrayNode)mdataDiff.get("attributeDifferences")).getElements();
			while(attrbDiffItr.hasNext()){
				JsonNode attrDiff = attrbDiffItr.next();
				summary.add(new DifferenceSummaryDetail(mdataDiff.get("nodeKey").asText(),
														mdataType,
														DifferenceSummaryDetail.ChangeType.MODIFY,
														new ArrayList<String>(),
														attrDiff.get("attributeName").asText(),
														attrDiff.get("attributeValueLeft").asText(),
														attrDiff.get("attributeValueRight").asText())
							);

				System.out.println(String.format("'%s' of %s:'%s' was changed from '%s' to '%s', ",
												  attrDiff.get("attributeName").asText(),
												  mdataType,
												  mdataDiff.get("nodeKey").asText(),
												  attrDiff.get("attributeValueLeft").asText(),
											      attrDiff.get("attributeValueRight").asText())
								);
			}
		}
		if(mdataDiff.get("childNodeListDifferenceMap")!=null){
			Iterator<String> whatChanged = mdataDiff.get("childNodeListDifferenceMap").getFieldNames();
			String changesStr = "";
			DifferenceSummaryDetail detl = new DifferenceSummaryDetail(mdataDiff.get("nodeKey").asText(),
																		mdataType,
																		DifferenceSummaryDetail.ChangeType.MODIFY,
																		new ArrayList<String>()
																		);
			summary.add(detl);
			while(whatChanged.hasNext()){
				String change = whatChanged.next();
				detl.addChange(change);
				changesStr +=String.format("'%s', ",change);
			}
			
			if(changesStr!=null && changesStr.length()>0){
				changesStr = changesStr.substring(0,changesStr.length()-2);
				System.out.println(String.format("%s in %s:'%s' was modified.",changesStr,mdataType,mdataDiff.get("nodeKey").asText()));
			}
		}
		return summary;
	}
}
