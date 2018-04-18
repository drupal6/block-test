package com.gene.block;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlockServices {
	
	private static BlockServices instance = new BlockServices() ;
	
	public static BlockServices getIns() {
		return instance;
	}
	
	public BlockServices() {
		Block firstBlock = new Block();
		firstBlock.setIndex(0);
		firstBlock.setBpm(0);
		firstBlock.setCreateTime(new Date());
		firstBlock.setHash("");
		firstBlock.setPrevHash("");
		blocks.add(firstBlock);
	}
	
	private List<Block> blocks = new ArrayList<Block>();
	
	/**
	 * @param newBlocks
	 */
	public void replayceBlocks(List<Block> newBlocks) {
		if(newBlocks.size() > blocks.size()) {
			blocks = newBlocks;
		}
	}

	public Block addBlock(int bpm) {
		Block block = BlockUtil.generateBlock(blocks.get(blocks.size() - 1), bpm);
		if(false == BlockUtil.isBlockValid(block, blocks.get(blocks.size() - 1))) {
			return null;
		}
		blocks.add(block);
		return block;
	}
	
	public List<Block> getBlocks() {
		return blocks;
	}
}
