package com.gene.cmd.client;

import java.util.ArrayList;
import java.util.List;

import com.gene.block.Block;
import com.gene.block.BlockServices;
import com.gene.cmd.Cmd;
import com.gene.cmd.Command;
import com.gene.p2p.net.PBMessage;
import com.gene.proto.ServerProto.BlockMsg;
import com.gene.proto.ServerProto.SyncBlocks;

import io.netty.channel.Channel;

@Cmd(code = 101, desc = "同步block")
public class SyncBlockCmd implements Command {

	public void execute(Channel channel, PBMessage packet) throws Exception {
		SyncBlocks syncBlocks = SyncBlocks.parseFrom(packet.getBytes());
		System.out.println("syncblock:" + syncBlocks.toString());
		List<Block> blocks = new ArrayList<Block>();
		for(BlockMsg bm : syncBlocks.getBlocksList()) {
			blocks.add(new Block(bm));
		}
		BlockServices.getIns().replayceBlocks(blocks);
	}
}
