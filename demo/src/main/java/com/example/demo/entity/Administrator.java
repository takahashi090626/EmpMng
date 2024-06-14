package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="administrator")
@Data

public class Administrator {
	
	@Id
	@Column(name="email")
	private String email;
	private String password;

}
