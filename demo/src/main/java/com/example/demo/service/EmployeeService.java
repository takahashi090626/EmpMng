package com.example.demo.service;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeUpdateRequest;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {

    @Autowired private EmployeeRepository employeeRepository;

    /**
	 * 全検索
	 * @return
	 */
    public List<Employee> searchAll() {
        return employeeRepository.findByDeleteFlgFalse();
    }

    /**
	 * 社員主キー(id)検索
	 * @return 検索結果
	 */
    public Employee findById(Long id) {
        return employeeRepository
            .findById(id)
            .get();
    }

    /**
	 * 社員情報更新
	 * @param employee 社員更新
	 */
    public void update(EmployeeUpdateRequest employeeUpdateRequest) {
        Employee employee = findById(employeeUpdateRequest.getId());
        employee.setImage(employeeUpdateRequest.getImage());
        employee.setName(employeeUpdateRequest.getName());
        employee.setAddress(employeeUpdateRequest.getAddress());
        employee.setBackground(employeeUpdateRequest.getBackground());
        employee.setBirth_date(employeeUpdateRequest.getBirth_date());
        employee.setDivision(employeeUpdateRequest.getDivision());
        employee.setEmail(employeeUpdateRequest.getEmail());
        employee.setSex(employeeUpdateRequest.getSex());
        employee.setSkill(employeeUpdateRequest.getSkill());
        employee.setPhone(employeeUpdateRequest.getPhone());
        employee.setHira(employeeUpdateRequest.getHira());
        employee.setDivisionkana(employeeUpdateRequest.getDivisionkana());
        employee.setYubin(employeeUpdateRequest.getYubin());
        employee.setBanchi(employeeUpdateRequest.getBanchi());

        employeeRepository.save(employee);
    }

    /**
 * 社員　削除
 * @param id 社員ID
 */
@Transactional
public void softDelete(Long id) {
    Employee employee = findById(id);
    if (employee != null) {
        employee.setDeleteFlg(true);
        employeeRepository.save(employee); // 削除フラグの更新をデータベースに永続化
    }
}

    /**
	 * 社員新規登録
	 * @param employee 社員情報
	 */

    public void create(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        employee.setImage(employeeRequest.getImage());
        employee.setName(employeeRequest.getName());
        employee.setSex(employeeRequest.getSex());
        employee.setDivision(employeeRequest.getDivision());
        employee.setBirth_date(employeeRequest.getBirth_date());
        employee.setEmail(employeeRequest.getEmail());
        employee.setPhone(employeeRequest.getPhone());
        employee.setAddress(employeeRequest.getAddress());
        employee.setBackground(employeeRequest.getBackground());
        employee.setSkill(employeeRequest.getSkill());
        employee.setHira(employeeRequest.getHira());
        employee.setDivisionkana(employeeRequest.getDivisionkana());
        employee.setYubin(employeeRequest.getYubin());
        employee.setBanchi(employeeRequest.getBanchi());

        employeeRepository.save(employee);
    }

    /**
	 * 社員検索
	 */

    public Employee findById2(Long id) {
        return employeeRepository
            .findById(id)
            .orElse(null);
    }

    public Optional<Employee> findByName(String name) {
        return employeeRepository.findByName(name);
    }
    /**
     * ページング機能
     */
    public Page<Employee> findPaginated(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

	public List<Employee> searchRetiredEmployees() {
        return employeeRepository.findByDeleteFlgTrue();
    }

    //delete_flg 論理削除のため
	public void unretire(Long id) {
		Employee employee = findById(id);
		if (employee != null) {
			employee.setDeleteFlg(false); // delete_flg を 0 に設定
			employeeRepository.save(employee); // データベースに保存
		}
	}

    public List<Employee> findByKeyword(String keyword) {
        return employeeRepository.findByNameContainingIgnoreCase(keyword);
    }
    public List<Employee> findByNameContaining(String keyword) {
        return employeeRepository.findByNameContaining(keyword);
    }

}
