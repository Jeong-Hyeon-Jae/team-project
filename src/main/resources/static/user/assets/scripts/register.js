const $registerForm = document.getElementById('registerForm');
const $addressFindDialog = document.getElementById('addressFindDialog');
const nameRegex = new RegExp('^[a-zA-Z가-힣]{2,6}$');
const emailRegex = new RegExp('^(?=.{8,50}$)([\\da-z\\-_.]{4,})@([\\da-z][\\da-z\\-]*[\\da-z]\\.)?([\\da-z][\\da-z\\-]*[\\da-z])\\.([a-z]{2,15})(\\.[a-z]{2,3})?$');
const passwordRegex = new RegExp('^(?=.*[!@#$%^&*()\\-_=+\\[\\]{}\\\\|;:\'",.<>/?]).{8,50}$');
const contactRegex = new RegExp('^[\\d]{4}$');
//주소찾기.
$registerForm['addressFindButton'].addEventListener('click', () => {
    $addressFindDialog.show();
    const $modal = $addressFindDialog.querySelector(':scope>.modal');
    $addressFindDialog.onclick = () => {
        $addressFindDialog.hide();
    }
    new daum.Postcode({
        width: '100%',
        height: '100%',
        oncomplete: (data) => {
            $registerForm['addressPostal'].value = data.zonecode;
            $registerForm['addressPrimary'].value = data.address;
            $registerForm['addressSecondary'].focus();
            $addressFindDialog.hide();
        }

    }).embed($modal);
    $addressFindDialog.show();
})

//회원가입
$registerForm.onsubmit = (e) => {
    e.preventDefault();
    const $nameLabel = $registerForm.querySelector('.--object-label:has(input[name="name"])');
    const $emailLabel = $registerForm.querySelector('.--object-label:has( input[name="email"])');
    const $passwordLabel = $registerForm.querySelector('.--object-label:has( input[name="passwordCheck"])');
    const $createdAtLabel = $registerForm.querySelector('.--object-label:has(input[name="createdAt"])');
    const $contactLabel = $registerForm.querySelector('.--object-label:has(select[name="contactMvno"])')
    const $contactSecondLabel = $registerForm.querySelector('.--object-label:has(input[name="contactSecond"])')
    const $addressPostalLabel = $registerForm.querySelector('.--object-label:has( input[name="addressPostal"])');

    // 초기화
    const $label=[$nameLabel, $emailLabel,$contactSecondLabel, $passwordLabel, $createdAtLabel, $addressPostalLabel];
    $label.forEach(label => {label.setVisible(false)});

    // 유효성 검사
    if ($registerForm['name'].value === '') {
        $nameLabel.setVisible(true, '이름을 입력해주세요.');
        $registerForm['name'].focus();
        return;
    }
    if (!nameRegex.test($registerForm['name'].value)) {
        $nameLabel.setVisible(true, '올바른 형식으로 입력해주세요.');
        $registerForm['name'].focus();
        return;
    }

    if ($registerForm['email'].value === '') {
        $emailLabel.setVisible(true, '이메일을 입력해주세요.');
        $registerForm['email'].focus();
        return;
    }
    if (!emailRegex.test($registerForm['email'].value)) {
        $emailLabel.setVisible(true, '올바른 형식으로 입력해주세요.');
        $registerForm['email'].focus();
        return;
    }

    if ($registerForm['password'].value === '') {
        $passwordLabel.setVisible(true, '비밀번호를 입력해주세요.');
        $registerForm['password'].focus();
        return;
    }
    if (!passwordRegex.test($registerForm['password'].value)) {
        $passwordLabel.setVisible(true, '올바른 형식으로 입력해줏세요.');
        $registerForm['password'].focus();
        return;
    }
    if ($registerForm['password'].value !== $registerForm['passwordCheck'].value) {
        $passwordLabel.setVisible(true, '비밀번호를 다시 확인해주세요.');
        $registerForm['passwordCheck'].focus();
        return;
    }

    if ($registerForm['createdAt'].value === '') {
        $createdAtLabel.setVisible(true, '입사일을 입력해주세요.');
        $registerForm['createdAt'].focus();
        return;
    }
    // ⏱️ 미래 날짜 불가 검사 추가
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    const inputDate = new Date($registerForm['createdAt'].value);
    if (inputDate > today) {
        $createdAtLabel.setVisible(true, '입사일을 다시 확인해 주세요.');
        $registerForm['createdAt'].focus();
        return;
    }
    if ($registerForm['createdAt'].value>Date.now()) {
        $createdAtLabel.setVisible(true, '입사일을 다시 입력해주세요.');
        $registerForm['createdAt'].focus();
        return;
    }
    if ($registerForm['contactMvno'].value === '-1') {
        $contactLabel.setVisible(true, '통신사를 선택해주세요.');
        $registerForm['contactMvno'].focus();
        return;
    }
    if ($registerForm['contactSecond'].value === '') {
        $contactSecondLabel.setVisible(true, '전화번호를 입력해주세요.');
        $registerForm['contactSecond'].focus();
        return;
    }
    if (!contactRegex.test($registerForm['contactSecond'].value)) {
        $contactSecondLabel.setVisible(true, '올바른 형식으로 입력해주세요.');
        $registerForm['contactSecond'].focus();
        return;
    }
    if (!contactRegex.test($registerForm['contactThird'].value)) {
        $contactSecondLabel.setVisible(true, '올바른 형식으로 입력해주세요.');
        $registerForm['contactThird'].focus();
        return;
    }
    if ($registerForm['addressPostal'].value === '') {
        $addressPostalLabel.setVisible(true,'주소를 입력해주세요.');
        $registerForm['addressPostal'].focus();
        return;
    }

    // 전송
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
        if (xhr.readyState !== XMLHttpRequest.DONE) return;

        if (xhr.status < 200 || xhr.status >= 300) {
            dialog.showSimpleOk('회원가입', `${xhr.status} 오류`);
            return;
        }
        const response = JSON.parse(xhr.responseText);
        switch (response.result) {
            case 'success':
                dialog.showSimpleOk('회원가입', '회원가입에 성공하였습니다.',{
                    onOkCallback:()=> {
                        location.href = "/user/login"
                    }
                });

                break;
            case 'failure_invalid_name':
                dialog.showSimpleOk('회원가입', '이름이 올바르지 않습니다. 다시 입력해 주세요.');
                $registerForm['name'].focus();
                $registerForm['name'].select();
                break;
            case 'failure_invalid_email':
                dialog.showSimpleOk('회원가입', '이메일이 올바르지 않습니다. 다시 입력해 주세요.');
                $registerForm['email'].focus();
                $registerForm['email'].select();
                break;
            case 'failure_invalid_password':
                dialog.showSimpleOk('회원가입', '비밀번호가 올바르지 않습니다. 다시 입력해 주세요.');
                $registerForm['password'].focus();
                $registerForm['password'].select();
                break;
            case 'failure_duplicate':
                dialog.showSimpleOk('회원가입', '이메일이 중복됩니다. 다시 입력해 주세요.');
                $registerForm['email'].focus();
                $registerForm['email'].select();
                break;
            case 'failure':
                dialog.showSimpleOk('회원가입', '회원가입에 실패하였습니다. 잠시 후 다시 시도해 주세요.');
                break;
            default:
                dialog.showSimpleOk('회원가입', '알 수 없는 오류가 발생했습니다. 다시 시도해 주세요.');
                break;
        }
    };

    xhr.open('POST', '/user/register');
    xhr.send(formData);
};
