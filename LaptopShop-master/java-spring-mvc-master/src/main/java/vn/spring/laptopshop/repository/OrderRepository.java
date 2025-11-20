package vn.spring.laptopshop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.spring.laptopshop.domain.Order;
import vn.spring.laptopshop.domain.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findOrderByUserId( User user ) ;

  Page<Order> findAll(Pageable page) ;

  List<Order> findByUser(User user);

  public void deleteByUserId(long id) ;

  // public void findAllOrderIdsByUserId( long id) ;

  // Lấy danh sách orderId dựa trên userId (sử dụng @Query để rõ ràng)
    @Query("SELECT o.id FROM Order o WHERE o.user.id = :userId")
    List<Long> findAllOrderIdsByUserId(long userId);
}
