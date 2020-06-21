package com.dm.danmu;

import com.dm.WebsocketDanmuServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;

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
 * @date 2019/06/21
 * @slogan: 我自横刀向天笑，笑完我就去睡觉
 * @version V1.0
 */
public class WebsocketDanmuServer {

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new WebsocketDanmuServer(port).run();
    }

    private int port;

    public WebsocketDanmuServer(int port){
        this.port = port;
    }
    public void run() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HttpRequestDecoder()); // 解码

                            pipeline.addLast(new HttpResponseEncoder()); // 编码
                            pipeline.addLast(new HttpObjectAggregator(65536)); // Http请求头请求体合并，合并大小最大64kb
                            pipeline.addLast(new ChunkedWriteHandler());
                            pipeline.addLast(new HttpRequestHandler("/ws"));
                            pipeline.addLast(new WebSocketServerProtocolHandler("/ws")); // websocket协议的编解码
                            pipeline.addLast(new TextWebSocketFrameServerHandler());
                        }
                    });


            System.out.println("danmu server start");
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            System.out.println("danmu server close");
        }
    }
}
