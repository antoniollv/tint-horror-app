package com.mapfre.tron.api.cmn.peca;

import java.util.Map;
import java.util.Set;

import lombok.Data;

/**
 * PECA configuration.
 */
@Data
public class NwtPecaAspectConfig {
    	/** PECA logger configuration: signatures to manage. */
	private Set<String> signatures;
	/** PECA logger configuration: objects to manage. */
	private Set<String> objects;
	/** PECA logger configuration: log pattern. */
	private String pattern;
	/** ASYNC logger. */
	private boolean async;
	/** GSON serializer. */
	private boolean gson;
	/** Trace all response entity. */
	private boolean response;
	/** Logback Appender data. */
	private Map<String, String> appender;
}
