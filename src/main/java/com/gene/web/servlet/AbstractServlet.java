package com.gene.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void response(HttpServletResponse resp, String msg) {
		resp.setHeader("Content-type", "text/html;charset=UTF-8");
		PrintWriter writer = null;
		try {
			writer = resp.getWriter();
			writer.print(msg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	public void response(HttpServletResponse resp, String ret, String msg) {
		resp.setHeader("Content-type", "text/html;charset=UTF-8");  
		PrintWriter writer;
		try {
			writer = resp.getWriter();
			if(ret != null) {
				writer.print(ret + ":" + msg);
			}else {
				writer.print(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
