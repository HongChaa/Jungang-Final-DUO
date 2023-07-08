<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="/assets/css/common.css">
    <link rel="stylesheet" href="/assets/css/board/board-write.css">

</head>
<body>

<div id="main-wrapper">

<!--    <dialog>-->
        <div id="board-write-wrapper">

            <form action="/board/write">
                <div class="text-area-wrapper">
                    <label>
                        <textarea id="board-title" name="boardTitle" placeholder="제목"></textarea>
                    </label>
                </div>

                <!--            일반 사용자의 경우 -->
                <div id="board-category-wrapper">
                    <label for="board-category-free">자유게시판</label>
                    <input type="radio" id="board-category-free" name="boardType" value="FREE">
                    <label for="board-category-accuse">신고하기</label>
                    <input type="radio" id="board-category-accuse" name="boardType" value="ACCUSE">
                </div>

                <!--            관리자의 경우 -->
                <!--            <label>-->
                <!--                <input type="radio" name="boardType" value="NOTICE" style="display: none">-->
                <!--            </label>-->

                <div class="text-area-wrapper">
                    <label>
                        <textarea id="board-content" name="boardContent" placeholder="글내용"></textarea>
                    </label>
                </div>

                <div id="board-btn-wrapper">
                    <button type="submit">등록</button>
                    <button>취소하기</button>
                </div>
            </form>
        </div>
<!--    </dialog>-->

</div>

</body>
</html>