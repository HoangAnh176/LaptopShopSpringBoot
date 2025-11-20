package vn.spring.laptopshop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.spring.laptopshop.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User save(User spring);

  List<User> findOneByEmail(String email);

  List<User> findAll();

  User findById(long id);

  void deleteById(long id);

  boolean existsByEmail(String email);

  User findByEmail(String email);

  Page<User> findAll(Pageable page) ;
}
