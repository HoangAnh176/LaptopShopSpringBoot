<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="utf-8" />
            <meta http-equiv="X-UA-Compatible" content="IE=edge" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
            <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
            <meta name="author" content="Hỏi Dân IT" />
            <title>Đánh giá</title>
            <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
            <link href="/css/styles.css" rel="stylesheet" />
            <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        </head>

        <body class="sb-nav-fixed">
            <jsp:include page="../layout/header.jsp" />
            <div id="layoutSidenav">
                <jsp:include page="../layout/sidebar.jsp" />
                <div id="layoutSidenav_content">
                    <main>
                        <div class="container-fluid px-4">
                            <h1 class="mt-4">Đánh giá</h1>
                            <ol class="breadcrumb mb-4">
                                <li class="breadcrumb-item"><a href="/admin">Tổng quan</a></li>
                                <li class="breadcrumb-item active"><a href="/admin/cmtCus">Đánh giá</a></li>
                            </ol>
                            <div class="mt-5">
                                <div class="row">
                                    <div class="col-12 mx-auto">
                                        <div class="d-flex justify-content-between">
                                            <h3>Bảng đánh giá</h3>
                                        </div>
                                        <hr />
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th scope="col">ID</th>
                                                    <th scope="col">Tên</th> <!-- Thay bằng email -->
                                                    <th scope="col">Tên sản phẩm</th>
                                                    <th scope="col">Đánh giá</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="feed" items="${feeds}">
                                                    <tr>
                                                        <th scope="row">${feed.user.id}</th>
                                                        <td>${feed.user.fullName}</td>
                                                        <!-- Sử dụng email thay vì fullname -->
                                                        <td style="font-size: 17px;">${feed.product.name}</td>
                                                        <td>${feed.content}</td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                        <nav aria-label="Page navigation example">
                                            <ul class="pagination justify-content-center">
                                                <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                                                    <a class="page-link" href="/admin/cmtCus?page=${currentPage - 1}"
                                                        aria-label="Previous">
                                                        <span aria-hidden="true">«</span>
                                                    </a>
                                                </li>
                                                <c:if test="${totalPage > 0}">
                                                    <c:forEach begin="1" end="${totalPage}" varStatus="loop">
                                                        <li
                                                            class="page-item ${loop.index eq currentPage ? 'active' : ''}">
                                                            <a class="page-link"
                                                                href="/admin/cmtCus?page=${loop.index}">${loop.index}</a>
                                                        </li>
                                                    </c:forEach>
                                                </c:if>
                                                <c:if test="${totalPage == 0}">
                                                    <li class="page-item disabled">
                                                        <a class="page-link" href="#">Không có trang</a>
                                                    </li>
                                                </c:if>
                                                <li class="page-item ${currentPage >= totalPage ? 'disabled' : ''}">
                                                    <a class="page-link" href="/admin/cmtCus?page=${currentPage + 1}"
                                                        aria-label="Next">
                                                        <span aria-hidden="true">»</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </nav>
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