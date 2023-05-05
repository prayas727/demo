package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity,Integer>{
	@Query("SELECT DISTINCT (course_name) FROM CourseEntity c")
	public List<String> getCourses();

}
