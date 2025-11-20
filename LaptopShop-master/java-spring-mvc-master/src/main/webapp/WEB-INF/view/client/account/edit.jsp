<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8">
                <title>Chỉnh sửa thông tin - Laptopshop</title>
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

                <!-- Custom CSS -->
                <style>
                    .error {
                        color: red;
                        font-size: 0.9em;
                        display: none;
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

                <!-- Edit Account Page Start -->
                <div class="container-fluid py-5">
                    <div class="container py-5">
                        <h2 class="text-center">Chỉnh sửa thông tin cá nhân</h2>
                        <div class="row justify-content-center">
                            <div class="col-md-6">
                                <form:form method="post" action="/account/update" modelAttribute="newUser1"
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
                                        <label class="form-label">Phone number</label>
                                        <form:input type="text" class="form-control" path="phone" pattern="[0-9]{10,11}"
                                            title="Số điện thoại chỉ được chứa số và có độ dài 10-11 chữ số"
                                            required="true" />
                                        <form:errors path="phone" cssClass="error" />
                                        <span id="phoneError" class="error">Số điện thoại chỉ được chứa số (10-11 chữ
                                            số).</span>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Full Name</label>
                                        <form:input type="text" class="form-control" path="fullName"
                                            pattern="[A-Za-zÀ-ỹ\s]+"
                                            title="Họ tên chỉ được chứa chữ cái và khoảng trắng (bao gồm ký tự tiếng Việt)"
                                            required="true" />
                                        <form:errors path="fullName" cssClass="error" />
                                        <span id="fullNameError" class="error">Họ tên chỉ được chứa chữ cái và khoảng
                                            trắng (bao gồm ký tự tiếng Việt).</span>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Address</label>
                                        <form:input type="text" class="form-control" path="address" />
                                    </div>
                                    <button type="submit" class="btn btn-warning">Update</button>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Edit Account Page End -->

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

                <!-- Custom JavaScript -->
                <script>
                    function validateForm() {
                        const fullName = document.querySelector("input[name='fullName']").value;
                        const phone = document.querySelector("input[name='phone']").value;
                        const fullNameError = document.getElementById("fullNameError");
                        const phoneError = document.getElementById("phoneError");
                        let isValid = true;

                        // Kiểm tra fullName (chỉ chữ và khoảng trắng, bao gồm ký tự tiếng Việt)
                        if (!/^[A-Za-zÀ-ỹ\s]+$/.test(fullName)) {
                            fullNameError.style.display = "block";
                            isValid = false;
                        } else {
                            fullNameError.style.display = "none";
                        }

                        // Kiểm tra phone (chỉ số, 10-11 chữ số)
                        if (!/^[0-9]{10,11}$/.test(phone)) {
                            phoneError.style.display = "block";
                            isValid = false;
                        } else {
                            phoneError.style.display = "none";
                        }

                        return isValid;
                    }

                    // Thêm sự kiện kiểm tra khi nhập liệu
                    document.querySelector("input[name='fullName']").addEventListener("input", function () {
                        const fullNameError = document.getElementById("fullNameError");
                        if (!/^[A-Za-zÀ-ỹ\s]*$/.test(this.value)) {
                            fullNameError.style.display = "block";
                        } else {
                            fullNameError.style.display = "none";
                        }
                    });

                    document.querySelector("input[name='phone']").addEventListener("input", function () {
                        const phoneError = document.getElementById("phoneError");
                        if (!/^[0-9]{0,11}$/.test(this.value)) {
                            phoneError.style.display = "block";
                        } else {
                            phoneError.style.display = "none";
                        }
                    });
                </script>
            </body>

            </html>