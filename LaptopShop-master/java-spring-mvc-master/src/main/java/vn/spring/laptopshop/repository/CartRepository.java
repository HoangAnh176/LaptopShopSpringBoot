package vn.spring.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.spring.laptopshop.domain.Cart;
import vn.spring.laptopshop.domain.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
  Cart findByUser(User user);

  public void deleteByUserId(Long id );

  @Query("SELECT c.id FROM Cart c WHERE c.user.id = :userId")
  List<Long> findAllCartIdsByUserId(long userId);
}
