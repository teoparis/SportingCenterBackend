package com.sportingCenterBackEnd.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sportingCenterBackEnd.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	boolean existsByEmail(String email);

	@Query("select u from User u inner join u.roles r where r.roleId = :role")
	List<User> findUsersByRole(Long role);

}
