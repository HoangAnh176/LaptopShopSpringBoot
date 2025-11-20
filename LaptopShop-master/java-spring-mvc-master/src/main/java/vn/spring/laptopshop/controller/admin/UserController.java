package vn.spring.laptopshop.controller.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import jakarta.validation.Valid;
import vn.spring.laptopshop.domain.User;
import vn.spring.laptopshop.service.OrderService;
import vn.spring.laptopshop.service.UploadService;
import vn.spring.laptopshop.service.UserService;

@Controller
public class UserController {

  private final OrderService orderService;
  private final UserService userService;
  private final UploadService uploadService;
  private final PasswordEncoder passwordEncoder;

  public UserController(UserService userService,
      UploadService uploadService,
      PasswordEncoder passwordEncoder, OrderService orderService) {
    this.userService = userService;
    this.uploadService = uploadService;
    this.passwordEncoder = passwordEncoder;
    this.orderService = orderService;
  }

  @RequestMapping("/")
  public String getHomePage(Model model) {
    List<User> arrUsers = this.userService.getOneUserByEmail("phamquangchien243@gmail.com");
    System.out.println(arrUsers);
    model.addAttribute("eric", "test");
    model.addAttribute("spring", "from controller with model");
    return "hello";
  }

  @RequestMapping("/admin/user")
  public String getUserPage(Model model , @RequestParam(value = "page", defaultValue = "1") int page) {
    Pageable pageable = PageRequest.of(page-1, 4) ; 
    Page<User> users = this.userService.fetchUsers(pageable);
    List<User> listUsers = users.getContent() ; 
    model.addAttribute("users" , listUsers) ;
    model.addAttribute("currentPage" , page) ;
    model.addAttribute("totalPages" , users.getTotalPages()) ;

    return "admin/user/show";
  }

  @RequestMapping("/admin/user/{id}")
  public String getUserDetailPage(Model model, @PathVariable long id) {
    User user = this.userService.getUserById(id);
    model.addAttribute("user", user);
    model.addAttribute("id", id);
    return "admin/user/detail";
  }

  @GetMapping("/admin/user/create") // GET
  public String getCreateUserPage(Model model) {
    model.addAttribute("newUser", new User());
    return "admin/user/create";
  }

  @PostMapping(value = "/admin/user/create")
  public String createUserPage(Model model,
      @ModelAttribute("newUser") @Valid User spring, BindingResult newUserBindingResult,
      @RequestParam("springFile") MultipartFile file) {
    // List<FieldError> errors = newUserBindingResult.getFieldErrors();
    // for (FieldError error : errors) {
    // System.out.println(" >>>>>>> " + error.getField() + " - " +
    // error.getDefaultMessage());
    // }
    // Validate
    if (newUserBindingResult.hasErrors()) {
      return "/admin/user/create";
    }

    String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
    String hashPassword = this.passwordEncoder.encode(spring.getPassword());

    spring.setAvatar(avatar);
    spring.setPassword(hashPassword);
    spring.setRole(this.userService.getRoleByName(spring.getRole().getName()));

    // save
    this.userService.handleSaveUser(spring);
    return "redirect:/admin/user";
  }

  @RequestMapping("/admin/user/update/{id}")
  public String getUpdateUserDetailPage(Model model, @PathVariable long id) {
    User currentUser = this.userService.getUserById(id);
    model.addAttribute("newUser", currentUser);
    return "admin/user/update";
  }

  @PostMapping("/admin/user/update")
  public String postUpdateUser(Model model, @ModelAttribute("newUser") User spring) {
    User currentUser = this.userService.getUserById(spring.getId());
    if (currentUser != null) {
      currentUser.setAddress(spring.getAddress());
      currentUser.setFullName(spring.getFullName());
      currentUser.setPhone(spring.getPhone());
      this.userService.handleSaveUser(currentUser);
    }
    return "redirect:/admin/user";
  }

  @GetMapping("/admin/user/delete/{id}")
  public String getDeleteUserPage(Model model, @PathVariable long id) {
    model.addAttribute("id", id);
    // User user = new User();
    // user.setId(id);
    model.addAttribute("newUser", new User());
    return "admin/user/delete";
  }

  @PostMapping("/admin/user/delete")
  public String postDeleteUser(Model model, @ModelAttribute("newUser") User eric) {
    this.userService.deleteUser(eric.getId());
    return "redirect:/admin/user";
  }
}