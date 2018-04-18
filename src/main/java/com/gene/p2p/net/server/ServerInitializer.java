package com.gene.p2p.net.server;

import com.gene.p2p.net.code.MessageDecoder;
import com.gene.p2p.net.code.MessageEncoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		// 2分钟客户端没有读写操作就断开连接
//		pipeline.addLast("idleStateHandler", new IdleStateHandler(0, 0, 3, TimeUnit.MINUTES));
		pipeline.addLast("decoder", new MessageDecoder());
		pipeline.addLast("encoder", new MessageEncoder());
		pipeline.addLast("handler", new ServerHandler());
	}

}
