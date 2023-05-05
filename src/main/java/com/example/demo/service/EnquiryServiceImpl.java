package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.binding.DashboardResponse;
import com.example.demo.binding.EnquiryForm;
import com.example.demo.binding.EnquirySearchCriteria;
import com.example.demo.entity.CourseEntity;
import com.example.demo.entity.EnqStatusEntity;
import com.example.demo.entity.StudentEnqEntity;
import com.example.demo.entity.UserDtlsEntity;
import com.example.demo.repo.CourseRepo;
import com.example.demo.repo.EnqStatusRepo;
import com.example.demo.repo.StudentEnqRepo;
import com.example.demo.repo.UserDtlsRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {

//	private static final List<CourseEntity> CourseEntity = null;
	@Autowired
	private UserDtlsRepo userDtlsRepo;
	@Autowired
	private StudentEnqRepo studentEnqRepo;

	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private EnqStatusRepo enqStatusRepo;
	@Autowired
	private HttpSession httpSession;

	@Override
	public DashboardResponse getDashBoardResponse(Integer userId) {
		DashboardResponse dashboardResponse = new DashboardResponse();
		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		if (findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enqStatusEntity = userDtlsEntity.getEnquires();
			Integer total = enqStatusEntity.size();
			Integer lost = enqStatusEntity.stream().filter(x -> x.getEnqStatus().equals("Lost"))
					.collect(Collectors.toList()).size();
			Integer enrolled = enqStatusEntity.stream().filter(x -> x.getEnqStatus().equals("Enrolled"))
					.collect(Collectors.toList()).size();
//			private Integer totaltEnquiry;
//			private Integer enroller;
//			private Integer lost;

			dashboardResponse.setTotaltEnquiry(total);
			dashboardResponse.setLost(lost);
			dashboardResponse.setEnroller(enrolled);

		}

		return dashboardResponse;

	}

	public void addStudentEnquiry(EnquiryForm enquiryForm, Integer userId) {
		// TODO Auto-generated method stub
		StudentEnqEntity studentEnqEntity = new StudentEnqEntity();
		BeanUtils.copyProperties(enquiryForm, studentEnqEntity);
		UserDtlsEntity userDtlsEntity = userDtlsRepo.findById(userId).get();
		studentEnqEntity.setUser(userDtlsEntity);
		System.out.println(studentEnqEntity);
		studentEnqRepo.save(studentEnqEntity);
	}

	@Override
	public List<String> getCourses() {
		List<CourseEntity> courseEntity = courseRepo.findAll();
		List<String> listOfCourse = new ArrayList<String>();
		for (CourseEntity c : courseEntity) {
			listOfCourse.add(c.getCourse_name());
		}

		return listOfCourse;
	}

	@Override
	public List<String> getEnqStatus() {
		List<EnqStatusEntity> enqStatusEntity = enqStatusRepo.findAll();
		List<String> listOfEnquiry = new ArrayList<String>();
		for (EnqStatusEntity c : enqStatusEntity) {
			listOfEnquiry.add(c.getStatus_name());
		}

		return listOfEnquiry;
	}


	public List<StudentEnqEntity> getEnquires() {
		Integer userId = (Integer) httpSession.getAttribute("userId");
		List<StudentEnqEntity> enquires = userDtlsRepo.findById(userId).get().getEnquires();

		return enquires;
	}

	@Override
	public List<StudentEnqEntity> getFilteredEnquires(EnquirySearchCriteria enquirySearchCriteria,Integer userId) {
		List<StudentEnqEntity> enquires = userDtlsRepo.findById(userId).get().getEnquires();
		if(null!=enquirySearchCriteria.getCourse()&&""!=enquirySearchCriteria.getCourse()) {
			enquires=	enquires.stream().filter(x->x.getCourseName().equals(enquirySearchCriteria.getCourse())).collect(Collectors.toList());
		}
		if(null!=enquirySearchCriteria.getEnqStatusEntity()&&""!=enquirySearchCriteria.getEnqStatusEntity()) {
			enquires=	enquires.stream().filter(x->x.getEnqStatus().equals(enquirySearchCriteria.getEnqStatusEntity())).collect(Collectors.toList());
		}
		if(null!=enquirySearchCriteria.getClassroom()&&""!=enquirySearchCriteria.getClassroom()) {
			enquires=	enquires.stream().filter(x->x.getClassMode().equals(enquirySearchCriteria.getClassroom())).collect(Collectors.toList());
		}
		return enquires;
	}



}
