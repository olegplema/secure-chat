package com.plema.message.dtos;

import lombok.Data;

@Data
public class FullUserInfo {
    private String id;
    private String pubKey;
    private String username;
    private String email;
}
