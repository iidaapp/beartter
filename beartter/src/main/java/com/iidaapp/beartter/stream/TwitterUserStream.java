package com.iidaapp.beartter.stream;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.Session;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserStreamAdapter;
import twitter4j.auth.AccessToken;

import com.iidaapp.beartter.db.DbUtils;
import com.iidaapp.beartter.entity.AccessTokenEntity;

public class TwitterUserStream extends UserStreamAdapter {

	private static TwitterStream twitterStream = null;
	public static Set<Session> sessions = null;


	public TwitterUserStream(String beartterId, Session session) throws Exception {

		sessions = Collections.synchronizedSet(new HashSet());

		sessions.add(session);
		List<AccessTokenEntity> entityList = DbUtils.selectAccessTokenListFromAccessToken(beartterId);

		if(entityList.size() == 0)
			throw new Exception();

		AccessTokenEntity entity = entityList.get(0);
		AccessToken accessToken = new AccessToken(entity.getoAuthToken(), entity.getoAuthSecret());
		twitterStream = new TwitterStreamFactory().getInstance(accessToken);

		StatusListener listner = new MyStreamAdapter();

		twitterStream.addListener(this);
		twitterStream.user();

	}


	@Override
	public void onStatus(Status status) {

		try {

			for(Session session : sessions) {
				// TODO statusを適切な形にして返す
				User user = status.getUser();
				String resStr = "@" + user.getScreenName() + " : " + status.getText();
				System.out.println(resStr);

				session.getBasicRemote().sendText(resStr);
			}

		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		}
	}


	public void shutdownStream() {

		twitterStream.removeListener(this);
		twitterStream.shutdown();

	}
}

// イベントを受け取るリスナーオブジェクト
class MyStreamAdapter extends UserStreamAdapter {

	public void onException(Exception e) {
		e.printStackTrace();

	}


	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
	}


	public void onStatus(Status status) {

		System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
	}


	public void onStallWarning(StallWarning warning) {
		System.out.println("Got stall warning:" + warning);

	}


	public void onScrubGeo(long userId, long upToStatusId) {
		System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
	}


	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
	}
}
