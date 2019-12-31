package com.dm.code;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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
  * @ClassName:NettyClient 
  * @Description:TODO
  * @author dm
  * @date 2019/12/31
  * @slogan: 我自横刀向天笑，笑完我就去睡觉
  * @version V1.0
  */
public class NettyClient {

    public static void main(String[] args) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
//                            pipeline.addLast(new StringEncoder());
//                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new LongToByteEncoder());
                            pipeline.addLast(new ByteToLongDecoder());
                            pipeline.addLast(new NettyClientHandler());
                        }
                    });

            System.out.println("netty client start。。");
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9000).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }

    }
}
