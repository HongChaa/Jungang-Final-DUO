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

    <link rel="stylesheet" href="/assets/css/my-page/mypage-myinfo.css">
    <script src="/assets/js/my-page/mypage-myinfo.js" type="module" defer></script>
    <!--    <link rel="icon" href="/assets/img/main/simple-favicon-navy.png">-->
</head>
<body>
<%--<div id = "profile-modal-background">--%>
<%--    <div id = "profile-modal">--%>
<%--        <div class = "arrow"><img id = "prev" src="/assets/img/mypage/arrow.png" alt = "prev"></div>--%>
<%--        <div id = "profile-details-frame">--%>
<%--            <img id = "profile-detail" src="/assets/img/mypage/user-profile.jpg" alt = "">--%>
<%--            <img id = "cancel" src="/assets/img/mypage/cancel-circle.png" alt = "cancel"></div>--%>
<%--        <div class = "arrow"><img id = "next" src="/assets/img/mypage/arrow.png" alt = "next"></div>--%>
<%--    </div>--%>
<%--</div>--%>
<div id="main-wrapper">
    <%@ include file="../common/header.jsp" %>
    <!--    마이페이지 좌측 메뉴바 영역 -->
    <div style="display: flex">

        <%@ include file="../my-page/my-page.jsp" %>

        <!-- 이 아래에 div 태그 열어서 width: 80% 으로 주기 -->
        <!--    마이페이지 우측 콘텐츠 영역 -->
        <div id="my-page-content">
            <!--        원하는 내용 추가 -->
            <div id="myinfo-wrapper">
                <div id="profile-box">
                    <div class="myinfo-title">대표 프로필 사진</div>
                    <div id="profile-frame">
                        <img id="main-profile-image" src="${login.userProfileImage}" alt="profile">
                    </div>
                    <div id="add-profile">사진 추가</div>
                    <input type="file" style="display: none" id="profileImages" name="profileImages">
                </div>
                <!--            내 정보 -->
                <div id="my-info-box">
                    <!--               내 수정 가능한 정보 -->
                    <div class="myinfo-title">내 가입 정보</div>
                    <div id="my-user-info">
                        <div class="my-info">
                            <div class="my-info-data"><span>닉네임</span><label><input class="my-registered-info"
                                                                                    id="userNickname"
                                                                                    name="userNickname"
                                                                                    value="${login.userNickname}"></label>
                            </div>
                            <div class="my-info-data"><span>생년월일</span><label><input type="date"
                                                                                     class="my-registered-info"
                                                                                     id="userBirthday"
                                                                                     name="userBirthday"
                                                                                     value="${login.userBirthday}"></label>
                            </div>
                            <div class="my-info-data"><span>롤계정</span><label><input class="my-registered-info"
                                                                                    id="lolNickname" name="lolNickname"
                                                                                    value="${login.lolNickname}"></label>
                            </div>
                        </div>
                        <div class="my-info">
                            <div class="my-info-data"><img class="sns-image" src="/assets/img/mypage/instagram.png"
                                                           alt="sns"><label><input class="my-registered-info"
                                                                                   id="instagram"
                                                                                   name="userInstagram"
                                                                                   value="${login.userInstagram}"
                                                                                   placeholder="sns id를 입력하세요"></label>
                            </div>
                            <div class="my-info-data"><img class="sns-image" src="/assets/img/mypage/facebook.png"
                                                           alt="sns"><label><input class="my-registered-info"
                                                                                   id="facebook"
                                                                                   name="userFacebook"
                                                                                   value="${login.userFacebook}"
                                                                                   placeholder="sns id를 입력하세요"></label>
                            </div>
                            <div class="my-info-data"><img class="sns-image" src="/assets/img/mypage/twitter.png"
                                                           alt="sns"><label><input
                                    class="my-registered-info" id="twitter" name="userTwitter"
                                    value="${login.userTwitter}" placeholder="sns id를 입력하세요"></label></div>
                        </div>
                        <div id="my-info-btn" class="my-info-btn">
                            <button type="submit" id="modify-btn" class="btn">
                                정보변경
                            </button>
                        </div>
                    </div>
                    <!--               내 듀오 활동 내역 -->
                    <div id="my-activity">
                        <div id="my-duo-activity">
                            <div id="first-box">
                                <div class="myinfo-title">내 호감도</div>
                                <div id="my-popularity">
                                    <div id="follow-info-box">
                                        <div class="my-popularity-data"><span>팔로워</span><span
                                                class="data">${me.followers}</span>건
                                        </div>
                                        <div class="my-popularity-data"><span>팔로잉</span><span
                                                class="data">${me.followings}</span>건
                                        </div>
                                        <div class="my-popularity-data"><span>호감도</span><span
                                                class="data">${me.userAvgRate}</span>P
                                        </div>
                                    </div>
                                    <div id="other-info-box">
                                        <div class="my-other-data"><span>팔로워 순위</span><span
                                                class="data">${me.rank == null ? "집계x" : me.rank}</span>등
                                        </div>
                                        <div class="my-other-data"><span>비매너 신고</span><span
                                                class="data">${me.accuseCount}</span>건
                                        </div>
                                        <div class="my-other-data"><span>총 활동점수</span><span
                                                class="data">${me.totalScore}</span>점
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="second-box">
                                <div class="my-duo-activity-title">
                                    <div id="duo-info-title" class="myinfo-title">듀오 활동 정보</div>
                                    <div id="open-duo-history">내 전적 보러가기 &gt;</div>
                                </div>
                                <div id="my-matching">
                                    <div id="my-matching-head">
                                        <div class="col1"></div>
                                        <div class="col2">받은 매칭</div>
                                        <div class="col3">수락 매칭</div>
                                        <div class="col4">매칭률</div>
                                    </div>
                                    <div id="my-matching-body">
                                        <div class="my-matching-data">
                                            <div class="col1">남자</div>
                                            <div class="col2"><span>${me.receivedMatchingFromMale}</span>건</div>
                                            <div class="col3"><span>${me.confirmedMatchingWithMale}</span>건</div>
                                            <div class="col4"><span>${me.confirmRateWithMale}</span>%</div>
                                        </div>
                                        <div class="my-matching-data">
                                            <div class="col1">여자</div>
                                            <div class="col2"><span>${me.receivedMatchingFromFemale}</span>건</div>
                                            <div class="col3"><span>${me.confirmedMatchingWithFemale}</span>건</div>
                                            <div class="col4"><span>${me.confirmRateWithFemale}</span>%</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="my-board-activity">
                            <div class="myinfo-title">WOULD U DUO 활동 이력</div>
                            <div id="my-board-point-info">
                                <div id="my-board-history"><p class="activity-title">내 활동</p>
                                    <div class="my-board-data"><p>작성 게시글 수</p><span class="data-amount">0</span>건<span
                                            class="point-amount">0</span>POINT
                                    </div>
                                    <div class="my-board-data"><p>작성 댓글 수</p><span class="data-amount">0</span>건<span
                                            class="point-amount">0</span>POINT
                                    </div>
                                </div>
                                <div id="my-point-history"><p class="activity-title">내 포인트</p>
                                    <div class="my-point-data"><p>오늘 획득한 포인트</p><span class="data-amount">0</span>POINT
                                    </div>
                                    <div class="my-point-data"><p>이달 획득한 포인트</p><span class="data-amount">0</span>POINT
                                    </div>
                                    <div class="my-point-data"><p>누적 획득 포인트</p><span class="data-amount">0</span>POINT
                                    </div>
                                    <div class="my-point-data"><p>누적 사용 포인트</p><span class="data-amount">0</span>POINT
                                    </div>
                                    <div id="current-point"><p>사용 가능 포인트</p><span class="data-amount">0</span>POINT
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <%@ include file="../common/footer.jsp" %>
</div>
</body>
</html>