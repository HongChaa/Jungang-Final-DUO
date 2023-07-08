// 모든 모달 버튼에 이벤트를 등록하는 함수
import {getMessages} from "../chatting/messageRendering.js";
import {getChattingList, renderTotalUnreadMessages} from "../chatting/chatting-modal.js";

export function modalHandler($modalBtn) {


    $modalBtn.onclick = e => {
        e.preventDefault();
        const $target = e.target.closest('.modal-btn').nextElementSibling;

        if (!$target.hasAttribute('open')) {
            // 해당 모달이 채팅목록이라면 채팅목록 출력
            if ($target.classList.contains('chatting-modal-dialog')){
                const unread = document.getElementById('unread-chatting-count');
                unread.style.display = 'none';
                // console.log('채팅모달 열기 진입');
                getChattingList().then();
            }
            // 해당 모달이 메세지 다이어로그라면 내부 메세지 출력
            if($target.classList.contains('message-dialog')){
                const room = e.target.closest('.chatting-card').id;
                getMessages(room);
            }
            $target.showModal();
        }
        else {
            renderTotalUnreadMessages();
            $target.close();
        }
    }
}

export function addModalBtnEvent() {

    [...document.querySelectorAll('.modal-btn')].forEach(
        $modalBtn => modalHandler($modalBtn)
    );

}

export function addModalCloseEvent() {

    [...document.querySelectorAll('dialog')].forEach(
        $dialog => $dialog.onclick = e => {
            if (e.target.matches('dialog')) {
                if (e.target.classList.contains('chatting-modal-dialog') || e.target.classList.contains('message-dialog')){
                    renderTotalUnreadMessages();
                }
                $dialog.close();
            }
        }
    )

}