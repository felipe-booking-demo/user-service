package io.felipe.bookingdemo.user_service.repository;

import io.felipe.bookingdemo.user_service.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
