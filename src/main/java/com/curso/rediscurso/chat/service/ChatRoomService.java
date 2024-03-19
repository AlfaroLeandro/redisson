package com.curso.rediscurso.chat.service;

import org.redisson.api.RListReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service
public class ChatRoomService implements WebSocketHandler {

    @Autowired
    private RedissonReactiveClient client;

    @Override
    public Mono<Void> handle(WebSocketSession session) {

        //contains url
        String room = getChatRoomName(session);
        var topic = this.client.getTopic(room, StringCodec.INSTANCE);
        RListReactive<String> list = this.client.getList("history:" + room, StringCodec.INSTANCE);

        //subscribe -> escucha el topic
        session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                        .flatMap(msg -> list.add(msg).then(topic.publish(msg)))
                        .doOnError(System.out::println)
                        .doFinally(s -> System.out.println("Susbscriber finally " + s))
                        .subscribe();


        // publisher
        var flux = topic.getMessages(String.class)
                        .startWith(list.iterator())
                        .map(session::textMessage)
                        .doOnError(System.out::println)
                        .doFinally(s -> System.out.println("publisher finally " + s));
        return session.send(flux);
    }

    private String getChatRoomName(WebSocketSession socketSession){
        var uri = socketSession.getHandshakeInfo().getUri();
        return UriComponentsBuilder.fromUri(uri)
                .build()
                .getQueryParams()
                .toSingleValueMap()
                .getOrDefault("room", "default");
    }
}
