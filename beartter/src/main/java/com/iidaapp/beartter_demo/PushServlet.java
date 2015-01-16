package com.iidaapp.beartter_demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PushServlet", urlPatterns = { "/push" })
public class PushServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4299696180289759362L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			request.setCharacterEncoding("utf-8");
			String text = request.getParameter("text");
			ServletContext servletContext = request.getServletContext();
			ArrayList<AsyncContext> contexts = (ArrayList<AsyncContext>) servletContext.getAttribute(CometTest.CONTEXT_NAME);
			if(contexts != null) {
				for(AsyncContext ac : contexts) {
					try {
						ac.getResponse().setCharacterEncoding("utf-8");
						PrintWriter writer = ac.getResponse().getWriter();
						writer.println(text);
						writer.close();
						ac.complete();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				contexts.clear();
			}
			out.println("ok");
		} finally {
			out.close();
		}
	}
}