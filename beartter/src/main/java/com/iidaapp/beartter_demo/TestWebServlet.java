package com.iidaapp.beartter_demo;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/wsdemo")
public class TestWebServlet {
	
    private static Set<Session> ses = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("onOpen : " + session);
        ses.add(session);
    }
   
    @OnMessage
    public String onMessage(String text) {
        return "echo => " + text;
    }
    
    @OnClose
    public void onClose(Session session) {
        System.out.println("onClose : " + session);
        ses.remove(session);
    }
    
    public static void sendMessage(String msg) {
        for (Session ses : ses) {
            ses.getAsyncRemote().sendText(msg);
        }
    }
}
