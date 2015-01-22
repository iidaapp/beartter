package com.iidaapp.beartter.stream;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.websocket.Session;

import org.apache.commons.lang3.StringUtils;

public class TwitterUserStreamMap {

	public static ConcurrentMap<String, TwitterUserStream> userStreamMap;

	static {
		userStreamMap = new ConcurrentHashMap<String, TwitterUserStream>();
	}


	public static void add(String beartterId, Session session) throws Exception {

		userStreamMap.putIfAbsent(beartterId, new TwitterUserStream(beartterId, session));

	}


	public static void remove(String beartterId) {

		if(StringUtils.isEmpty(beartterId)) {
			System.out.println("beartterId is null");
			return;
		}

		// TODO Mapに存在する場合のみ操作、でいいか検証
		if(userStreamMap.containsKey(beartterId)) {
			userStreamMap.get(beartterId).shutdownStream();
			userStreamMap.remove(beartterId);
		}

	}

}
