package com.mapfre.tron.sfv.pgm.beans.impl.cm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.mapfre.tron.sfv.bo.NofitFilesData;
import com.mapfre.tron.sfv.bo.OPlySmnDocQryS;
import com.mapfre.tron.sfv.bo.PlyDocument;
import com.mapfre.tron.sfv.pgm.dl.IDlPlySmnDocQryDAO;

@Component
public class DocumentsComponentImpl implements DocumentsComponent {
	
	private static final String LNG_VAL = "lngVal";
	private static final String USR_VAL = "usrVal";
	private static final String CMP_VAL = "cmpVal";

	@Value("${app.env.report.endpoint:https://trnic.desa.mapfre.net/report_be-web/api/reports}")
    private String basePath;
	
	private RestTemplate restTemplate = new RestTemplate();

	@Autowired private IDlPlySmnDocQryDAO docsRepo;
	
	@Override
	public List<OPlySmnDocQryS> getDocsByStep(BigDecimal cmpVal, BigDecimal lobVal, String step) {
		return docsRepo.getDocNamLike(cmpVal, lobVal, "PCT_DOC_ESTATICA_" + step);
	}
	
	@Override
	public List<OPlySmnDocQryS> getDocs(BigDecimal cmpVal, BigDecimal lobVal) {
		return docsRepo.getDocNamLike(cmpVal, lobVal, "PCT_DOC_ESTATICA_");
	}
	
	@Override
	public List<OPlySmnDocQryS> getDocsMap(BigDecimal cmpVal, BigDecimal lobVal) {
		return docsRepo.getDocNam(cmpVal, lobVal, "PCT_MAPA_DOCUMENTAL");
	}
	
	@Override
	public List<PlyDocument> getDocsByPolicy(BigDecimal cmpVal, String usrVal, String plyVal) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Void> request = new HttpEntity<>(headers);
		
		String url = UriComponentsBuilder.fromHttpUrl(basePath + "policy/" + plyVal)
			.queryParam(CMP_VAL, cmpVal)
			.queryParam(USR_VAL, usrVal)
			.build(false)
			.toUriString();
		
