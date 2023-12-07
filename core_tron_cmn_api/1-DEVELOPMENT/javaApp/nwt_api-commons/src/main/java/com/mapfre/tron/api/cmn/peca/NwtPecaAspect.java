package com.mapfre.tron.api.cmn.peca;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * PECA logger aspect based.
 */
@Component
@Aspect
@Profile("peca")
@Slf4j
public class NwtPecaAspect {
	/** Serializer using jackson. */
	private ObjectMapper om = new ObjectMapper();
	/** Serializer using GSON. */
	private Gson gson = new Gson();
	/** PECA Config. */
	private NwtPecaAspectConfig config;
	/** Logger for PECA traces. */
	private Logger logPeca = null;
	/** Host server. */
	private String server;
	
	/**
	 * Load PECA logger configuration.
	 */
	@PostConstruct
	public void init() {
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		loadPecaConf();
		createPecaLogger();
		getMachine();
		NwtPecaObjectLogger.init(config, om , gson, server);
		log.debug("PECA logger started...");
	}
	
	/**
	 * Gracefully shutdown to achive async logger events queued.
	 */
	@PreDestroy
	public void end() {
		LoggerContext logCtx = (LoggerContext) LoggerFactory.getILoggerFactory();
		logCtx.stop();
	}

	/**
	 * Load json configuration.
	 */
	private void loadPecaConf() {
		try {
			config = om.readValue(getClass().getResourceAsStream("/peca.json"), NwtPecaAspectConfig.class);
		} catch (IOException e) {
			log.error("PECA loading configuration", e);
		}
	}

	/**
	 * Creates peca logger programatically. Configuration values from
	 * logback.properties file.
	 */
	private void createPecaLogger() {
		log.debug("Configure PECA logger...");
		LoggerContext logCtx = (LoggerContext) LoggerFactory.getILoggerFactory();

		PatternLayoutEncoder logEncoder = new PatternLayoutEncoder();
		logEncoder.setContext(logCtx);
		logEncoder.setPattern(config.getAppender().get("pattern"));
		logEncoder.start();

		RollingFileAppender<ILoggingEvent> logAppender = new RollingFileAppender<>();
		logAppender.setContext(logCtx);
		logAppender.setName("peca");
		logAppender.setEncoder(logEncoder);
		logAppender.setAppend(true);
		logAppender.setFile(config.getAppender().get("filename"));

		SizeAndTimeBasedRollingPolicy<ILoggingEvent> logPolicy = new SizeAndTimeBasedRollingPolicy<>();
		logPolicy.setContext(logCtx);
		logPolicy.setParent(logAppender);
		logPolicy.setMaxFileSize(FileSize.valueOf(StringUtils.defaultIfEmpty(config.getAppender().get("maxsize"), "5MB")));
		logPolicy.setMaxHistory(Integer.valueOf(StringUtils.defaultIfEmpty(config.getAppender().get("maxhistory"), "10")));
		logPolicy.setFileNamePattern(config.getAppender().get("filenamepattern"));
		logPolicy.start();

		logAppender.setRollingPolicy(logPolicy);
		logAppender.start();

		logPeca = logCtx.getLogger("peca");
		if (config.isAsync()) {
			AsyncAppender asynAppernder = new AsyncAppender();
			asynAppernder.setContext(logCtx);
			asynAppernder.setQueueSize(Integer.valueOf(StringUtils.defaultIfEmpty(config.getAppender().get("queueSize"), "256")));
			asynAppernder.addAppender(logAppender);
			asynAppernder.setName("peca");
			asynAppernder.start();
			logPeca.addAppender(asynAppernder);
		} else {
			logPeca.addAppender(logAppender);
		}
		logPeca.setLevel(Level.WARN);
		logPeca.setAdditive(false); /* set to true if root should log too */
	}

	/**
	 * Gets the host server id.
	 */
	private void getMachine() {
		try {
			server = InetAddress.getLocalHost().getHostName() + "/" + InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			server = "UNKNOWN";
		}
	}

	/**
	 * Intercepts calls for PECA log.
	 * @param pjp joinpoint.
	 * @return result of the method
	 * @throws Throwable error
	 */
	@Around("execution(public * com.mapfre.tron.api.*.sr.*.*(..))")
	public Object pecaLog(ProceedingJoinPoint pjp) throws Throwable {
		log.debug("PECA Logger intercept " + pjp.getSignature().toLongString());

		Object result = null;
		Object error = null;
		try {
			result = pjp.proceed();
			String op = getOperation(pjp.getSignature().toShortString());
			boolean manage = pecaManage(op, pjp.getArgs(), result);
			if (manage) {
				log.debug("PECA Logger traces call " + pjp.getSignature().toLongString(), result);
				NwtPecaObjectLogger obj = new NwtPecaObjectLogger(op, pjp.getArgs(), result, error, getUser(pjp));
				logPeca.warn("{}", obj);
				log.debug("PECA Logger -> " + obj);
			}
		} catch (Throwable th) {
			error = th;
			throw th;
		}

		return result;
	}

	/**
	 * Get the operation name deleting (..) params indicator.
	 * @param op operation pjp.getSignature().toShortString()
	 * @return operation name
	 */
	private String getOperation(String op) {
		int idx = op.indexOf('(');
		if (idx > 0) {
			return op.substring(0, idx);
		}
		return op;
	}

	/**
	 * Checks if the call must be logged for PECA.
	 * @param signature method signature
	 * @param args  call in arguments
	 * @return true if must be logged
	 */
	private boolean pecaManage(String signature, Object[] args, Object result) {
		return (config.getSignatures() != null && config.getSignatures().contains(signature)) || (config.getObjects() != null && existsInObjects(args) || (config.getObjects() != null && existsInObjects(new Object[] {result})));
	}

	/**
	 * Checks if object type exists in arguments.
	 * @param args arguments
	 * @return true if object type exists in object
	 */
	private boolean existsInObjects(Object[] args) {
		if (args != null) {
			for (Object o : args) {
			    	if(o instanceof ResponseEntity) {
			    	    o = ((ResponseEntity<?>)o).getBody();
			    	}
				if (o instanceof List<?>) {
					List<?> lst = (List<?>) o;
					if (lst != null && !lst.isEmpty() && lst.get(0) != null && config.getObjects().contains(lst.get(0).getClass().getSimpleName())) {
						return true;
					}
				} else if (o != null && config.getObjects().contains(o.getClass().getSimpleName())) {
					return true;
				} else if(o != null && o instanceof Object) {
					BeanWrapper objNwtWrapper = new BeanWrapperImpl(o);
					// Se obtienen las propiedades del objeto
					PropertyDescriptor[] props = objNwtWrapper.getPropertyDescriptors();				    
					for (PropertyDescriptor prop : props) {
					    if(prop.getClass().isAssignableFrom(ArrayList.class) || config.getObjects().contains(prop.getName())) {
						return true;        					
					    }
					}
				    }
			}
		}
		return false;
	}
	
	/**
	 * Gets user
	*/
    private String getUser(ProceedingJoinPoint pjp) {
	Method[] ms = pjp.getSignature().getDeclaringType().getMethods();
	for (Method m : ms) {
	    if (m.getName().equals(pjp.getSignature().getName()) && m.getParameterCount() == pjp.getArgs().length) {
		Parameter[] ps = m.getParameters();
		if (ps != null) {
		    for (int i = 0; i < ps.length; i++) {
			if ("usrVal".equals(ps[i].getName())) {
			    return Objects.toString(pjp.getArgs()[i]);
			}
		    }
		}
	    }
	}
	return "UNKNOWN";
    }
}
