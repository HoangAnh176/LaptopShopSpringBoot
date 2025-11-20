package vn.spring.laptopshop.controller.client;


import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.spring.laptopshop.domain.Order;
import vn.spring.laptopshop.domain.Product;
import vn.spring.laptopshop.domain.User;
import vn.spring.laptopshop.domain.dto.RegisterDTO;
import vn.spring.laptopshop.service.OrderService;
import vn.spring.laptopshop.service.ProductService;
import vn.spring.laptopshop.service.UploadService;
import vn.spring.laptopshop.service.UserService;

@Controller
public class HomePageController {
  private final ProductService productService;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final OrderService orderService ;
  private final UploadService uploadService ;

  public HomePageController(ProductService productService, UserService userService, PasswordEncoder passwordEncoder , OrderService orderService , UploadService uploadService) {
    this.productService = productService;
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.orderService = orderService ;
    this.uploadService = uploadService ;
  }

  @GetMapping("/")
  public String getHomePage(Model model) {
    List<Product> products = this.productService.getAllProducts();
    model.addAttribute("products", products);
    return "client/homepage/show";
  }

  @GetMapping("/register")
  public String getRegisterPage(Model model) {
    model.addAttribute("registerUser", new RegisterDTO());
    return "client/auth/register";
  }

  @PostMapping("/register")
  public String handleRegister(@ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
      BindingResult bindingResult , @RequestParam("springFile") MultipartFile file) {

    // Validate
    if (bindingResult.hasErrors()) {
      return "client/auth/register";
    }

    User user = this.userService.registerDTOtoUser(registerDTO);
    String avatar = this.uploadService.handleSaveUploadFile(file, "avatar") ;

    String hashPassword = this.passwordEncoder.encode(user.getPassword());
    
    user.setAvatar(avatar);
    user.setPassword(hashPassword);
    user.setRole(this.userService.getRoleByName("USER"));

    // save
    this.userService.handleSaveUser(user);
    return "redirect:/login";
  }

  @GetMapping("/login")
  public String getLoginPage(Model model) {
    return "client/auth/login";
  }

  @GetMapping("/access-deny")
  public String getDenyPage(Model model) {
    return "client/auth/deny";
  }

  @GetMapping("/order-history")
    public String getOrderHistoryPage(Model model, HttpServletRequest request) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        List<Order> orders = this.orderService.fetchOrderByUser(currentUser);
        model.addAttribute("orders", orders);

        return "client/cart/order-history";
    }

    @GetMapping("/order-history/{id}")
    public String getTotalViewOrderHistoryPage(Model model, @PathVariable long id) {
        Optional<Order> optionalOrder = this.orderService.getOrderById(id);
        Order order = optionalOrder.get();
        model.addAttribute("order", order);
        return "client/cart/order-history-detail";
    }

    @GetMapping("/account")
    public String getAccountPage(Model model, HttpServletRequest request) {
    // Kiểm tra session
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("id") == null) {
        System.out.println("Session invalid or user not logged in");
        return "redirect:/login";
    }

    // Lấy ID người dùng từ session
    long id = (long) session.getAttribute("id");
    System.out.println("User ID from session: " + id);

    // Lấy thông tin người dùng
    User currentUser = userService.getUserById(id);
    if (currentUser == null) {
        System.out.println("User not found for ID: " + id);
        return "redirect:/login";
    }

    // Lấy danh sách đơn hàng
    List<Order> orders = orderService.fetchOrderByUser(currentUser);
    System.out.println("Orders: " + orders);

    // Truyền dữ liệu vào model
    model.addAttribute("user", currentUser);
    model.addAttribute("orders", orders);

    return "client/account/account";
}

  @RequestMapping("/account/update/{id}")
  public String getUpdateUserDetailPage(Model model, @PathVariable long id) {
    User currentUser = this.userService.getUserById(id);
    model.addAttribute("newUser1", currentUser);
    return "client/account/edit";
  }

  @PostMapping("/account/update")
  public String postUpdateUser(Model model, @ModelAttribute("newUser1") User spring) {
    User currentUser = this.userService.getUserById(spring.getId());
    if (currentUser != null) {
      currentUser.setAddress(spring.getAddress());
      currentUser.setFullName(spring.getFullName());
      currentUser.setPhone(spring.getPhone());
      this.userService.handleSaveUser(currentUser);
    }
    return "redirect:/account";
  }
}