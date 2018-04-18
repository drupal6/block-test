package com.gene.p2p.net.client;


import com.gene.cmd.Command;
import com.gene.cmd.CommandMgr;
import com.gene.p2p.net.PBMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

	
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		ClientService.getIns().removeClient(ctx.channel());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		PBMessage message = (PBMessage) msg;
		Command cmd = CommandMgr.getCommand(message.getCode());
		if(cmd != null) {
			cmd.execute(ctx.channel(), message);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	}
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
	}

}
