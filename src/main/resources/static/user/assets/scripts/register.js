const $registerForm = document.getElementById('registerForm');

$registerForm.onsubmit = (e) => {
    e.preventDefault();

    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('email', $registerForm['email'].value);
    formData.append('name', $registerForm['name'].value);
    formData.append('password', $registerForm['password'].value);
    formData.append('createdAt', $registerForm['createdAt'].value);
    formData.append('contactMvno', $registerForm['contactMvno'].value);
    formData.append('contactFirst', $registerForm['contactFirst'].value);
    formData.append('contactSecond', $registerForm['contactSecond'].value);
    formData.append('contactThird', $registerForm['contactThird'].value);
    formData.append('addressPostal', $registerForm['addressPostal'].value);
    formData.append('addressPrimary', $registerForm['addressPrimary'].value);
    formData.append('addressSecondary', $registerForm['addressSecondary'].value);
    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) {
            return;
        }
        if (xhr.status < 200 || xhr.status >= 300) {
            alert(xhr.status);
            return;
        }
        const response = JSON.parse(xhr.responseText);
        switch (response.result) {
            case 'success':
                location.href = "/user/login";
                break;
            case'failure':
                dialog.showSimpleOk('회원가입', '회원가입에 실패하였습니다. 잠시 후 다시 시도해 주세요');
                break;
            default:
                alert('요청 중 오류');
                break;
        }
    };
    xhr.open('POST', '/user/register');
    xhr.send(formData);
};