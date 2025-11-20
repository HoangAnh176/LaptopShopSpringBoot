<%@page contentType="text/html" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <style>
      /* Hover màu đỏ cho nav-link */
      .sb-sidenav .nav-link:hover {
        background-color: #e53935 !important;
        /* đỏ tươi */
        color: white !important;
      }

      .sb-sidenav .nav-link:hover .sb-nav-link-icon i {
        color: white !important;
      }
    </style>
    <div id="layoutSidenav_nav">
      <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
        <div class="sb-sidenav-menu">
          <div class="nav">
            <div class="sb-sidenav-menu-heading">Tính năng</div>

            <a class="nav-link" href="/admin">
              <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
              Tổng quan
            </a>

            <a class="nav-link" href="/admin/user">
              <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
              Quản lý người dùng
            </a>

            <a class="nav-link" href="/admin/product">
              <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
              Quản lý sản phẩm
            </a>

            <a class="nav-link" href="/admin/order">
              <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
              Quản lý đơn hàng
            </a>

            <div class="sb-sidenav-menu-heading">Khác</div>

            <a class="nav-link" href="/admin/cmtCus">
              <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
              Đánh giá
            </a>
          </div>
        </div>
        <div class="sb-sidenav-footer">
          <div class="small">Đăng nhập bằng :</div>
          <c:out value="${pageContext.request.userPrincipal.name}" />
        </div>
      </nav>
    </div>