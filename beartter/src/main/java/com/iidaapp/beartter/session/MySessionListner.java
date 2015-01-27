package com.iidaapp.beartter.session;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.iidaapp.beartter.db.DbUtils;
import com.iidaapp.beartter.entity.AccessTokenEntity;
import com.iidaapp.beartter.stream.TwitterUserStreamMap;

public class MySessionListner implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
	}


	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {

		String beartterId = (String) arg0.getSession().getAttribute("beartterId");
		List<AccessTokenEntity> entityList;
		try {
			entityList = DbUtils.selectAccessTokenListFromAccessToken(beartterId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TwitterUserStreamMap.remove(beartterId, entityList);
	}

}
