package vn.spring.laptopshop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.spring.laptopshop.domain.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>{
    public Feedback save(Feedback feed);

    public List<Feedback> findAll();

    public Page<Feedback> findAll(Pageable pageable);

    public void deleteByProductId(Long productId) ;

    public void deleteByUserId(Long userId); // Phương thức để xóa feedback dựa trên user_id
}
