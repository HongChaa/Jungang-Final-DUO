<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>

    <%@ include file="../common/static-head.jsp" %>

    <link rel="stylesheet" href="/assets/css/my-page/pwdchange.css">
    <script src="/assets/js/my-page/pwdchange.js" defer></script>
    <!--    <link rel="icon" href="/assets/img/main/simple-favicon-navy.png">-->
</head>
<body>

<div id="main-wrapper">
    <%@ include file="../common/header.jsp" %>
    <!--    마이페이지 좌측 메뉴바 영역 -->
    <div style="display: flex">

        <%@ include file="../my-page/my-page.jsp" %>


        <!-- 이 아래에 div 태그 열어서 width: 80% 으로 주기 -->
        <!--    마이페이지 우측 콘텐츠 영역 -->
        <div id="my-page-content">
            <!--        원하는 내용 추가 -->
            <div class="sub-wrapper">
                <div class="now-pwd">
                    <p>기존 비밀번호</p>
                    <input class="n-pwd" name="userPassword" type="password" id="user-password">
                    <div id="nowPwdChk"></div>
                </div>

                <div class="change-pwd">
                    <p>변경 비밀번호</p>
                    <input class="c-pwd" name="newPassword" type="password" id="new-user-password">
                    <div id="pwChk"></div>
                </div>

                <div class="pwd-check">
                    <p>비밀번호확인</p>
                    <input class="checkon-pwd" type="password" id="new-user-password-check">
                    <div id="pwChk2"></div>
                </div>

                <button class="alter-change" id="modifyBtn"><span>비밀번호변경</span></button>
            </div>
        </div>
    </div>

    <%@ include file="../common/footer.jsp" %>

</div>

</body>
</html>
