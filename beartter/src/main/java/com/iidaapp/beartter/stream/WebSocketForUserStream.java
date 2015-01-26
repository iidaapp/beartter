package com.iidaapp.beartter.stream;

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
public class WebSocketForUserStream {

	private static Logger log = LoggerFactory.getLogger(WebSocketForUserStream.class);


	@OnOpen
	public void onOpen(Session session, EndpointConfig config) throws Exception {

		System.out.println("onOpen now");
		System.out.println("client had accessed" + session.getRequestURI().toString());

		HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		System.out.println("http session is " + httpSession);

		if(httpSession == null) {
			session.getAsyncRemote().sendText("null");
			return;
		}

		String beartterId = (String) httpSession.getAttribute("beartterId");

		if(StringUtils.isEmpty(beartterId)) {
			System.out.println("beartterid is null");
			return;
		}

		TwitterUserStreamMap.add(beartterId, session);
		httpSession.setAttribute("session", session);
		httpSession.removeAttribute(beartterId);
	}


	@OnClose
	public void onClose(Session session) throws Exception {

		System.out.println("onClose now");
		HttpSession httpSession = (HttpSession) session.getUserProperties().get(HttpSession.class.getName());
		System.out.println("http session is " + httpSession);

		if(httpSession == null)
			return;

		String beartterId = null;
		System.out.println(beartterId);
		try {
			beartterId = (String) httpSession.getAttribute("beartterId");
		} catch (IllegalStateException e) {
			// TODO エラー処理
			log.info(e.getMessage());
			return;
		}

		if(StringUtils.isEmpty(beartterId)) {
			// TODO エラー処理
			System.out.println("beartterid is null");
			return;
		}
		TwitterUserStreamMap.userStreamMap.get(beartterId).removeSession(session);
		TwitterUserStreamMap.remove(beartterId);

	}


	@OnError
	public void onError(Session session, Throwable t) throws Exception {

		System.out.println("onError now");

		System.out.println(t.getMessage());

	}
}
