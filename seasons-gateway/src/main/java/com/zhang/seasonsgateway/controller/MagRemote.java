package com.zhang.seasonsgateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class MagRemote {

    @Autowired
    private WebClient.Builder clientBuilder;

    @GetMapping("/mag/auth/{param}")
    public Mono<Object> getMag(@PathVariable("param") String param) {
        return clientBuilder.build()
                .get()
                .uri("http://seasons-magazine/auth/" + param)
                .retrieve()
                .bodyToMono(Object.class);
    }

}
