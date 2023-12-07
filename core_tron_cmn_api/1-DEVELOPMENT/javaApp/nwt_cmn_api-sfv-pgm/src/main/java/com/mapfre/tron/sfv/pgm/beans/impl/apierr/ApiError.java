package com.mapfre.tron.sfv.pgm.beans.impl.apierr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestClientResponseException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapfre.tron.sfv.bo.Message;

@SuppressWarnings("unchecked")
public class ApiError {

	private static final ObjectMapper om = new ObjectMapper();
	private static final String KEY = "key";
	private static final String VALUE = "value";
	
	private ApiError() {}

	public static List<Message> apiErrors(RestClientResponseException e) {
		List<Message> msgs = new ArrayList<>();
		try {
			Map<String, Object> data = om.readValue(e.getResponseBodyAsByteArray(), new TypeReference<Map<String, Object>>() {});
			if (data.containsKey("application") && data.containsKey("errors")) { // API controlled error
				List<Map<String, Object>> errors = (List<Map<String, Object>>) data.get("errors");
				if (!errors.isEmpty()) {
					for (Map<String, Object> error : errors) {
						msgs.add(getError(error));
					}
				} else {
					Message o = new Message();
					o.setMsgTxtVal(Objects.toString(data.get("exception")));
					if (StringUtils.isEmpty(o.getMsgTxtVal())) {
						o.setMsgTxtVal(Objects.toString(data.get("message")));
					}
					o.setMsgVal(Integer.parseInt(Objects.toString(data.get("code"), "0")));
					o.setMsgTypVal(3);
					msgs.add(o);
				}
			} else {
				Message o = new Message();
				o.setMsgTxtVal(e.getResponseBodyAsString());
				o.setMsgVal(500);
				o.setMsgTypVal(3);
				msgs.add(o);
			}
		} catch (IOException oe) {
			Message o = new Message();
			o.setMsgTxtVal(e.getResponseBodyAsString());
			o.setMsgVal(500);
			o.setMsgTypVal(3);
			msgs.add(o);
		}
		return msgs;
	}

	private static Message getError(Map<String, Object> error) {
		Message o = new Message();
		o.setMsgTxtVal(Objects.toString(error.get("rootcase")));
		o.setMsgTypVal(3);
		if (error.containsKey("info")) {
			List<Map<String, Object>> infos = (List<Map<String, Object>>) error.get("info");
			for (Map<String, Object> info : infos) {
				if ("prpNam".equals(info.get(KEY))) {
					o.fldNam(Objects.toString(info.get(VALUE), null));
				}
				if ("errVal".equals(info.get(KEY))) {
					o.setMsgVal(Integer.parseInt(Objects.toString(info.get(VALUE), "0")));
				}
			}
		}
		return o;
	}
}
