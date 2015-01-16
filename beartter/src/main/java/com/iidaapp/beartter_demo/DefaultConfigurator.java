package com.iidaapp.beartter_demo;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class DefaultConfigurator extends ServerEndpointConfig.Configurator {

	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		HttpSession httpSession = (HttpSession) request.getHttpSession();
		try {
			sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
		} catch (Exception e) {
			//TODO 適切な処理
			throw e;
		}

	}
}
