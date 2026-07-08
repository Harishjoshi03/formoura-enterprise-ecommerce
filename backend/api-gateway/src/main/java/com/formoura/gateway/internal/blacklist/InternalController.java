package com.formoura.gateway.internal.blacklist;

import com.formoura.gateway.service.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
public class InternalController {

    private final TokenBlacklistService service;

    @PostMapping("/blacklist")
    public Mono<Void> blacklist(

            @RequestBody LogoutRequest request){

        return service

                .blacklist(
                        request.getToken(),
                        request.getExpiry()
                )

                .then();

    }

}