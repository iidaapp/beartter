package com.iidaapp.beartter.stream;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserStreamAdapter;
import twitter4j.auth.AccessToken;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iidaapp.beartter.db.DbUtils;
import com.iidaapp.beartter.entity.AccessTokenEntity;
import com.iidaapp.beartter.entity.TweetStatus;

public class TwitterUserStream extends UserStreamAdapter {

	private static TwitterStream twitterStream = null;
	public static Set<Session> sessions = null;
	private static Logger log = LoggerFactory.getLogger(TwitterUserStream.class);


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TwitterUserStream(String beartterId, Session session) throws Exception {

		sessions = Collections.synchronizedSet(new HashSet());

		sessions.add(session);
		List<AccessTokenEntity> entityList = DbUtils.selectAccessTokenListFromAccessToken(beartterId);

		if (entityList.size() == 0)
			throw new Exception();

		AccessTokenEntity entity = entityList.get(0);
		AccessToken accessToken = new AccessToken(entity.getoAuthToken(), entity.getoAuthSecret());
		twitterStream = new TwitterStreamFactory().getInstance(accessToken);

		twitterStream.addListener(this);
		twitterStream.user();

	}


	@Override
	public void onStatus(Status status) {

		try {

			for (Session session : sessions) {
				// TODO statusを適切な形にして返す
				User user = status.getUser();
				String resStr = "@" + user.getScreenName() + " : " + status.getText();

				System.out.println(resStr);

				TweetStatus tweetStatus = new TweetStatus(status);
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(tweetStatus);

				System.out.println(json);
				session.getBasicRemote().sendText(json);
			}

		} catch (IOException e) {

			log.error(e.getMessage());
			shutdownStream();

		}
	}


	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

	}


	public void shutdownStream() {

		twitterStream.removeListener(this);
		twitterStream.shutdown();

	}
}
