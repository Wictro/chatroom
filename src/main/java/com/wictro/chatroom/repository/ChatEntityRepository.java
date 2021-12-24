package com.wictro.chatroom.repository;

import com.wictro.chatroom.model.ChatEntity;
import org.springframework.data.repository.CrudRepository;

public interface ChatEntityRepository extends CrudRepository<ChatEntity, Long> {

}
