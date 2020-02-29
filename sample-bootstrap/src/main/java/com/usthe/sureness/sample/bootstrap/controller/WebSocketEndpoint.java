package com.usthe.sureness.sample.bootstrap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * webSocket资源模拟示例,供websocket测试
 * @author tomsun28
 * @date 22:21 2019-05-26
 */
@Component
@ServerEndpoint(value = "/webSocket/demo")
public class WebSocketEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEndpoint.class);

    @OnOpen
    public void onOpen(Session session) {
        logger.info("webSocket: /webSocket/demo onOpen, session is : {} ", session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("webSocket: /webSocket/demo receive message: {}, the session is : {} ",
                message, session);
    }

    @OnClose
    public void onClose() {
        logger.info("webSocket: /webSocket/demo on Close");
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("webSocket: /webSocket/demo on Error, the session is {} ",
                session, error);
    }
}
