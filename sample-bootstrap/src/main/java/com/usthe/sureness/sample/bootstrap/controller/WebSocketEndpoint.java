package com.usthe.sureness.sample.bootstrap.controller;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * webSocket示例
 * @author tomsun28
 * @date 22:21 2019-05-26
 */
@Component
@ServerEndpoint(value = "/webSocket/demo")
public class WebSocketEndpoint {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println(message);
    }

    @OnClose
    public void onClose() {

    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println(session);
    }

}
