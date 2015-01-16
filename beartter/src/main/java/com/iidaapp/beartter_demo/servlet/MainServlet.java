package com.iidaapp.beartter_demo.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iidaapp.beartter_demo.util.BeartterProperties;
import com.iidaapp.beartter_demo.util.BeartterUtils;

/**
 * メイン画面表示処理
 * @author iida
 *
 */
public class MainServlet extends HttpServlet {

	private static Logger log = LoggerFactory.getLogger(MainServlet.class);
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

		// 共通処理へ
		execute(req, resp);
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

		// 共通処理へ
		execute(req, resp);
	}


	private void execute(HttpServletRequest req, HttpServletResponse resp) {

		log.info(BeartterProperties.MESSAGE_START_MAIN_SERVLET);

		// SessionがNULLの場合はエラー
		HttpSession session = req.getSession(false);
		if (session == null) {
			log.error(BeartterProperties.MESSAGE_ERROR_NULL_SESSION);
			try {
				resp.sendRedirect("error");
			} catch (IOException e1) {
				log.error(e1.toString());
			}
			return;
		}

		// beartterIdがNULLの場合はエラー
		String beartterId = (String) session.getAttribute("beartterId");
		if (StringUtils.isEmpty(beartterId)) {

			log.error(BeartterProperties.MESSAGE_ERROR_NULL_BEARTTER_ID);
			try {
				resp.sendRedirect("error");
				return;
			} catch (IOException e1) {
				log.error(e1.toString());
				return;
			}
		}

		String characterName = null;

		try {
			characterName = BeartterUtils.getCharacterName(beartterId);
		} catch (SQLException e2) {
			log.error(e2.toString());
			try {
				resp.sendRedirect("error");
				return;
			} catch (IOException e1) {
				log.error(e1.toString());
				return;
			}
		}

		// ツイートエラー情報をセッションから取得
		String error = (String) session.getAttribute("error");

		// エラー情報が存在する場合はリクエストスコープに保存し、セッションからは削除
		if (!StringUtils.isEmpty(error)) {
			req.setAttribute("error", error);
			session.removeAttribute("error");
		}

		// 各情報のセット
		req.setAttribute("newLine", "\n");
		session.setAttribute("characterName", characterName);

		// 遷移
		try {
			req.getRequestDispatcher("/Streaming.jsp").forward(req, resp);
		} catch (ServletException e) {
			log.error(e.toString());
			try {
				resp.sendRedirect("error");
				return;
			} catch (IOException e1) {
				log.error(e1.toString());
				return;
			}
		} catch (IOException e) {
			log.error(e.toString());
			return;
		}
		return;
	}
}
