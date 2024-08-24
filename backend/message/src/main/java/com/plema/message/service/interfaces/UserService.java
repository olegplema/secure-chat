package com.plema.message.service.interfaces;

import java.util.List;
import java.util.UUID;

import com.plema.message.dtos.FullUserInfo;

public interface UserService {

    void createIfNotExists(UUID userId);
    
    List<FullUserInfo> getUsersByIds(List<UUID> ids);
}
