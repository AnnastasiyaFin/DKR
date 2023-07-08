package com.example.dkrproject.repository;

import com.example.dkrproject.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Cacheable("user")
    List<User> findByName(String name);

    //Select * from User where name LIKE "%tr%"
    @Cacheable("user_containing")
    List<User> findByNameContaining(String keyword);

    //select * from User where name=? AND location=?
//    List<User> findByNameAndLocation(String name, String location);

//    @Query("FROM User WHERE name = :name OR location = :location")
//    List<User> getUsersByNameAndLocation(String name, String location);

    @Query(value = "select u.* FROM Users u INNER JOIN Department d ON d.id = u.department_id WHERE d.name = :dep",
            nativeQuery = true)
    @Cacheable("user_department")
    List<User> getUsersByDepartment(String dep);

    @Transactional
    @Modifying
    @Query("DELETE FROM User where name = :name")
    Integer deleteUserByName(String name);

    @Query(value = "SELECT * FROM users ORDER BY random() LIMIT 1", nativeQuery = true)
    Optional<User> findRandom();

    List<User> findByReaderCardId(Long readerCard);
}
