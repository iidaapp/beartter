package com.iidaapp.beartter_demo;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MySessionListner implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
	}


	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {

		TwitterUserStreamMap.remove((String) arg0.getSession().getAttribute("beartterId"));
	}

}
