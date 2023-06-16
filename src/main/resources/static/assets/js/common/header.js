import {addModalBtnEvent, addModalCloseEvent} from "./modal-handler.js";
import {toBack} from "../chatting/chatting-modal.js";
import {toTopBtnHandler} from "./to-top-btn-handler.js";

(() => {
    // 모달 열고 닫는 이벤트
    addModalBtnEvent();
    addModalCloseEvent();

    // 채팅 메세지 모달 안에서 채팅방 모달로 돌아가기
    toBack();

    // 위로가기 버튼 이벤트
    toTopBtnHandler();
})();