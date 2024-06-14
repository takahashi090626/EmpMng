package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Administrator;
import java.util.List;



@Repository
public interface UserInfoRepository extends JpaRepository<Administrator,String>{
	Administrator findByEmail(String email);

}
