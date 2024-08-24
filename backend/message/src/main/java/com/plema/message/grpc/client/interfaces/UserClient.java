package com.plema.message.grpc.client.interfaces;

import java.util.List;
import java.util.UUID;

import com.plema.message.dtos.FullUserInfo;

public interface UserClient {

    FullUserInfo getUserById(UUID id);

    List<FullUserInfo> getUsersByIds(List<UUID> ids);

}
