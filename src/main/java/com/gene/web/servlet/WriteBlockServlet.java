package com.gene.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.gene.block.Block;
import com.gene.block.BlockServices;
import com.gene.p2p.net.PBMessage;
import com.gene.p2p.net.server.ServerService;
import com.gene.proto.ServerProto.SyncBlocks;

public class WriteBlockServlet extends AbstractServlet{
	
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bpm = Integer.parseInt(request.getParameter("bpm"));
		Block block = BlockServices.getIns().addBlock(bpm);
		if(block == null) {
			response(response, "add block error");
		} else {
			SyncBlocks.Builder builder = SyncBlocks.newBuilder();
			List<Block> blocks = BlockServices.getIns().getBlocks();
			for(Block b : blocks) {
				builder.addBlocks(b.toMsg());
			}
			PBMessage message = new PBMessage((short) 101, builder.build());
			ServerService.getIns().broad(message);
			
			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(block);
			response(response, jsonObject.toString());
		}
	}
}
