<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <%@ include file="../common/static-head.jsp" %>
    <link rel="stylesheet" href="/assets/css/admin/admin.css">

    <style>
        /* 페이지 css */
        /* 페이지 액티브 기능 */
        .pagination .page-item.p-active a {
            color: #fff !important;
            pointer-events: none;
        }

        .pagination .page-item:hover a {
            color: #fff !important;
        }
    </style>
</head>

<body>
<div id="main-wrapper">
    <%@ include file="../common/header.jsp" %>

    <div id="admin-wrapper">
        <div class="menu" id="left_menu">
            <ul class="Today_Total">
                <li>Today</li>
                <li>Total</li>
            </ul>
            <ul class="menu_box">
                <ul class="two_box">
                    <div class="one_box" id="todayUserButton">
                        <ul class="one_box_text">
                            <li class="pic"><img class="admin_img" id="sign" src="/assets/img/admin/금일가입자수.png"
                                                 alt="today_user">
                            </li>
                            <li class="admin_category">금일 가입자</li>
                            <li class="mem_count">${count.todayJoinCount} 명</li>
                        </ul>
                    </div>
                    <div class="one_box" id="totalUserButton">
                        <ul class="one_box_text">
                            <li class="pic"><img class="admin_img" id="member" src="/assets/img/admin/회원관리.png"
                                                 alt="user_management"></li>
                            <li class="admin_category">회원관리</li>
                            <li class="mem_count">${count.totalJoinCount} 명</li>
                        </ul>
                    </div>
                </ul>
                <ul class="two_box">
                    <div class="one_box" id="todayAccuseButton">
                        <ul class="one_box_text">
                            <li class="pic"><img class="admin_img accuse" src="/assets/img/admin/신고사진.png"
                                                 alt="today_accuse"></li>
                            <li class="admin_category">금일 신고</li>
                            <li class="mem_count">${count.todayAccuseCount} 회</li>
                        </ul>
                    </div>
                    <div class="one_box" id="totalAccuseButton">
                        <ul class="one_box_text">
                            <li class="pic"><img class="admin_img accuse" src="/assets/img/admin/신고사진.png"
                                                 alt="accuse_management">
                            </li>
                            <li class="admin_category">신고 관리</li>
                            <li class="mem_count">${count.totalAccuseCount}회</li>
                        </ul>
                    </div>
                </ul>
                <ul class="two_box">
                    <div class="one_box" id="todayBoardButton">
                        <ul class="one_box_text">
                            <li class="pic"><img class="admin_img accuse" src="/assets/img/admin/게시판관리.png"
                                                 alt="today_board"></li>
                            <li class="admin_category">금일 작성 게시물</li>
                            <li class="mem_count">${count.todayBoardCount}건</li>
                        </ul>
                    </div>
                    <div class="one_box" id="totalBoardButton">
                        <ul class="one_box_text">
                            <li class="pic"><img class="admin_img accuse" src="/assets/img/admin/게시판관리.png"
                                                 alt="board_management">
                            </li>
                            <li class="admin_category">게시물 관리</li>
                            <li class="mem_count">${count.totalBoardCount}건</li>
                        </ul>
                    </div>
                </ul>


            </ul>

        </div>


        <div class="menu" id="right_menu">
            <div class="top_menu">
                <div class="search">
                    <input id="search_input" name="keyword" type="text">
                    <button id="search_button"><img src="/assets/img/admin/검색.png" alt="search"></button>
                </div>
                <ul class="admin_board">
                    <li><a href="/board/write">글쓰기</a></li>
                    <li><a href="/board/list">공지관리</a></li>
                </ul>
            </div>
            <!-- 회원관리 -->

            <div class="bottom_menu">
                <div class="menubar" id="user_menu_bar" style="display: none;">
                    <div class="no">no</div>
                    <div class="nickname">닉네임</div>
                    <div class="gender">성별</div>
                    <div class="board_count">작성게시물수</div>
                    <div class="reply_count">작성댓글수</div>
                    <div class="warn_count">경고횟수</div>
                    <div class="point">포인트</div>
                    <div class="follow">팔로우수</div>
                    <div class="sign_date">가입일자</div>
                </div>
                <% for (int i = 1; i < 11; i++) { %>
                <a href="javascript:void(0);"
                   onclick="window.location.href = '/user/detail/admin?userNickname=' + userNickname;" class="user_li"
                   style="display: none;">
                    <div class="menubar">
                        <div class="no uln"></div>
                        <div class="nickname uli"></div>
                        <div class="gender ulg"></div>
                        <div class="board_count ulb"></div>
                        <div class="reply_count ulr"></div>
                        <div class="warn_count ulw"></div>
                        <div class="point ulp"></div>
                        <div class="follow ulf"></div>
                        <div class="sign_date uls"></div>
                    </div>
                </a>

                <% } %>


                <!-- 게시글관리 -->
                <div class="menubar" id="board_menu_bar" style="display: none;">
                    <div class="no">no</div>
                    <div class="nickname">닉네임</div>
                    <div class="title">제목</div>
                    <div class="board_write_date">작성일자</div>
                    <div class="select_count" id="board_select_count">조회수</div>
                </div>
                <% for (int i = 1; i < 11; i++) { %>

                <a href="#" class="board_li" style="display: none;">
                    <div class="menubar">
                        <div class="no bln"></div>
                        <div class="nickname blm"></div>
                        <div class="title blt"></div>
                        <div class="board_write_date bld"></div>
                        <div class="select_count blc"></div>
                        <div class="delete" onclick="getBoardNo(yourList)">삭제</div>
                    </div>
                </a>
                <% } %>


                <!-- 경고관리 -->
                <div class="menubar" id="accuse_menu_bar" style="display: none;">
                    <div class="accuse_no">no</div>
                    <div class="accuse_nickname">악성유저</div>
                    <div class="accuse_title">신고내역</div>
                    <div class="accuse_etc">신고내역(etc)</div>
                    <div class="accuse_count">신고날짜</div>
                </div>

                <% for (int i = 1; i < 11; i++) { %>

                <a href="#" class="accuse_li" style="display: none;">
                    <div class="menubar">
                        <div class="accuse_no aln"></div>
                        <div class="accuse_nickname alm"></div>
                        <div class="accuse_title alt"></div>
                        <div class="accuse_etc ale"></div>
                        <div class="accuse_count alc"></div>
                    </div>
                </a>
                <% } %>
                <ul class="pageNo">
                    <ul class="pagination justify-content-center page_no">
                    </ul>
                </ul>


            </div>
        </div>
    </div>
    <%@ include file="../common/footer.jsp" %>
