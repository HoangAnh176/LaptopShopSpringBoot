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
        <title>Update user</title>
        <link href="/css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <style>
          .error {
            color: red;
            font-size: 0.9em;
            display: none;
          }
        </style>
      </head>

      <body class="sb-nav-fixed">
        <jsp:include page="../layout/header.jsp" />
        <div id="layoutSidenav">
          <jsp:include page="../layout/sidebar.jsp" />
          <div id="layoutSidenav_content">
            <main>
              <div class="container-fluid px-4">
                <h1 class="mt-4">Quản lý người dùng</h1>
                <ol class="breadcrumb mb-4">
                  <li class="breadcrumb-item"><a href="/admin">Tổng quan</a></li>
                  <li class="breadcrumb-item active"><a href="/admin/user">Người dùng</a></li>
                  <li class="breadcrumb-item active">Cập nhật</li>
                </ol>
                <div class="container mt-5">
                  <div class="row">
                    <div class="col-md-6 col-12 mx-auto">
                      <h1>Cập nhật người dùng</h1>
                      <hr />
                      <form:form method="post" action="/admin/user/update" modelAttribute="newUser"
                        onsubmit="return validateForm()">
                        <div class="mb-3" style="display: none;">
                          <label class="form-label">Id</label>
                          <form:input type="text" class="form-control" path="id" />
                        </div>
                        <div class="mb-3">
                          <label class="form-label">Email</label>
                          <form:input type="email" class="form-control" path="email" disabled="true" />
                        </div>
                        <div class="mb-3">
                          <label class="form-label">Số điện thoại</label>
                          <form:input type="text" class="form-control" path="phone" pattern="[0-9]{10,11}"
                            title="Phone number must contain only numbers and be 10-11 digits long" required="true" />
                          <span id="phoneError" class="error">Số điện thoại chỉ chứa số (10-11
                            số).</span>
                        </div>
                        <div class="mb-3">
                          <label class="form-label">Tên đầy đủ</label>
                          <form:input type="text" class="form-control" path="fullName" pattern="[A-Za-zÀ-ỹ\s]+"
                            title="Full name must contain only letters and spaces" required="true" />
                          <span id="nameError" class="error">Tên đầy đủ chỉ chứa chữ và dấu cách(có thể chứa chữ tiếng
                            việt).</span>
                        </div>
                        <div class="mb-3">
                          <label class="form-label">Địa chỉ</label>
                          <form:input type="text" class="form-control" path="address" />
                        </div>
                        <button type="submit" class="btn btn-warning">Cập nhật</button>
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
        <script>
          function validateForm() {
            const phone = document.querySelector("input[name='phone']").value;
            const fullName = document.querySelector("input[name='fullName']").value;
            const phoneError = document.getElementById("phoneError");
            const nameError = document.getElementById("nameError");
            let isValid = true;

            // Kiểm tra phone (chỉ số, 10-11 chữ số)
            if (!/^[0-9]{10,11}$/.test(phone)) {
              phoneError.style.display = "block";
              isValid = false;
            } else {
              phoneError.style.display = "none";
            }

            // Kiểm tra fullName (chỉ chữ và khoảng trắng, bao gồm ký tự tiếng Việt)
            if (!/^[A-Za-zÀ-ỹ\s]+$/.test(fullName)) {
              nameError.style.display = "block";
              isValid = false;
            } else {
              nameError.style.display = "none";
            }

            return isValid;
          }

          // Thêm sự kiện kiểm tra khi nhập liệu
          document.querySelector("input[name='phone']").addEventListener("input", function () {
            const phoneError = document.getElementById("phoneError");
            if (!/^[0-9]{0,11}$/.test(this.value)) {
              phoneError.style.display = "block";
            } else {
              phoneError.style.display = "none";
            }
          });

          document.querySelector("input[name='fullName']").addEventListener("input", function () {
            const nameError = document.getElementById("nameError");
            if (!/^[A-Za-zÀ-ỹ\s]*$/.test(this.value)) {
              nameError.style.display = "block";
            } else {
              nameError.style.display = "none";
            }
          });
        </script>
      </body>

      </html>