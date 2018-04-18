package com.gene.p2p.net.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gene.p2p.net.PBMessage;
import com.gene.proto.ServerProto.ClientConnServerInfo;
import com.gene.proto.ServerProto.ServerInfo;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class ClientService {
	
	private static final AttributeKey<String> ipPortKey = AttributeKey.valueOf("ip#port");
	
	private static ClientService service = new ClientService();
	
	private Map<Channel, Client> clientMap = new HashMap<Channel, Client>();
	
	private List<ServerInfo> otherServers = new ArrayList<ServerInfo>();
	
	private List<String> otherServerIpPort = new ArrayList<String>();
	
	public static ClientService getIns() {
		return service;
	}
	
	/**
	 * 
	 * @param serverInfo
	 * @param type 0 新启节点  1连接新启节点
	 */
	public void connect(String ip, int port, ServerInfo serverInfo) {
		String ipPort = ip + "#" + port;
		if(otherServerIpPort.contains(ipPort)) {
			return;
		}
		otherServerIpPort.add(ipPort);
		ServerInfo.Builder node = ServerInfo.newBuilder();
		node.setIp(ip);
		node.setPort(port);
		otherServers.add(node.build());
		
		Client client = new Client();
		Channel channel = client.connect(ip, port);
		Attribute<String> ipPortAttr = channel.attr(ipPortKey);
		ipPortAttr.set(ipPort);
		clientMap.put(channel, client);
		
		if(serverInfo != null) {
			ClientConnServerInfo.Builder builder = ClientConnServerInfo.newBuilder();
			builder.setClientInfo(serverInfo);
			PBMessage message = new PBMessage((short) 1, builder.build());
			client.send(message);
		}
		
		System.out.println("connect to ip=" + ip + " port=" + port);
	}
	
	public void removeClient(Channel channel) {
		Client client = clientMap.remove(channel);
		if(client != null) {
			Attribute<String> ipPortAttr = channel.attr(ipPortKey);
			String ipPort = ipPortAttr.get();
			if(ipPort != null) {
				otherServerIpPort.remove(ipPort);
				String[] str = ipPort.split("#");
				Iterator<ServerInfo> iterator = otherServers.iterator();
				while(iterator.hasNext()) {
					ServerInfo info = iterator.next();
					if(info.getIp().equals(str[0]) && info.getPort() == Integer.parseInt(str[1])) {
						iterator.remove();
					}
				}
			}
			client.stop();
		}
	}

	public List<ServerInfo> getOtherServers() {
		return otherServers;
	}
}
