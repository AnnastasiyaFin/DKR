package com.example.dkrproject.controller;

import com.example.dkrproject.exception.ResourceNotFoundException;
import com.example.dkrproject.model.Department;
import com.example.dkrproject.dto.DepartmentDTO;
import com.example.dkrproject.service.DepartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/department")
@Tag(name="Отделы", description="Получение данных об отделах")
public class DepartmentController {

    @Autowired
    private DepartmentService depService;

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        return new ResponseEntity<>(depService.getDepartments(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createDepartment(@RequestBody DepartmentDTO departmentRequest) {
        return new ResponseEntity<>(depService.saveDepartment(departmentRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable("id") long id, @RequestBody DepartmentDTO departmentRequest) throws ResourceNotFoundException {
        Department depUpdated = depService.updateDepartment(id, departmentRequest);
        return ResponseEntity.ok().body(depUpdated);
    }
}
