<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <link rel="stylesheet" href="/assets/css/user/sign-up.css">
    '

    <script src="/assets/js/user/sign-up.js" defer type="module"></script>

</head>
<body>

<div id="main-wrapper">
    <header>
        <h1>
            <a href="/">
                <img src="/assets/img/sign-up/logo.png" alt="로고 이미지">
            </a>
        </h1>
        <h2>회원가입</h2>
    </header>
    <form action="/user/sign-up" id="signupResult" method="post" enctype="multipart/form-data">
        <div id="sign-up-wrapper">
            <section id="profile-img-section">
                <h2>프로필 사진</h2>
                <div id="main-profile-img-wrapper" class="img-wrapper btn"
                     style="background-image: url('/assets/img/sign-up/default-profile-img.png');">
                    <input type="file" style="display: none" name="profileImages">
                </div>
                <div id="sub-profile-img-wrapper">
                    <div class="img-wrapper btn"
                         style="background-image: url('/assets/img/sign-up/default-profile-img.png');">
                        <input type="file" style="display: none" name="profileImages">
                    </div>
                    <div class="img-wrapper btn"
                         style="background-image: url('/assets/img/sign-up/default-profile-img.png');">
                        <input type="file" style="display: none" name="profileImages">
                    </div>
                    <div class="img-wrapper btn"
                         style="background-image: url('/assets/img/sign-up/default-profile-img.png');">
                        <input type="file" style="display: none" name="profileImages">
                    </div>
                </div>
            </section>
            <div id="sign-up-request-info-wrapper">
                <div id="sign-up-request-input-wrapper">
                    <label for="user-nickname">닉네임</label>
                    <input type="text" id="user-nickname" name="userNickname">
                    <div id="nicknameChk"></div>

                    <label>
                        <select name="userGender" id="user-gender" class="btn">
                            <option value="none">성별</option>
                            <option value="M">남자</option>
                            <option value="F">여자</option>
                        </select>
                    </label>

                    <label for="user-email">이메일</label>
                    <input type="text" id="user-email" name="userEmail">
                    <%--                    <div id="emailChk"></div>--%>

                    <button id="verification-btn" class="btn modal-btn">인증</button>
                    <dialog id="email-confirm-modal">
                        <div>
                            인증 이메일이 발송되었습니다.<br>
                            이메일 확인 후 인증번호를 입력해주세요.
                        </div>

                        <div>
                            <label for="confirm-code">인증번호</label>
                            <input type="text" id="confirm-code">
                            <button class="btn">확인</button>
                        </div>

                        <div>
                            <button class="btn">이메일 재전송</button>
                        </div>
                    </dialog>

                    <label for="user-password">비밀번호</label>
                    <input type="password" id="user-password" name="userPassword">
                    <div id="pwChk"></div>

                    <label for="user-password-check">비밀번호 확인</label>
                    <input type="password" id="user-password-check">
                    <div id="pwChk2"></div>

                    <label for="user-birthday">생년월일</label>
                    <input type="date" id="user-birthday" name="userBirthday">
                    <div id="birthdayChk"></div>

                    <label for="lol-nickname">롤계정</label>
                    <input type="text" id="lol-nickname" name="lolNickname">
                    <div id="lolNicknameChk"></div>

                    <!--                     빈칸 -->
                    <div class="space"></div>
                    <div></div>
                    <div></div>
                    <!--                     빈칸 끝 -->

                    <!--                    sns 계정 기입란-->
                    <div>SNS</div>
                    <div class="sns-input-wrapper">
                        <label for="instagram"><img src="/assets/img/main/instagram.png" alt=""></label>
                        <input type="text" id="instagram" name="userInstagram" placeholder="사용자 아이디를 입력하세요">
                        <div id="instagramChk"></div>
                    </div>
                    <div></div>

                    <div></div>
                    <div class="sns-input-wrapper">
                        <label for="facebook"><img src="/assets/img/main/facebook.png" alt=""></label>
                        <input type="text" id="facebook" name="userFacebook" placeholder="사용자 아이디를 입력하세요">
                        <div id="facebookChk"></div>
                    </div>
                    <div></div>

                    <div></div>
                    <div class="sns-input-wrapper">
                        <label for="twitter"><img src="/assets/img/main/twitter.png" alt=""></label>
                        <input type="text" id="twitter" name="userTwitter" placeholder="사용자 아이디를 입력하세요">
                        <div id="twitterChk"></div>
                    </div>
                    <div></div>
                </div>
                <div id="submit-btn-wrapper">
                    <button type="submit" id="signup-btn" class="btn">
                        회원가입
                    </button>
                </div>
            </div>
        </div>
    </form>


</div>

</body>
</html>