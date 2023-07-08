import {renderUnreadMessages, searchPointHistory} from "./chatting-modal.js";
import {getRecentMatchingNo, matchingRequestEvent, matchingResponseEvent} from "./matching.js";

export function scrollDown() {
    const chatMessages = document.querySelectorAll('.chatting-message-body');
    //scroll down
    chatMessages.forEach(cm => {
        cm.scrollTop = cm.scrollHeight;
    });
}

//메세지박스 렌더링
export async function outputMessage(message) {
    // console.log('outputMessage 진입');
    const userNickname = document.getElementById('loginUserInfo').dataset.userNickname;
    const room = document.getElementById(message.room);
    if (room === null) return;

    if(room.dataset.chattingFrom === userNickname) {
        matchingRequestEvent();
    } else {
        matchingResponseEvent();
    }


    const matchingBtn = room.querySelector('.matching-accept-btn');
    const chatting_message_option = room.querySelector('.chatting-message-option');

    const div = document.createElement('div');
    div.classList.add('chatting-message-card');

    // 내가 보낸 메세지
    if (message.username === userNickname) {
        div.classList.add('chatting-message-card');
        div.classList.add('message-from');
        div.innerHTML = `
                <img class="chatting-profile" src="${message.myProfile}" alt="프로필이미지">
                <div class="message-content-container">
                    <div class="message-nickname">${message.username}</div>
                    <div class="message-content-wrapper">
                        <div class="message-content">${message.text}</div>
                        <span class="send-time">${message.time}</span>
                    </div>
                </div>
            `;

    // 내가 받은 메세지일때
    } else {
        //상대방 프로필 사진
        const yourProfile = room.querySelector('.chatting-profile-img').src;

        // 매칭 ststus가 request로 변하면 메세지 받은 사람 버튼 수락/거절로 변경
        if(room.dataset.chattingFrom !== userNickname && message.matchingStatus === 'REQUEST' && matchingBtn.childNodes[1].nodeValue !== `매칭 수락`){
            getRecentMatchingNo(room.id);
            matchingBtn.dataset.matchingStatus = message.matchingStatus;
            matchingBtn.childNodes[1].nodeValue = `매칭 수락`;
            matchingBtn.disabled = false;

            const gameover_container = document.createElement('div');
            gameover_container.classList.add('gameover-container');
            chatting_message_option.appendChild(gameover_container);

            const matching_reject_btn = document.createElement('button');
            matching_reject_btn.classList.add('matching-reject-btn');
            matching_reject_btn.dataset.matchingStatus = message.matchingStatus;

            gameover_container.appendChild(matching_reject_btn);
            matching_reject_btn.append(`매칭 거절`);
            matchingResponseEvent();
        }

        // 매칭 status가 confirm으로 변하면 메세지 받은 사람 버튼 변경
        if(room.dataset.chattingFrom === userNickname && message.matchingStatus === 'CONFIRM' && matchingBtn.childNodes[1].nodeValue !== `게임 완료`){
            getRecentMatchingNo(room.id);
            matchingBtn.dataset.matchingStatus = message.matchingStatus;
            matchingBtn.childNodes[1].nodeValue = `게임 완료`;
            matchingBtn.disabled = false;
        }

        if(room.dataset.chattingFrom === userNickname && message.matchingStatus === 'REJECT' && matchingBtn.childNodes[1].nodeValue !== `매칭 신청`){
            getRecentMatchingNo(room.id);
            matchingBtn.dataset.matchingStatus = message.matchingStatus;
            matchingBtn.childNodes[1].nodeValue = `매칭 신청`;
            matchingBtn.disabled = false;
        }

        //매칭 status가 done으로 변하면 메세지 받은사람 버튼 변경
        if(room.dataset.chattingFrom !== userNickname && message.matchingStatus === 'DONE' && matchingBtn.childNodes[1].nodeValue !== `매칭 대기`){
            // getRecentMatchingNo(room.id);
            // const matchingNo = room.querySelector('.matching-accept-btn').dataset.matchingNo;
            // const flag = searchPointHistory(matchingNo);
            // if(!flag) {
                matchingBtn.disabled = false;
                matchingBtn.dataset.matchingStatus = message.matchingStatus;
                document.querySelector('.chatting-handshake-img').src = '/assets/img/chattingModal/checkmark.png';
                document.querySelector('.chatting-handshake-img').alt = '게임완료이미지';
                matchingBtn.childNodes[1].nodeValue = `포인트 받기`;
            // }

        }

        if(room.dataset.chattingFrom === userNickname && message.matchingStatus === 'DONE'){
            const matchingNo = await getRecentMatchingNo(room.id);
            console.log(matchingBtn.dataset.matchingNo);
            // const matchingNo = room.querySelector('.matching-accept-btn').dataset.matchingNo;
            const flag = await searchPointHistory(matchingNo);
            console.log(flag);
            if(!flag){
                matchingBtn.disabled = true;
                matchingBtn.childNodes[1].nodeValue = `정산중`;
            }else {
                matchingBtn.disabled = false;
                matchingBtn.childNodes[1].nodeValue = `매칭 신청`;
            }
        }

        div.classList.add('chatting-message-card');
        div.classList.add('message-to');
        div.innerHTML = `
                <img class="chatting-profile" src="${yourProfile}" alt="프로필이미지">
                <div class="message-content-container">
                        <div class="message-nickname">${message.username}</div>
                        <div class="message-content-wrapper">
                            <div class="message-content">${message.text}</div>
                            <span class="send-time">${message.time}</span>
                        </div>
                    </div>
                    `;
    }
    room.querySelector('.chatting-message-body').appendChild(div);
}

//DB에서 메세지 읽어오기
export function getMessages(room) {

    fetch(`/api/v1/chat/messages/${room}`)
        .then(res => res.json())
        .then(result => {
            setChattingDetailBox(room, result);
        });
}

//채팅방 최초 진입시 렌더링
function setChattingDetailBox(chattingNo, result) {
    // console.log('setChattingDetailBox 도달');
    const room = document.getElementById(chattingNo);
    room.querySelector('.chatting-message-body').innerHTML = '';
    const {userNickname, myProfileImage, yourProfileImage, messageList} = result;

    for (const msg of messageList) {
        const {messageFrom, messageContent, messageTime} = msg;
        let message = {
            room: chattingNo,
            username: messageFrom,
            myProfile: myProfileImage,
            yourProfile: yourProfileImage,
            text: messageContent,
            time: messageTime
        }

        if(message.myProfile === 'noProfile'){
            message.myProfile = '/assets/img/chattingModal/user.png';
        }
        if(message.yourProfile === 'noProfile'){
            message.yourProfile = '/assets/img/chattingModal/user.png';
        }

        outputMessage(message);
    }
    scrollDown();
    renderUnreadMessages(chattingNo);
}

// 메세지 저장
export function saveMessage({username, room, msg}) {
    const messageDTO = {
        chattingNo: room,
        messageContent: msg,
        messageFrom: username
    }
    const requestInfo = {
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify(messageDTO)
    };
    fetch(`/api/v1/chat/messages`, requestInfo)
        .then(res => res.json());
        // .then(flag => {
            // if (flag) console.log('메세지 저장 성공');
            // else console.log('메세지 저장 실패');
        // })

}

// 채팅중일때 메세지 읽음 처리
export function readMessages(chattingNo){
    const requestInfo = {
        method: 'PUT',
        headers: {
            'content-type': 'application/json'
        },
        body: chattingNo
    };
    fetch(`/api/v1/chat/messages/read`, requestInfo);
}