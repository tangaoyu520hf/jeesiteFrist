package com.thinkgem.jeesite.common.websocket;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class MyHandler extends TextWebSocketHandler {
    private static final Logger logger;
    private static final ConcurrentHashMap<String,WebSocketSession> connections;

    static {
        logger = Logger.getLogger(MyHandler.class);
        connections = new ConcurrentHashMap<>();
    }
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // ...
        try {
            String payload = message.getPayload();
            session.sendMessage(message);
            logger.info(payload);
            logger.info(connections);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接成功时候，会触发UI上onopen方法
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("connect to the websocket success......");
        Object currentUser = session.getAttributes().get("currentUser");
        if(connections.containsKey(currentUser)){
            WebSocketSession webSocketSession = connections.get(currentUser);
            if(webSocketSession.isOpen()){
                webSocketSession.close();
            }
        }
        connections.put(session.getAttributes().get("currentUser")+"",session);
        // 这块会实现自己业务，比如，当用户登录后，会把离线消息推送给用户
        TextMessage returnMessage = new TextMessage("你大爷");
        MyHandler.sendMessageToUsers(returnMessage);
    }
    /**
     * 用户关闭连接时候
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.info("websocket connection closed......");
        connections.remove(session);
    }
    /**
     * 给所有在线用户发送消息
     * @param message
     */
    public static void sendMessageToUsers(TextMessage message) {
        sendMessage(null,message);
    }

    private static void sendMessage(String userName, TextMessage message) {
        if(null==message)
            throw new  NullPointerException("The message can't be empty");
        logger.info(connections.size());
        logger.info(connections);
        Set<Map.Entry<String, WebSocketSession>> entries = connections.entrySet();
        for (Map.Entry<String, WebSocketSession> user : entries) {
            WebSocketSession session = user.getValue();
            try {
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 给某个用户发送消息
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        WebSocketSession session = connections.get(userName);
        if(null!=session){
            try {
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}