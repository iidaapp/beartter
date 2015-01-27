package com.iidaapp.beartter.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.websocket.Session;

import org.apache.commons.lang3.StringUtils;

import com.iidaapp.beartter.entity.AccessTokenEntity;

public class TwitterUserStreamMap {

	public static ConcurrentMap<String, TwitterUserStream> userStreamMap;

	static {
		userStreamMap = new ConcurrentHashMap<String, TwitterUserStream>();
	}


	public static void add(String beartterId, List<AccessTokenEntity> entityList, Session session) throws Exception {

		List<TwitterUserStream> streamList = new ArrayList<TwitterUserStream>();

		synchronized(userStreamMap) {

			for(AccessTokenEntity entity : entityList) {

				TwitterUserStream stream = null;

				if(userStreamMap.containsKey(Long.toString(entity.getUserId()))) {

					stream = userStreamMap.get(beartterId);
					streamList.add(stream);
					System.out.println("うえ");

				} else {

					stream = new TwitterUserStream(entity);
					userStreamMap.putIfAbsent(Long.toString(entity.getUserId()), stream);
					System.out.println("した");
				}

				stream.addSession(session);
			}

		}
	}


	public static void remove(String beartterId, List<AccessTokenEntity> entityList) {

		if(StringUtils.isEmpty(beartterId)) {
			System.out.println("beartterId is null");
			return;
		}
		synchronized(userStreamMap) {

			if(userStreamMap.containsKey(beartterId)) {
				TwitterUserStream stream = userStreamMap.get(beartterId);
				if(stream.countSession() == 0) {
					System.out.println("remove user stream map");
					userStreamMap.get(beartterId).shutdownStream();
					userStreamMap.remove(beartterId);
				}

			}
		}
	}

}
