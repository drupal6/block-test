package com.gene.cmd.server;

import java.util.List;

import com.gene.cmd.Cmd;
import com.gene.cmd.Command;
import com.gene.p2p.net.PBMessage;
import com.gene.p2p.net.client.ClientService;
import com.gene.proto.ServerProto.ClientConnServerInfo;
import com.gene.proto.ServerProto.OtherServerInfos;
import com.gene.proto.ServerProto.ServerInfo;

import io.netty.channel.Channel;

@Cmd(code = 1, desc = "客户端连接")
public class ClientConnectCmd implements Command {

	public void execute(Channel channel, PBMessage packet) throws Exception {
		ClientConnServerInfo param = ClientConnServerInfo.parseFrom(packet.getBytes());
		List<ServerInfo> nodes = ClientService.getIns().getOtherServers();
		OtherServerInfos.Builder builder = OtherServerInfos.newBuilder();
		builder.addAllServers(nodes);
		PBMessage message = new PBMessage((short) 100, builder.build());
		channel.writeAndFlush(message);
		ClientService.getIns().connect(param.getClientInfo().getIp(), param.getClientInfo().getPort(), null);
	}
}
