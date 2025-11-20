<%@ page contentType="text/html" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

      <!DOCTYPE html>
      <html lang="en">

      <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Register</title>

        <!-- Bootstrap 5 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom CSS -->
        <style>
          html,
          body {
            height: 100%;
            margin: 0;
            padding: 0;
          }

          .gradient-custom-3 {
            background: linear-gradient(to right, rgba(132, 250, 176, 0.5), rgba(143, 211, 244, 0.5));
          }

          .gradient-custom-4 {
            background: linear-gradient(to right, rgba(132, 250, 176, 1), rgba(143, 211, 244, 1));
          }

          .bg-image {
            background-image: url('https://mdbcdn.b-cdn.net/img/Photos/new-templates/search-box/img4.webp');
            background-size: cover;
            background-position: center;
            min-height: 100vh;
          }

          .btn-custom {
            width: 100%;
            /* Tăng chiều dài nút */
            padding: 15px 0;
            /* Tăng chiều cao nút */
            font-size: 18px;
            /* Cỡ chữ lớn hơn */
            border-radius: 25px;
            /* Góc bo tròn mềm mại hơn */
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            /* Thêm bóng đổ cho nút */
            transition: all 0.3s ease;
            /* Hiệu ứng khi di chuột qua */
          }

          .btn-custom:hover {
            background-color: #4bbf73;
            /* Màu nền thay đổi khi hover */
            transform: translateY(-5px);
            /* Đẩy nút lên khi di chuột qua */
          }

          .btn-custom:active {
            transform: translateY(0);
            /* Khi nhấn vào nút, nút trở về trạng thái bình thường */
            box-shadow: 0 3px 7px rgba(0, 0, 0, 0.1);
            /* Hiệu ứng bóng khi nhấn */
          }
        </style>
      </head>

      <body>
        <section class="bg-image d-flex align-items-center justify-content-center">
          <div class="mask w-100 h-100 gradient-custom-3 d-flex align-items-center justify-content-center">
            <div class="container">
              <div class="row justify-content-center">
                <div class="col-md-9 col-lg-7 col-xl-6">
                  <div class="card" style="border-radius: 15px;">
                    <div class="card-body p-5">
                      <h2 class="text-uppercase text-center mb-5">Tạo tài khoản</h2>
                      <form:form method="post" action="/register" modelAttribute="registerUser"
                        enctype="multipart/form-data">
                        <c:set var="errorConfirmPassword">
                          <form:errors path="confirmPassword" cssClass="invalid-feedback" />
                        </c:set>
                        <c:set var="errorPassword">
                          <form:errors path="password" cssClass="invalid-feedback" />
                        </c:set>
                        <c:set var="errorEmail">
                          <form:errors path="email" cssClass="invalid-feedback" />
                        </c:set>
                        <c:set var="errorFirstName">
                          <form:errors path="firstName" cssClass="invalid-feedback" />
                        </c:set>
                        <c:set var="errorLastName">
                          <form:errors path="lastName" cssClass="invalid-feedback" />
                        </c:set>
                        <!-- First Name -->
                        <div class="form-outline mb-4">
                          <label class="form-label" for="form3Example1cg">Họ</label>
                          <form:input type="text" id="form3Example1cg"
                            class="form-control ${not empty errorFirstName ? 'is-invalid' : ''}" path="firstName" />
                          ${errorFirstName}
                        </div>

                        <!-- Last Name -->
                        <div class="form-outline mb-4">
                          <label class="form-label" for="form3Example1cg">Tên</label>
                          <form:input type="text" id="form3Example1cg"
                            class="form-control ${not empty errorLastName ? 'is-invalid' : ''}" path="lastName" />
                          ${errorLastName}
                        </div>

                        <!-- Email -->
                        <div class="form-outline mb-4">
                          <label class="form-label" for="form3Example3cg">Email</label>
                          <form:input type="email" id="form3Example3cg"
                            class="form-control ${not empty errorEmail ? 'is-invalid' : ''}" path="email" />
                          ${errorEmail}
                        </div>

                        <!-- Password -->
                        <div class="form-outline mb-4">
                          <label class="form-label" for="form3Example4cg">Mật khẩu</label>
                          <form:input type="password" id="form3Example4cg"
                            class="form-control ${not empty errorPassword ? 'is-invalid' : ''}" path="password" />
                          ${errorPassword}
                        </div>

                        <!-- Confirm Password -->
                        <div class="form-outline mb-4">
                          <label class="form-label" for="form3Example4cdg">Nhập lại mật khẩu</label>
                          <form:input type="password" id="form3Example4cdg"
                            class="form-control ${not empty errorConfirmPassword ? 'is-invalid' : ''}"
                            path="confirmPassword" />
                          ${errorConfirmPassword}
                        </div>

                        <div class="mb-3 col-12 col-md-6">
                          <label for="avatarFile" class="form-label">Avatar</label>
                          <input class="form-control" type="file" id="avatarFile" name="springFile"
                            accept=".png, .jpg, .jpeg" />
                        </div>
                        <div class="mb-3 col-12">
                          <img style="max-height: 250px; display: none;" id="avatarPreview" alt="Avatar preview">
                        </div>

                        <!-- Submit -->
                        <div class="d-flex justify-content-center">
                          <button type="submit"
                            class="btn btn-success btn-block btn-lg gradient-custom-4 text-body btn-custom">
                            Đăng ký
                          </button>
                        </div>

                        <p class="text-center text-muted mt-5 mb-0">
                          Đã có tài khoản ?
                          <a href="/login" class="fw-bold text-body"><u>Đăng nhập</u></a>
                        </p>
                      </form:form>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          </div>
        </section>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
      </body>

      </html>