import {selectDateEvent} from "./chatting-calendar.js";

// 매칭요청을 하는 사람의 이벤트
export function matchingRequestEvent(){
    // console.log('matchingRequestEvent 진입');
    const requestBtn = [...document.querySelectorAll('.matching-accept-btn')];
    // console.log(requestBtn);
    requestBtn.forEach(
        mr => mr.onclick = async e => {
            const matchingStatus = e.target.closest('.matching-accept-btn').dataset.matchingStatus;
            // console.log(matchingStatus);
            const $chattingNo = e.target.closest('.chatting-card').id;
            const $matchingNo = e.target.closest('.matching-accept-btn').dataset.matchingNo;
            switch (matchingStatus){
                case 'null' :
                case 'REJECT' :
                case 'DONE' :
                    const result = await checkIsPossible($chattingNo);
                    if(result >= 0){
                        matchingRequest($chattingNo);
                    }
                    break;
                case 'CONFIRM' : gameDone($chattingNo, $matchingNo); break;
            }
        });
}

//매칭 신청 가능여부 확인
function checkIsPossible(chattingNo){

    return fetch(`/api/v1/points/check/${chattingNo}`)
        .then(res => res.json())
        .then(result => {
            if(result < 0) {
                alert('포인트가 부족하여 매칭을 신청할 수 없습니다..');
            } else {
                alert(`포인트를 사용합니다!`);
            }
            return result;
        })

}

// 요청을 받은 사람의 이벤트
export function matchingResponseEvent(){
    //매칭 진행시 이벤트 목록
    const requestedBtn = [...document.querySelectorAll('.matching-accept-btn')];
    requestedBtn.forEach(
        mr => mr.onclick = e => {
            const matchingStatus = e.target.closest('.matching-accept-btn').dataset.matchingStatus;
            const $chattingNo = e.target.closest('.chatting-card').id;
            const $matchingNo = e.target.closest('.matching-accept-btn').dataset.matchingNo;
            // console.log('온클릭 이벤트까지 넘어옴');
            switch (matchingStatus){
                case 'REQUEST' :
                    document.querySelector('.match-calendar').click();
                    selectDateEvent();
                    break;
                case 'DONE' :
                    getMatchingPoint($chattingNo, $matchingNo);
            }

        });

    //매칭 거절
    const rejectBtn = [...document.querySelectorAll('.matching-reject-btn')];
    rejectBtn.forEach(
        mr => mr.onclick = e => {
            // console.log('거절 온클릭 이벤트까지 넘어옴');
            const $chattingNo = e.target.closest('.chatting-card').id;
            const $target = e.target.closest('.chatting-message-option');
            const $acceptBtn = $target.querySelector('.matching-accept-btn');
            const $matchingNo = $acceptBtn.dataset.matchingNo;
            $acceptBtn.dataset.matchingStatus = 'REJECT';
            $acceptBtn.disabled = true;
            $acceptBtn.childNodes[1].nodeValue = '매칭 대기';

            const btnBox = e.target.closest('.chatting-message-option');
            btnBox.children[1].remove();

            rejectMatching($chattingNo, $matchingNo);
        });
}

//CONFIRM -> REJECT 로 변경
function rejectMatching(chattingNo, matchingNo){
    const requestInfo = {
        method: 'PUT',
        headers: {
            'content-type': 'application/json'
        },
        body: matchingNo
    }
    fetch(`/api/v1/matchings/reject`, requestInfo)
        .then(res => res.json())
        .then(result => {
            if(result){
               sendNoticeMessage(chattingNo, '매칭이 거절되었습니다. 다음에 다시 신청해주세요!');
            }
        })
}

//포인트 받기 클릭시 이벤트
function getMatchingPoint($chattingNo, $matchingNo){
    const payload = {
        chattingNo : $chattingNo,
        matchingNo : $matchingNo
    }
    const requestInfo = {
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify(payload)
    }

    fetch(`/api/v1/points/give`, requestInfo)
        .then(res => res.json())
        .then(point => receivedPoint($chattingNo, point));
}

//포인트 적립 후 이벤트
function receivedPoint($chattingNo, point){
    // console.log(point);
    alert(`${point} 포인트가 지급되었습니다!`);
    const chatCard = document.getElementById($chattingNo);
    const $target = chatCard.querySelector('.matching-accept-btn');
    const handShakeImg = $target.querySelector('.chatting-handshake-img');
    handShakeImg.src = '/assets/img/chattingModal/handshake.png';
    handShakeImg.alt = '매칭수락이미지';
    // $target.dataset.matchingStatus = '';
    $target.disabled = true;
    $target.childNodes[1].nodeValue = '매칭 대기';
    // $target.dataset.matchingNo = '';
    sendNoticeMessage($chattingNo, '즐거운 게임이었어요');
}

