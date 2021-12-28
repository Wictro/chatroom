let inputArea = document.querySelector(".chat-input");

let baseUrl = 'http://localhost:8085';

let chatIdCounter = 0;

function setupChatInput(){
    inputArea = document.querySelector(".chat-input");
}

function updateChat(){
    fetch(`${baseUrl}/chatroom/${window.chatroomId}/chat?index=${chatIdCounter}`)
        .then(response => response.json())
        .then(data => {
            //clearChat();
            for(let i = 0; i < data.length; i++){
                let chat = data[i];
                appendMessage(chat.text, chat.sender.firstName + ' ' + chat.sender.lastName, chat.sentTime);
                scrollToBottom();
                chatIdCounter = chat.id;
            }
        })

    // var data = "";
    //
    // var xhr = new XMLHttpRequest();
    // xhr.withCredentials = true;
    //
    // xhr.addEventListener("readystatechange", function() {
    //     if(this.readyState === 4) {
    //         console.log(this.responseText);
    //         clearChat();
    //         let data = JSON.parse(this.responseText);
    //         for(let i = 0; i < data.length; i++){
    //             let chat = data[i];
    //             appendMessage(chat.text, chat.sender.firstName + ' ' + chat.sender.lastName);
    //             scrollToBottom();
    //         }
    //     }
    // });
    //
    // xhr.open("GET", "http://localhost:8085/chatroom/qdLmoDuM/chat");
    // xhr.setRequestHeader("Content-Type", "application/json");
    //
    // xhr.send(data);
}

function scrollToBottom(){
    let chat = document.querySelector(".chat-body");
    chat.scrollTop = chat.scrollHeight;
}

function clearChat(){
    let chatBody = document.querySelector(".chat-body");

    chatBody.innerHTML = '';
}

function sendText(){
    let message = inputArea.querySelector('textarea').value;

    if(message == null)
        return;

    if(message.trim() === ""){
        inputArea.querySelector('textarea').value = "";
        return;
    }

    sendMessage(message);

    inputArea.querySelector('textarea').value = "";
}

function sendMessage(message){
    fetch(`${baseUrl}/chatroom/${window.chatroomId}/chat`, {
        method: 'POST',
        body: JSON.stringify({
            text: message
        }),
        headers: {
            'Content-Type': 'application/json'
        }
    })
}

function appendMessage(message, sender, time){
    let chatBody = document.querySelector(".chat-body");

    chatBody.innerHTML += messageTemplate(message, sender, time);
}

function messageTemplate(message, sender, time){
    return `<div class="chat-element">
    <div class="chat-element-head">
        <div class="chat-element-head-name">
            ${sender}:
        </div>

        <div class="chat-element-head-time">
            ${getFormattedTime(time)}
        </div>
    </div>

    <div class="chat-element-body">
        ${message}
    </div>
</div>`
}

function getFormattedTime(time){
    let timeOnly = time.split(" ")[4].split(":"); //Mon, 27 Dec 2021 21:11:05 UTC

    let offsetMinutes = new Date().getTimezoneOffset();

    let hours = (timeOnly[0] - (offsetMinutes/60)) % 24;
    let minutes = timeOnly[1];

    return (hours >= 10? hours : "0" + hours) + ":" + minutes;
}

export {setupChatInput, sendText, inputArea, updateChat, scrollToBottom, baseUrl};
