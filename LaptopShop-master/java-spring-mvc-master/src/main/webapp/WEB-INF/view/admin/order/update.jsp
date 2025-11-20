<%@page contentType="text/html" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
      <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
          <meta charset="utf-8" />
          <meta http-equiv="X-UA-Compatible" content="IE=edge" />
          <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
          <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
          <meta name="author" content="Hỏi Dân IT" />
          <title>Update Order</title>
          <link href="/css/styles.css" rel="stylesheet" />
          <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

          <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        </head>

        <body class="sb-nav-fixed">
          <jsp:include page="../layout/header.jsp" />
          <div id="layoutSidenav">
            <jsp:include page="../layout/sidebar.jsp" />
            <div id="layoutSidenav_content">
              <main>
                <div class="container-fluid px-4">
                  <h1 class="mt-4">Quản lý đơn hàng</h1>
                  <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item"> <a href="/admin">Tổng quan </a></li>
                    <li class="breadcrumb-item active"><a href="/admin/order"> Đơn hàng </a></li>
                    <li class="breadcrumb-item active"> Cập nhật </li>
                  </ol>
                  <div class="container mt-5">
                    <div class="row">
                      <div class="col-md-6 col-12 mx-auto">
                        <h1> Cập nhật người dùng </h1>
                        <hr />
                        <form:form method="post" action="/admin/order/update" modelAttribute="newOrder" class="row"
                          enctype="multipart/form-data">
                          <div class="mb-3" style="display: none;">
                            <label class="form-label">Id </label>
                            <form:input type="text" class="form-control" path="id" />
                          </div>
                          <div class="mb-3 alert alert-warning">
                            <div class="mb-3 col-12">
                              <label> ID đơn hàng = ${newOrder.id} </label>
                            </div>
                            <div class="col-12">
                              <label class="form-label">Tổng giá trị </label>
                              <fmt:formatNumber type="number" value="${newOrder.totalPrice}" /> đ
                            </div>

                          </div>

                          <div class="mb-3 col-12">
                            <label class="form-label">Người dùng:</label>
                            <form:input type="text" class="form-control" disabled="true" path="user.fullName" />
                          </div>

                          <div class="mb-3 col-12 col-md-6">
                            <label class="form-label">Trạng thái:</label>
                            <form:select class="form-select" path="status">
                              <form:option value="PENDING">PENDING</form:option>
                              <form:option value="SHIPPING">SHIPPING</form:option>
                              <form:option value="COMPLETE">COMPLETE</form:option>
                              <form:option value="CANCEL">CANCEL</form:option>
                            </form:select>
                          </div>


                          <div class="mb-5 col-12">
                            <button type="submit" class="btn btn-warning">Cập nhật</button>
                          </div>

                        </form:form>
                      </div>
                    </div>
                  </div>
                </div>
              </main>
              <jsp:include page="../layout/footer.jsp" />
            </div>
          </div>
          <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
            crossorigin="anonymous"></script>
          <script src="/js/scripts.js"></script>
        </body>

        </html>