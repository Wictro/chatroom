package com.wictro.chatroom.repository;

import com.wictro.chatroom.model.SessionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SessionEntityRepository extends CrudRepository<SessionEntity, UUID> {

}
