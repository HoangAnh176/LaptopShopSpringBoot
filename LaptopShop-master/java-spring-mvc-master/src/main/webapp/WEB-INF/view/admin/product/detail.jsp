<%@page contentType="text/html" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
      <!DOCTYPE html>
      <html lang="en">

      <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
        <meta name="author" content="Hỏi Dân IT" />
        <title>Chi tiết sản phẩm</title>
        <link href="/css/styles.css" rel="stylesheet" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script>
          $(document).ready(() => {
            const orgImage = "${product.image}";
            if (orgImage) {
              const urlImage = "/images/product/" + orgImage;
              $("#avatarPreview").attr("src", urlImage);
              $("#avatarPreview").css({ "display": "block" });
            }
          });
        </script>
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
      </head>

      <body class="sb-nav-fixed">
        <jsp:include page="../layout/header.jsp" />
        <div id="layoutSidenav">
          <jsp:include page="../layout/sidebar.jsp" />
          <div id="layoutSidenav_content">
            <main>
              <div class="container-fluid px-4">
                <h1 class="mt-4">Quản lý sản phẩm</h1>
                <ol class="breadcrumb mb-4">
                  <li class="breadcrumb-item"> <a href="/admin"> Tổng quan </a></li>
                  <li class="breadcrumb-item active"><a href="/admin/product"> Sản phẩm </a></li>
                  <li class="breadcrumb-item active"> ID sản phẩm = ${id}</li>
                </ol>
                <div class="container mt-5">
                  <div class="row">
                    <div class="col-12 mx-auto">
                      <div class="d-flex justify-content-between">
                        <h3> ID sản phẩm = ${id}</h3>
                      </div>
                      <hr />
                      <div class="card" style="width: 60%;">
                        <img style="display: none;" id="avatarPreview" alt="Product preview">
                        <!-- <img src="/images/product/${product.image}" id="avatarPreview" alt="Product preview"> -->
                        <div class="card-header">
                          Thông tin sản phẩm
                        </div>
                        <ul class="list-group list-group-flush">
                          <li class="list-group-item">ID: ${product.id}</li>
                          <li class="list-group-item">Tên: ${product.name}</li>
                          <li class="list-group-item">Mô tả đầy đủ: ${product.detailDesc}</li>
                          <li class="list-group-item">Mô tả ngắn gọn: ${product.shortDesc}</li>
                          <li class="list-group-item">Số lượng: ${product.quantity}</li>
                          <li class="list-group-item">Nhà sản xuất: ${product.factory}</li>
                          <li class="list-group-item">Mục tiêu: ${product.target}</li>
                        </ul>
                      </div>
                      <a href="/admin/user" class="btn btn-success mt-2">Quay về</a>
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