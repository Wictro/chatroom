function setupButtonFunctions(){
    //when settings button is clicked, the settings appear
    let settingsButton = document.querySelector(".chat-header-settings");
    let settingsContainer = document.querySelector(".chat-settings-container");

    settingsButton.addEventListener('click', () => {
        settingsContainer.style.display = "flex";
    });

    //when the back button is clicked, the settings disappear
    let settingsBackButton = document.querySelector(".chat-settings-header span");

    settingsBackButton.addEventListener('click', () => {
        settingsContainer.style.display = 'none';
    });
}

export default setupButtonFunctions;