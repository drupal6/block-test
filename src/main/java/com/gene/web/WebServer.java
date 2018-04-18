package com.gene.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.gene.web.servlet.GetBlockServlet;
import com.gene.web.servlet.WriteBlockServlet;

public class WebServer {
	
	private Server server;
	private static WebServer instance = new WebServer();
	
	public static WebServer getInstance() {
		return instance;
	}
	
	public boolean start(int port) {
		server = new Server(port);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
		context.setContextPath("/block");
		context.addServlet(new ServletHolder(new GetBlockServlet()), "/getBlock/*");
		context.addServlet(new ServletHolder(new WriteBlockServlet()), "/writeBlock/*");
		server.setHandler(context);
		try {
			server.start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
