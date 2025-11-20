<%@page contentType="text/html" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
      <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
          <meta charset="utf-8" />
          <meta http-equiv="X-UA-Compatible" content="IE=edge" />
          <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
          <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
          <meta name="author" content="Hỏi Dân IT" />
          <title>Cập nhật sản phẩm</title>
          <link href="/css/styles.css" rel="stylesheet" />
          <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
          <script>
            $(document).ready(() => {
              const avatarFile = $("#avatarFile");
              const orgImage = "${newProduct.image}";
              if (orgImage) {
                const urlImage = "/images/product/" + orgImage;
                $("#avatarPreview").attr("src", urlImage);
                $("#avatarPreview").css({ "display": "block" });
              }

              avatarFile.change(function (e) {
                const imgURL = URL.createObjectURL(e.target.files[0]);
                $("#avatarPreview").attr("src", imgURL);
                $("#avatarPreview").css({ "display": "block" });
              });
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
                    <li class="breadcrumb-item"> <a href="/admin">Tổng quan </a></li>
                    <li class="breadcrumb-item active"><a href="/admin/product"> Sản phẩm </a></li>
                    <li class="breadcrumb-item active"> Cập nhật</li>
                  </ol>
                  <div class="container mt-5">
                    <div class="row">
                      <div class="col-md-6 col-12 mx-auto">
                        <h1> Cập nhật sản phẩm </h1>
                        <hr />
                        <form:form method="post" action="/admin/product/update" modelAttribute="newProduct" class="row"
                          enctype="multipart/form-data">
                          <!-- Thêm CSRF token -->
                          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                          <!-- Thêm hidden input cho id -->
                          <form:hidden path="id" />

                          <div class="mb-3 col-12 col-md-6">
                            <c:set var="errorName">
                              <form:errors path="name" cssClass="invalid-feedback" />
                            </c:set>
                            <label class="form-label">Tên</label>
                            <form:input type="text" class="form-control ${not empty errorName ? 'is-invalid' : ''}"
                              path="name" />
                            ${errorName}
                          </div>
                          <div class="mb-3 col-12 col-md-6">
                            <c:set var="errorPrice">
                              <form:errors path="price" cssClass="invalid-feedback" />
                            </c:set>
                            <label class="form-label">Giá</label>
                            <form:input type="number" class="form-control ${not empty errorPrice ? 'is-invalid' : ''}"
                              path="price" step="0.01" />
                            ${errorPrice}
                          </div>

                          <div class="mb-3 col-12">
                            <c:set var="errorDetailDesc">
                              <form:errors path="detailDesc" cssClass="invalid-feedback" />
                            </c:set>
                            <label class="form-label">Mô tả đầy đủ</label>
                            <form:textarea type="text"
                              class="form-control ${not empty errorDetailDesc ? 'is-invalid' : ''}" path="detailDesc" />
                            ${errorDetailDesc}
                          </div>


                          <div class="mb-3 col-12 col-md-6">
                            <c:set var="errorShortDesc">
                              <form:errors path="shortDesc" cssClass="invalid-feedback" />
                            </c:set>
                            <label class="form-label">Mô tả ngắn gọn</label>
                            <form:input type="text" class="form-control ${not empty errorShortDesc ? 'is-invalid' : ''}"
                              path="shortDesc" />
                            ${errorShortDesc}
                          </div>

                          <div class="mb-3 col-12 col-md-6">
                            <c:set var="errorQuantity">
                              <form:errors path="quantity" cssClass="invalid-feedback" />
                            </c:set>
                            <label class="form-label">Số lượng</label>
                            <form:input type="number"
                              class="form-control ${not empty errorQuantity ? 'is-invalid' : ''}" path="quantity" />
                            ${errorQuantity}
                          </div>

                          <div class="mb-3 col-12 col-md-6">
                            <label class="form-label">Nhà sản xuất</label>
                            <form:select class="form-select" path="factory">
                              <form:option value="APPLE">Apple (MacBook)</form:option>
                              <form:option value="ASUS">Asus</form:option>
                              <form:option value="DELL">Dell</form:option>
                              <form:option value="ACER">Acer</form:option>
                              <form:option value="LENOVO">Lenovo</form:option>
                              <form:option value="LG">LG</form:option>
                            </form:select>
                          </div>
                          <div class="mb-3 col-12 col-md-6">
                            <label class="form-label">Mục tiêu</label>
                            <form:select class="form-select" path="target">
                              <form:option value="GAMING">Gaming</form:option>
                              <form:option value="SINHVIEN-VANPHONG">Sinh viên - Văn phòng</form:option>
                              <form:option value="THIET-KE-DO-HOA">Thiết kế đồ họa</form:option>
                              <form:option value="MONG-NHE">Mỏng nhẹ</form:option>
                              <form:option value="DOANH-NHAN ">Doanh nhân</form:option>
                            </form:select>
                          </div>
                          <div class="mb-3 col-12 col-md-6">
                            <label for="avatarFile" class="form-label">Ảnh</label>
                            <input class="form-control" type="file" id="avatarFile" name="springFile"
                              accept=".png, .jpg, .jpeg" />
                          </div>
                          <div class="mb-3 col-12">
                            <img style="max-height: 250px; display: none;" id="avatarPreview" alt="Avatar preview">
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