		ResponseEntity<List<PlyDocument>> res = restTemplate.exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<List<PlyDocument>>() {});
		
		return res.getBody();
	}
	
	@Override
	public List<PlyDocument> getDocsByQuotation(BigDecimal cmpVal, String usrVal, String qtnVal, Integer aplVal) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Void> request = new HttpEntity<>(headers);
		
		String url = UriComponentsBuilder.fromHttpUrl(basePath + "quotation/" + qtnVal + "?aplVal=" + aplVal)
			.queryParam(CMP_VAL, cmpVal)
			.queryParam(USR_VAL, usrVal)
			.build(false)
			.toUriString();
		
		ResponseEntity<List<PlyDocument>> res = restTemplate.exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<List<PlyDocument>>() {});
		
		return res.getBody();
	}
	
	@Override
	public Set<String> getDocNames(List<PlyDocument> plyDocs) {
		return plyDocs.stream().map(PlyDocument::getDcnNam).collect(Collectors.toSet());
	}
	
	@Override
	public List<OPlySmnDocQryS> getUnsignedDocs(List<OPlySmnDocQryS> docs, Set<String> avlDocs) {
		List<OPlySmnDocQryS> res = new ArrayList<>();
		for (OPlySmnDocQryS d : docs) {
			if ("2".equals(d.getDflChc()) && !avlDocs.contains(d.getRowNam())) {
				res.add(d);
			}
		}
		return res;
	}
	
	@Override
	public List<OPlySmnDocQryS> getPendingDocs(List<OPlySmnDocQryS> docs, Set<String> avlDocs) {
		List<OPlySmnDocQryS> res = new ArrayList<>();
		for (OPlySmnDocQryS d : docs) {
			if ((!"0".equals(d.getDflChc()) || StringUtils.isEmpty(d.getRowVal())) && "S".equals(d.getJmpChc()) && !avlDocs.contains(d.getRowNam())) {
				res.add(d);
			}
		}
		return res;
	}
	
	@Override
	public List<OPlySmnDocQryS> getMandatoryDocsPending(List<OPlySmnDocQryS> docs, List<PlyDocument> gddocs, List<OPlySmnDocQryS> docsMap) {
		List<OPlySmnDocQryS> res = new ArrayList<>();

		List<OPlySmnDocQryS> mdocs = docs.stream().filter(x -> "S".equals(x.getJmpChc())).collect(Collectors.toList());
		for (OPlySmnDocQryS d : mdocs) {
			Optional<OPlySmnDocQryS> op = docsMap.stream().filter(x -> d.getCncVal().equals(x.getCncVal())).findFirst();
			if (op.isPresent()) {
				Optional<PlyDocument> opgd = gddocs.stream().filter(x -> op.get().getRowVal().equals(x.getDcnVal()) && op.get().getRowNam().equals(x.getGrpVal())).findFirst();
				if (!opgd.isPresent()) {
					res.add(d);
				}
			} else {
				res.add(d);
			}
		}
		
		return res;
	}
	
	@Override
	public List<OPlySmnDocQryS> getSignedDocsPending(List<OPlySmnDocQryS> docs, List<PlyDocument> gddocs, List<OPlySmnDocQryS> docsMap, List<String> listsToSign, Pair<String, Boolean> cs, Pair<String, Boolean> cf) {
		List<OPlySmnDocQryS> res = new ArrayList<>();

		List<OPlySmnDocQryS> sdocs = docs.stream().filter(x -> listsToSign.contains(x.getVbrNam())).collect(Collectors.toList());
		for (OPlySmnDocQryS d : sdocs) {
			Optional<OPlySmnDocQryS> op = docsMap.stream().filter(x -> d.getCncVal().equals(x.getCncVal())).findFirst();
			if (op.isPresent()) {
				Optional<PlyDocument> opgd = gddocs.stream().filter(x -> op.get().getRowVal().equals(x.getDcnVal()) && op.get().getRowNam().equals(x.getGrpVal()) && "F".equals(x.getSgtVal())).findFirst();
				checkSigned(cs, cf, res, d, op.get(), opgd);
			}
		}
		
		return res;
	}

	private void checkSigned(Pair<String, Boolean> cs, Pair<String, Boolean> cf, List<OPlySmnDocQryS> res, OPlySmnDocQryS d, OPlySmnDocQryS op, Optional<PlyDocument> opgd) {
		if (cs.getLeft().equals(op.getCncVal())) {
			if (Boolean.TRUE.equals(cs.getRight()) && !opgd.isPresent()) {
				res.add(d);
			}
		} else if (cf.getLeft().equals(op.getCncVal())) {
			if (Boolean.TRUE.equals(cf.getRight()) && !opgd.isPresent()) {
				res.add(d);
			}
		} else if (!opgd.isPresent()) {
			res.add(d);
		}
	}

	@Override
	public void generateDocument(BigDecimal cmpVal, String usrVal, String lngVal, NofitFilesData nfd, String qtnVal, String plyVal) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Object> data = new HashMap<>();
		
		data.put(CMP_VAL, cmpVal);
		Map<String, String> emission = new HashMap<>();
		emission.put("enrSqn", nfd.getEnrSqn());
		emission.put("aplVal", nfd.getAplVal());
		if (StringUtils.isNotEmpty(plyVal)) {
			emission.put("plyVal", plyVal);
		} else if (StringUtils.isNotEmpty(qtnVal)) {
			emission.put("qtnVal", qtnVal);
		}
		emission.put("aplEnrSqn", nfd.getAplEnrSqn());
		emission.put("delVal", nfd.getDelVal());
		emission.put("oprFlt", nfd.getOprFlt());
		emission.put("lobVal", nfd.getLobVal());
		data.put("emission", emission);
		data.put(USR_VAL, usrVal);
		data.put(LNG_VAL, lngVal);
		
		String notifBasePath = basePath.replace("reports/", "");
		String url = UriComponentsBuilder.fromHttpUrl(notifBasePath + "/notifications/files")
			.queryParam(CMP_VAL, cmpVal)
			.queryParam(USR_VAL, usrVal)
			.build(false)
			.toUriString();
		
		for (String action : nfd.getActions()) {
			data.put("oprIdnVal", action);
		
			try {
				HttpEntity<Map<String, Object>> request = new HttpEntity<>(data, headers);
				restTemplate.exchange(url, HttpMethod.POST, request, String.class);
			} catch(RestClientResponseException e) {
				String sErr = e.getResponseBodyAsString();
				if (!sErr.contains("No existe formulario")) {
					throw e;
				}
			}
		}
	}
}
