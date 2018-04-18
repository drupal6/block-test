package com.gene.p2p.net.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {
	
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private int port;
	private String add = "127.0.0.1";

	public boolean start(String ip, int port) {
		this.port = port;
		this.add = ip;
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
		bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.childHandler(new ServerInitializer());
		try {
			bootstrap.bind(port).sync();
			System.out.println("start port=" + port);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
//			bossGroup.shutdownGracefully();
//			workerGroup.shutdownGracefully();
		}
	}

	public int getPort() {
		return port;
	}

	public String getAdd() {
		return add;
	}
}
