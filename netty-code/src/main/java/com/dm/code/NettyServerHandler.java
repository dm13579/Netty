package com.dm.code;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
  *                  ,;,,;
  *                ,;;'(    
  *      __      ,;;' ' \   
  *   /'  '\'~~'~' \ /'\.)  
  * ,;(      )    /  |.     
  *,;' \    /-.,,(   ) \    
  *     ) /       ) / )|    
  *     ||        ||  \)     
  *    (_\       (_\
  * @ClassName:NettyServerHandler 
  * @Description:TODO
  * @author dm
  * @date 2019/12/31
  * @slogan: 我自横刀向天笑，笑完我就去睡觉
  * @version V1.0
  */
public class NettyServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //System.out.println("从客户端读取到String：" + msg.toString());
//        System.out.println("从客户端读取到Object：" + ((User)msg).toString());
        System.out.println("从客户端读取到Long：" + (Long)msg);
        //给客户端发回一个long数据
        ctx.writeAndFlush(2000L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
