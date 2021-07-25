function redirectHandle(xhr) {
    let url = xhr.getResponseHeader("redirectUrl");
    console.log("redirectUrl = " + url);

    let enable = xhr.getResponseHeader("enableRedirect");

    if ((enable === "true") && (url !== "")) {
        let win = window;
        while (win !== win.top) {
            win = win.top;
        }
        win.location.href = url;
    }
}

$(function () {
    $(document).ajaxComplete(function (event, xhr, settings) {
        redirectHandle(xhr);
    })
})