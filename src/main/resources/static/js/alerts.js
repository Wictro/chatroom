window.customAlert = function(message, positive = true, duration = 2000, delay = 100){
    let element = document.createElement("div");

    element.classList.add("alert");

    element.textContent = message;

    if(positive)
        element.classList.add("success");
    else
        element.classList.add("danger");

    document.body.appendChild(element);

    element.style.display = "block";

    setTimeout(() => {
        element.style.opacity = "1";

        setTimeout(() => {
            element.style.opacity = "0";

            setTimeout(() => {

                element.style.display = "none";

            }, parseInt(delay));

        }, parseInt(duration));

    }, parseInt(delay));
}