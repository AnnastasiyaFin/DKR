package com.example.dkrproject.service;

import com.example.dkrproject.exception.ResourceNotFoundException;
import com.example.dkrproject.model.Department;
import com.example.dkrproject.model.User;
import com.example.dkrproject.model.Location;
import com.example.dkrproject.repository.UserRepository;
import com.example.dkrproject.dto.UserDTO;
import com.example.dkrproject.transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private UserTransformer userTransformer;

    public List<UserDTO> getAllUsers(int pageNumber, int pageSize) {
        Pageable pages = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "name", "birthDate"));
        List<User> userList = userRepository.findAll(pages).getContent();
        List<UserDTO> responseList = new ArrayList<>();
        userList.forEach(user ->
                responseList.add(userTransformer.transformDTOToUser(user))
        );
        return responseList;
    }

    public UserDTO qetUserResponseById(long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
        return userTransformer.transformDTOToUser(user);
    }

    public User qetUserById(long id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    public UserDTO saveUser(UserDTO userRequest) throws ResourceNotFoundException {
        User user = userTransformer.transformDTOToUser(userRequest);
        Location location = locationService.getLocation(userRequest.getLocation());
        if (location == null) {
            location = locationService.save(new Location(userRequest.getLocation()));
        }
        user.setLocation(location);

        if (userRequest.getDepartment() != null) {
            Department department = departmentService.findDepartmentByName(userRequest.getDepartment());
            if (department == null) {
                throw new ResourceNotFoundException("Department not found for this id :" + userRequest.getDepartment());
            }
            user.setDepartment(department);
        }

        user = userRepository.save(user);
        return userTransformer.transformDTOToUser(user);
    }

    public UserDTO updateUser(long id, UserDTO userRequest) throws ResourceNotFoundException {
        User userUpdated = qetUserById(id);
        userUpdated.setName(userRequest.getName());
        userUpdated.setBirthDate(LocalDate.parse(userRequest.getBirthDate()));
        userUpdated.setEmail(userRequest.getEmail());
        userUpdated.setPhone(userRequest.getPhone());

        Location location = locationService.getLocation(userRequest.getLocation());
        if (location == null) {
            location = locationService.save(new Location(userRequest.getLocation()));
        }
        userUpdated.setLocation(location);

        if (userRequest.getDepartment() != null) {
            Department department = departmentService.findDepartmentByName(userRequest.getDepartment());
            if (department == null) {
                throw new ResourceNotFoundException("Department not found for this id :" + userRequest.getDepartment());
            }
            userUpdated.setDepartment(department);
        }

        return userTransformer.transformDTOToUser(userRepository.save(userUpdated));
    }

    public List<UserDTO> getUsersByName(String name) {
        List<User> list = userRepository.findByName(name);
        List<UserDTO> responseList = new ArrayList<>();
        list.forEach(user -> {
            responseList.add(userTransformer.transformDTOToUser(user));
        });
        return responseList;
    }

    public List<UserDTO> getUsersByNameContaining(String name) {
        List<User> list = userRepository.findByNameContaining(name);
        List<UserDTO> responseList = new ArrayList<>();
        list.forEach(user -> {
            responseList.add(userTransformer.transformDTOToUser(user));
        });
        return responseList;
    }

    public Integer deleteUserByName(String name) {
        return userRepository.deleteUserByName(name);
    }

    public List<UserDTO> getUsersByDepartment(String department) {
        List<User> list = userRepository.getUsersByDepartment(department);
        List<UserDTO> responseList = new ArrayList<>();
        list.forEach(user -> {
            responseList.add(userTransformer.transformDTOToUser(user));
        });
        return responseList;
    }

//    public UserDTO assignDepToUser(Long userId, Long depId) throws ResourceNotFoundException {
//        User user = userRepository.findById(userId).stream().findFirst()
//                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
//        Department department = departmentService.findById(depId);
//        Set<Department> departmentSet = user.getDepartments();
//        departmentSet.add(department);
//        user.setDepartments(departmentSet);
//        userRepository.save(user);
//        return userTransformer.transformDTOToUser(user);
//    }

    public long getRandom() throws ResourceNotFoundException {
        User user = userRepository.findRandom()
                .orElseThrow(() -> new ResourceNotFoundException("No user found"));
        return user.getId();
    }
}
