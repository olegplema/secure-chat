package com.plema.message.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plema.message.dtos.FullUserInfo;
import com.plema.message.entities.User;
import com.plema.message.grpc.client.interfaces.UserClient;
import com.plema.message.repositories.UserRepository;
import com.plema.message.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createIfNotExists(UUID userId) {
        Optional<User> oprionalUser = userRepository.findById(userId);

        if (oprionalUser.isPresent()) {
            return;
        }

        FullUserInfo user = userClient.getUserById(userId);

        UUID id = UUID.fromString(user.getId());

        userRepository.save(User.builder().id(id).build());
    }

	@Override
	public List<FullUserInfo> getUsersByIds(List<UUID> ids) {
        return userClient.getUsersByIds(ids);
	}


}
