<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8">
                <title>Chi tiết đơn hàng - Laptopshop</title>
                <meta content="width=device-width, initial-scale=1.0" name="viewport">
                <meta content="" name="keywords">
                <meta content="" name="description">

                <!-- Google Web Fonts -->
                <link rel="preconnect" href="https://fonts.googleapis.com">
                <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                <link
                    href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
                    rel="stylesheet">

                <!-- Icon Font Stylesheet -->
                <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
                <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
                    rel="stylesheet">

                <!-- Libraries Stylesheet -->
                <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
                <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

                <!-- Customized Bootstrap Stylesheet -->
                <link href="/client/css/bootstrap.min.css" rel="stylesheet">

                <!-- Template Stylesheet -->
                <link href="/client/css/style.css" rel="stylesheet">

                <!-- Custom Styles -->
                <style>
                    .product-image {
                        width: 80px;
                        height: 80px;
                    }

                    .product-info {
                        padding-left: 15px;
                    }

                    .product-price {
                        font-weight: bold;
                    }
                </style>
            </head>

            <body>

                <!-- Spinner Start -->
                <div id="spinner"
                    class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50 d-flex align-items-center justify-content-center">
                    <div class="spinner-grow text-primary" role="status"></div>
                </div>
                <!-- Spinner End -->

                <jsp:include page="../layout/header.jsp" />

                <!-- Order Detail Page Start -->
                <div class="container-fluid py-5">
                    <div class="container py-5" style="margin-top: 20px;">
                        <h2 class="text-center">Chi tiết đơn hàng</h2>
                        <p style="margin-top: 16px;">ID đơn hàng : ${order.id}</p>
                        <p>Trạng Thái: ${order.status}</p>
                        <p>Ngày đặt hàng: ${order.dateOrder}</p>

                        <div class="table-responsive" style="margin-top: 32px;">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col">Sản phẩm</th>
                                        <th scope="col">Tên</th>
                                        <th scope="col">Số lượng</th>
                                        <th scope="col">Giá</th>
                                        <th scope="col">Tổng cộng</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:set var="totalPrice" value="0" />
                                    <c:forEach var="orderDetail" items="${order.orderDetails}">
                                        <tr>
                                            <td>
                                                <img src="/images/product/${orderDetail.product.image}"
                                                    class="product-image rounded-circle" alt="">
                                            </td>
                                            <td class="product-info">
                                                <p class="mb-0 mt-4">
                                                    <a href="/product/${orderDetail.product.id}"
                                                        target="_blank">${orderDetail.product.name}</a>
                                                </p>
                                            </td>
                                            <td>
                                                <p class="mb-0 mt-4">${orderDetail.quantity}</p>
                                            </td>
                                            <td class="product-price">
                                                <fmt:formatNumber type="number" value="${orderDetail.price}" /> đ
                                            </td>
                                            <td>
                                                <fmt:formatNumber type="number"
                                                    value="${orderDetail.price * orderDetail.quantity}" /> đ
                                            </td>
                                        </tr>
                                        <c:set var="totalPrice"
                                            value="${totalPrice + (orderDetail.price * orderDetail.quantity)}" />
                                    </c:forEach>
                                    <tr>
                                        <td colspan="4" class="text-end"><strong>Tổng:</strong></td>
                                        <td>
                                            <fmt:formatNumber type="number" value="${totalPrice}" /> đ
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>
                <!-- Order Detail Page End -->

                <jsp:include page="../layout/footer.jsp" />


                <!-- Back to Top -->
                <a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i
                        class="fa fa-arrow-up"></i></a>

                <!-- JavaScript Libraries -->
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                <script src="/client/lib/easing/easing.min.js"></script>
                <script src="/client/lib/waypoints/waypoints.min.js"></script>
                <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
                <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>

                <!-- Template Javascript -->
                <script src="/client/js/main.js"></script>
            </body>

            </html>