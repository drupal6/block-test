package com.gene.cmd;

import com.gene.p2p.net.PBMessage;

import io.netty.channel.Channel;

public interface Command {

	public abstract void execute(Channel channel, PBMessage packet) throws Exception;

}
