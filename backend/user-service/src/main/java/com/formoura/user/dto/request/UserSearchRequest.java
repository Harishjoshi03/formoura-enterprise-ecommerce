package com.formoura.user.dto.request;

import lombok.Data;

@Data
public class UserSearchRequest {

    private String keyword;

    private String city;

    private String state;

    private Boolean enabled;

    private Integer page = 0;

    private Integer size = 10;

}