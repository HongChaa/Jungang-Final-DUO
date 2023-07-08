import {addModalBtnEvent, addModalCloseEvent} from "../common/modal-handler.js";

export async function getChattingList() {
    // 추후 session에 회원정보 담기면 경로 수정
    await fetch(`/api/v1/chat/chattings`)
        .then(res => res.json())
        .then(result => renderChattingList(result));
}

// 채팅 클릭했을때 메세지창 열어줘야함
function makeChatting(chat) {

    chat.onclick = (e) => {
        const userAccount = chat.closest('.duo-profile').id;
        // console.log('makeChatting 도달');
        const requestInfo = {
            method: 'POST',
            headers: {
                'content-type': 'application/json'
            },
            body: userAccount
        }

        fetch(`/api/v1/chat/chattings/new`, requestInfo)
            .then(res => res.json())
            .then(async result => {
                // result = 생성된 채팅번호
                // console.log(result);
                await getChattingList();
                document.querySelector('.chatting-modal-dialog').open = true;
                const chatting = document.getElementById(result);
                // console.log(chatting);
                chatting.querySelector('.modal-btn').click();
            });
    }
}


export function makeChattingRoom() {
    [...document.querySelectorAll('.chatting-icon')].forEach(
        chat => makeChatting(chat)
    );
}

