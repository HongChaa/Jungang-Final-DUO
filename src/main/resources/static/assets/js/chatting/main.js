import {outputMessage, readMessages, saveMessage, scrollDown} from "./messageRendering.js";
import {getChattingList, renderTotalUnreadMessages, renderUnreadMessages} from "./chatting-modal.js";

export function connectSocket() {
    const socket = io("http://localhost:3000");

    socket.on('message', message => {

        const chat_list = document.querySelector('.chatting-modal-dialog');

        if(!document.getElementById(message.room)
            && chat_list.hasAttribute('open')
        ){
            getChattingList();
        }

        //output message to DOM
        outputMessage(message);

        if(chat_list.hasAttribute('open')){

            const chatroom = document.getElementById(message.room);
            if(!chatroom.querySelector('.message-dialog').hasAttribute('open')){
                renderUnreadMessages(message.room);
            }else {
                readMessages(message.room);
            }
        } else {
            renderTotalUnreadMessages();
        }


        scrollDown();
    });

    const $chatCard = document.querySelector('.chatting-modal-container');
    $chatCard.addEventListener('submit', e => {
        e.preventDefault();

        //Get message text and room
        const username = document.getElementById('loginUserInfo').dataset.userNickname;
        const msg = e.target.querySelector('.msg').value;
        const room = e.target.closest('.chatting-card').id;
        const myProfile = document.querySelector('.myProfileImage').src;
        const chatBox = document.getElementById(room);
        const matchingStatus = chatBox.querySelector('.matching-accept-btn').dataset.matchingStatus;
        // const matchingNo = chatBox.querySelector('.matching-accept-btn').dataset.matchingNo;

        // if(msg.length > 100){
        //     alert('메세지는 100자 이내로 작성해주세요');
        //     return;
        // }

        //Emit message to server
        socket.emit('chatMessage', {username, room, myProfile, msg, matchingStatus});

        //save message
        saveMessage({username, room, msg, matchingStatus});

        //clear message
        e.target.querySelector('.msg').value = '';
    });

}