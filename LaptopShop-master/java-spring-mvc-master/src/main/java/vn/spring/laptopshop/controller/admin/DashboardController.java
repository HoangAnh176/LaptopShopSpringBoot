package vn.spring.laptopshop.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import vn.spring.laptopshop.domain.Order;
import vn.spring.laptopshop.domain.Product;
import vn.spring.laptopshop.domain.User;
import vn.spring.laptopshop.service.OrderService;
import vn.spring.laptopshop.service.ProductService;
import vn.spring.laptopshop.service.UserService;

@Controller  
public class DashboardController {

  private final UserService userService;

  private final ProductService productService ;
  
  private final OrderService orderService ;


  public DashboardController(UserService userService , ProductService productService , OrderService orderService) {
    this.userService = userService;
    this.productService = productService ; 
    this.orderService = orderService ;
  }


  @GetMapping("/admin")
  public String getDashboard(Model model, @AuthenticationPrincipal UserDetails userDetails, HttpSession session) {

    List<User> users = this.userService.getAllUsers() ;
    int countUsers = 0 ;
    for ( User users2 : users ) {
      if(users2 != null) {
        countUsers += 1 ; // đếm ds ng dùng
      }
    }

    List<Product> products = this.productService.getAllProducts() ;
    int countProducts = 0 ;
    for ( Product pro : products ) {
      if ( pro != null ) {
        countProducts += 1 ; // đếm danh sáhc sp
      }
    }

    List<Order> orders = this.orderService.getAllOrders() ;
    int countOrders = 0 ; 
    for ( Order ord : orders) {
      if ( ord != null ) {
        countOrders += 1 ; // đếm ds đơn hàng
      }
    }

    // đếm đơn hàng chờ xác nhận
    int countOrdersPending = 0 ;
    for ( Order ord : orders ) {
      if ( ord.getStatus().equals("PENDING")) {
        countOrdersPending += 1 ;
      }
    }

    // đếm đơn hàng đang vận chuyển
    int countOrdersShipping = 0 ; 
    for ( Order ord : orders ) {
      if ( ord.getStatus().equals("SHIPPING")) {
        countOrdersShipping += 1 ;
      }
    }

    // đếm đơn hàng đã hoàn tất
    int countOrdersComplete = 0 ;
    for ( Order ord : orders ) {
      if ( ord.getStatus().equals("COMPLETE")) {
        countOrdersComplete += 1 ;
      }
    }

    User user = this.userService.findUserByEmail(userDetails.getUsername()) ;
    session.setAttribute("currentUserLogin", user.getFullName());

    model.addAttribute("countUsers", countUsers);
    model.addAttribute("countProducts", countProducts);
    model.addAttribute("countOrders", countOrders);

    model.addAttribute("pending" , countOrdersPending) ;
    model.addAttribute("shipping" , countOrdersShipping) ; 
    model.addAttribute("complete" , countOrdersComplete) ;

    List<Order> dataTable = new ArrayList<>() ;
    for ( Order ord : orders ) {
      if ( ord.getStatus().equals("COMPLETE")) {
        dataTable.add(ord) ;
      }
    }
    model.addAttribute("orders", dataTable);
    return "admin/dashboard/show";
  }
}
