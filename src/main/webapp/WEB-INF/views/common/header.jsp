<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<header>
    <h1 id="logo-wrapper">
        <a href="/">
            <img src="/assets/img/header/logo-for-header.png" alt="로고 이미지" id="logo">
        </a>
    </h1>

    <nav id="nav-bar">
        <nav id="user-nav-bar">
            <c:if test="${login == null}">
                <div class="user-icon-wrapper">
                    <form action="/user/sign-in" method="post">
                        <button id="sign-in-btn">
                            <img src="/assets/img/header/sign-in.png" alt="로그인 아이콘" class="user-icon">
                        </button>
                        <!--         로그인 모달           -->

                        <div id="sign-in-modal-wrapper" class="invisible">
                            <% String returnURI = request.getRequestURI().substring(15); %>
                            <input type="hidden" name = "requestURI" value = "<%=returnURI.substring(0, returnURI.length()-4)%>">
                            <div id="sign-in-modal" class="user-modal">
                                <div id="id-input-wrapper" class="input-wrapper">
                                    <label for="sign-in-user-account">이메일 주소</label>
                                    <input type="text" name="userAccount" id="sign-in-user-account">
                                </div>
                                <div id="pw-input-wrapper" class="input-wrapper">
                                    <label for="sign-in-user-pw">비밀번호</label>
                                    <input type="password" name="userPassword" id="sign-in-user-pw">
                                </div>
                                <div id="sns-sign-in-wrapper">
                                    <div id="kakao-wrapper"><a href="#"><img src="/assets/img/header/kakao.png"
                                                                             alt="카카오 아이콘"></a></div>
                                    <div id="google-wrapper"><a href="#"><img src="/assets/img/header/google.png"
                                                                              alt="구글 아이콘" id="sign-in-google"></a>
                                    </div>
                                    <div id="facebook-wrapper"><a href="#"><img src="/assets/img/header/facebook.png"
                                                                                alt="페이스북 아이콘"></a></div>
                                </div>
                                <div id="auto-sign-in-wrapper">

                                    <label>
                                        <input type="checkbox" name="autoLogin" id="sign-in-auto-login">
                                        자동 로그인
                                    </label>
                                    <button type="submit" id="sign-in-submit-btn" class="btn">로그인</button>
                                </div>
                                <div id="pw-forgot-wrapper">
                                    <a href="#" class="btn">비밀번호를 잊으셨나요?</a>
                                </div>
                            </div>
                        </div>

                    </form>
                </div>
                <div class="user-icon-wrapper">
                    <a href="/user/sign-up">
                        <img src="/assets/img/header/sign-up.png" alt="회원가입 아이콘" class="user-icon">
                    </a>
                </div>
                <!-- end of sign-in, up page -->
            </c:if>
            <!--        로그인 하면 위의 두 버튼 대신 아래의 버튼이 보입니다        -->
            <c:if test="${login != null}">
                <div id="loginUserInfo" data-user-account="${login.userAccount}" data-user-nickname = "${login.userNickname}" class="user-icon-wrapper">
                    <button id="user-info-btn">
                        <img src="/assets/img/header/my-page.png" alt="마이 페이지 아이콘" class="user-icon">
                    </button>

                    <div id="my-page-modal-wrapper" class="invisible">
                        <div id="my-page-modal" class="user-modal">
                            <div id="my-page-header">
                                <div id="profile-img-wrapper">
                                    <c:if test="${login.userProfileImage == null}">
                                        <img class = "myProfileImage" src="/assets/img/chattingModal/user.png" alt="프로필 이미지">
                                    </c:if>
                                    <c:if test="${login.userProfileImage != null}">
                                        <img class = "myProfileImage" src="${login.userProfileImage}" alt="프로필 이미지">
                                    </c:if>
                                </div>
                                <div id="nickname-wrapper">${login.userNickname}</div>
                            </div>
                            <div id="user-info-wrapper">
                                <div>
                                    라이엇 계정 : ${login.lolNickname}
                                </div>
                                <div>
                                    잔여 포인트 : ${login.userCurrentPoint} point
                                </div>
                            </div>
                            <div id="my-page-btn-wrapper">
                                <a class="btn" href="/user/my-page">마이페이지</a>
                                <a class="btn" href="/user/sign-out">로그아웃</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if> <!-- end of my page -->
        </nav>

        <nav id="common-nav-bar">
            <c:if test="${login.role eq 'ADMIN'}">
                <div class="common-menu-wrapper">
                    <a href="/user/admin">관리자 페이지</a>
                </div>
            </c:if>
            <div class="common-menu-wrapper">
                <a href="/board/list">커뮤니티</a>
            </div>
            <div class="common-menu-wrapper">
                <a href="/ad">포인트 충전소</a>
            </div>
        </nav>
    </nav>

</header>

<div id="ad-wrapper">
    광고가 표시될 영역입니다
</div>

<div id="fixed-btn-wrapper">
    <!--    스크롤이 조금이라도 내려가면 아래 버튼은 안보입니다    -->
    <button class="fixed-btn btn" id="to-top-btn">
        <img src="/assets/img/header/to-top.png" alt="위로 버튼 아이콘">
    </button>

    <!--   로그인 안했으면 아래 버튼은 안보입니다  -->

    <c:if test="${login != null}">
        <button class="fixed-btn btn modal-btn" id="chatting-btn">
            <img class=".modal-btn" src="/assets/img/header/chatting.png" alt="채팅 버튼 아이콘">
            <!--     읽지 않은 채팅 표시   -->
            <span id="unread-chatting-count">
<%--                    ${false}--%>
            </span>
        </button>
        <%@ include file="../chatting/chattingModal.jsp" %>
        <%@ include file="../chatting/chattingCalendar.jsp" %>
    </c:if>


</div>
<!-- 고정 버튼 끝 -->

