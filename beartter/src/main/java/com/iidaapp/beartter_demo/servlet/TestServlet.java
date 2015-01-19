package com.iidaapp.beartter_demo.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.iidaapp.beartter_demo.util.BeartterProperties;
import com.iidaapp.beartter_demo.util.BeartterUtils;

public class TestServlet extends HttpServlet {

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

		// SessionがNULLの場合はエラー
		HttpSession session = req.getSession(true);

		String characterName = null;
		String beartterId = "iidaapp";
		
		session.setAttribute("beartterId", beartterId);

		try {
			characterName = BeartterUtils.getCharacterName(beartterId);
		} catch (SQLException e2) {
			try {
				e2.printStackTrace();
				resp.sendRedirect("error");
				return;
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			}
		}

		// ツイートエラー情報をセッションから取得
		String error = (String) session.getAttribute("error");

		// エラー情報が存在する場合はリクエストスコープに保存し、セッションからは削除
		if(!StringUtils.isEmpty(error)) {
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
			e.printStackTrace();
			try {
				resp.sendRedirect("error");
				return;
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		return;
	}
}
