package com.iidaapp.beartter_demo;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/streamdemo", configurator = DefaultConfigurator.class)
public class TestStream2 {

	@OnOpen
	public void onOpen(Session session, EndpointConfig config) throws Exception {

		System.out.println("client had accessed" + session.getRequestURI().toString());

		HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		System.out.println("http session is " + httpSession);

		String beartterId = (String) httpSession.getAttribute("beartterId");
		TwitterUserStreamMap.add(beartterId, session);
	}


	@OnClose
	public void onClose(Session session) {

		HttpSession httpSession = (HttpSession) session.getUserProperties().get(HttpSession.class.getName());
		System.out.println("http session is " + httpSession);

		String beartterId = (String) httpSession.getAttribute("beartterId");
		TwitterUserStreamMap.remove(beartterId);
	}
	
	@OnError
	public void onError(Session session, Throwable t) throws Exception{
		
		HttpSession httpSession = (HttpSession) session.getUserProperties().get(HttpSession.class.getName());
		System.out.println("http session is " + httpSession);

		String beartterId = (String) httpSession.getAttribute("BeartterId");
		TwitterUserStreamMap.remove(beartterId);
		
		t.printStackTrace();
	}
}
