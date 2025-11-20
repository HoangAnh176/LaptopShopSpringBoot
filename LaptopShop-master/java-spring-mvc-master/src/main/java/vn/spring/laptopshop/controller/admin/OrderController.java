package vn.spring.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.io.source.ByteArrayOutputStream;

import vn.spring.laptopshop.domain.Order;
import vn.spring.laptopshop.service.EmailService;
import vn.spring.laptopshop.service.OrderService;

@Controller  
public class OrderController {

  private final OrderService orderService;
  private final EmailService emailService ;

  public OrderController(OrderService orderService , EmailService emailService) {
    this.orderService = orderService;
    this.emailService = emailService;
  }

  @GetMapping("/admin/order")
  public String getDashboard(Model model ,  @RequestParam(value = "page", defaultValue = "1") int page) {
    Pageable pageable = PageRequest.of(page-1, 4) ; 
    Page<Order> orders = this.orderService.fetchOrders(pageable) ; 
    List<Order> listOrders = orders.getContent() ;   
    model.addAttribute("orders", listOrders);
    model.addAttribute("currentPage" , page) ; 
    model.addAttribute("totalPages" , orders.getTotalPages()) ; 
    return "admin/order/show"; 
  }

  @GetMapping("/admin/order/{id}")
  public String getOrderDetailPage(Model model, @PathVariable long id) {
    Order order = this.orderService.getOrderById(id).get(); 
    model.addAttribute("order", order);
    model.addAttribute("id", id); 
    model.addAttribute("orderDetails", order.getOrderDetails()); 
    return "admin/order/detail"; 
  }

  @RequestMapping("/admin/order/update/{id}")
  public String getUpdateOrderStatusPage(Model model, @PathVariable long id) {
    Optional<Order> currentOrder = this.orderService.getOrderById(id);
    model.addAttribute("newOrder", currentOrder.get());
    return "admin/order/update";
  }

  @PostMapping("/admin/order/update")
  public String hanldeUpdateOrder(@ModelAttribute("newOrder") Order order) {
    this.orderService.updateOrder(order);
    return "redirect:/admin/order"; 
  }

  @GetMapping("/admin/order/delete/{id}")
  public String getDeleteOrderPage(Model model, @PathVariable long id) {
    model.addAttribute("id", id);
    model.addAttribute("newOrder", new Order()); 
    return "admin/order/delete"; 
  }

  // Xử lý form xóa đơn hàng
  @PostMapping("/admin/order/delete")
  public String postDeleteOrder(@ModelAttribute("newOrder") Order order) {
    this.orderService.deleteOrderById(order.getId()); 
    return "redirect:/admin/order"; 
  }
  
  // CONTROLLER ĐỂ KIỂM TRA TRẠNG THÁI, NẾU ĐÚNG TT THÌ GỌI generateInvoicePDF ĐỂ
  // TẠO PDF TRƯỚC RỒI SỬ DỤNG ADDATACHMENT Ở TRONG EMAILSERVICES ĐÍNH KÈM FILE
  // PDF NÀY GỬI MAIL
  @GetMapping("/admin/order/print-invoice/{id}")
  public String printInvoice(@PathVariable long id) {
    Optional<Order> orderOptional = this.orderService.getOrderById(id);
    if (orderOptional.isPresent()) {
      Order order = orderOptional.get();
      if ("COMPLETE".equals(order.getStatus())) {
        try {
          ByteArrayOutputStream pdfStream = orderService.generateInvoicePDF(order);
          emailService.sendInvoiceEmail(order, pdfStream.toByteArray());
        } catch (Exception e) {
          e.printStackTrace();
        } // ==> Ở SHOW JSP CÓ DÒNG KIỂM TRA TRẠNG THÁI ĐƠN HÀNG NẾU TRẠNG THÁI ĐÚNG THÌ
          // HIỆN BUTTON
      }
    }
    return "redirect:/admin/order/" + id;
  }
}