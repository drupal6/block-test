package com.gene;

import com.gene.cmd.CommandMgr;
import com.gene.p2p.net.client.ClientService;
import com.gene.p2p.net.server.ServerService;
import com.gene.proto.ServerProto.ServerInfo;
import com.gene.web.WebServer;

public class Main {

	public static void main(String[] args) {
		if(args.length < 2) {
			throw new RuntimeException("args is error.");
		}
		int webPort = Integer.parseInt(args[0]);
		String serverIp = args[1];
		int serverPort = Integer.parseInt(args[2]);
		String targetIp = null;
		int targetPort = 0;
		if(args.length > 3) {
			targetIp = args[3];
			targetPort = Integer.parseInt(args[4]);
		}
		
		CommandMgr.init(Main.class.getPackage());
		
		WebServer.getInstance().start(webPort);
		ServerService.getIns().initServer(serverIp, serverPort);
		if(targetPort > 0) {
			ServerInfo.Builder builder = ServerInfo.newBuilder();
			builder.setIp(serverIp);
			builder.setPort(6666);
			ClientService.getIns().connect(targetIp, targetPort, builder.build());
		}
	}
}