//매칭확정 버튼 -> 날짜 선택 클릭시 이벤트
export function clickSelectDate($chattingNo, $matchingNo, matchingYear, matchingMonth, matchingDate){
    // console.log('날짜선택 클릭까지 넘어옴');
    const selectBtn = document.getElementById('select-date');
    selectBtn.onclick = e => {
        if (confirm(matchingMonth + '월 ' + matchingDate + '일자로 매칭을 확정하시겠습니까?')) {
            // const selectedDate = new Date(+matchingYear, +matchingMonth, +matchingDate);
            // console.log('확정됨');
            const selectedDate = matchingYear + "-" + matchingMonth + "-" + matchingDate;
            // console.log(selectedDate);
            matchingConfirm($chattingNo, $matchingNo, selectedDate);
        }
    }

}

//매칭 확정버튼 누르기
export function  matchingConfirm(chattingNo, matchingNo, selectedDate) {
    // console.log('매칭 확정 fetch까지 넘어옴');
    const matchingFixRequestDTO = {
        matchingNo : matchingNo,
        matchingDate : selectedDate
    }
    const requestInfo = {
        method: 'PUT',
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify(matchingFixRequestDTO)
    }

    fetch(`/api/v1/matchings/fix`, requestInfo)
        .then(res => res.json())
        .then(result => {

            if(result === true){
                document.querySelector('.match-calendar').click();
                changeMatchingToConfirm(chattingNo);
                sendNoticeMessage(chattingNo, `매칭이 확정되었습니다.<br>매칭일자 : ${selectedDate}`);
            }
        });
}

//매칭 신청
function matchingRequest(chattingNo){

    const requestInfo = {
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        body: chattingNo
    }

    fetch(`/api/v1/matchings`, requestInfo)
        .then(res => res.json())
        .then(result =>
            changeMatchingStatus(chattingNo, result))
        .then(result => sendNoticeMessage(result , '듀오 매칭을 요청합니다'));
}

//REQUEST -> CONFIRM으로 상태 변경
function changeMatchingToConfirm(chattingNo){
    const chatCard = document.getElementById(chattingNo);
    const $target = chatCard.querySelector('.matching-accept-btn');
    $target.dataset.matchingStatus = 'CONFIRM';
    $target.disabled = true;
    $target.childNodes[1].nodeValue = '매칭 확정';
    //matchingResponseEvent();

    const btnBox = chatCard.querySelector('.chatting-message-option');
    btnBox.children[1].remove();

}

// 게임완료 버튼 눌렀을때 이벤트
function gameDone($chattingNo, $matchingNo){
    // console.log($matchingNo);
    const requestInfo = {
        method: 'PUT',
        headers: {
            'content-type': 'application/json'
        },
        body: $matchingNo
    }

    fetch(`/api/v1/matchings/done`, requestInfo)
        .then(res => res.json())
        .then(result => {
            if(result === true){
                matchingDone($chattingNo);
                sendNoticeMessage($chattingNo, '즐거운 게임이었어요');
            }
        });
}

//'' -> REQUEST로 상태 변경
async function changeMatchingStatus(chattingNo, result) {
    const chatCard = document.getElementById(chattingNo);
    const $target = chatCard.querySelector('.matching-accept-btn');
    $target.dataset.matchingStatus = 'REQUEST';
    $target.setAttribute('disabled', 'disabled');
    $target.dataset.matchingNo = result;
    $target.childNodes[1].nodeValue = '수락대기중';
    return chattingNo;
}

// CONFIRM -> DONE으로 상태 변경
function matchingDone(chattingNo){
    // console.log(chattingNo);
    const chatCard = document.getElementById(chattingNo);
    // console.log(chatCard);
    const $target = chatCard.querySelector('.matching-accept-btn');
    $target.disabled = true;
    $target.dataset.matchingStatus = 'DONE';
    $target.childNodes[1].nodeValue = '정산중';

}

//이벤트 걸린 사람이 메세지에 상태 담아 보내기
function sendNoticeMessage(chattingNo, msg){
    // console.log(chattingNo);
    const chat = document.getElementById(chattingNo);
    // console.log(chat);
    // const name = chat.querySelector('div.message-to div.message-nickname').innerText;

    chat.querySelector('.msg').value = msg;
    chat.querySelector('.message-send-btn').click();
}

//최근 매칭내역 가져오기(이벤트 상대방 매칭넘버 렌더링용)
export function getRecentMatchingNo(chattingNo){

    return fetch(`/api/v1/matchings/${chattingNo}`)
        .then(res => res.json())
        .then(result => {
            setMatchingNo(chattingNo, result);
            return result;
        });
}

//버튼이벤트 상배방 메세지창에 메칭넘버 렌더링해주기
function setMatchingNo(chattingNo, matchingNo){
    const chatting = document.getElementById(chattingNo);

    chatting.querySelector('.matching-accept-btn').dataset.matchingNo = matchingNo;
    if(chatting.querySelector('.matching-reject-btn') !== null){
        chatting.querySelector('.matching-reject-btn').dataset.matchingNo = matchingNo;
    }
}
