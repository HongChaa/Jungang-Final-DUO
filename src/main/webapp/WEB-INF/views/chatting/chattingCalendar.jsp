<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="match-calendar modal-btn"></div>
<dialog class="match-calendar-dialog">
    <div id="calendar-wrapper">
        <p id="select-date">날짜 선택</p>
        <div id="matching-calendar">
            <div id="calendar-title">
                <div id="now-year-box"><span id="now-year"></span>년</div>
                <div id="now-month-box"><span id="now-month"></span>월</div>
                <div id="arrows">
                    <!--                앞으로 가기, 뒤로 가기-->
                    <img id="prev" class="blue-arrow" src="/assets/img/chattingModal/blue-arrow.png" alt="prev">
                    <img id="next" class="blue-arrow" src="/assets/img/chattingModal/blue-arrow.png" alt="next">
                </div>
            </div>
            <!--           시작 - 요일 출력 hard-coding -->
            <div id="days-box">
                <div id="sun" class="days my-calendar">일</div>
                <div id="mon" class="days my-calendar">월</div>
                <div id="tue" class="days my-calendar">화</div>
                <div id="wed" class="days my-calendar">수</div>
                <div id="thu" class="days my-calendar">목</div>
                <div id="fri" class="days my-calendar">금</div>
                <div id="sat" class="days my-calendar">토</div>
            </div>
            <!--           끝 - 요일 출력 hard-coding -->
            <!--        시작 - 날짜 출력 -->
            <div id="dates" class="date my-calendar"></div>
            <!--        끝 - 날짜 출력 -->
        </div>
    </div>
</dialog>