function loginCheck(){
    var username = document.getElementById("username");
    var password = document.getElementById("password");

    if(username.value === ""){
        alert("Inserire username");
        username.value='';
        username.focus();
        return false;
    }

    if(password.value === ""){
        alert("Inserire password");
        password.value='';
        password.focus();
        return false;
    }

    return true;
}