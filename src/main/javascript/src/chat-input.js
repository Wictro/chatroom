let inputArea = document.querySelector(".chat-input");

function setupChatInput(){
    inputArea = document.querySelector(".chat-input");
}

function updateChat(){
    fetch(`http://10.45.0.160:8085/chatroom/${window.chatroomId}/chat`)
        .then(response => response.json())
        .then(data => {
            clearChat();
            for(let i = 0; i < data.length; i++){
                let chat = data[i];
                appendMessage(chat.text, chat.sender.firstName + ' ' + chat.sender.lastName);
                scrollToBottom();
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
    //appendMessage(message);

    inputArea.querySelector('textarea').value = "";
}

function sendMessage(message){
    fetch(`http://10.45.0.160:8085/chatroom/${window.chatroomId}/chat`, {
        method: 'POST',
        body: JSON.stringify({
            text: message
        }),
        headers: {
            'Content-Type': 'application/json'
        }
    })
}

function appendMessage(message, sender){
    let chatBody = document.querySelector(".chat-body");

    chatBody.innerHTML += messageTemplate(message, sender);
}

function messageTemplate(message, sender){
    return `<div class="chat-element">
    <div class="chat-element-head">
        <div class="chat-element-head-name">
            ${sender}:
        </div>

        <div class="chat-element-head-time">
            ${getFormattedTime()}
        </div>
    </div>

    <div class="chat-element-body">
        ${message}
    </div>
</div>`
}

function getFormattedTime(){
    let date = new Date();

    let hour = date.getHours();
    let minutes = date.getMinutes() < 10? "0" + date.getMinutes() : date.getMinutes();

    return hour + ":" + minutes;
}

export {setupChatInput, sendText, inputArea, updateChat, scrollToBottom};
