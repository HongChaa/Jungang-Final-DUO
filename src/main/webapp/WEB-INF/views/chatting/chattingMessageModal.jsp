<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--    <div class="modal-background show-modal"></div>--%>
<%--<div class="modal-background show-modal"></div>--%>
<dialog class = "message-dialog">
<div class=" chatting-message-modal-wrapper">
    <div class="chatting-message-modal-container">
        <div class="chatting-message-head">
            <img class="chatting-arrow-img toBack" src="/assets/img/chattingModal/arrows.png" alt="뒤로가기">
            <div class="chatting-message-nickname">홍차</div>
        </div>
        <div class="chatting-message-option">
            <div class="matching-accept-container">
                <button class="matching-accept-btn">
                    <img class="chatting-handshake-img" src="/assets/img/chattingModal/handshake.png" alt="매칭수락이미지">
                    매칭 확정
                </button>
            </div>
            <div class="gameover-container">
                <button class="gameover-btn">
                    <img class="chatting-gameover-img" src="/assets/img/chattingModal/checkmark.png" alt="게임완료이미지">
                    게임 완료
                </button>
            </div>
        </div>
        <div class="chatting-message-body">
            <div class="chatting-message-card message-from">
                <img class="chatting-profile" src="/assets/img/chattingModal/woogi.jpg" alt="프로필이미지">
                <div class="message-content-container">
                    <div class="message-nickname">상대방</div>
                    <div class="message-content-wrapper">
                        <div class="message-content">하이하이하이하이하이항히아하이하이ㅏㅎ이하이ㅏㅎ이항히ㅏㅇ히아히아힝히ㅏ이항히ㅏ이ㅏㅎ이</div>
                        <span class="send-time">22:00</span>
                    </div>
                </div>
            </div>
        </div>

        <form class="chatting-message-input-box chat-form">
            <input class="message-send-box msg" type="text" placeholder="메시지를 입력해주세요" required autofocus>
            <button class="message-send-btn">전송</button>
        </form>
    </div>
</div>
</dialog>