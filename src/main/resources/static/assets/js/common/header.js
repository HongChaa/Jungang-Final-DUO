import {addModalBtnEvent, addModalCloseEvent} from "./modal-handler.js";
import {makeChattingRoom, renderTotalUnreadMessages, toBack} from "../chatting/chatting-modal.js";
import {toTopBtnHandler} from "./to-top-btn-handler.js";
import {connectSocket} from "../chatting/main.js";
import {addDropdownEvent} from "./dropdown-handler.js";
import {signInSubmitHandler} from "../user/sign-in-submit-handler.js";
import {renderMatchingCalendar} from "../chatting/chatting-calendar.js";
import {preventCharPwd} from "./preventCharPwd.js";

(async () => {

    // 로그인 했을때만 실행되는 함수
    if (document.getElementById('chatting-btn')) {

        // 소켓 연결
        connectSocket();

        //안읽은 메세지 출력
        renderTotalUnreadMessages();

        // 채팅 메세지 모달 안에서 채팅방 모달로 돌아가기
        toBack();

        //매칭 날짜 캘린더
        renderMatchingCalendar();

        // 내 정보 모달 드롭다운 이벤트
        addDropdownEvent('user-info-btn', 'my-page-modal-wrapper');
    } else {
        // 로그인 드롭다운 이벤트
        addDropdownEvent('sign-in-btn', 'sign-in-modal-wrapper');

        // 로그인 모달 인풋에서 엔터키 눌렀을 때 서브밋 보내기 이벤트
        signInSubmitHandler();
    }
    // 모달 열고 닫는 이벤트
    addModalBtnEvent();
    addModalCloseEvent();

    // 위로가기 버튼 이벤트
    toTopBtnHandler();

    // 비밀번호에 한글 입력 막기
    preventCharPwd();

})();
