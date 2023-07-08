<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../common/static-head.jsp"%>
<link rel="stylesheet" href="/assets/css/admin/adminModal.css">
</head>
<body>
    <div id="main-wrapper">
        <form action="/user/accuse" method="POST" class="container">
            <div class="inbox">
                <ul class="inbox_text">
                    <li>플레이어 신고하기</li>
                </ul>
                <div class="inbox_img"><img src="/assets/img/admin/비행기.png" alt="send"></div>
            </div>

            <ul class="checkbox_table">
                <div class="checkbox_content">
                    <input type="checkbox" name="accuseType" class="check_input" id="check_accuse">
                    <label for="check_accuse"> 타인을 비방 / 모방하는 글 또는 댓글 </label>
                </div>
                <div class="checkbox_content">
                    <input type="checkbox" name="accuseType" class="check_input" id="check_accuse">
                    <label for="check_accuse"> 부적절한 사진 및 게시물 게시</label>
                </div>
                <div class="checkbox_content">
                    <input type="checkbox" name="accuseType" class="check_input" id="check_accuse" >
                    <label for="check_accuse"> 타 유저 경고 확인 결과 관리자 판단하의 추가 경고 </label>
                </div>
                <div class="checkbox_content">
                    <input type="checkbox" name="accuseType" class="check_input" id="check_accuse">
                    <label for="check_accuse"> 부적절한 프로필 사진 업로드 </label>
                </div>
                <div class="checkbox_content">
                    <input type="checkbox" name="accuseType" class="check_input" id="check_accuse">
                    <label for="check_accuse"> 기타 : </label>
                    <input type="text" id="acc"> 
                </div>
            </ul>


        </form>


    </div>
    
</body>
</html>