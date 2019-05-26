package com.usthe.sureness.sample.controller;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @author tomsun28
 * @date 22:21 2019-05-26
 */
@ServerEndpoint(value = "/webSocket/demo")
@Component
public class WebSocketEndpoint {

    @OnOpen
    public void onOpen(Session session) {
    }

    @OnMessage
    public void onMessage(String message, Session session) {

    }

    @OnClose
    public void onClose() {

    }

    @OnError
    public void onError() {

    }

}
