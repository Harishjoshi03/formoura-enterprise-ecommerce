package com.formoura.gateway.internal.blacklist;

import lombok.Data;

@Data
public class LogoutRequest {

    private String token;

    private long expiry;

}