async function renderChattingList(result) {
    const myNickname = document.getElementById('loginUserInfo').dataset.userNickname;
    document.querySelector('.chatting-modal-container').innerHTML = '';

    if (result.length === 0) {
        const $chattings = document.createElement('li');
        $chattings.id = 'empty-chatting-list';
        $chattings.innerHTML = '<p>아직 채팅 내역이 없어요..<br>다른 듀오 회원에게<br>말을 걸어보세요!</p>';
        document.querySelector('.chatting-modal-container').appendChild($chattings);

    } else {
        for (let i = 0; i < result.length; i++) {

            let {
                chattingNo,
                chattingFrom, //채팅을 먼저 요청한 사람 닉네임
                profileImage,
                userNickname, //체팅 상대방 닉네임
                messageContent,
                messageUnreadCount,
                matchingStatus,
                matchingNo,
            } = result[i];

            if(profileImage === 'noProfile'){
                profileImage = '/assets/img/chattingModal/user.png';
            }

            let $chattings = document.createElement('li');
            $chattings.id = chattingNo;
            $chattings.classList.add('chatting-card');
            $chattings.dataset.chattingFrom = chattingFrom;

            const $chat_card = document.createElement('div');
            $chat_card.classList.add('chat-card');

            const $modal_btn = document.createElement('div');
            $modal_btn.classList.add('chatting-card-inner');
            $modal_btn.classList.add('modal-btn');

            $modal_btn.innerHTML = `<img src="${profileImage}" alt="프로필 이미지" class="chatting-profile-img">
                <div class="chatting-info">
                    <div class="chatting-nickname">${userNickname}</div>
                    <div class="chatting-current-message">${messageContent}</div>
                </div>
                <div class="chatting-unread">${messageUnreadCount}</div>`;

            $chattings.appendChild($chat_card);
            $chat_card.appendChild($modal_btn);

            const chattingUnread = $modal_btn.querySelector('.chatting-unread');

            if(messageUnreadCount === 0){
                chattingUnread.style.display = 'none';
            } else if (messageUnreadCount >= 0 && messageUnreadCount <= 200) {
                chattingUnread.style.display = 'block';
                chattingUnread.innerText = messageUnreadCount;
            } else {
                chattingUnread.style.display = 'block';
                chattingUnread.innerText = '200+';
            }

            const $dialog = document.createElement('dialog');
            $dialog.classList.add('message-dialog');
            $chat_card.appendChild($dialog);

            const $chatting_message_modal_wrapper = document.createElement('div');
            $chatting_message_modal_wrapper.classList.add('chatting-message-modal-wrapper');
            $dialog.appendChild($chatting_message_modal_wrapper);

            const $chatting_message_modal_container = document.createElement('div');
            $chatting_message_modal_container.classList.add('chatting-message-modal-container');
            $chatting_message_modal_wrapper.appendChild($chatting_message_modal_container);

            const $chatting_message_head = document.createElement('div');
            $chatting_message_head.classList.add('chatting-message-head');
            const chatting_message_option = document.createElement('div');
            chatting_message_option.classList.add('chatting-message-option');
            const chatting_message_body = document.createElement('div');
            chatting_message_body.classList.add('chatting-message-body');
            const chatForm = document.createElement('form');
            chatForm.classList.add('chatting-message-input-box');
            chatForm.classList.add('chat-form');

            $chatting_message_modal_container.appendChild($chatting_message_head);
            $chatting_message_modal_container.appendChild(chatting_message_option);
            $chatting_message_modal_container.appendChild(chatting_message_body);
            $chatting_message_modal_container.appendChild(chatForm);

            const $toBack = document.createElement('img');
            $toBack.classList.add('chatting-arrow-img');
            $toBack.classList.add('toBack');
            $toBack.src = '/assets/img/chattingModal/arrows.png';
            $toBack.alt = '뒤로가기';
            $chatting_message_head.appendChild($toBack);

            const chatting_message_nickname = document.createElement('div');
            chatting_message_nickname.classList.add('chatting-message-nickname');
            chatting_message_nickname.textContent = userNickname;
            $chatting_message_head.appendChild(chatting_message_nickname);

            const matching_accept_container = document.createElement('div');
            matching_accept_container.classList.add('matching-accept-container');
            chatting_message_option.appendChild(matching_accept_container);

            // 로그인 한 사람이 Chatting To일때
            // console.log(myNickname);
            // console.log(chattingFrom);
            if (chattingFrom !== myNickname) {
                const matching_accept_btn = document.createElement('button');
                matching_accept_btn.classList.add('matching-accept-btn');
                matching_accept_btn.dataset.matchingStatus = matchingStatus;
                matching_accept_container.appendChild(matching_accept_btn);

                const chatting_handshake_img = document.createElement('img');
                chatting_handshake_img.classList.add('chatting-handshake-img');
                chatting_handshake_img.src = '/assets/img/chattingModal/handshake.png';
                chatting_handshake_img.alt = '매칭수락이미지';
                matching_accept_btn.appendChild(chatting_handshake_img);
                matching_accept_btn.append('매칭 대기');

                switch (matchingStatus) {
                    case 'REQUEST':
                        matching_accept_btn.disabled = false;
                        matching_accept_btn.childNodes[1].nodeValue = `매칭 수락`;
                        matching_accept_btn.dataset.matchingNo = matchingNo;

                        const gameover_container = document.createElement('div');
                        gameover_container.classList.add('gameover-container');
                        chatting_message_option.appendChild(gameover_container);

                        const matching_reject_btn = document.createElement('button');
                        matching_reject_btn.classList.add('matching-reject-btn');
                        gameover_container.appendChild(matching_reject_btn);
                        matching_reject_btn.append(`매칭 거절`);
                        matching_reject_btn.dataset.matchingNo = matchingNo;
                        break;

                    case 'CONFIRM':
                        matching_accept_btn.disabled = true;
                        matching_accept_btn.childNodes[1].nodeValue = `매칭 확정`;
                        matching_accept_btn.dataset.matchingNo = matchingNo;
                        break;

                    case 'REJECT':
                        matching_accept_btn.disabled = true;
                        matching_accept_btn.childNodes[1].nodeValue = `매칭 대기`;
                        matching_accept_btn.dataset.matchingNo = matchingNo;
                        break;

                    case 'DONE':
                        const flag = await searchPointHistory(matchingNo);
                        // console.log('왜?');
                        matching_accept_btn.dataset.matchingNo = matchingNo;
                        // console.log(flag);
                        if(!flag) {
                        // console.log('안받음');
                            matching_accept_btn.disabled = false;
                            chatting_handshake_img.src = '/assets/img/chattingModal/checkmark.png';
                            chatting_handshake_img.alt = '게임완료이미지';
                            matching_accept_btn.childNodes[1].nodeValue = `포인트 받기`;
                            break;
                        }else {
                        // console.log('받음');
                            matching_accept_btn.disabled = true;
                            matching_accept_btn.dataset.matchingNo = '';
                            chatting_handshake_img.src = '/assets/img/chattingModal/handshake.png';
                            chatting_handshake_img.alt = '매칭수락이미지';
                            matching_accept_btn.childNodes[1].nodeValue = '매칭 대기';
                            break;
                        }
                    default :
                        matching_accept_btn.disabled = true;
                        matching_accept_btn.dataset.matchingNo = '';
                }

            }

            // 로그인 한 사람이 Chatting From일 때
            // console.log(myNickname);
            // console.log(chattingFrom);
            if (chattingFrom === myNickname) {

                let $rightBtn = document.createElement('button');
                $rightBtn.classList.add('gameover-btn');
                $rightBtn.classList.add('matching-accept-btn');
                $rightBtn.dataset.matchingStatus = matchingStatus;
                matching_accept_container.appendChild($rightBtn);

                if(chattingFrom === userNickname){
                    $rightBtn.style.display = 'none';
                }

                const chatting_handshake_img = document.createElement('img');
                chatting_handshake_img.classList.add('chatting-handshake-img');
                chatting_handshake_img.src = '/assets/img/chattingModal/handshake.png';
                chatting_handshake_img.alt = '매칭수락이미지';
                $rightBtn.appendChild(chatting_handshake_img);
                $rightBtn.append('매칭신청');

                switch (matchingStatus) {
                    case 'REQUEST':
                        $rightBtn.disabled = true;
                        $rightBtn.childNodes[1].nodeValue = `수락 대기중`;
                        $rightBtn.dataset.matchingNo = matchingNo;
                        break;
                    case 'CONFIRM':
                        $rightBtn.childNodes[1].nodeValue = `게임 완료`;
                        $rightBtn.dataset.matchingNo = matchingNo;
                        break;
                    case 'REJECT':
                        $rightBtn.childNodes[1].nodeValue = `매칭 신청`;
                        $rightBtn.dataset.matchingNo = matchingNo;
                        break;
                    case 'DONE':
                        const flag = await searchPointHistory(matchingNo);
                        if(flag) {
                            $rightBtn.disabled = false;
                            // console.log('안받음');
                            chatting_handshake_img.src = '/assets/img/chattingModal/handshake.png';
                            chatting_handshake_img.alt = '매칭수락이미지';
                            $rightBtn.dataset.matchingNo = matchingNo;
                            $rightBtn.childNodes[1].nodeValue = `매칭 신청`;
                        } else {
                            $rightBtn.disabled = true;
                            $rightBtn.childNodes[1].nodeValue = `정산중`;
                        }
                        // $rightBtn.onclick = async e => {
                        //     const $rateModal = await renderRateModal(matchingNo, userNickname);
                        //     document.body.appendChild($rateModal);
                        //     $rateModal.show();
                        // }
                        break;
                    default :
                        $rightBtn.dataset.matchingNo = '';
                }
            }

            const message_send_box = document.createElement('input');
            message_send_box.classList.add('message-send-box');
            message_send_box.classList.add('msg');
            message_send_box.placeholder = '메시지를 100자 이내로 입력해주세요';
            message_send_box.setAttribute('maxlength', '100');
            message_send_box.setAttribute('required', 'required');
            message_send_box.setAttribute('autofocus', 'autofocus');
            chatForm.appendChild(message_send_box);

            const message_send_btn = document.createElement('button');
            message_send_btn.classList.add('message-send-btn');
            message_send_btn.textContent = '전송';
            chatForm.appendChild(message_send_btn);

            document.querySelector('.chatting-modal-container').appendChild($chattings);

        }
        addModalBtnEvent();
        addModalCloseEvent();
        toBack();
    }
}

