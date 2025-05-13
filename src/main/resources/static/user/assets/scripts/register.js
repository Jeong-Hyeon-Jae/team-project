const $registerForm = document.getElementById('registerForm');

$registerForm.onsubmit= (e) => {
    e.preventDefault();
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('email', $registerForm['email'].value);
    formData.append('name', $registerForm['name'].value);
    formData.append('password', $registerForm['password'].value);
    formData.append('role', $registerForm['role'].value);
    formData.append('joinedAt', $registerForm['joinedAt'].value);
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
                alert('등록 완료');
                location.href = '/';
                break;
            case'failure':
                alert('실패');
                break;
            case 'failure_invalid_email':
                alert('이메일 오류');
                break;
            case 'failure_invalid_password':
                alert('password 오류');
                break;
            case 'failure_invalid_name':
                alert('name 오류');
                break;
            default:
                alert('요청 중 오류');
                break;
        }
    };
    xhr.open('POST', '/user/register');
    xhr.send(formData);
};