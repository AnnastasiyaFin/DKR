package com.example.dkrproject.service;

import com.example.dkrproject.dto.UserBooksDTO;
import com.example.dkrproject.dto.UserDTO;
import com.example.dkrproject.exception.ResourceNotFoundException;
import com.example.dkrproject.model.Department;
import com.example.dkrproject.model.Location;
import com.example.dkrproject.model.ReaderCard;
import com.example.dkrproject.model.User;
import com.example.dkrproject.repository.UserRepository;
import com.example.dkrproject.transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ReaderCardService readerCardService;

    @Autowired
    private UserTransformer userTransformer;

    public List<UserDTO> getAllUsers(int pageNumber, int pageSize) {
        Pageable pages = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "name", "birthDate"));
        List<User> userList = userRepository.findAll(pages).getContent();
        return userList.stream().map(userTransformer::transformUserToDTO).toList();
    }

    public UserDTO qetUserResponseById(long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
        return userTransformer.transformUserToDTO(user);
    }

    public User qetUserById(long id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Transactional(rollbackFor = Throwable.class)
    public UserDTO saveUser(UserDTO userRequest) throws ResourceNotFoundException {
        User user = userTransformer.transformUserToDTO(userRequest);
        final ReaderCard card = ReaderCard.builder().user(user).build();
        user.setReaderCard(card);
        Location location = locationService.getLocation(userRequest.getLocation());
        if (location == null) {
            location = locationService.save(new Location(userRequest.getLocation()));
        }
        user.setLocation(location);

        if (userRequest.getDepartment() != null) {
            Department department = departmentService.findDepartmentByName(userRequest.getDepartment());
            if (department == null) {
                throw new ResourceNotFoundException("Department was not found : " + userRequest.getDepartment());
            }
            user.setDepartment(department);
        }

        user = userRepository.save(user);
        return userTransformer.transformUserToDTO(user);
    }

    @Transactional(rollbackFor = Throwable.class)
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
                throw new ResourceNotFoundException("Department was not found : " + userRequest.getDepartment());
            }
            userUpdated.setDepartment(department);
        }

        return userTransformer.transformUserToDTO(userRepository.save(userUpdated));
    }

    public List<UserDTO> getUsersByName(String name) {
        List<User> list = userRepository.findByName(name);
        return list.stream().map(userTransformer::transformUserToDTO).toList();
    }

    public List<UserDTO> getUsersByNameContaining(String name) {
        List<User> list = userRepository.findByNameContaining(name);
        return list.stream().map(userTransformer::transformUserToDTO).toList();
    }

    public Integer deleteUserByName(String name) {
        return userRepository.deleteUserByName(name);
    }

    public List<UserDTO> getUsersByDepartment(String department) {
        List<User> list = userRepository.getUsersByDepartment(department);
        return list.stream().map(userTransformer::transformUserToDTO).toList();
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
        return user.getReaderCard().getId();
    }

    public List<UserBooksDTO> getUsersBooks(String userName) {
        List<User> list = userRepository.findByName(userName);
        return list.stream().map(userTransformer::transformUserToUserBooksDTO).toList();
    }

    public List<UserBooksDTO> getUsersBooksByReaderCard(Long readerCard) {
        List<User> list = userRepository.findByReaderCardId(readerCard);
        return list.stream().map(userTransformer::transformUserToUserBooksDTO).toList();
    }
}
