package com.gene.p2p.net.code;

import com.gene.p2p.net.PBMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class MessageDecoder extends LengthFieldBasedFrameDecoder{

	public MessageDecoder() {
		super(PBMessage.DECODER_MAX_FRAME_LENGTH, PBMessage.DECODER_LENGTH_FIELD_OFFSET, 
				PBMessage.DECODER_LENGTH_FIELD_LENGTH, PBMessage.DECODER_LENGTH_ADJUSTMEN, 0);
	}

	@Override
	protected PBMessage decode(ChannelHandlerContext ctx, ByteBuf in2) throws Exception {
		ByteBuf in = (ByteBuf) super.decode(ctx, in2);
		if (in == null) {
		    return null;
		}
		if (in.readableBytes() < PBMessage.HDR_SIZE) {
			return null;
		}
		in.markReaderIndex();
		int headerFlag = in.readShort();
		if (PBMessage.HEADER != headerFlag) { 
			return null;
		}
		int len = in.readShort(); 
		if (len <= 0 || len >= PBMessage.PACKET_MAX_LEN) {
			return null;
		}
		if (in.readableBytes() < len - 4) {
			in.resetReaderIndex();
			return null;
		}
		PBMessage message = new PBMessage();
		message.readHeader(in);
		byte[] data = new byte[len - PBMessage.HDR_SIZE];
		in.readBytes(data);
		message.setBytes(data);
		in.release();
		return message;
	}
}
