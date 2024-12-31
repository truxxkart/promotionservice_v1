package com.truxxkart.promotionservice_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truxxkart.promotionservice_v1.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
