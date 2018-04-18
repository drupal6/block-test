package com.gene.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.gene.block.Block;
import com.gene.block.BlockServices;

public class GetBlockServlet extends AbstractServlet{
	
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Block> blocks = BlockServices.getIns().getBlocks();
		JSONArray blockArray = (JSONArray) JSONArray.toJSON(blocks);
		response(response, blockArray.toString());
	}
}
