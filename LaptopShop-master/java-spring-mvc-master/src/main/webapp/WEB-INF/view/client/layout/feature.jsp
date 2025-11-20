<%@page contentType="text/html" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <!-- Font Awesome (nếu chưa có) -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">

    <style>
      .featurs-item {
        transition: all 0.3s ease-in-out;
        border-radius: 1.5rem !important;
      }

      .featurs-item:hover {
        transform: translateY(-8px);
        box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
      }

      .featurs-icon {
        width: 80px;
        height: 80px;
        display: flex;
        align-items: center;
        justify-content: center;
        background: linear-gradient(45deg, #d32f2f, #ff4081);
        /* Gradient đỏ đẹp */
        border-radius: 50%;
      }
    </style>

    <!-- Featurs Section Start -->
    <div class="container-fluid featurs py-5" style="background-color: #ffeeee;">
      <div class="container py-5">
        <div class="row g-4">
          <!-- Free Shipping -->
          <div class="col-md-6 col-lg-3">
            <div class="featurs-item text-center bg-light p-4">
              <div class="featurs-icon mx-auto mb-4">
                <i class="fas fa-truck-fast fa-2x text-white"></i>
              </div>
              <div class="featurs-content">
                <h5>Free Shipping</h5>
                <p class="mb-0">Hỏa tốc trong 2h</p>
              </div>
            </div>
          </div>

          <!-- Security Payment -->
          <div class="col-md-6 col-lg-3">
            <div class="featurs-item text-center bg-light p-4">
              <div class="featurs-icon mx-auto mb-4">
                <i class="fas fa-lock fa-2x text-white"></i>
              </div>
              <div class="featurs-content">
                <h5>Security Payment</h5>
                <p class="mb-0">Giao dịch an toàn</p>
              </div>
            </div>
          </div>

          <!-- 30 Day Return -->
          <div class="col-md-6 col-lg-3">
            <div class="featurs-item text-center bg-light p-4">
              <div class="featurs-icon mx-auto mb-4">
                <i class="fas fa-arrow-rotate-left fa-2x text-white"></i>
              </div>
              <div class="featurs-content">
                <h5>30 Day Return</h5>
                <p class="mb-0">Đổi trả miễn phí</p>
              </div>
            </div>
          </div>

          <!-- 24/7 Support -->
          <div class="col-md-6 col-lg-3">
            <div class="featurs-item text-center bg-light p-4">
              <div class="featurs-icon mx-auto mb-4">
                <i class="fas fa-headset fa-2x text-white"></i>
              </div>
              <div class="featurs-content">
                <h5>24/7 Support</h5>
                <p class="mb-0">Hỗ trợ nhiệt tình</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Featurs Section End -->