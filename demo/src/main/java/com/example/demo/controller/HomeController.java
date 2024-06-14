package com.example.demo.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeUpdateRequest;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;

import jakarta.transaction.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;

@Controller
public class HomeController {
    // @GetMapping("/home") public String view() { return "home"; }

    /**
     * 社員情報
     */
    @Autowired
    private EmployeeService employeeService;

    /**
     * 社員情報一覧を表示
     * 
     * @param model Model
     * @return 社員情報一覧画面
     */
    @GetMapping(value = "/home")
    public String displayList(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "id") String sortField) {
        int pageSize = 10;
        Pageable pageable;
        Sort.Direction direction = sort.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        switch (sortField) {
            case "id":
                pageable = PageRequest.of(page, pageSize, Sort.by(direction, "id"));
                break;
            case "hira":
                pageable = PageRequest.of(page, pageSize, Sort.by(direction, "hira"));
                break;
            case "divisionkana":
                pageable = PageRequest.of(page, pageSize, Sort.by(direction, "divisionkana"));
                break;

            default:
                pageable = PageRequest.of(page, pageSize, Sort.by(direction, sortField));
                break;
        }

        Page<Employee> paginatedEmployees = employeeService.findPaginated(pageable);
        List<Employee> employees = paginatedEmployees.getContent().stream()
                .filter(employee -> !employee.getDeleteFlg())
                .collect(Collectors.toList());

        model.addAttribute("emplist", employees);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", paginatedEmployees.getTotalPages());
        model.addAttribute("sort", sort);
        model.addAttribute("sortField", sortField);
        return "home";
    }

    /**
     * 社員詳細画面を表示
     * 
     * @param id表示する社員ID
     * @param model      Model
     * @return 社員詳細画面
     */

    @GetMapping("/{id}")
    public String displayView(@PathVariable Long id, Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "asc") String sort) {
        int pageSize = 100;
        Pageable pageable;
        if (sort.equals("asc")) {
            pageable = PageRequest.of(page, pageSize);
        } else {
            pageable = PageRequest.of(page, pageSize);
        }
        Employee employee = employeeService.findById(id);
        Page<Employee> paginatedEmployees = employeeService.findPaginated(pageable);
        model.addAttribute("emplist", paginatedEmployees.getContent());

        model.addAttribute("empData", employee);
        Instant instant = employee.getBirth_date().toInstant();
        LocalDate birthDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        Period period = Period.between(birthDate, now);
        int age = period.getYears();
        model.addAttribute("age", age);
        return "view";
    }

    /*
     * 社員編集画面を表示
     * 
     * @param id表示する社員ID
     * 
     * @Param Model model
     * 
     * @return 社員編集画面
     */
    @GetMapping("/{id}/edit")
    public String displayEdit(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id);
        EmployeeUpdateRequest employeeUpdateRequest = new EmployeeUpdateRequest();
        employeeUpdateRequest.setId(employee.getId());
        employeeUpdateRequest.setImage(employee.getImage());
        employeeUpdateRequest.setName(employee.getName());
        employeeUpdateRequest.setSex(employee.getSex());
        employeeUpdateRequest.setDivision(employee.getDivision());
        employeeUpdateRequest.setBirth_date(employee.getBirth_date());
        employeeUpdateRequest.setEmail(employee.getEmail());
        employeeUpdateRequest.setPhone(employee.getPhone());
        employeeUpdateRequest.setEmail(employee.getEmail());
        employeeUpdateRequest.setAddress(employee.getAddress());
        employeeUpdateRequest.setBackground(employee.getBackground());
        employeeUpdateRequest.setSkill(employee.getSkill());
        employeeUpdateRequest.setHira(employee.getHira());
        employeeUpdateRequest.setDivisionkana(employee.getDivisionkana());
        employeeUpdateRequest.setBanchi(employee.getBanchi());
        employeeUpdateRequest.setYubin(employee.getYubin());

        model.addAttribute("employeeUpdateRequest", employeeUpdateRequest);
        return "edit";
    }

    /**
     * 社員更新
     * 
     * @param employeeRequest リクエストデータ
     * @param model           Model
     * @return 社員情報詳細画面
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            @Validated @ModelAttribute EmployeeUpdateRequest employeeUpdateRequest,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("employeeUpdateRequest", employeeUpdateRequest);
            return "edit";
        }
        // ユーザー情報の更新
        employeeService.update(employeeUpdateRequest);
        return "redirect:/" + employeeUpdateRequest.getId(); // リダイレクト先を修正
    }

    /**
     * 社員削除
     * 
     * @param 表示する社員ID
     * @param model    Model
     * @return 社員情報詳細画面
     */

    @GetMapping("/{id}/soft-delete")
    public String softDelete(@PathVariable Long id, Model model) {
        employeeService.softDelete(id);
        return "redirect:/home";
    }

    /**
     * 社員新規登録画面を表示
     * 
     * @param model Model
     * @return ユーザー情報一覧画面
     */
    @GetMapping(value = "/add")
    public String displayAdd(Model model) {
        model.addAttribute("employeeRequest", new EmployeeRequest());
        return "add";
    }

    /**
     * 社員新規登録
     * 
     * @param userRequest リク エストデータ
     * @param model       Model
     * @return ユーザー情報一覧画面
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(
            @Validated @ModelAttribute EmployeeRequest employeeRequest,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "add";
        }
        employeeService.create(employeeRequest);
        return "redirect:/home";
    }

    @GetMapping("/result")
    public String searchEmployeeById(@RequestParam("id") Long id, Model model) {
        Employee employee = employeeService.findById2(id);
        if (employee != null) {
            model.addAttribute("employee", employee);
            return "result";
        } else {
            model.addAttribute("errorMessage", "Employee not found");
            return "error";
        }
    }

    
    @GetMapping("/resultName")
    public String searchEmployeeByName(
            @RequestParam("keyword") String keyword,
            Model model) {
        List<Employee> employees = employeeService.findByNameContaining(keyword);
        if (!employees.isEmpty()) {
            // ここでは最初の結果のみを取得していますが、必要に応じて処理を変更できます
            Employee employee = employees.get(0);
            model.addAttribute("employee", employee);
            return "resultname"; // テンプレート名に注意
        } else {
            model.addAttribute("errorMessage", "Employee not found");
            return "resultname"; // エラーの場合も同じテンプレートを返す
        }
    }

    @Autowired
    private JdbcTemplate jdbcTemplate; // JdbcTemplateを追加

    /**
     * 円グラフ
     * 
     * @return data
     */
    @GetMapping("/data")
    public String showData(Model model) {
        // データベースから性別とその数を取得するクエリを実行
        List<Map<String, Object>> genderData = jdbcTemplate.queryForList(
                "SELECT sex, COUNT(*) AS count FROM employee WHERE delete_flg=0 GROUP BY sex");
        model.addAttribute("genderData", genderData);

        // 年代別のデータを取得するクエリを実行
        List<Map<String, Object>> ageDataList = jdbcTemplate.queryForList(
                "SELECT CASE WHEN TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 19 AND 29 THEN '20代' " +
                        "WHEN TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 30 AND 39 THEN '30代' " +
                        "WHEN TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 40 AND 49 THEN '40代' " +
                        "WHEN TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) >= 50 THEN '50代以上' END AS age_group, " +
                        "COUNT(*) AS count FROM employee WHERE delete_flg=0 GROUP BY age_group " +
                        "ORDER BY FIELD(age_group, '20代', '30代', '40代', '50代以上')");

        // モデルに年代別データを設定
        model.addAttribute("ageDataList", ageDataList);

        return "data";
    }

    /**
     * 詳細検索
     */
  

    @GetMapping("/sample")
    public String sample() {
        return "sample";
    }
    // @GetMapping("/ageData") public String getAgeData(Model model) {
    // 年代別のデータを取得するクエリを実行 List<Map<String, Object>> ageDataList =
    // jdbcTemplate.queryForList( "SELECT CASE WHEN TIMESTAMPDIFF(YEAR,
    // birth_date, CURDATE()) BETWEEN 20 AND 29 " + "THEN '20代' WHEN
    // TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 30 AND 39 T" +
    // "HEN '30代' WHEN TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 40 AND 49
    // TH" + "EN '40代' WHEN TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) >= 50
    // THEN '50代以上' EN" + "D AS age_group, COUNT(*) AS count FROM employee
    // GROUP BY age_group" ); モデルに年代別データを設定
    // model.addAttribute("ageDataList", ageDataList); return "ageData"; }

    @Autowired
