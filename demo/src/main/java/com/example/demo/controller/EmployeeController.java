package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Employee;
// あなたの Employee クラスのパッケージをインポートします
import com.example.demo.repository.EmployeeRepository;
// あなたの EmployeeRepository クラスのパッケージをインポートします


/*
 * csvエクスポートのためのcontroller
 */
@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired private EmployeeRepository employeeRepository; // EmployeeRepository をインジェクトします

    @GetMapping("/allEmployees")
    public List<Employee> getAllEmployees() {
        // すべての従業員データを取得します。
        return employeeRepository.findAll();
    }
}
