package com.iidaapp.beartter.stream;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultConfigurator extends ServerEndpointConfig.Configurator {

	private static Logger log = LoggerFactory.getLogger(DefaultConfigurator.class);


	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		HttpSession httpSession = (HttpSession) request.getHttpSession();
		if(httpSession != null) {

			try {
				sec.getUserProperties().putIfAbsent(HttpSession.class.getName(), httpSession);
			} catch (Exception e) {
				log.error(e.getMessage());
				return;
			}
		} else {

			log.info("session is null.");
			return;
		}
	}
}