private EmployeeRepository employeeRepository;

/*
 * csvインポート
 */
@PostMapping("/upload")
@Transactional
public String upload(@RequestParam("file") MultipartFile file, Model model) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try (InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] csvSplit = line.split(",");
            
            // データ検証
            if (csvSplit.length < 14) {
                throw new IllegalArgumentException("CSV file is missing required fields");
            }

            for (int i = 0; i < csvSplit.length; i++) {
                csvSplit[i] = csvSplit[i].replaceAll("'", "").trim();
            }

            Employee employee = new Employee();
            employee.setImage(csvSplit[0]);
            employee.setName(csvSplit[1]);
            employee.setDivision(csvSplit[2]);
            employee.setSex(csvSplit[3]);
            employee.setEmail(csvSplit[4]);
            employee.setPhone(csvSplit[5]);
            employee.setAddress(csvSplit[6]);
            employee.setBackground(csvSplit[7]);
            employee.setSkill(csvSplit[8]);

            Date birthDate = dateFormat.parse(csvSplit[9]);
            employee.setBirth_date(birthDate);

            employee.setHira(csvSplit[10]);
            employee.setDivisionkana(csvSplit[11]);
            employee.setYubin(csvSplit[12]);
            employee.setBanchi(csvSplit[13]);

            employeeRepository.save(employee);
        }
    } catch (IOException | ParseException | IllegalArgumentException e) {
        model.addAttribute("error", e.getMessage());
        return "error"; // エラービューにリダイレクト
    }
    return "redirect:/home"; // 処理完了後のリダイレクト

}

@ExceptionHandler(Exception.class)
public String handleAllExceptions(Exception ex, Model model) {
    model.addAttribute("error", ex.getMessage());
    return "error"; // エラービューにリダイレクト
}
@GetMapping("/search")
    public String searchview(Model model){
        List<Employee> emplist = employeeService.searchAll();
        model.addAttribute("emplist",emplist);
        return "search";
    }
}
