package com.gene.cmd.client;

import com.gene.cmd.Cmd;
import com.gene.cmd.Command;
import com.gene.p2p.net.PBMessage;
import com.gene.p2p.net.client.ClientService;
import com.gene.proto.ServerProto.OtherServerInfos;
import com.gene.proto.ServerProto.ServerInfo;

import io.netty.channel.Channel;

@Cmd(code = 100, desc = "客户端连接返回")
public class ClientConnectCmd implements Command {

	public void execute(Channel channel, PBMessage packet) throws Exception {
		OtherServerInfos param = OtherServerInfos.parseFrom(packet.getBytes());
		for(ServerInfo node : param.getServersList()) { 
			ClientService.getIns().connect(node.getIp(), node.getPort(), null);
		}
	}
}
