package com.example.dkrproject.service;

import com.example.dkrproject.exception.ResourceNotFoundException;
import com.example.dkrproject.model.Department;
import com.example.dkrproject.repository.DepartmentRepository;
import com.example.dkrproject.dto.DepartmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public Department findDepartmentByName(String name) {
        return departmentRepository.findByName(name).stream().findFirst().orElse(null);
    }

    public void save(Department dep) {
        departmentRepository.save(dep);
    }

    public Department findById(Long depId) throws ResourceNotFoundException  {
        return departmentRepository.findById(depId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found for this id :: " + depId));
    }

    public String saveDepartment(DepartmentDTO departmentRequest) {
        Department dep = new Department();
        dep.setName(departmentRequest.getName());
        departmentRepository.save(dep);
        return "OK";
    }

    public void saveDepartment(String departmentName) {
        Department dep = new Department();
        dep.setName(departmentName);
        departmentRepository.save(dep);
    }

    public Department updateDepartment(long id, DepartmentDTO departmentRequest) throws ResourceNotFoundException {
        Department departmentUpdated = findById(id);
        departmentUpdated.setName(departmentRequest.getName());
        return departmentRepository.save(departmentUpdated);
    }
}
