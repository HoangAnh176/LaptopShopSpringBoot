<%@ page contentType="text/html" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

      <!DOCTYPE html>
      <html lang="en">

      <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng nhập</title>

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
            background-repeat: no-repeat;
            height: 100vh;
            /* height thay vì min-height */
            width: 100%;
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
                      <h2 class="text-uppercase text-center mb-5">Đăng nhập</h2>
                      <form method="post" action="/login">
                        <c:if test="${param.error != null}">
                          <div class="my-2" style="color: red;">Sai email hoặc mật khẩu.
                          </div>
                        </c:if>
                        <c:if test="${param.logout != null}">
                          <div class="my-2" style="color: green;">Đăng xuất thành công.
                          </div>
                        </c:if>

                        <!-- Email -->
                        <div class="form-outline mb-4">
                          <label class="form-label" for="form3Example3cg">Email</label>
                          <input type="email" id="form3Example3cg" class="form-control" name="username" path="email" />
                        </div>

                        <!-- Password  -->
                        <div class="form-outline mb-4">
                          <label class="form-label" for="form3Example4cg">Mật khẩu</label>
                          <input type="password" id="form3Example4cg" class="form-control" name="password"
                            path="password" />
                        </div>
                        <div>
                          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        </div>

                        <!-- Submit -->
                        <div class="d-flex justify-content-center">
                          <button type="submit"
                            class="btn btn-success btn-block btn-lg gradient-custom-4 text-body btn-custom">
                            Đăng nhập
                          </button>
                        </div>

                        <p class="text-center text-muted mt-5 mb-0">
                          Chưa có tài khoản ?
                          <a href="/register" class="fw-bold text-body"><u>Đăng ký</u></a>
                        </p>

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