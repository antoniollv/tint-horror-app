package com.mapfre.tron.config.api.cmn;

import com.mapfre.tron.config.api.cmn.wsproxy.EnviarMensaje;
import com.mapfre.tron.config.api.cmn.wsproxy.Reporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = { "classpath:/nwt_be-mail-sender.xml"
		/*, "classpath:/nwt_be-sms-sender.xml", "classpath:/nwt_be-report.xml"*/})
public class NwtCmnApiBeWsProxy {
	// Workaround
	@Bean
	public com.mapfre.mutua.plataformasms.webservices.control.EnviarMensaje enviarMensaje() {
		return new EnviarMensaje();
	}

	// Workaround
	@Bean
	public com.mapsoft.wtw.reports.Reporter reporter() {
		return new Reporter();
	}
}
