package vn.spring.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.spring.laptopshop.domain.Cart;
import vn.spring.laptopshop.domain.CartDetail;
import vn.spring.laptopshop.domain.Product;
import vn.spring.laptopshop.domain.User;
import vn.spring.laptopshop.service.ProductService;

import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;

import vn.spring.laptopshop.domain.Feedback;
import vn.spring.laptopshop.domain.Product_;
import vn.spring.laptopshop.domain.dto.ProductCriteriaDTO;
import vn.spring.laptopshop.service.FeedbackService;
import vn.spring.laptopshop.service.OrderService;
import vn.spring.laptopshop.service.UserService;

@Controller
public class ItemController {

  private final ProductService productService;
  private final OrderService orderService ;
  private final UserService userService ;
  private final FeedbackService feedbackService ;

  public ItemController(ProductService productService , OrderService orderService , UserService userService , FeedbackService feedbackService  ) {
    this.productService = productService;
    this.orderService = orderService ; 
    this.userService = userService ; 
    this.feedbackService = feedbackService ; 
  }

  @GetMapping("/product/{id}")
  public String getProductPage(Model model, @PathVariable long id) {
    Product product = this.productService.getProductById(id).orElse(null);
    if (product == null) {
        return "redirect:/";
    }
    model.addAttribute("product", product);
    model.addAttribute("id", id);
    model.addAttribute("newFeed", new Feedback()); // Đảm bảo dòng này
    return "client/product/detail";
}
  @PostMapping("/cmt/finish")
    public String cmtFinish(Model model, @ModelAttribute("newFeed") Feedback feed,
            @AuthenticationPrincipal UserDetails userDetails) {
        String content = feed.getContent();
        Feedback feedBack = new Feedback();
        feedBack.setContent(content);
        feedBack.setDate(new Date());
        feedBack.setUser(this.userService.findUserByEmail(userDetails.getUsername()));
        feedBack.setProduct(feed.getProduct());
        this.feedbackService.saveFeedBack(feedBack);
        return "redirect:/";
    }

  @PostMapping("/add-product-to-cart/{id}")
  public String addProductToCart(@PathVariable long id, HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    long productId = id;
    String email = (String) session.getAttribute("email");
    this.productService.handleAddProductToCart(email, productId, session);
    return "redirect:/";
  }

  @GetMapping("/cart")
  public String getCartPage(Model model, HttpServletRequest request) {
    User currentUser = new User();
    HttpSession session = request.getSession(false);
    long id = (long) session.getAttribute("id");
    currentUser.setId(id);

    Cart cart = this.productService.fetchByUser(currentUser);

    List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

    double totalPrice = 0;
    for (CartDetail cd : cartDetails) {
      totalPrice += cd.getPrice() * cd.getQuantity();
    }

    model.addAttribute("cartDetails", cartDetails);
    model.addAttribute("totalPrice", totalPrice);
    model.addAttribute("cart", cart);

    return "client/cart/show";
  }

  @PostMapping("/delete-cart-product/{id}")
  public String deleteCartDetail(@PathVariable long id, HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    long cartDetailId = id;
    this.productService.handleRemoveCartDetail(cartDetailId, session);
    return "redirect:/cart";
  }

  @GetMapping("/checkout")
  public String getCheckOutPage(Model model, HttpServletRequest request) {
    User currentUser = new User(); // null
    HttpSession session = request.getSession(false);
    long id = (long) session.getAttribute("id");
    currentUser.setId(id);

    Cart cart = this.productService.fetchByUser(currentUser);

    List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

    double totalPrice = 0;
    for (CartDetail cd : cartDetails) {
      totalPrice += cd.getPrice() * cd.getQuantity();
    }

    model.addAttribute("cartDetails", cartDetails);
    model.addAttribute("totalPrice", totalPrice);

    return "client/cart/checkout";
  }

  @PostMapping("/confirm-checkout")
  public String getCheckOutPage(@ModelAttribute("cart") Cart cart) {
    List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
    this.productService.handleUpdateCartBeforeCheckout(cartDetails);

    return "redirect:/checkout";
  }

  @PostMapping("/place-order")
  public String handlePlaceOrder(
      HttpServletRequest request,
      @RequestParam("receiverName") String receiverName,
      @RequestParam("receiverAddress") String receiverAddress,
      @RequestParam("receiverPhone") String receiverPhone) {
    User currentUser = new User(); // null
    HttpSession session = request.getSession(false);
    long id = (long) session.getAttribute("id");
    currentUser.setId(id);

    this.productService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);

    return "redirect:/thanks";
  }

  @GetMapping("/thanks")
  public String getThankYouPage(Model model) {
    return "client/cart/thanks";
  }

  @GetMapping("/products")
    public String getProductPage(Model model,
            ProductCriteriaDTO productCriteriaDTO,
            HttpServletRequest request) {
        int page = 1;
        try {
            if (productCriteriaDTO.getPage().isPresent()) {
                // convert from String to int
                page = Integer.parseInt(productCriteriaDTO.getPage().get());
            } else {
                // page = 1
            }
        } catch (Exception e) {
            // page = 1
            // TODO: handle exception
        }

        // check sort page
        Pageable pageable = PageRequest.of(page - 1, 10);

        if (productCriteriaDTO.getSort() != null && productCriteriaDTO.getSort().isPresent()) {
            String sort = productCriteriaDTO.getSort().get();
            if (sort.equals("gia-tang-dan")) {
                pageable = PageRequest.of(page - 1, 10, Sort.by(Product_.PRICE).ascending());
            } else if (sort.equals("gia-giam-dan")) {
                pageable = PageRequest.of(page - 1, 10, Sort.by(Product_.PRICE).descending());
            }
        }

        Page<Product> prs = this.productService.fetchProductsWithSpec(pageable, productCriteriaDTO);

        // case 1
        // double min = minOptional.isPresent() ? Double.parseDouble(minOptional.get())
        // : 0;
        // Page<Product> prs = this.productService.fetchProductsWithSpec(pageable, min);

        // case 2
        // double max = maxOptional.isPresent() ? Double.parseDouble(maxOptional.get())
        // : 0;
        // Page<Product> prs = this.productService.fetchProductsWithSpec(pageable, max);

        // case 3
        // String factory = factoryOptional.isPresent() ? factoryOptional.get() : "";
        // Page<Product> prs = this.productService.fetchProductsWithSpec(pageable,
        // factory);

        // case 4
        // List<String> factory = Arrays.asList(factoryOptional.get().split(","));
        // Page<Product> prs = this.productService.fetchProductsWithSpec(pageable,
        // factory);

        // case 5
        // String price = priceOptional.isPresent() ? priceOptional.get() : "";
        // Page<Product> prs = this.productService.fetchProductsWithSpec(pageable,
        // price);

        // case 6
        // List<String> price = Arrays.asList(priceOptional.get().split(","));
        // Page<Product> prs = this.productService.fetchProductsWithSpec(pageable,
        // price);

        List<Product> products = prs.getContent().size() > 0 ? prs.getContent()
                : new ArrayList<Product>();

        String qs = request.getQueryString();
        if (qs != null && !qs.isBlank()) {
            // remove page
            qs = qs.replace("page=" + page, "");
        }

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", prs.getTotalPages());
        model.addAttribute("queryString", qs);
      return "client/homepage/products";
  }
}