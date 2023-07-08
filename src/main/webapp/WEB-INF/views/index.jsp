<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>WOULD U DUO</title>
    <link rel="icon" href="/assets/img/main/simple-favicon-navy.png">

    <%@ include file="common/static-head.jsp" %>
    <script src="/assets/js/user/index.js" type="module" defer></script>
</head>
<body>
<!--    프로필 카드 상단 검색 조건 메뉴바 -->

<div id="main-wrapper">

    <%@ include file="common/header.jsp" %>
    <div id="login-check" data-login="${login.userAccount}" style="display: none;"></div>
    <div id="search-bar">
        <!--        인풋 닉네임 검색 -->
        <label><input type="text" id="searchBy-nickname" placeholder="듀오 검색하기" name="keyword"
                      spellcheck="false"></label>
        <!--        포지션 검색 라디오 -->
        <div id="searchBy-position">
            <label class="position-option">
                <input class="select-position" type="radio" name="position" value="all">
                <img class="position-image" src="/assets/img/main/ALL.png" alt="ALL">
            </label>
            <label class="position-option">
                <input class="select-position" type="radio" name="position" value="TOP">
                <img class="position-image" src="/assets/img/main/TOP.png" alt="TOP"></label>
            <label class="position-option">
                <input class="select-position" type="radio" name="position" value="JUG">
                <img class="position-image" src="/assets/img/main/JUG.png" alt="JUG"></label>
            <label class="position-option">
                <input class="select-position" type="radio" name="position" value="MID">
                <img class="position-image" src="/assets/img/main/MID.png" alt="MID"></label>
            <label class="position-option">
                <input class="select-position" type="radio" name="position" value="AD">
                <img class="position-image" src="/assets/img/main/AD.png" alt="AD"></label>
            <label class="position-option">
                <input class="select-position" type="radio" name="position" value="SUP">
                <img class="position-image" src="/assets/img/main/SUP.png" alt="SUP"></label>
        </div>
        <!--        성별 검색 라디오 -->
        <div id="searchBy-gender">
            <input class="select-gender" id="gender-m" type="radio" name="gender" value="M"><label for="gender-m"
                                                                                                   class="gender-option">남</label>
            <input class="select-gender" id="gender-f" type="radio" name="gender" value="F"><label for="gender-f"
                                                                                                   class="gender-option">여</label>
        </div>
        <!--        티어 검색 라디오 -->
        <div id="searchByTier">
            <label class="tier-option"><input class="select-tier" type="radio" name="tier" value="UNR">
                <img class="tier-image" src="/assets/img/main/unranked-removebg-preview.png" alt="언랭"></label>
            <label class="tier-option"><input class="select-tier" type="radio" name="tier" value="IRO">
                <img class="tier-image" src="/assets/img/main/TFT_Regalia_Iron.png" alt="아이언"></label>
            <label class="tier-option"><input class="select-tier" type="radio" name="tier" value="BRO">
                <img class="tier-image" src="/assets/img/main/TFT_Regalia_Bronze.png" alt="브론즈"></label>
            <label class="tier-option"><input class="select-tier" type="radio" name="tier" value="SIL">
                <img class="tier-image" src="/assets/img/main/TFT_Regalia_Silver.png" alt="실버"></label>
            <label class="tier-option"><input class="select-tier" type="radio" name="tier" value="GOL">
                <img class="tier-image" src="/assets/img/main/TFT_Regalia_Gold.png" alt="골드"></label>
            <label class="tier-option"><input class="select-tier" type="radio" name="tier" value="PLA">
                <img class="tier-image" src="/assets/img/main/TFT_Regalia_Platinum.png" alt="플래티넘"></label>
            <label class="tier-option"><input class="select-tier" type="radio" name="tier" value="DIA">
                <img class="tier-image" src="/assets/img/main/TFT_Regalia_Diamond.png" alt="다이아"></label>
            <label class="tier-option"><input class="select-tier" type="radio" name="tier" value="MAS">
                <img class="tier-image" src="/assets/img/main/TFT_Regalia_Master.png" alt="마스터"></label>
            <label class="tier-option"><input class="select-tier" type="radio" name="tier" value="GRA">
                <img class="tier-image" src="/assets/img/main/TFT_Regalia_GrandMaster.png" alt="그랜드마스터"></label>
            <label class="tier-option"><input class="select-tier" type="radio" name="tier" value="CHA">
                <img class="tier-image" src="/assets/img/main/TFT_Regalia_Challenger.png" alt="챌린저"></label>
        </div>
        <!--        전체보기, 팔로잉, 정렬 -->
        <div id="sort-box">
            <div class="sortby-menubar" id="view-all">전체보기</div>
            <div class="sortby-menubar" id="view-followings">팔로잉</div>
            <div class="sortby-menubar" id="sort-by">
                <label for="order-list"><select id="order-list" name="sort">
                    <option class="dropdown-list" value="userAvgRate">별점순</option>
                    <option class="dropdown-list" value="userMatchingRate">매칭률순</option>
                </select>
                </label>
            </div>
        </div>
    </div>
    <!--    프로필카드 리스트 영역 -->
    <div id="profile-cards-wrapper">
        <!--        프로필 카드 한 장 -->
        <!--        가장 바깥 div에 유저아이디 넣기 -->
        <%--        <div id = "test5@test.com" class="duo-profile">--%>
        <%--            <img class="duo-tier" src="/assets/img/main/TFT_Regalia_Challenger.png" alt="tier">--%>
        <%--            <!--            프로필 카드 좌측 -->--%>
        <%--             <div class="profile-left-side">--%>
        <%--                <div class="profile-frame">--%>
        <%--                    <img class="profile-image" src="/assets/img/main/profile-image.jpg" alt="프로필 이미지">--%>
        <%--                </div>--%>
        <%--                <div class="profile-sns">--%>
        <%--                    <a href="#" class="sns-type instagram"><img class="sns-image" src="/assets/img/main/instagram.png"--%>
        <%--                                                                alt="instagram"></a>--%>
        <%--                    <a href="#" class="sns-type facebook"><img class="sns-image" src="/assets/img/main/facebook.png"--%>
        <%--                                                               alt="facebook"></a>--%>
        <%--                    <a href="#" class="sns-type twitter"><img class="sns-image" src="/assets/img/main/twitter.png"--%>
        <%--                                                              alt="twitter"></a>--%>
        <%--                    <div class="sns-type chatting-icon"><img class="sns-image" src="/assets/img/main/chatting-icon.png"--%>
        <%--                                                             alt="chatting"></div>--%>
        <%--                </div>--%>
        <%--            </div>--%>
        <%--            <!--            프로필 카드 우측 -->--%>
        <%--            <div class="profile-right-side">--%>
        <%--                <div class="position-nickname">--%>
        <%--                    <img class="preferred-position" src="/assets/img/main/MID.png" alt="포지션">--%>
        <%--                    <p class="user-nickname">jhlee0622</p>--%>
        <%--                    <img class="follow-status" src="/assets/img/main/not-following.png" alt="following">--%>
        <%--                </div>--%>
        <%--                <div class="rate-matching-point">--%>
        <%--                    <div class="rate-matching-point rate-point-box"><img class="rate-matching-point-image"--%>
        <%--                                                                         src="/assets/img/main/star.png" alt="rate">--%>
        <%--                        <p class="avg-rate">89.2</p></div>--%>
        <%--                    <div class="rate-matching-point matching-point-box"><img class="rate-matching-point-image"--%>
        <%--                                                                             src="/assets/img/main/coin.png" alt="coin">--%>
        <%--                        <p class="matching-point">500</p></div>--%>
        <%--                </div>--%>
        <%--                <div class="profile-comment"><p>여기에 자기소개를 적을거에요^^ 같이 게임해용~~~</p></div>--%>
        <%--                <div class="profile-most-champ">--%>
        <%--                    <ul class="champ-list">--%>
        <%--                        <li class="most-pic first-champ"><img src="/assets/img/main/Skarner_3.jpg" alt="first-champ">--%>
        <%--                        </li>--%>
        <%--                        <li class="most-pic second-champ"><img src="/assets/img/main/TahmKench_0.jpg"--%>
        <%--                                                               alt="second-champ"></li>--%>
        <%--                        <li class="most-pic third-champ"><img src="/assets/img/main/Ziggs_2.jpg" alt="third-champ"></li>--%>
        <%--                    </ul>--%>
        <%--                </div>--%>
        <%--            </div>--%>
        <%--        </div>--%>
        <%--        <!--        아래부터는 지워도 되는 부분입니다 --------------------------------------------------------------------->--%>
        <%--        <!--        프로필 카드 한 장 -->--%>
        <%--        <!--        가장 바깥 div에 유저아이디 넣기 -->--%>
        <%--        <div id = "test@test.com" class="duo-profile">--%>
        <%--            <img class="duo-tier" src="/assets/img/main/TFT_Regalia_Challenger.png" alt="tier">--%>
        <%--            <!--            프로필 카드 좌측 -->--%>
        <%--            <div class="profile-left-side">--%>
        <%--                <div class="profile-frame">--%>
        <%--                    <img class="profile-image" src="/assets/img/main/profile-image.jpg" alt="프로필 이미지">--%>
        <%--                </div>--%>
        <%--                <div class="profile-sns">--%>
        <%--                    <a href="#" class="sns-type instagram"><img class="sns-image" src="/assets/img/main/instagram.png"--%>
        <%--                                                                alt="instagram"></a>--%>
        <%--                    <a href="#" class="sns-type facebook"><img class="sns-image" src="/assets/img/main/facebook.png"--%>
        <%--                                                               alt="facebook"></a>--%>
        <%--                    <a href="#" class="sns-type twitter"><img class="sns-image" src="/assets/img/main/twitter.png"--%>
        <%--                                                              alt="twitter"></a>--%>
        <%--                    <div class="sns-type chatting-icon"><img class="sns-image" src="/assets/img/main/chatting-icon.png"--%>
        <%--                                                             alt="chatting"></div>--%>
        <%--                </div>--%>
        <%--            </div>--%>
        <%--            <!--            프로필 카드 우측 -->--%>
        <%--            <div class="profile-right-side">--%>
        <%--                <div class="position-nickname">--%>
        <%--                    <img class="preferred-position" src="/assets/img/main/MID.png" alt="포지션">--%>
        <%--                    <p class="user-nickname">jhlee0622</p>--%>
        <%--                    <img class="follow-status" src="/assets/img/main/not-following.png" alt="following">--%>
        <%--                </div>--%>
        <%--                <div class="rate-matching-point">--%>
        <%--                    <div class="rate-matching-point rate-point-box"><img class="rate-matching-point-image"--%>
        <%--                                                                         src="/assets/img/main/star.png" alt="rate">--%>
        <%--                        <p class="avg-rate">89.2</p></div>--%>
        <%--                    <div class="rate-matching-point matching-point-box"><img class="rate-matching-point-image"--%>
        <%--                                                                             src="/assets/img/main/coin.png" alt="coin">--%>
        <%--                        <p class="matching-point">500</p></div>--%>
        <%--                </div>--%>
        <%--                <div class="profile-comment"><p>여기에 자기소개를 적을거에요^^ 같이 게임해용~~~</p></div>--%>
        <%--                <div class="profile-most-champ">--%>
        <%--                    <ul class="champ-list">--%>
        <%--                        <li class="most-pic first-champ"><img src="/assets/img/main/Skarner_3.jpg" alt="first-champ">--%>
        <%--                        </li>--%>
        <%--                        <li class="most-pic second-champ"><img src="/assets/img/main/TahmKench_0.jpg"--%>
        <%--                                                               alt="second-champ"></li>--%>
        <%--                        <li class="most-pic third-champ"><img src="/assets/img/main/Ziggs_2.jpg" alt="third-champ"></li>--%>
        <%--                    </ul>--%>
        <%--                </div>--%>
        <%--            </div>--%>
        <%--        </div>--%>
        <!--  여기까지 지워도 되는 부분입니다 --------------------------------------------------------------------->
    </div>

    <!--</div>-->

    <%@ include file="common/footer.jsp" %>
</div>


</body>
</html>
