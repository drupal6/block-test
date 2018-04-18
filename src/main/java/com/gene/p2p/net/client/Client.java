package com.gene.p2p.net.client;

import com.gene.p2p.net.PBMessage;
import com.gene.proto.ServerProto.ServerInfo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

	private EventLoopGroup group;
	private Bootstrap bootstrap;
	private Channel channel;

	public Client() {
		group = new NioEventLoopGroup();
		bootstrap = new Bootstrap();
		bootstrap.group(group);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.handler(new ClientInitializer());
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000);
	}

	public Channel connect(String ip, int port) {
		try {
			ChannelFuture future = bootstrap.connect(ip, port).sync();
			channel = future.channel();
			return channel;
		} catch (Exception e) {
			return null;
		}
	}
	
	public void send(PBMessage message) {
		channel.writeAndFlush(message);
	}
	
	public void stop() {
		group.shutdownGracefully();
	}
}
