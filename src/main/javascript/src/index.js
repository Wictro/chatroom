import setupButtonFunctions from "./chat-button-functions";
import {setupChatInput, sendText, inputArea, updateChat, scrollToBottom, baseUrl} from "./chat-input";

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
    fetch(`${baseUrl}/chatroom/${window.chatroomId}`, {
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
    if(window.confirm("Are you sure you want to leave this chatroom?")){
        clearInterval(updateInterval);
        fetch(`${baseUrl}/chatroom/${window.chatroomId}/leave`, {
            method: 'POST'
        }).then(response => {
            location.href = `${baseUrl}/dashboard`;
        });
    }
});

let deleteChatroomButton = document.querySelector("#deleteChatroomButton");

deleteChatroomButton?.addEventListener("click", () => {
    if(window.confirm("Are you sure you want to delete this chatroom?")){
        clearInterval(updateInterval);
        fetch(`${baseUrl}/chatroom/${window.chatroomId}`, {
            method: 'DELETE'
        }).then(response => {
            location.href = `${baseUrl}/dashboard`;
        });
    }
});