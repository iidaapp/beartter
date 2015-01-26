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


	public static void  add(String beartterId, Session session) throws Exception {

		TwitterUserStream stream = null;
		synchronized(userStreamMap) {
		
		if(userStreamMap.containsKey(beartterId)){
			stream = userStreamMap.get(beartterId);
			System.out.println(stream.toString());
			System.out.println("うえ");
		}else{
			stream = new TwitterUserStream(beartterId);
			userStreamMap.putIfAbsent(beartterId, stream);
		System.out.println("した");}

		stream.addSession(session);
		}
	}


	public static void remove(String beartterId) {

		
		if(StringUtils.isEmpty(beartterId)) {
			System.out.println("beartterId is null");
			return;
		}
		synchronized(userStreamMap) {
			
		if(userStreamMap.containsKey(beartterId)) {
			TwitterUserStream stream = userStreamMap.get(beartterId);
			if(stream.countSession() == 0){
				System.out.println("remove user stream map");
				userStreamMap.get(beartterId).shutdownStream();
				userStreamMap.remove(beartterId);
			}
			
		}
		}	
	}

}