</div>


<script>
    // 클릭 이벤트 핸들러 등록 - 전체 회원
    const totalUserButton = document.getElementById('totalUserButton');
    const todayUserButton = document.getElementById('todayUserButton');
    const UserMenuBar = document.getElementById('user_menu_bar');
    const userList = document.getElementsByClassName('user_li');


    // 클릭 이벤트 핸들러 등록 - 전체 게시글
    const totalBoardButton = document.getElementById('totalBoardButton');
    const todayBoardButton = document.getElementById('todayBoardButton');
    const boardMenuBar = document.getElementById('board_menu_bar');
    const boardList = document.getElementsByClassName('board_li');


    // 클릭 이벤트 핸들러 등록 - 전체 경고리스트
    const totalAccuseButton = document.getElementById('totalAccuseButton');
    const todayAccuseButton = document.getElementById('todayAccuseButton');
    const accuseMenuBar = document.getElementById('accuse_menu_bar');
    const accuseList = document.getElementsByClassName('accuse_li');


    function boardDisplayNone() {
        boardMenuBar.style.display = 'none';
        for (let i = 0; i < boardList.length; i++) {
            boardList[i].style.display = 'none';
        }
    }

    function accuseDisplayNone() {
        accuseMenuBar.style.display = 'none';
        for (let i = 0; i < accuseList.length; i++) {
            accuseList[i].style.display = 'none';
        }
    }


    function userDisplayNone() {
        UserMenuBar.style.display = 'none';
        for (let i = 0; i < userList.length; i++) {
            userList[i].style.display = 'none';
        }
    }

    totalUserButton.onclick = e => {
        boardDisplayNone();
        accuseDisplayNone();
        UserMenuBar.style.display = '';

        const pageNum = 1; // 초기 페이지 설정
        const searchInput = document.getElementById('search_input');
        const button = document.getElementById('search_button');

        let keyword = ""; // 초기에는 빈 문자열로 설정합니다.
        console.log('클릭전 키워드', keyword);

        button.onclick = e => {
            keyword = searchInput.value; // 입력된 텍스트 값으로 keyword를 업데이트합니다.
            console.log(keyword);


            if (keyword == "") {
                keyword = "";
            }

            // 사용자 목록을 가져오는 함수 호출
            getUserList(pageNum, keyword);
        };

        // 사용자 목록을 가져오는 함수 호출 (초기에는 keyword가 빈 문자열로 호출됩니다.)
        getUserList(pageNum, keyword);
        console.log('함수전달할 때  키워드', keyword);

    };

    function getUserList(pageNum, keyword) {

        console.log('totalpageNum=================', pageNum);
        console.log('회원관리 비동기 호출');
        fetch(`/api/v1/users/admin?pageNum=\${pageNum}&keyword=\${keyword}`)
            .then(response => response.json())
            .then(res => {

                const list = res.list;
                console.log('list: ', list);

                for (let listOne of list) {
                    const {
                        rowNum,
                        userNickname,
                        gender,
                        boardCount,
                        replyCount,
                        reportCount,
                        point,
                        followCount,
                        joinDate,
                    } = listOne;
                }

                for (let i = 0; i < userList.length; i++) {
                    userList[i].style.display = '';
                }
                console.log('res', res);

                totalUser(list);
                renderUserList(res);

                locationToDetail(list);

            });
    }


    function totalUser(list) {
        uln(list);
        uli(list);
        ulg(list);
        ulb(list);
        ulr(list);
        ulw(list);
        ulp(list);
        ulf(list);
        uls(list);
    }

    function uln(list) {
        const ulArray = document.querySelectorAll('.uln');
        ulArray.forEach((ulElement, index) => {
            const asd = list[index].rowNum;
            ulElement.innerText = asd;

        });
    }

    function uli(list) {
        const ulArray = document.querySelectorAll('.uli');
        ulArray.forEach((ulElement, index) => {
            const asd = list[index].userNickname;
            ulElement.innerText = asd;
        });
    }

    function ulg(list) {
        const ulArray = document.querySelectorAll('.ulg');
        ulArray.forEach((ulElement, index) => {
            const asd = list[index].gender;
            ulElement.innerText = asd;
        });
    }

    function ulb(list) {
        const ulArray = document.querySelectorAll('.ulb');
        ulArray.forEach((ulElement, index) => {
            const asd = list[index].boardCount;
            ulElement.innerText = asd + "  회";
        });
    }

    function ulr(list) {
        const ulArray = document.querySelectorAll('.ulr');
        ulArray.forEach((ulElement, index) => {
            const asd = list[index].replyCount;
            ulElement.innerText = asd + "  회";
        });
    }

    function ulw(list) {
        const ulArray = document.querySelectorAll('.ulw');
        ulArray.forEach((ulElement, index) => {
            const asd = list[index].reportCount;
            ulElement.innerText = asd + "  회";
        });
    }

    function ulp(list) {
        const ulArray = document.querySelectorAll('.ulp');
        ulArray.forEach((ulElement, index) => {
            const asd = list[index].point;
            ulElement.innerText = asd + "  point";
        });
    }

    function ulf(list) {
        const ulArray = document.querySelectorAll('.ulf');
        ulArray.forEach((ulElement, index) => {
            const asd = list[index].followCount;
            ulElement.innerText = asd + "  명";
        });
    }

    function uls(list) {
        const ulArray = document.querySelectorAll('.uls');
        ulArray.forEach((ulElement, index) => {
            const asd = list[index].joinDate;
            ulElement.innerText = asd;
        });
    }


    function renderUserList({
                                count,
                                pageInfo,
                                list
                            }) {
        console.log('userList count: ', count);
        console.log('userList pageInfo: ', pageInfo);
        console.log('userList list: ', list);

        // 페이지 렌더링
        renderPage(pageInfo);

    }


    //페이징
    function renderPage({
                            startPage,
                            endPage,
                            currentPage,
                            prev,
                            next,
                            totalCount,
                            PAGE_COUNT
                        }) {
        console.log(`userList 페이지 렌더링 함수`);

        let tag = "";

        //이전 버튼 만들기
        if (prev) {
            tag += "<li class='page-item'><a class='page-link page-active' href='" + (startPage - 1) +
                "'>이전</a></li>";
        }
        //페이지 번호 리스트 만들기
        for (let i = startPage; i <= endPage; i++) {
            let active = '';
            if (currentPage.pageNo === i) {
                console.log(currentPage.pageNo);
                active = 'p-active';
            }

            tag += "<li class='page-item " + active + "'><a class='page-link page-custom' href='" + i +
                "'>" + i + "</a></li>";
        }
        //다음 버튼 만들기
        if (next) {
            tag += "<li class='page-item'><a class='page-link page-active' href='" + (endPage + 1) +
                "'>다음</a></li>";
        }

        // 페이지태그 렌더링
        const $pageUl = document.querySelector('.pagination');
        $pageUl.innerHTML = tag;

        // ul에 마지막페이지 번호 저장.
        $pageUl.dataset.fp = endPage;


    }


    // 유저페이지 클릭 이벤트 핸들러
    function makePageButtonUserClickEvent() {
        // 페이지 버튼 클릭이벤트 처리
        const $pageUl = document.querySelector('.pagination');
        $pageUl.onclick = e => {
            if (!e.target.matches('.page-item a')) return;

            e.preventDefault(); // 태그의 기본 동작 중단

            // 누른 페이지 번호 가져오기
            const pageNum = e.target.getAttribute('href');
            console.log("======total", pageNum);

            // 페이지 번호에 맞는 목록 비동기 요청
            getUserList(pageNum);
        };
    }

    makePageButtonUserClickEvent();


    function locationToDetail(list) {
        console.log('list: ', list);

        for (let i = 0; i < list.length; i++) {
            const userNickname = list[i].userNickname; // userAccount 값을 가져옴

            userList[i].onclick = e => {
                console.log('userNickname: ', userNickname);

                window.location.href = '/user/detail/admin?userNickname=' + userNickname;
            };
        }
    }


    todayUserButton.onclick = e => {
        console.log('today userlist click 실행');

        boardDisplayNone();
        accuseDisplayNone();

        UserMenuBar.style.display = '';

        const pageNum = 1; // 초기 페이지 설정

        const searchInput = document.getElementById('search_input');
        const button = document.getElementById('search_button');

        let keyword = ""; // 초기에는 빈 문자열로 설정합니다.
        console.log('클릭전 키워드', keyword);

        button.onclick = e => {
            keyword = searchInput.value; // 입력된 텍스트 값으로 keyword를 업데이트합니다.
            console.log(keyword);

            if (keyword == "") {
                keyword = "";
            }

            // 사용자 목록을 가져오는 함수 호출
            getUserTodayList(pageNum, keyword);
        };

        // 사용자 목록을 가져오는 함수 호출 (초기에는 keyword가 빈 문자열로 호출됩니다.)
        getUserTodayList(pageNum, keyword);
        console.log('함수전달할 때  키워드', keyword);

    };


    // 금일 유저
    function getUserTodayList(pageNum, keyword) {
        fetch(`/api/v1/users/admin1?pageNum=\${pageNum}&keyword=\${keyword}`)
            .then(response => response.json())
            .then(res => {
                console.log('res', res);
                const list = res.list;

                for (let listOne of list) {
                    const {
                        rowNum,
                        userNickname,
                        gender,
                        boardCount,
                        replyCount,
                        reportCount,
                        point,
                        followCount,
                        joinDate,
                    } = listOne;

                }

                for (let i = 0; i < userList.length; i++) {
                    userList[i].style.display = '';
                }

                // 유저리스트 관련 동작
                uln1(list);
                uli1(list);
                ulg1(list);
                ulb1(list);
                ulr1(list);
                ulw1(list);
                ulp1(list);
                ulf1(list);
                uls1(list);
                locationToTodayDetail(list);

                renderUserTodayList(res);
            });
    }


    function uln1(list) {
        const ulArray = document.querySelectorAll('.uln');
        ulArray.forEach((ulElement, index) => {
            if (index >= list.length) {
                ulElement.innerText = ''; // 공백으로 출력
            } else {
                const asd = list[index].rowNum;
                ulElement.innerText = asd;
            }
        });
    }

    function uli1(list) {
        const ulArray = document.querySelectorAll('.uli');
        ulArray.forEach((ulElement, index) => {
            if (index >= list.length) {
                ulElement.innerText = ''; // 공백으로 출력
            } else {
                const asd = list[index].userNickname;
                ulElement.innerText = asd;
            }
        });
    }

    function ulg1(list) {
        const ulArray = document.querySelectorAll('.ulg');
        ulArray.forEach((ulElement, index) => {
            if (index >= list.length) {
                ulElement.innerText = ''; // 공백으로 출력
            } else {
                const asd = list[index].gender;
                ulElement.innerText = asd;
            }
        });
    }

    function ulb1(list) {
        const ulArray = document.querySelectorAll('.ulb');
        ulArray.forEach((ulElement, index) => {
            if (index >= list.length) {
                ulElement.innerText = ''; // 공백으로 출력
            } else {
                const asd = list[index].boardCount;
                ulElement.innerText = asd + "  회";
            }
        });
    }

    function ulr1(list) {
        const ulArray = document.querySelectorAll('.ulr');
        ulArray.forEach((ulElement, index) => {
            if (index >= list.length) {
                ulElement.innerText = ''; // 공백으로 출력
            } else {
                const asd = list[index].replyCount;
                ulElement.innerText = asd + "  회";
            }
        });
    }

    function ulw1(list) {
        const ulArray = document.querySelectorAll('.ulw');
        ulArray.forEach((ulElement, index) => {
            if (index >= list.length) {
                ulElement.innerText = ''; // 공백으로 출력
            } else {
                const asd = list[index].reportCount;
                ulElement.innerText = asd + "  회";
            }
        });
    }

    function ulp1(list) {
        const ulArray = document.querySelectorAll('.ulp');
        ulArray.forEach((ulElement, index) => {
            if (index >= list.length) {
                ulElement.innerText = ''; // 공백으로 출력
            } else {
                const asd = list[index].point;
                ulElement.innerText = asd + "  point";
            }
        });
    }

    function ulf1(list) {
        const ulArray = document.querySelectorAll('.ulf');
        ulArray.forEach((ulElement, index) => {
            if (index >= list.length) {
                ulElement.innerText = ''; // 공백으로 출력
            } else {
                const asd = list[index].followCount;
                ulElement.innerText = asd + "  명";
            }
        });
    }

    function uls1(list) {
        const ulArray = document.querySelectorAll('.uls');
        ulArray.forEach((ulElement, index) => {
            if (index >= list.length) {
                ulElement.innerText = ''; // 공백으로 출력
            } else {
                const asd = list[index].joinDate;
                ulElement.innerText = asd;
            }
        });
    }

    function renderUserTodayList({
                                     count,
                                     pageInfo,
                                     list
                                 }) {
        console.log('count: ', count);
        console.log('pageInfo: ', pageInfo);
        console.log('list: ', list);

        //페이지 렌더링
        renderUserTodayPage(pageInfo);
    }

    //페이징
    function renderUserTodayPage({
                                     startPage,
                                     endPage,
                                     currentPage,
                                     prev,
                                     next,
                                     totalCount,
                                     PAGE_COUNT
                                 }) {


        let tag = "";

        //이전 버튼 만들기
        if (prev) {
            tag += "<li class='page-item'><a class='page-link page-active' href='" + (startPage - 1) +
                "'>이전</a></li>";
        }
        //페이지 번호 리스트 만들기
        for (let i = startPage; i <= endPage; i++) {
            let active = '';
            if (currentPage.pageNo === i) {
                console.log(currentPage.pageNo);
                active = 'p-active';
            }

            tag += "<li class='page-item " + active + "'><a class='page-link page-custom' href='" + i +
                "'>" + i + "</a></li>";
        }
        //다음 버튼 만들기
        if (next) {
            tag += "<li class='page-item'><a class='page-link page-active' href='" + (endPage + 1) +
                "'>다음</a></li>";
        }

        // 페이지태그 렌더링
        const $pageUl = document.querySelector('.pagination');
        $pageUl.innerHTML = tag;

        // ul에 마지막페이지 번호 저장.
        $pageUl.dataset.fp = finalPage;

    }

    // 유저페이지 클릭 이벤트 핸들러
    function makePageButtonUserTodayClickEvent() {
        // 페이지 버튼 클릭이벤트 처리
        const $pageUl = document.querySelector('.pagination');
        $pageUl.onclick = e => {
            if (!e.target.matches('.page-item a')) return;

            e.preventDefault(); // 태그의 기본 동작 중단

            // 누른 페이지 번호 가져오기
            const pageNum = e.target.getAttribute('href');
            // console.log(pageNum);
            console.log("======today", pageNum)

            // 페이지 번호에 맞는 목록 비동기 요청
            getUserTodayList(pageNum);
        };
    }


    // 페이지 버튼 이벤트 등록
    makePageButtonUserTodayClickEvent();


    function locationToTodayDetail(list) {
        console.log('list: ', list);

        for (let i = 0; i < list.length; i++) {
            const userNickname = list[i].userNickname; // userAccount 값을 가져옴

            userList[i].onclick = e => {
                console.log('userNickname: ', userNickname);

                window.location.href = '/user/detail/admin?userNickname=' + userNickname;
            };
        }
    }


    //보드


    totalBoardButton.onclick = e => {
        userDisplayNone();
        accuseDisplayNone();
        boardMenuBar.style.display = '';

        const pageNum = 1; // 초기 페이지 설정


        const searchInput = document.getElementById('search_input');
        const button = document.getElementById('search_button');

        let keyword = ""; // 초기에는 빈 문자열로 설정합니다.

        button.onclick = e => {
            keyword = searchInput.value; // 입력된 텍스트 값으로 keyword를 업데이트합니다.
            console.log(keyword);

            if (keyword == "") {
                keyword = "";
            }

            // 사용자 목록을 가져오는 함수 호출
            getBoardList(pageNum, keyword);
        };

        // 사용자 목록을 가져오는 함수 호출 (초기에는 keyword가 빈 문자열로 호출됩니다.)
        getBoardList(pageNum, keyword);
        console.log('함수전달할 때  키워드', keyword);

    };


    function getBoardList(pageNum, keyword) {

        fetch(`/api/v1/boards/admin?pageNum=\${pageNum}&keyword=\${keyword}`)
            .then(response => response.json())
            .then(res => {
                const list = res.list;

                for (let listOne of list) {
                    const {
                        boardNo,
                        userAccount,
                        boardTitle,
                        boardWrittenDate,
                        boardViewCount
                    } = listOne;
                }

                for (let i = 0; i < boardList.length; i++) {
                    boardList[i].style.display = '';
                }

                // 보드리스트
                bln(list);
                blm(list);
                blt(list);
                bld(list);
                blc(list);

                const yourList = list; // yourList에 list를 할당

                const boardDelete = document.querySelectorAll('.delete');
                for (let index = 0; index < boardDelete.length; index++) {
                    boardDelete[index].onclick = function () {
                        if (confirm('게시물을 삭제하시겠습니까?')) {
                            alert('게시물 삭제 완료');
                            deleteBoard(yourList, index);
                        }
                    };
                }

                renderBoardList(res);

            });
    }

    function bln(list) {
        const ulArray = document.querySelectorAll('.bln');
        ulArray.forEach((ulElement, index) => {
            const asd = list[index].boardNo;
            ulElement.innerText = asd;
        });
    }

    function blm(list) {
        const ulArray = document.querySelectorAll('.blm');
        ulArray.forEach((ulElement, index) => {
            const asd = list[index].userAccount;
            ulElement.innerText = asd;
        });
    }

    function blt(list) {
        const ulArray = document.querySelectorAll('.blt');
        ulArray.forEach((ulElement, index) => {
            const asd = list[index].boardTitle;
            ulElement.innerText = asd;
        });
    }

    function bld(list) {
        const ulArray = document.querySelectorAll('.bld');
        ulArray.forEach((ulElement, index) => {
            const asd = list[index].boardWrittenDate;
            ulElement.innerText = asd;
        });
    }

    function blc(list) {
        const ulArray = document.querySelectorAll('.blc');
        ulArray.forEach((ulElement, index) => {
            const asd = list[index].boardViewCount;
            ulElement.innerText = asd;
        });
    }

    //게시물삭제
    function deleteBoard(list, index) {
        const boardNo = list[index].boardNo;
        console.log('asd123====', boardNo);

        fetch(`/api/v1/boards/admin/delete?boardNo=` + boardNo, {
            method: 'DELETE'
        })
            .then(response => response.json())
            .then(res => {
                console.log('res:', res);

                // 게시물 삭제 후 새로운 데이터 가져오기
                fetch('/api/v1/boards/admin') // 새로운 데이터 가져오기 API 엔드포인트로 변경해야 함
                    .then(response => response.json())
                    .then(res => {
                        const newList = res.list;
                        // 새로운 데이터를 이용하여 화면 업데이트
                        updateBoardList(newList);
                    });
            });
    }

    function updateBoardList(newList) {
        // 게시물 리스트 업데이트
        bln(newList);
        blm(newList);
        blt(newList);
        bld(newList);
        blc(newList);
    }


    //보드 페이징

    function renderBoardList({
                                 count,
                                 pageInfo,
                                 list
                             }) {
        console.log('count: ', count);
        console.log('pageInfo: ', pageInfo);
        console.log('list: ', list);

        //페이지 렌더링
        renderBoardPage(pageInfo);
    }

    //페이징
    function renderBoardPage({
                                 startPage,
                                 endPage,
                                 currentPage,
                                 prev,
                                 next,
                                 totalCount,
                                 PAGE_COUNT
                             }) {


        let tag = "";

        //이전 버튼 만들기
        if (prev) {
            tag += "<li class='page-item'><a class='page-link page-active' href='" + (startPage - 1) +
                "'>이전</a></li>";
        }
        //페이지 번호 리스트 만들기
        for (let i = startPage; i <= endPage; i++) {
            let active = '';
            if (currentPage.pageNo === i) {
                console.log(currentPage.pageNo);
                active = 'p-active';
            }

            tag += "<li class='page-item " + active + "'><a class='page-link page-custom' href='" + i +
                "'>" + i + "</a></li>";
        }
        //다음 버튼 만들기
        if (next) {
            tag += "<li class='page-item'><a class='page-link page-active' href='" + (endPage + 1) +
                "'>다음</a></li>";
        }

        // 페이지태그 렌더링
        const $pageUl = document.querySelector('.pagination');
        $pageUl.innerHTML = tag;

        // ul에 마지막페이지 번호 저장.
        $pageUl.dataset.fp = finalPage;

    }

    // 유저페이지 클릭 이벤트 핸들러
    function makePageButtonBoardClickEvent() {
        // 페이지 버튼 클릭이벤트 처리
        const $pageUl = document.querySelector('.pagination');
        $pageUl.onclick = e => {
            if (!e.target.matches('.page-item a')) return;

            e.preventDefault(); // 태그의 기본 동작 중단

            // 누른 페이지 번호 가져오기
            const pageNum = e.target.getAttribute('href');
            // console.log(pageNum);
            console.log("======today", pageNum)

            // 페이지 번호에 맞는 목록 비동기 요청
            getBoardList(pageNum);
        };
    }


    // 페이지 버튼 이벤트 등록
    makePageButtonBoardClickEvent();


    todayBoardButton.onclick = e => {
        userDisplayNone();
        accuseDisplayNone();
        boardMenuBar.style.display = '';

        const pageNum = 1; // 초기 페이지 설정


        const searchInput = document.getElementById('search_input');
        const button = document.getElementById('search_button');

        let keyword = ""; // 초기에는 빈 문자열로 설정합니다.

        button.onclick = e => {
            keyword = searchInput.value; // 입력된 텍스트 값으로 keyword를 업데이트합니다.
            console.log(keyword);

            if (keyword == "") {
                keyword = "";
            }

            // 사용자 목록을 가져오는 함수 호출
            getBoardTodayList(pageNum, keyword);
        };

        // 사용자 목록을 가져오는 함수 호출 (초기에는 keyword가 빈 문자열로 호출됩니다.)
        getBoardTodayList(pageNum, keyword);
        console.log('함수전달할 때  키워드', keyword);

    };

    function getBoardTodayList(pageNum, keyword) {

        fetch(`/api/v1/boards/admin1?pageNum=\${pageNum}&keyword=\${keyword}`)
            .then(response => response.json())
            .then(res => {
                // console.log('res: ', res);

                const list = res.list;
                // console.log('list: ', list);

                for (let listOne of list) {
                    const {
                        boardNo,
                        userAccount,
                        boardTitle,
                        boardWrittenDate,
                        boardViewCount
                    } = listOne;

                    //   console.log('userAccount: ', userAccount);


                }

                for (let i = 0; i < boardList.length; i++) {
                    boardList[i].style.display = '';
                }
                //보드리스트
                bln1(list);
                blm1(list);
                blt1(list);
                bld1(list);
                blc1(list);

                const yourList = list; // yourList에 list를 할당

                const boardDelete = document.querySelectorAll('.delete');
                for (let index = 0; index < boardDelete.length; index++) {
                    boardDelete[index].onclick = function () {
                        if (confirm('게시물을 삭제하시겠습니까?')) {
                            alert('게시물 삭제 완료');
                            deleteTodayBoard(yourList, index);
                        }
                    };
                }
                renderBoardTodayList(res);

            });
    }

    function bln1(list) {
        const ulArray = document.querySelectorAll('.bln');
        ulArray.forEach((ulElement, index) => {
            if (index >= list.length) {
                ulElement.innerText = ''; // 공백으로 출력
            } else {
                const asd = list[index].boardNo;
                ulElement.innerText = asd;
            }
        });
    }

    function blm1(list) {
        const ulArray = document.querySelectorAll('.blm');
        ulArray.forEach((ulElement, index) => {
            if (index >= list.length) {
                ulElement.innerText = ''; // 공백으로 출력
            } else {
                const asd = list[index].userAccount;
                ulElement.innerText = asd;
            }
        });
    }

    function blt1(list) {
        const ulArray = document.querySelectorAll('.blt');
        ulArray.forEach((ulElement, index) => {
            if (index >= list.length) {
                ulElement.innerText = ''; // 공백으로 출력
            } else {
                const asd = list[index].boardTitle;
                ulElement.innerText = asd;
            }
        });
    }

    function bld1(list) {
        const ulArray = document.querySelectorAll('.bld');
        ulArray.forEach((ulElement, index) => {
            if (index >= list.length) {
                ulElement.innerText = ''; // 공백으로 출력
            } else {
                const asd = list[index].boardWrittenDate;
                ulElement.innerText = asd;
            }
        });
    }

    function blc1(list) {
        const ulArray = document.querySelectorAll('.blc');
        ulArray.forEach((ulElement, index) => {
            if (index >= list.length) {
                ulElement.innerText = ''; // 공백으로 출력
            } else {
                const asd = list[index].boardViewCount;
                ulElement.innerText = asd;
            }
        });
    }

    function deleteTodayBoard(list, index) {
        const boardNo = list[index].boardNo;
        console.log('asd123====', boardNo);

        fetch(`/api/v1/boards/admin/delete?boardNo=` + boardNo, {
            method: 'DELETE'
        })
            .then(response => response.json())
            .then(res => {
                console.log('res:', res);

                // 게시물 삭제 후 새로운 데이터 가져오기
                fetch('/api/v1/boards/admin1') // 새로운 데이터 가져오기 API 엔드포인트로 변경해야 함
                    .then(response => response.json())
                    .then(res => {
                        const newList = res.list;
                        // 새로운 데이터를 이용하여 화면 업데이트
                        updateBoardList(newList);
                    });
            });
    }

    function updateBoardList(newList) {
        // 게시물 리스트 업데이트
        bln1(newList);
        blm1(newList);
        blt1(newList);
        bld1(newList);
        blc1(newList);
    }

    //보드 페이징

    function renderBoardTodayList({
                                      count,
                                      pageInfo,
                                      list
                                  }) {
        console.log('count: ', count);
        console.log('pageInfo: ', pageInfo);
        console.log('list: ', list);

        //페이지 렌더링
        renderBoardTodayPage(pageInfo);
    }

    //페이징
    function renderBoardTodayPage({
                                      startPage,
                                      endPage,
                                      currentPage,
                                      prev,
                                      next,
                                      totalCount,
                                      PAGE_COUNT
                                  }) {


        let tag = "";

        //이전 버튼 만들기
        if (prev) {
            tag += "<li class='page-item'><a class='page-link page-active' href='" + (startPage - 1) +
                "'>이전</a></li>";
        }
        //페이지 번호 리스트 만들기
        for (let i = startPage; i <= endPage; i++) {
            let active = '';
            if (currentPage.pageNo === i) {
                console.log(currentPage.pageNo);
                active = 'p-active';
            }

            tag += "<li class='page-item " + active + "'><a class='page-link page-custom' href='" + i +
                "'>" + i + "</a></li>";
        }
        //다음 버튼 만들기
        if (next) {
            tag += "<li class='page-item'><a class='page-link page-active' href='" + (endPage + 1) +
                "'>다음</a></li>";
        }

        // 페이지태그 렌더링
        const $pageUl = document.querySelector('.pagination');
        $pageUl.innerHTML = tag;

        // ul에 마지막페이지 번호 저장.
        $pageUl.dataset.fp = finalPage;

    }

    // 유저페이지 클릭 이벤트 핸들러
    function makePageButtonTodayBoardClickEvent() {
        // 페이지 버튼 클릭이벤트 처리
        const $pageUl = document.querySelector('.pagination');
        $pageUl.onclick = e => {
            if (!e.target.matches('.page-item a')) return;

            e.preventDefault(); // 태그의 기본 동작 중단

            // 누른 페이지 번호 가져오기
            const pageNum = e.target.getAttribute('href');
            // console.log(pageNum);
            console.log("======today", pageNum)

            // 페이지 번호에 맞는 목록 비동기 요청
            getBoardTodayList(pageNum);
        };
    }


    // 페이지 버튼 이벤트 등록
    makePageButtonTodayBoardClickEvent();


    // const keyword = '';
    // const page = 1;
    // const size = 10;
    //경고

    totalAccuseButton.onclick = e => {

        accuseMenuBar.style.display = '';

        userDisplayNone();
        boardDisplayNone();


        fetch('/api/v1/user/accuse')
            .then(response => response.json())
            .then(res => {
                // console.log('res: ', res);

                const list = res.list;
                // console.log('list: ', list);

                for (let listOne of list) {
                    const {
                        accuseNo,
                        userNickname,
                        accuseType,
                        accuseETC,
                        accuseWrittenDate
                    } = listOne;


                }
                for (let i = 0; i < accuseList.length; i++) {
                    accuseList[i].style.display = '';
                }
                //경고리스트
                aln(list);
                alm(list);
                alt(list);
                ale(list);
                alc(list);
                renderUserList(res);

            });
    }

    function aln(list) {
        const ulArray = document.querySelectorAll('.aln');
        ulArray.forEach((ulElement, index) => {
            const asd = list[index].accuseNo;
            ulElement.innerText = asd;
        });
    }

    function alm(list) {
        const ulArray = document.querySelectorAll('.alm');
        ulArray.forEach((ulElement, index) => {
            const asd = list[index].userNickname;
            ulElement.innerText = asd;
        });
    }

    function alt(list) {
        const ulArray = document.querySelectorAll('.alt');
        ulArray.forEach((ulElement, index) => {
            const asd = list[index].accuseType;
            ulElement.innerText = asd;
        });
    }

    function ale(list) {
        const ulArray = document.querySelectorAll('.ale');
        ulArray.forEach((ulElement, index) => {
            const asd = list[index].accuseETC;
            ulElement.innerText = asd;
        });
    }

    function alc(list) {
        const ulArray = document.querySelectorAll('.alc');
        ulArray.forEach((ulElement, index) => {
            const asd = list[index].accuseWrittenDate;
            ulElement.innerText = asd;
        });
    }


    //금일 신고

    todayAccuseButton.onclick = e => {

        accuseMenuBar.style.display = '';
        userDisplayNone();
        boardDisplayNone();
        fetch('/api/v1/user/accuse1')
            .then(response => response.json())
            .then(res => {
                console.log('res: ', res);

                const list = res.list;
                console.log('list: ', list);

                for (let listOne of list) {
                    const {
                        accuseNo,
                        userNickname,
                        accuseType,
                        accuseETC,
                        accuseWrittenDate
                    } = listOne;


                }
                for (let i = 0; i < accuseList.length; i++) {
                    accuseList[i].style.display = '';
                }
                //경고리스트
                aln1(list);
                alm1(list);
                alt1(list);
                ale1(list);
                alc1(list);
                renderUserList(res);

            });
    }

    function aln1(list) {
        const ulArray = document.querySelectorAll('.aln');
        ulArray.forEach((ulElement, index) => {
            if (index >= list.length) {
                ulElement.innerText = ''; // 공백으로 출력
            } else {
                const asd = list[index].accuseNo;
                ulElement.innerText = asd;
            }
        });
    }

    function alm1(list) {
        const ulArray = document.querySelectorAll('.alm');
        ulArray.forEach((ulElement, index) => {
            if (index >= list.length) {
                ulElement.innerText = ''; // 공백으로 출력
            } else {
                const asd = list[index].userNickname;
                ulElement.innerText = asd;
            }
        });
    }

    function alt1(list) {
        const ulArray = document.querySelectorAll('.alt');
        ulArray.forEach((ulElement, index) => {
            if (index >= list.length) {
                ulElement.innerText = ''; // 공백으로 출력
            } else {
                const asd = list[index].accuseType;
                ulElement.innerText = asd;
            }
        });
    }

    function ale1(list) {
        const ulArray = document.querySelectorAll('.ale');
        ulArray.forEach((ulElement, index) => {
            if (index >= list.length) {
                ulElement.innerText = ''; // 공백으로 출력
            } else {
                const asd = list[index].accuseETC;
                ulElement.innerText = asd;
            }
        });
    }

    function alc1(list) {
        const ulArray = document.querySelectorAll('.alc');
        ulArray.forEach((ulElement, index) => {
            if (index >= list.length) {
                ulElement.innerText = ''; // 공백으로 출력
            } else {
                const asd = list[index].accuseWrittenDate;
                ulElement.innerText = asd;
            }
        });
    }
</script>
</body>

</html>