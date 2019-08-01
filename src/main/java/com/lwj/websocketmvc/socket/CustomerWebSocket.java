package com.lwj.websocketmvc.socket;

import com.lwj.websocketmvc.socket.enumerate.WebSocketCode;
import com.lwj.websocketmvc.socket.util.WebsocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint(value = "/websocket")
@Component
@Slf4j
public class CustomerWebSocket {
    /**
     *  静态变量，用来记录当前在线连接数，把它设计成线程安全的。
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    /**
     *  concurrent包的线程安全Map，用来存放每个客户端对应的WebSocket对象。
     */
    public static ConcurrentHashMap<String, CustomerWebSocket> userSocketMap = new ConcurrentHashMap<>();

    /**
     *  与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        addOnlineCount(); //在线数加
        try {
            sendMessage(WebSocketCode.connectSuccess());
        } catch (IOException e) {
            log.info("IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从map中删除
        userSocketMap.remove(this);
        //在线数减1
        subOnlineCount();
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        //消息分发到指定控制器
        DispatcherSocketRequest.dispatcher(message,this);
    }

    /**
     * 发生错误时调用
     */
    public void onError(Session session, Throwable error) {
        log.info("发生错误");
        error.printStackTrace();
    }

    /**
     * 发送消息
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message+":"+ WebsocketUtil.getRemoteAddress(session));
    }


    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {
//        for (MyWebSocket item : webSocketSet) {
//            try {
//                item.sendMessage(message);
//            } catch (IOException e) {
//                continue;
//            }
//        }
    }

    /**
     * 获取在线人数
     */
    public static synchronized int getOnlineCount() {
        return onlineCount.get();
    }

    /**
     * 加入新用户
     */
    public static synchronized void addOnlineCount() {
        CustomerWebSocket.onlineCount.incrementAndGet();
    }

    /**
     * 用户离线
     */
    public static synchronized void subOnlineCount() {
        CustomerWebSocket.onlineCount.decrementAndGet();
    }
}