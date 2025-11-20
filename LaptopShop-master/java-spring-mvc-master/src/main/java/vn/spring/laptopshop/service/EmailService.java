package vn.spring.laptopshop.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import vn.spring.laptopshop.domain.Order;
import vn.spring.laptopshop.domain.User;

@Service
// sử dụng 2 thử viện chính là javamailsender và MimeMessageHelper để gửi email
// javamailsender để tạo mail và gửi mail
// MimeMessageHelper để hỗ trợ việc tạo nội dung email, bao gồm cả phần đính kèm
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendInvoiceEmail(Order order, byte[] pdfBytes) throws MessagingException {
        logger.debug("Sending invoice email for order ID: {}", order.getId());
        // khởi tạo
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        User user = order.getUser();
        String email = user != null ? user.getEmail() : "default@example.com";
        // KIỂM TRA VÀ TẠO NỘI DUNG
        helper.setTo(email);
        helper.setSubject("Your Order Invoice - Order #" + order.getId());
        helper.setText("Dear " + order.getReceiverName() + ",\n\n" +
                "Thank you for your purchase! Please find attached the invoice for your order #" + order.getId()
                + ".\n\n" +
                "Best regards,\nElcome Store");
        // ĐÍNH KÈM FILE PDF
        helper.addAttachment("invoice_order_" + order.getId() + ".pdf", new ByteArrayResource(pdfBytes));
        // GỬI EMAIL
        mailSender.send(message);
    }
}