package vn.spring.laptopshop.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import vn.spring.laptopshop.Specs.ProductSpecs;
import vn.spring.laptopshop.domain.Cart;
import vn.spring.laptopshop.domain.CartDetail;
import vn.spring.laptopshop.domain.Order;
import vn.spring.laptopshop.domain.OrderDetail;
import vn.spring.laptopshop.domain.Product;
import vn.spring.laptopshop.domain.Product_;
import vn.spring.laptopshop.domain.User;
import vn.spring.laptopshop.domain.dto.ProductCriteriaDTO;
import vn.spring.laptopshop.repository.CartDetailRepository;
import vn.spring.laptopshop.repository.CartRepository;
import vn.spring.laptopshop.repository.FeedbackRepository;
import vn.spring.laptopshop.repository.OrderDetailRepository;
import vn.spring.laptopshop.repository.OrderRepository;
import vn.spring.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
  private final ProductRepository productRepository;
  private final CartRepository cartRepository;
  private final CartDetailRepository cartDetailRepository;
  private final UserService userService;
  private final OrderRepository orderRepository;
  private final OrderDetailRepository orderDetailRepository;
  private final FeedbackRepository feedbackRepository ;

  public ProductService(ProductRepository productRepository,
      CartRepository cartRepository,
      CartDetailRepository cartDetailRepository,
      UserService userService,
      OrderRepository orderRepository,
      OrderDetailRepository orderDetailRepository,
      FeedbackRepository feedbackRepository ) {
    this.productRepository = productRepository;
    this.cartRepository = cartRepository;
    this.cartDetailRepository = cartDetailRepository;
    this.userService = userService;
    this.orderRepository = orderRepository;
    this.orderDetailRepository = orderDetailRepository;
    this.feedbackRepository = feedbackRepository ;
  }

  public List<Product> getAllProducts() {
    return this.productRepository.findAll();
  }

  private Specification<Product> nameLike(String name){
    return (root, query, criteriaBuilder)
    -> criteriaBuilder.like(root.get(Product_.NAME), "%"+name+"%");
  }
  public Page<Product> fetchProducts(Pageable page) {
    return this.productRepository.findAll(page);
  }

   public Page<Product> fetchProductsWithSpec(Pageable page, ProductCriteriaDTO productCriteriaDTO) {

        if (productCriteriaDTO.getTarget() == null && productCriteriaDTO.getFactory() == null
                && productCriteriaDTO.getPrice() == null) {
            return this.productRepository.findAll(page);
        }
        Specification<Product> combineSpec = Specification.where(null);

        if (productCriteriaDTO.getTarget() != null && productCriteriaDTO.getTarget().isPresent()) {
            Specification<Product> currentSpecs = ProductSpecs.matchListTarget(productCriteriaDTO.getTarget().get());
            combineSpec = combineSpec.and(currentSpecs);
        }

        if (productCriteriaDTO.getFactory() != null && productCriteriaDTO.getFactory().isPresent()) {
            Specification<Product> currentSpecs = ProductSpecs.matchListFactory(productCriteriaDTO.getFactory().get());
            combineSpec = combineSpec.and(currentSpecs);
        }

        if (productCriteriaDTO.getPrice() != null && productCriteriaDTO.getPrice().isPresent()) {
            Specification<Product> currentSpecs = this.buildPriceSpecification(productCriteriaDTO.getPrice().get());
            combineSpec = combineSpec.and(currentSpecs);
        }

        return this.productRepository.findAll(combineSpec, page);
    }

  // case 1 
  // public Page<Product> fetchProductsWithSpec( Pageable page , double min) {
  //   return this.productRepository.findAll(ProductSpecs.minPrice(min) , page);
  // }

  // case 2
  // public Page<Product> fetchProductsWithSpec( Pageable page , double max) {
  //   return this.productRepository.findAll(ProductSpecs.maxPrice(max) , page);
  // }

  // case 3
  // public Page<Product> fetchProductsWithSpec( Pageable page , String factory) {
  //   return this.productRepository.findAll(ProductSpecs.matchFactory(factory) , page);
  // }

  // case 4 :
  // public Page<Product> fetchProductsWithSpec( Pageable page , List<String> factory) {
  //   return this.productRepository.findAll(ProductSpecs.matchListFactory(factory) , page);
  // }

  // case 5 : 
  // public Page<Product> fetchProductsWithSpec( Pageable page , String price) {
  //   // eg: price 10-toi-15-trieu
  //   if(price.equals("10-toi-15-trieu")) {
  //     double min = 10000000 ; 
  //     double max = 15000000 ; 
  //     return this.productRepository.findAll(ProductSpecs.matchPrice(min , max) , page) ;
  //   }
  //   else if ( price.equals("15-toi-30-trieu")) {
  //     double min = 15000000 ; 
  //     double max = 30000000 ; 
  //     return this.productRepository.findAll(ProductSpecs.matchPrice(min , max) , page) ;
  //   }
  //   else {
  //     return this.productRepository.findAll(page) ;
  //   }
  // }

  // case 6 :
  public Specification<Product> buildPriceSpecification(List<String> price) {
        Specification<Product> combinedSpec = Specification.where(null);
        for (String p : price) {
            double min = 0;
            double max = 0;

            // Set the appropriate min and max based on the price range string
            switch (p) {
                case "duoi-10-trieu":
                    min = 1;
                    max = 10000000;
                    break;
                case "10-toi-15-trieu":
                    min = 10000000;
                    max = 15000000;
                    break;
                case "15-toi-20-trieu":
                    min = 15000000;
                    max = 20000000;
                    break;
                case "tren-20-trieu":
                    min = 20000000;
                    max = 200000000;
                    break;
            }

            if (min != 0 && max != 0) {
                Specification<Product> rangeSpec = ProductSpecs.matchMultiplePrice(min, max);
                combinedSpec = combinedSpec.or(rangeSpec);
            }
        }

        return combinedSpec;
    }

  public Optional<Product> getProductById(long id) {
    return this.productRepository.findById(id);
  }

  public Product createProduct(Product product) {
    return this.productRepository.save(product);
  }

  @Transactional // Thêm annotation này
    public void deleteProduct(long id) {
        // Xóa các phản hồi liên quan
        feedbackRepository.deleteByProductId(id);
        // Xóa các bản ghi trong cart_detail liên quan
        cartDetailRepository.deleteByProductId(id);
        // Xóa các bản ghi trong order_detail liên quan
        orderDetailRepository.deleteByProductId(id);
        // Sau đó xóa sản phẩm
        productRepository.deleteById(id);
    }

  public void handleAddProductToCart(String email, long productId, HttpSession session) {
    User user = this.userService.getUserByEmail(email);
    if (user != null) {
      Cart cart = this.cartRepository.findByUser(user);
      if (cart == null) {
        // tạo mới cart
        Cart otherCart = new Cart();
        otherCart.setUser(user);
        otherCart.setSum(0);

        cart = this.cartRepository.save(otherCart);
      }

      // save cart detail
      // tìm product by ID

      Optional<Product> productOptional = this.productRepository.findById(productId);
      if (productOptional.isPresent()) {
        Product realProduct = productOptional.get();
        // check sản phẩm đã có trong giỏ hàng trước đây chưa?
        CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);

        if (oldDetail == null) {
          CartDetail cartDetail = new CartDetail();
          cartDetail.setCart(cart);
          cartDetail.setProduct(realProduct);
          cartDetail.setPrice(realProduct.getPrice());
          cartDetail.setQuantity(1);
          this.cartDetailRepository.save(cartDetail);

          // update cart sum
          int s = cart.getSum() + 1;
          cart.setSum(cart.getSum() + 1);
          cart = this.cartRepository.save(cart);
          session.setAttribute("sum", s);
        } else {
          oldDetail.setQuantity(oldDetail.getQuantity() + 1);
          this.cartDetailRepository.save(oldDetail);
        }
      }
    }
  }

  public Cart fetchByUser(User user) {
    return this.cartRepository.findByUser(user);
  }

  public void handleRemoveCartDetail(long cartDetailId, HttpSession session) {
    // tìm product by ID
    Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailId);
    if (cartDetailOptional.isPresent()) {
      CartDetail cartDetail = cartDetailOptional.get();

      Cart currentCart = cartDetail.getCart();

      // delete cart detail
      this.cartDetailRepository.deleteById(cartDetailId);

      // update cart

      if (currentCart.getSum() > 1) {
        // update current cart sum
        int s = currentCart.getSum() - 1;
        currentCart.setSum(s);
        session.setAttribute("sum", s);
        this.cartRepository.save(currentCart);
      } else {
        // delete cart (sum = 1)
        this.cartRepository.deleteById(currentCart.getId());
        session.setAttribute("sum", 0);
      }
    }
  }

  public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
    for (CartDetail cartDetail : cartDetails) {
      Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
      if (cdOptional.isPresent()) {
        CartDetail currentCartDetail = cdOptional.get();
        currentCartDetail.setQuantity(cartDetail.getQuantity());
        this.cartDetailRepository.save(currentCartDetail);
      }
    }
  }

  public void handlePlaceOrder(
      User user, HttpSession session,
      String receiverName, String receiverAddress, String receiverPhone) {

    // step 1: get cart by user
    Cart cart = this.cartRepository.findByUser(user);
    if (cart != null) {
      List<CartDetail> cartDetails = cart.getCartDetails();
      if (cartDetails != null) {

        // create order
        Order order = new Order();
        order.setUser(user);
        order.setReceiverName(receiverName);
        order.setReceiverAddress(receiverAddress);
        order.setReceiverPhone(receiverPhone);
        order.setStatus("PENDING");
        order.setDateOrder(new Date()) ;

        double sum = 0;
        for (CartDetail cd : cartDetails) {
          sum += cd.getPrice();
        }
        order.setTotalPrice(sum);
        order = this.orderRepository.save(order);

        // create orderDetail

        for (CartDetail cd : cartDetails) {
          OrderDetail orderDetail = new OrderDetail();
          orderDetail.setOrder(order);
          orderDetail.setProduct(cd.getProduct());
          orderDetail.setPrice(cd.getPrice());
          orderDetail.setQuantity(cd.getQuantity());

          this.orderDetailRepository.save(orderDetail);
        }

        // step 2: delete cart_detail and cart
        for (CartDetail cd : cartDetails) {
          this.cartDetailRepository.deleteById(cd.getId());
        }

        this.cartRepository.deleteById(cart.getId());

        // step 3: update session
        session.setAttribute("sum", 0);
      }
    }
  }
}