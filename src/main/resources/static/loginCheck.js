function loginCheck(){
    var username = document.getElementById("username");
    var password = document.getElementById("password");

    if(username.length === 0){
        alert("Inserire username");
        username.value='';
        username.focus();
        return false;
    }

    if(password.length === 0){
        alert("Inserire password");
        password.value='';
        password.focus();
        return false;
    }

    return true;
}