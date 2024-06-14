package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;

@Controller
public class RetirementController {

    @Autowired private EmployeeService employeeService;

    /*
     * 論理削除した社員の復帰のためのcontroler
     */
    @GetMapping("/retirement")
    public String displayRetirementList(Model model) {
        List<Employee> retiredEmployees = employeeService.searchRetiredEmployees();
        model.addAttribute("retiredEmployees", retiredEmployees);
        return "retirement";
    }
    @PostMapping("/unretire")
    public String unretireEmployee(@RequestParam("id")Long id) {
        employeeService.unretire(id); // 社員を復帰させる
        return "redirect:/home"; // ホーム画面にリダイレクト
    }
}