export function toBack() {

    [...document.querySelectorAll('.toBack')].forEach(
        $toBack => $toBack.onclick = e => {
            e.target.closest('.chatting-modal-dialog').close();
            document.getElementById('chatting-btn').click();
        }
    );
}

export function renderUnreadMessages(chattingNo) {
    const $chatting = document.getElementById(chattingNo);
    // console.log($chatting);
    const $targetCount = $chatting.querySelector('.chatting-unread');
    const $targetMessage = $chatting.querySelector('.chatting-current-message');
    // console.log($target);

    fetch(`/api/v1/chat/messages/unread/${chattingNo}`)
        .then(res => res.json())
        .then(result => {
            const {unreadCount, message} = result;
            if(unreadCount === 0){
                $targetCount.style.display = 'none';
            } else if (unreadCount >= 0 && unreadCount <= 200) {
                $targetCount.style.display = 'block';
                $targetCount.innerText = unreadCount;
            } else {
                $targetCount.style.display = 'block';
                $targetCount.innerText = '200+';
            }

            $targetMessage.innerText = message;
        })

}

export function renderTotalUnreadMessages(){
    // console.log('모달 꺼집니당 메세지 갯수 세어야함..');

    const $target = document.getElementById('unread-chatting-count');
    fetch(`/api/v1/chat/messages/unread`)
        .then(res => res.json())
        .then(totalUnread => {
            if(totalUnread === 0){
                $target.style.display = 'none';
            } else if (totalUnread >= 0 && totalUnread <= 200) {
                $target.style.display = 'block';
                $target.innerText = totalUnread;
            } else {
                $target.style.display = 'block';
                $target.innerText = '200+';
            }
        })
}

//해당 매칭으로 포인트 지급 받았는지 확인
export function searchPointHistory(matchingNo) {
    return fetch(`/api/v1/points/matching/${matchingNo}`)
        .then(res => res.json());
}

