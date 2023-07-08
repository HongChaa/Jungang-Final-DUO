<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>


    <%@ include file="../common/static-head.jsp" %>
    <link rel="stylesheet" href="/assets/css/user/history/community.css">
    <link rel="stylesheet" href="/assets/css/board/board-write.css">
    <script src="/assets/js/board/community.js" defer type="module"></script>
</head>

<body>
<%@ include file="../common/header.jsp" %>
<div id="main-wrapper">
    <div class="community-wrapper">
        <div class="search-write-wrapper">
            <div class="search-container">
                <input class="search-box" id="search" type="text">
                <button class="board-search-title-btn">

                    <img class="search-btn-img" src="/assets/img/community/search.png" alt="검색버튼" id="search"
                         onclick="handleSearchClick()">

                </button>
            </div>


            <div class="write-container">
                <button class="modal-btn"><p>글쓰기</p><img src="/assets/img/community/pencilwhite.png" alt="글쓰기버튼">
                </button>
                <%@ include file="../board/board-write.jsp" %>


                <div class="sort-wrapper">

                    <div class="category-wrapper" id="category-btn">

                        <button class="freedom-board selected-category" id="freedom-btn">자유</button>
                        <button class="notify-board">공지</button>
                        <button class="accuse-board" id="accuse-btn">신고</button>

                    </div>
                    //input radio
                    <div class="orderby-wrapper">
                        <button class="new-board-btn"><img src="/assets/img/community/sunwhite.png" alt="최신순이미지">최신순
                        </button>
                        <button class="week-recommend-btn"><img src="/assets/img/community/firewhite.png" alt="이번주이미지">이번주
                            추천순
                        </button>
                        <button class="month-recommend-btn"><img src="/assets/img/community/goodwhite.png" alt="이번달이미지">이번달
                            추천순
                        </button>
                    </div>
                </div>
                <div class="community-body-wrapper">
                    <div class="rank-wrapper">
                        <div class="rank-head">
                            <img src="/assets/img/community/trophy.png" alt="트로피이미지">
                            <div>Best Writer</div>
                            <img src="/assets/img/community/trophy.png" alt="트로피이미지">
                        </div>
                        <div class="rank-body">
                            <ul class="rank-container">
                                <li class="rank-card">
                                    <img src="/assets/img/community/firstmedal.png" alt="1등">
                                    <div class="rank-nickname">예비챌린저</div>
                                </li>
                                <li class="rank-card">
                                    <img src="/assets/img/community/secondmedal.png" alt="2등">
                                    <div class="rank-nickname">예비그랜드마스터</div>
                                </li>
                                <li class="rank-card">
                                    <img src="/assets/img/community/thirdmedal.png" alt="3등">
                                    <div class="rank-nickname">예비마스터</div>
                                </li>
                                <li class="rank-card">
                                    <img src="/assets/img/community/starmedal.png" alt="메달">
                                    <div class="rank-nickname">응애롤린이</div>
                                </li>
                                <li class="rank-card">
                                    <img src="/assets/img/community/starmedal.png" alt="메달">
                                    <div class="rank-nickname">응애롤린이</div>
                                </li>
                                <li class="rank-card">
                                    <img src="/assets/img/community/starmedal.png" alt="메달">
                                    <div class="rank-nickname">응애롤린이</div>
                                </li>
                                <li class="rank-card">
                                    <img src="/assets/img/community/starmedal.png" alt="메달">
                                    <div class="rank-nickname">응애롤린이</div>
                                </li>
                                <li class="rank-card">
                                    <img src="/assets/img/community/starmedal.png" alt="메달">
                                    <div class="rank-nickname">응애롤린이</div>
                                </li>
                                <li class="rank-card">
                                    <img src="/assets/img/community/starmedal.png" alt="메달">
                                    <div class="rank-nickname">응애롤린이</div>
                                </li>
                                <li class="rank-card">
                                    <img src="/assets/img/community/starmedal.png" alt="메달">
                                    <div class="rank-nickname">응애롤린이</div>
                                </li>
                            </ul>
                        </div>
                    </div>


                    </li>
                    </ul>
                </div>
            </div>a
        </div>
    </div>
    <%@ include file="../common/footer.jsp" %>
</div>
</body>

</html>