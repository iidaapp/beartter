package com.iidaapp.beartter_demo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.websocket.Session;

public class TwitterUserStreamMap {

	public static ConcurrentMap<String, TwitterUserStream> userStreamMap;

	static{
		userStreamMap = new ConcurrentHashMap<String, TwitterUserStream>();
	}

	public static void add(String beartterId, Session session) throws Exception {

		userStreamMap.putIfAbsent(beartterId, new TwitterUserStream(beartterId, session));

	}


	public static void remove(String beartterId) {

		userStreamMap.get(beartterId).shutdownStream();
		userStreamMap.remove(beartterId);

	}

}
