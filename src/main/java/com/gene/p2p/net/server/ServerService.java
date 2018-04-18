package com.gene.p2p.net.server;

import com.gene.p2p.net.PBMessage;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ServerService {
	
	private static ServerService service = new ServerService();
	
	private ChannelGroup clientGroup = new DefaultChannelGroup("ChannelGroups", GlobalEventExecutor.INSTANCE);
	
	private String ip;
	
	private int port;
	
	public static ServerService getIns() {
		return service;
	}
	
	public void initServer(String ip, int port) {
		this.ip = ip;
		this.port = port;
		Server server = new Server();
		server.start(ip, port);
	}
	
	public void addClient(Channel client) {
		clientGroup.add(client);
	}
	
	public void removeClient(Channel client) {
		clientGroup.remove(client);
	}
	
	public void broad(PBMessage message) {
		clientGroup.writeAndFlush(message);
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}
}
