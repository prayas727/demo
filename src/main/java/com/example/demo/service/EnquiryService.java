package com.example.demo.service;

import java.util.List;

import com.example.demo.binding.DashboardResponse;
import com.example.demo.binding.EnquiryForm;
import com.example.demo.binding.EnquirySearchCriteria;
import com.example.demo.entity.StudentEnqEntity;

public interface EnquiryService {

	public DashboardResponse getDashBoardResponse(Integer userId);

	public List<String> getCourses();

	public List<String> getEnqStatus();

	public void addStudentEnquiry(EnquiryForm enquiryForm, Integer userId);
	public List<StudentEnqEntity> getEnquires();
	public List<StudentEnqEntity> getFilteredEnquires(EnquirySearchCriteria enquirySearchCriteria,Integer userId);

}
