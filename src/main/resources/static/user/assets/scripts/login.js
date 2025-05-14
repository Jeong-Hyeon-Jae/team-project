const $loginForm = document.getElementById('loginForm');
$loginForm.onsubmit=(e)=>{
    e.preventDefault();
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('email', $loginForm['email'].value);
    formData.append('password', $loginForm['password'].value);
    xhr.onreadystatechange=()=>{
        if(xhr.readyState !== XMLHttpRequest.DONE){
            return;
        }
        if (xhr.status < 200 || xhr.status >= 300){
            alert(`${xhr.status}`)
            return;
        }
        const response = JSON.parse(xhr.responseText);
        switch(response.result){
            case 'success':
                console.log('로그인 성공');
                location.href = "/";
                break;
            case 'failure':
                console.log('로그인 실패');
                break;
            default:
                break;
        }
    };
    xhr.open('POST','/user/login');
    xhr.send(formData);
}