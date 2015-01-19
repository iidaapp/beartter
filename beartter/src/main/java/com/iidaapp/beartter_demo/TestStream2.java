package com.iidaapp.beartter_demo;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang3.StringUtils;

@ServerEndpoint(value = "/streamdemo", configurator = DefaultConfigurator.class)
public class TestStream2 {

	@OnOpen
	public void onOpen(Session session, EndpointConfig config) throws Exception {

		System.out.println("onOpen now");
		System.out.println("client had accessed" + session.getRequestURI().toString());

		HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		System.out.println("http session is " + httpSession);

		String beartterId = (String) httpSession.getAttribute("beartterId");

		if(StringUtils.isEmpty(beartterId))
			System.out.println("beartterid is null");

		TwitterUserStreamMap.add(beartterId, session);
	}


	@OnClose
	public void onClose(Session session) throws IOException {

		System.out.println("onClose now");
		HttpSession httpSession = (HttpSession) session.getUserProperties().get(HttpSession.class.getName());
		System.out.println("http session is " + httpSession);

		String beartterId = (String) httpSession.getAttribute("beartterId");
		TwitterUserStreamMap.remove(beartterId);

		httpSession.invalidate();
	}


	@OnError
	public void onError(Session session, Throwable t) throws Exception {

		System.out.println("onError now");
		HttpSession httpSession = (HttpSession) session.getUserProperties().get(HttpSession.class.getName());
		System.out.println("http session is " + httpSession);

		String beartterId = (String) httpSession.getAttribute("BeartterId");
		if(!StringUtils.isEmpty(beartterId) && httpSession != null)
			TwitterUserStreamMap.remove(beartterId);

		if(httpSession != null)
			httpSession.invalidate();

		t.printStackTrace();
	}
}
