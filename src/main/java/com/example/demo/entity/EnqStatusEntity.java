package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity

public class EnqStatusEntity {
	@Id
	@GeneratedValue
	private Integer status;
	private String status_name;

}
