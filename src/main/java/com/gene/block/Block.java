package com.gene.block;

import java.util.Date;

import com.gene.proto.ServerProto.BlockMsg;

public class Block {
	
	private int index;
	private Date createTime;
	private int bpm;
	private String hash;
	private String prevHash;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getBpm() {
		return bpm;
	}
	public void setBpm(int bpm) {
		this.bpm = bpm;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getPrevHash() {
		return prevHash;
	}
	public void setPrevHash(String prevHash) {
		this.prevHash = prevHash;
	}
	
	public Block() {
	}
	
	public Block(BlockMsg msg) {
		this.index = msg.getIndex();
		this.createTime = new Date(msg.getTime());
		this.bpm = msg.getBpm();
		this.hash = msg.getHash();
		this.prevHash = msg.getPrevHash();
	}
	
	public BlockMsg toMsg() {
		BlockMsg.Builder builder = BlockMsg.newBuilder();
		builder.setIndex(index);
		builder.setTime(createTime.getTime());
		builder.setBpm(bpm);
		builder.setHash(hash);
		builder.setPrevHash(prevHash);
		return builder.build();
	}
}
