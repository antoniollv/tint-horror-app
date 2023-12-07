package com.mapfre.tron.sfv.bo;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class NofitFilesData {
	private String delVal;
	private String oprFlt;
	private String aplEnrSqn;
	private String aplVal;
	private String enrSqn;
	private String lobVal;
	private List<String> actions;
}

