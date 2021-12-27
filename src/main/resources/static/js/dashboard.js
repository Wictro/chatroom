let newChatroomButton = document.querySelector(".new-chatroom span");
let popupBackground = document.querySelector(".popup-background-container");
let createChatroomTab = popupBackground.querySelector("#popup-create");
let joinChatroomTab = popupBackground.querySelector("#popup-join");

let buttons = popupBackground.querySelectorAll(".navigation-container a");
let createChatroomButton = buttons[0];
let joinChatroomButton = buttons[1];

popupBackground.addEventListener("click", (e) => {
    if (e.target !== popupBackground)
        return;
    popupBackground.style.display = "none";
});

newChatroomButton.addEventListener("click", () => {
    popupBackground.style.display = "flex";
});

createChatroomButton.addEventListener("click", () => {
    joinChatroomTab.style.display = "none";
    joinChatroomButton.classList.remove('active');

    createChatroomTab.style.display = "block";
    createChatroomButton.classList.add('active');
});

joinChatroomButton.addEventListener("click", () => {
    createChatroomTab.style.display = "none";
    createChatroomButton.classList.remove('active');

    joinChatroomTab.style.display = "block";
    joinChatroomButton.classList.add('active');
});

var openChatroom = (chatroomCode) => {
    location.href = "chatroom/" + chatroomCode;
}