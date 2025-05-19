const $loginForm = document.getElementById('loginForm');
const emailRegex = new RegExp('^(?=.{8,50}$)([\\da-z\\-_.]{4,})@([\\da-z][\\da-z\\-]*[\\da-z]\\.)?([\\da-z][\\da-z\\-]*[\\da-z])\\.([a-z]{2,15})(\\.[a-z]{2,3})?$');
const passwordRegex = new RegExp('^([\\da-zA-Z`~!@#$%^&*()\\-_=+\\[{\\]}\\\\|;:\'",<.>/?]{8,50})$');

$loginForm.onsubmit = (e) => {
    e.preventDefault();
    const $loginLabel = $loginForm.querySelector(':scope>.--object-label:has(input[name="email"])');
    const $passwordLabel = $loginForm.querySelector(':scope>.--object-label:has(input[name="password"])');
    [$loginLabel, $passwordLabel].forEach($label => $label.setVisible(false));
    if ($loginForm['email'].value === '') {
        $loginLabel.setVisible(true, '이메일을 입력해주세요.');
        $loginForm['email'].focus();
        return;
    }
    if (!emailRegex.test($loginForm['email'].value)) {
        $loginLabel.setVisible(true, '올바른 형식으로 입력해주세요.');
        $loginForm['email'].focus();
        return;
    }
    if ($loginForm['password'].value === '') {
        $passwordLabel.setVisible(true, '비밀번호를 입력해주세요.');
        $loginForm['password'].focus();
        return;
    }
    if (!passwordRegex.test($loginForm['password'].value)) {
        $passwordLabel.setVisible(true, '올바른 형식으로 입력해주세요.');
        $loginForm['password'].focus();
        return;
    }
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('email', $loginForm['email'].value);
    formData.append('password', $loginForm['password'].value);
    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) {
            return;
        }
        if (xhr.status < 200 || xhr.status >= 300) {
            dialog.showSimpleOk('로그인', `${xhr.status}`);
            return;
        }
        const response = JSON.parse(xhr.responseText);
        switch (response.result) {
            case 'success':
                dialog.showSimpleOk('로그인', '로그인에 성공하였습니다.', {
                    onOkCallback: () => {
                        if (response.admin === true) {
                            location.href = "/admin";
                        } else {
                            location.href = "/";
                        }
                    }
                })
                break;
            case 'failure':
                dialog.showSimpleOk('로그인', `로그인에 실패하였습니다.
                아이디나 비밀번호를 체크하여 주세요.`)
                $loginForm['email'].focus();
                $loginForm['email'].select();
                break;
            default:
                dialog.showSimpleOk('로그인', '알수 없는 이유로 로그인에 실패하였습니다.')
                break;
        }
    };
    xhr.open('POST', '/user/login');
    xhr.send(formData);
}