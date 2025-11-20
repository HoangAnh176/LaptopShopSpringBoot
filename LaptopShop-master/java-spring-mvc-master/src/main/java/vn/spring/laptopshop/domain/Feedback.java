package vn.spring.laptopshop.domain;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    private String content ; 

    private Date date ;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user ;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product ;

    public Feedback() {
    }

    public Feedback(String content, Date date, long id, Product product, User user) {
        this.content = content;
        this.date = date;
        this.id = id;
        this.product = product;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("feedback{");
        sb.append("id=").append(id);
        sb.append(", content=").append(content);
        sb.append(", date=").append(date);
        sb.append(", user=").append(user);
        sb.append(", product=").append(product);
        sb.append('}');
        return sb.toString();
    }
    
    
    
}
