package com.example.demo.Runners;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.entity.CourseEntity;
import com.example.demo.entity.EnqStatusEntity;
import com.example.demo.repo.CourseRepo;
import com.example.demo.repo.EnqStatusRepo;

@Component
public class UserRunner implements CommandLineRunner {
	@Autowired
	private CourseRepo cr;
	@Autowired
	private EnqStatusRepo enq;

	@Override
	public void run(String... args) throws Exception {
		cr.deleteAll();
		CourseEntity ce1=new CourseEntity();
		ce1.setCourse_name("Java Fullstack");
		CourseEntity ce2=new CourseEntity();
		ce2.setCourse_name("AWS");
		CourseEntity ce3=new CourseEntity();
		ce3.setCourse_name("DevOops");
		List<CourseEntity> courseEntityList=Arrays.asList(ce1,ce2,ce3);
		
		cr.saveAll(courseEntityList);
		enq.deleteAll();
		EnqStatusEntity es1=new EnqStatusEntity();
		es1.setStatus_name("Entrolled");
		EnqStatusEntity es2=new EnqStatusEntity();
		es2.setStatus_name("New");
		EnqStatusEntity es3=new EnqStatusEntity();
		es3.setStatus_name("Lost");
		List<EnqStatusEntity> EnqStatusEntityList=Arrays.asList(es1,es2,es3);
		
		enq.saveAll(EnqStatusEntityList);
		

	}

}
