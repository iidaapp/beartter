package com.iidaapp.beartter_demo;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint(value = "/streamdemo", configurator = DefaultConfigurator.class)
public class TestStream2 {

	private static Logger log = LoggerFactory.getLogger(TestStream2.class);


	@OnOpen
	public void onOpen(Session session, EndpointConfig config) throws Exception {

		System.out.println("onOpen now");
		System.out.println("client had accessed" + session.getRequestURI().toString());

		HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		System.out.println("http session is " + httpSession);

		String beartterId = (String) httpSession.getAttribute("beartterId");

		if(StringUtils.isEmpty(beartterId)) {
			System.out.println("beartterid is null");
			throw new Exception();
		}

		TwitterUserStreamMap.add(beartterId, session);
	}


	@OnClose
	public void onClose(Session session) throws Exception {

		System.out.println("onClose now");
		HttpSession httpSession = (HttpSession) session.getUserProperties().get(HttpSession.class.getName());
		System.out.println("http session is " + httpSession);

		if(httpSession == null)
			return;

		String beartterId = null;

		try {
			beartterId = (String) httpSession.getAttribute("beartterId");
		} catch (IllegalStateException e) {
			// TODO エラー処理
			log.info(e.getMessage());
			return;
		}

		if(StringUtils.isEmpty(beartterId)) {
			// TODO エラー処理
			return;
		}
		TwitterUserStreamMap.remove(beartterId);

	}


	@OnError
	public void onError(Session session, Throwable t) throws Exception {

		System.out.println("onError now");
		
		System.out.println(t.getMessage());

	}
}
