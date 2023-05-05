package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.binding.DashboardResponse;
import com.example.demo.binding.EnquiryForm;
import com.example.demo.binding.EnquirySearchCriteria;
import com.example.demo.entity.StudentEnqEntity;
import com.example.demo.service.EnquiryServiceImpl;

@Controller
public class EnquiryController {
	@Autowired
	private HttpSession httpSession;
	@Autowired
	private EnquiryServiceImpl enquiryServiceImpl;

	@GetMapping("/dashboard")
	public String getDashBoard(Model model) {
		Integer userId = (Integer) httpSession.getAttribute("userId");
		DashboardResponse dashboardResponse = enquiryServiceImpl.getDashBoardResponse(userId);

		model.addAttribute("dashboardResponse", dashboardResponse);
		return "dashboard";
	}

	@GetMapping("/logout")
	public String logout() {
		httpSession.invalidate();
		return "index";
	}

	@GetMapping("/viewEnquiry")
	public String viewEnquiry() {
		httpSession.invalidate();
		return "view-enquiries";
	}

	@GetMapping("/addEnquiry")
	public String addEnquiry(Model model) {

		EnquiryForm enquiryForm = new EnquiryForm();
		model.addAttribute("courses", enquiryServiceImpl.getCourses());
		model.addAttribute("enquires", enquiryServiceImpl.getEnqStatus());
		model.addAttribute("enquiryForm", enquiryForm);

		return "add-enquiry";
	}

	@PostMapping("saveEnquiry")
	public String saveEnquiry(@ModelAttribute EnquiryForm enquiryForm) {
		Integer userId = (Integer) httpSession.getAttribute("userId");
		System.out.println(enquiryForm);
		enquiryServiceImpl.addStudentEnquiry(enquiryForm, userId);
		System.out.println(enquiryForm);

		return "view-enquiries";
	}
	@GetMapping("/viewEnquiry")
	public String viewEnquiry(Model model) {

		EnquirySearchCriteria enquirySearchCriteria = new EnquirySearchCriteria();
		model.addAttribute("courses", enquiryServiceImpl.getCourses());
		model.addAttribute("enquires", enquiryServiceImpl.getEnqStatus());
		model.addAttribute("enquirySearchCriteria", enquirySearchCriteria);
		List<StudentEnqEntity> enquiresData =enquiryServiceImpl.getEnquires();
		model.addAttribute("allenquiresData", enquiresData);
		return "view-enquiries";
	}
	@GetMapping("getFilterData")
	@ResponseBody
	public String getFilterEnquiry(@RequestParam String cname,@RequestParam String ename,@RequestParam String classMode,Model model) {

		EnquirySearchCriteria enquirySearchCriteria = new EnquirySearchCriteria();
		enquirySearchCriteria.setClassroom(classMode);
		enquirySearchCriteria.setCourse(cname);
		enquirySearchCriteria.setEnqStatusEntity(ename);
		model.addAttribute("courses", enquiryServiceImpl.getCourses());
		model.addAttribute("enquires", enquiryServiceImpl.getEnqStatus());
		model.addAttribute("enquirySearchCriteria", enquirySearchCriteria);
		Integer userId = (Integer) httpSession.getAttribute("userId");
		List<StudentEnqEntity> enquiresData =enquiryServiceImpl.getFilteredEnquires(enquirySearchCriteria, userId);
		model.addAttribute("filteredenquiresData", enquiresData);
		return "filteredtable";
	}

}
