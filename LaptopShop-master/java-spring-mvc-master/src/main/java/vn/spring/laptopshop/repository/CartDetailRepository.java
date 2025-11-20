package vn.spring.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.spring.laptopshop.domain.Cart;
import vn.spring.laptopshop.domain.CartDetail;
import vn.spring.laptopshop.domain.Product;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
  boolean existsByCartAndProduct(Cart cart, Product product);

  CartDetail findByCartAndProduct(Cart cart, Product product);

  public void deleteByProductId(Long productId) ;

  public void deleteByCartId(Long cartId); // Phương thức để xóa các chi tiết giỏ hàng theo cart_id
}
