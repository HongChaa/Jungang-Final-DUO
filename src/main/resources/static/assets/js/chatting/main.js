const ChatForm = document.getElementById('chat-form');

document.addEventListener("DOMContentLoaded", function () {
    const socket = io("http://localhost:3000");

    //Message from server
    socket.on('message', message => {
        console.log(message);
        outputMessage(message);
    });

    // Message submit
    ChatForm.addEventListener('submit', (e) => {
       e.preventDefault();

       //Get message Text
       const msg = e.target.elements.msg.value;

       //Emit message to server
        socket.emit('chatMessage', msg);
    });

    //output message to DOM
    function outputMessage(message){
        const div = document.createElement('div');
        div.classList.add('chatting-message-card');
        div.innerHTML = `<img class="chatting-profile" src="/assets/img/chattingModal/woogi.jpg" alt="프로필이미지">
                    <div class="message-content-container">
                        <div class="message-nickname">워녕</div>
                        <div class="message-content-wrapper">
                            <div class="message-content">${message}</div>
                            <span class="send-time">22:00</span>
                        </div>
                    </div>`;
        document.querySelector('.chatting-message-body').appendChild(div);
    }
});