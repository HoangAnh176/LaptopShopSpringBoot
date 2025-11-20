package vn.spring.laptopshop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.spring.laptopshop.domain.Feedback;
import vn.spring.laptopshop.repository.FeedbackRepository;

@Service
public class FeedbackService {
    private FeedbackRepository feedbackRepository ;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

     public Feedback saveFeedBack(Feedback feedBack) {
        return this.feedbackRepository.save(feedBack);
    }

    public List<Feedback> findAllFeedback() {
        return this.feedbackRepository.findAll();
    }

    public Page<Feedback> paginationFeed(Pageable pageable) {
        return this.feedbackRepository.findAll(pageable);
    }
    
    public void deleteFeedbackById( long id) {
        this.feedbackRepository.deleteById(id);
    }
}
