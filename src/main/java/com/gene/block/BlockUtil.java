package com.gene.block;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class BlockUtil {
	
	/**
	 * �����µ�block
	 * @param oldBlock
	 * @param bpm
	 * @return
	 */
	public static Block generateBlock(Block oldBlock, int bpm) {
		Block newBlock = new Block();
		newBlock.setIndex(oldBlock.getIndex() + 1);
		newBlock.setCreateTime(new Date());
		newBlock.setBpm(bpm);
		newBlock.setPrevHash(oldBlock.getHash());
		newBlock.setHash(calculateHash(newBlock));
		return newBlock;
	}
	
	/**
	 * ��֤Block
	 * @param newBlock
	 * @param oldBlock
	 * @return
	 */
	public static boolean isBlockValid(Block newBlock, Block oldBlock) {
		if(newBlock.getIndex() != oldBlock.getIndex() + 1) {
			return false;
		}
		if(false == newBlock.getPrevHash().equals(oldBlock.getHash())) {
			return false;
		}
		if(false == newBlock.getHash().equals(calculateHash(newBlock))) {
			return false;
		}
		return true;
	}
	
	/**
	 * ����block hashֵ
	 * @param block
	 * @return
	 */
	public static String calculateHash(Block block) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(block.getIndex())
		.append(block.getCreateTime())
		.append(block.getBpm())
		.append(block.getPrevHash());
		return getSHA256Str(buffer.toString());
	}
	
	/**
     *  SHA256����
     * @param str ���ܺ�ı���
     * @return
     */
    public static String getSHA256Str(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * ��byteתΪ16����
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1�õ�һλ�Ľ��в�0����
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
