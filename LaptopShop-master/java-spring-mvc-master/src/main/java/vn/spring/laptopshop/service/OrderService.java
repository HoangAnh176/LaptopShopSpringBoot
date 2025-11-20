package vn.spring.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import vn.spring.laptopshop.domain.Order;
import vn.spring.laptopshop.domain.OrderDetail;
import vn.spring.laptopshop.domain.User;
import vn.spring.laptopshop.repository.OrderDetailRepository;
import vn.spring.laptopshop.repository.OrderRepository;

@Service
public class OrderService {

  private final OrderRepository orderRepository;
  private final OrderDetailRepository orderDetailRepository;

  public OrderService(
      OrderRepository orderRepository,
      OrderDetailRepository orderDetailRepository) {
    this.orderRepository = orderRepository;
    this.orderDetailRepository = orderDetailRepository;
  }

  public Page<Order> fetchOrders(Pageable page){
    return this.orderRepository.findAll(page) ;
  }
  
  public List<Order> getAllOrders() {
    return this.orderRepository.findAll();
  }

  public Optional<Order> getOrderById(long id) {
    return this.orderRepository.findById(id);
  }

  // public Order findOrderById( long id ) {
  //   return this.orderRepository.findById(id) ;
  // }

  public void deleteOrderById(long id) {
    // delete order detail first
    Optional<Order> orOptional = this.getOrderById(id);
    if (orOptional.isPresent()) {
      Order order = orOptional.get();
      List<OrderDetail> orderDetails = order.getOrderDetails();
      for (OrderDetail orderDetail : orderDetails) {
        this.orderDetailRepository.deleteById(orderDetail.getId());
      }
    }
    // then delete order
    this.orderRepository.deleteById(id);
  }

  public List<OrderDetail> getAllOrderDetails() {
    return this.orderDetailRepository.findAll();
  }

  public Optional<OrderDetail> getOrderDetailById(long id) {
    return this.orderDetailRepository.findById(id);
  }

  public void deleteOrderDetailById(long id) {
    this.orderDetailRepository.deleteById(id);
  }

  public void updateOrder(Order order) {
    Optional<Order> orderOptional = this.getOrderById(order.getId());
    if (orderOptional.isPresent()) {
      Order currentOrder = orderOptional.get();
      currentOrder.setStatus(order.getStatus());
      this.orderRepository.save(currentOrder);
    }
  }

  public List<Order> fetchOrderByUser(User user) {
        return this.orderRepository.findByUser(user);
    }

  public ByteArrayOutputStream generateInvoicePDF(Order order) throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = new PdfWriter(baos);
    PdfDocument pdf = new PdfDocument(writer);
    Document document = new Document(pdf);

    // Adding title
    document.add(new Paragraph("Invoice #" + order.getId())
        .setFontSize(20)
        .setBold());

    // Customer information
    document.add(new Paragraph("Customer: " + order.getUser().getFullName()));
    document.add(new Paragraph("Email: " + order.getUser().getEmail()));
    document.add(new Paragraph("Order Date: " + order.getDateOrder()));

    // Order details table
    Table table = new Table(UnitValue.createPercentArray(new float[] { 50, 20, 30 }));
    table.setWidth(UnitValue.createPercentValue(100));

    // Table headers
    table.addHeaderCell("Product");
    table.addHeaderCell("Quantity");
    table.addHeaderCell("Price");

    // Table content
    for (OrderDetail detail : order.getOrderDetails()) {
      table.addCell(detail.getProduct().getName());
      table.addCell(String.valueOf(detail.getQuantity()));
      table.addCell(String.format("$%.2f", detail.getPrice()));
    }

    document.add(table);

    // Total amount
    document.add(new Paragraph("Total Amount: $" + String.format("%.2f", order.getTotalPrice()))
        .setBold());

    document.close();
    return baos;
  }
}
