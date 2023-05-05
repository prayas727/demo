package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseEntity {
	@Id
	@GeneratedValue
	private Integer course_id;
	private String course_name; 

//4 :   AIT_ENQURIRY_STATUS
//
//	STATUS_ID			INTEGER		PK  AUTO_INCREMENT
//	STATUS_NAME			VARCHAR		

}
