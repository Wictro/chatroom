import setupButtonFunctions from "./chat-button-functions";
import {setupChatInput, sendText, inputArea, updateChat, scrollToBottom} from "./chat-input";
import 'whatwg-fetch'

window.chatroomId = location.pathname.split("/")[2];

updateChat();

let updateInterval = setInterval(() => {
    updateChat();
}, 1000);

setupButtonFunctions();
setupChatInput();
scrollToBottom();

inputArea.addEventListener('keypress', (event) => {
    if(event.key === 'Enter'){
        event.preventDefault();
        sendText();
        scrollToBottom();
    }
});

function getUpdatedChatroomDetails(){
    let chatroomName = document.querySelector("#newChatroomName").value;
    let chatroomSecret = document.querySelector("#newChatroomSecret").value;

    let obj = {
        displayName: chatroomName,
        password: chatroomSecret
    }

    return JSON.stringify(obj);
}

let updateChatroomButton = document.querySelector("#updateChatroomButton");

updateChatroomButton?.addEventListener("click", () => {
    fetch(`http://10.45.0.160:8085/chatroom/${window.chatroomId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: getUpdatedChatroomDetails()
    }).then(response => {
        location.reload();
    })
})

let leaveChatroomButton = document.querySelector("#leaveChatroomButton");

leaveChatroomButton?.addEventListener("click", () => {
    if(window.confirm("Are you sure you want to leave this chat?")){
        clearInterval(updateInterval);
        fetch(`http://10.45.0.160:8085/chatroom/${window.chatroomId}/leave`, {
            method: 'POST'
        }).then(response => {
            location.href = "http://10.45.0.160:8085/dashboard";
        });
    }
})