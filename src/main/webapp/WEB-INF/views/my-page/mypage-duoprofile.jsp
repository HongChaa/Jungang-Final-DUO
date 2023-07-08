<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- -----------------------페이지 인클루드 영역 ----------------------------------------------- -->
<!doctype html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <!-- <link rel="stylesheet" href="/assets/css/common.css"> -->
    <%@ include file="../common/static-head.jsp" %>
    <link rel="stylesheet" href="/assets/css/my-page/mypage-duoprofile.css">
    <script src="/assets/js/my-page/mypage-duoprofile.js" defer></script>
    <!--    <link rel="icon" href="/assets/img/main/simple-favicon-navy.png">-->
</head>

<body>
    <!-- -----------------------페이지 인클루드 영역 ----------------------------------------------- -->
    <div id="main-wrapper">
        <%@ include file="../common/header.jsp" %>
        <div id="my-page-wrapper">
            <%@ include file="../my-page/my-page.jsp" %>
            <!--    마이페이지 우측 콘텐츠 영역 -->
            <div id="my-page-content">
                <div id="duo-profile-box">
                    <!--        프로필 사진 -->
                    <!-- <img id="duo-tier-image" src="/assets/img/mypage/TFT_Regalia_Challenger.png" alt="tier"> -->
                    <div id="duo-image-frame">
                        <c:if test="${login.userProfileImage != null}">
                            <img id="duo-profile-image" src="${login.userProfileImage}" alt="profile-image">
                        </c:if>
                        <c:if test="${login.userProfileImage == null}">
                            <img id="duo-profile-image" src="/assets/img/main/basic-profile.png" alt="profile-image">
                        </c:if>
                    </div>
                    <!-- 포지션 선택 및 자기소개 -->
                    <div id="position-comment-box">
                        <form id="position-comment-form" action="/user/register-duo" method="post">
                            <!-- 포지션 선택 -->
                            <div id="preferred-position" data-position="${login.userPosition}">
                                <p id="preferred-position-title">주포지션</p>
                                <label class="position-option">
                                    <input class="select-position" type="radio" name="userPosition" value="ALL">
                                    <img class="position-image" src="/assets/img/mypage/ALL.png" alt="ALL">
                                </label>
                                <label class="position-option">
                                    <input class="select-position" type="radio" name="userPosition" value="TOP">
                                    <img class="position-image" src="/assets/img/mypage/TOP.png" alt="TOP"></label>
                                <label class="position-option">
                                    <input class="select-position" type="radio" name="userPosition" value="JUG">
                                    <img class="position-image" src="/assets/img/mypage/JGL.png" alt="JGL"></label>
                                <label class="position-option">
                                    <input class="select-position" type="radio" name="userPosition" value="MID">
                                    <img class="position-image" src="/assets/img/mypage/MID.png" alt="MID"></label>
                                <label class="position-option">
                                    <input class="select-position" type="radio" name="userPosition" value="AD">
                                    <img class="position-image" src="/assets/img/mypage/AD.png" alt="AD"></label>
                                <label class="position-option">
                                    <input class="select-position" type="radio" name="userPosition" value="SUP">
                                    <img class="position-image" src="/assets/img/mypage/SUP.png" alt="SUP"></label>
                            </div>
                            <!--  자기소개 -->
                            <label><textarea id="comment" name="userComment" spellcheck="false" placeholder="자유롭게 자기소개를 입력해주세요"
                                    autofocus>${login.userComment}</textarea></label>
                            <div id="submit-box">
                                <p id="matching-point-title">매칭포인트</p><label id="matching-point-label">
                                    <input id="matching-point" name="userMatchingPoint" value="${login.userMatchingPoint}" data-matchingpoint="${login.userMatchingPoint}"><span>POINT</span></label>
                                <c:if test="${login.userMatchingPoint == 0}">
                                    <div class="duo-btn" id="register-duo">듀오 등록</div>
                                </c:if>
                                <c:if test="${login.userMatchingPoint != 0}">
                                    <div class="duo-btn" id="modify-duo">수정</div>
                                    <div class="duo-btn" id="delete-duo">삭제</div>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="../common/footer.jsp" %>
</body>

</html>