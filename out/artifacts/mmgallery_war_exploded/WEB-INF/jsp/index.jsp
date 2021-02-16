<%--
  Created by IntelliJ IDEA.
  User: sbt
  Date: 2021/2/9
  Time: 9:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>PaintingShopSBT</title>
    <link rel="stylesheet" type="text/css" href="css/common.css">
    <script type="text/javascript" src="js/js1.js"></script>
</head>
<body>
<c:if test="${param.c != null}">
    <c:set var="categoryParam" value="&c=${param.c}"></c:set>
</c:if>
<c:if test="${param.c == null}">
    <c:set var="categoryParam" value=""></c:set>
</c:if>
<div class="header">
    <div class="logo">
        <img src="image/logo.png">
    </div>
    <div class="menu" onmouseleave="close_menu()">
        <div class="menu_title" onclick="show_menu()">
            <a href="#">内容分类</a>
        </div>
        <ul id = "menu1">
            <li><a href="/mmgallery_war_exploded/page?c=1">现实主义</a></li>
            <li><a href="/mmgallery_war_exploded/page?c=2">抽象主义</a></li>
        </ul>
    </div>
    <div class="user">
        <ul>
            <li><a href="#">注册</a></li>
            <li><a href="#">登录</a></li>
        </ul>
    </div>
</div>
<div class="content">
    <div class="banner">
        <img src="image/welcome.png" class="banner_img">
    </div>
    <div class="img-content">
        <ul>
            <c:forEach items="${pageModel.pageData}" var="painting">
                <li>
                    <img src="${painting.preview}" class="img-li">
                    <div class="info">
                        <h3>${painting.pname}</h3>
                        <p>
                            ${painting.description }
                        </p>
                        <div class="img-btn">
                            <div class="price"><fmt:formatNumber pattern="0.00" value="${painting.price}"></fmt:formatNumber></div>
                            <a href="#" class="cart">
                                <div class="btn">
                                    <img src="image/cart.svg">
                                </div>
                            </a>
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
    <div class="page-nav">
        <ul>
            <li><a href="/mmgallery_war_exploded/page?p=1${categoryParam}">首页</a></li>
            <li><a href="/mmgallery_war_exploded/page?p=${pageModel.hasPreviousPage?pageModel.page-1:1}${categoryParam}">上一页</a></li>
            <c:forEach begin="1" end="${pageModel.totalPages}" var="pno" step="1">
                <li><span ${pno==pageModel.page?"class='first-page'":""}>
                    <a href="/mmgallery_war_exploded/page?p=${pno}${categoryParam}">
                        ${pno}
                    </a>
                </span></li>
            </c:forEach>

            <li><a href="/mmgallery_war_exploded/page?p=${pageModel.hasNextPage?pageModel.page+1:pageModel.totalPages}${categoryParam}">下一页</a></li>
            <li><a href="/mmgallery_war_exploded/page?p=${pageModel.totalPages}${categoryParam}">尾页</a></li>
        </ul>
    </div>
</div>
<div class="footer">
    <p><span>M-GALLARY</span>©2020 POWERED BY MINOS-TY</p>
</div>
</body>
</html>
