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
  * @ClassName:test
  * @Description:TODO
  * @author dm
  * @date 2019/12/31
  * @slogan: 我自横刀向天笑，笑完我就去睡觉
  * @version V1.0
  */
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value="/websocket")
public class Test {

    //连接时执行
    @OnOpen
    public void onOpen(Session session) throws IOException{
        System.out.println("新连接");
    }

    //关闭时执行
    @OnClose
    public void onClose(){
        System.out.println("连接关闭");
    }

    //收到消息时执行
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("收到的消息:"+message);
        session.getBasicRemote().sendText("收到的消息"+message); //回复用户
    }

    //连接错误时执行
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("连接发送错误");
        error.printStackTrace();
    }

}
