const $changePasswordForm = document.getElementById('changePasswordForm');
const $confirmButton = $changePasswordForm.querySelector(':scope > button[name="confirm"]');
const $changeInput = $changePasswordForm.querySelector('.--object-label:has(input[name="changeInput"])');
const $changeButton = $changePasswordForm.querySelector(':scope>button[name="changeButton"]');
$changeInput.querySelector(':scope>.---row> input').setValid(false);
$changeButton.hide();

$confirmButton.addEventListener('click', () => {
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    const $changeNameLabel = $changePasswordForm.querySelector('.--object-label:has(input[name="name"])');
    const $changeEmailLabel = $changePasswordForm.querySelector('.--object-label:has(input[name="email"])');
    const $changeContactMvnoLabel = $changePasswordForm.querySelector('.--object-label:has(select[name="contactMvno"])')
    const $changeContactSecondLabel = $changePasswordForm.querySelector('.--object-label:has(input[name="contactSecond"])')
    const $changeContactThirdLabel = $changePasswordForm.querySelector('.--object-label:has(input[name="contactThird"])')
    const $labels = [$changeEmailLabel, $changeNameLabel, $changeContactMvnoLabel, $changeContactSecondLabel, $changeContactThirdLabel];
    $labels.forEach(label => {
        label.setVisible(false)
    })

    if ($changePasswordForm['name'].value === '') {
        $changeNameLabel.setVisible(true, '이름을 입력해주세요');
        $changePasswordForm['name'].focus();
        return;
    }
    if ($changePasswordForm['email'].value === '') {
        $changeEmailLabel.setVisible(true, '이메일을 입력해주세요');
        $changePasswordForm['email'].focus();
        return;
    }
    if ($changePasswordForm['contactMvno'].value === '') {
        $changeContactMvnoLabel.setVisible(true, '통신사를 선택해주세요');
        $changePasswordForm['contactMvno'].focus();
        return;
    }
    if ($changePasswordForm['contactSecond'].value === '') {
        $changeContactSecondLabel.setVisible(true, '전화번호를 입력해주세요');
        $changePasswordForm['contactSecond'].focus();
        return;
    }
    if ($changePasswordForm['contactThird'].value === '') {
        $changeContactThirdLabel.setVisible(true, '전화번호를 입력해주세요');
        $changePasswordForm['contactThird'].focus();
        return;
    }
    formData.append('name', $changePasswordForm['name'].value);
    formData.append('email', $changePasswordForm['email'].value);
    formData.append('contactMvno', $changePasswordForm['contactMvno'].value);
    formData.append('contactFirst', $changePasswordForm['contactFirst'].value);
    formData.append('contactSecond', $changePasswordForm['contactSecond'].value);
    formData.append('contactThird', $changePasswordForm['contactThird'].value);
    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) {
            return;
        }
        if (xhr.status < 200 || xhr.status >= 300) {
            dialog.showSimpleOk('비밀번호 변경', `${xhr.status}`);
            return;
        }
        const response = JSON.parse(xhr.responseText);
        switch (response.result) {
            case 'success':
                dialog.showSimpleOk('인증', '인증에 성공하셨습니다.');
                $changeInput.querySelector(':scope>.---row> input').setValid(true);
                $changeButton.show();
                break;
            case'failure':
                dialog.showSimpleOk('인증', '입력하신 정보가 없습니다. 다시 한번 확인해 주세요.');
                break;
            default:
                dialog.showSimpleOk('인증', '알수없는 오류');
                break;
        }
    };
    xhr.open('POST', '/user/findInfo/changePassword');
    xhr.send(formData);
})
$changePasswordForm.onsubmit = (e) => {
    e.preventDefault();
    const $changePasswordInputLabel = $changePasswordForm.querySelector('.--object-label:has(input[name="changeInput"])');
    if ($changePasswordForm['changeInput'].value === '') {
        $changePasswordInputLabel.setVisible(true, '바꾸실 비밀번호를 입력해주세요.');
        return;
    }
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('name', $changePasswordForm['name'].value);
    formData.append('email', $changePasswordForm['email'].value);
    formData.append('contactMvno', $changePasswordForm['contactMvno'].value);
    formData.append('contactFirst', $changePasswordForm['contactFirst'].value);
    formData.append('contactSecond', $changePasswordForm['contactSecond'].value);
    formData.append('contactThird', $changePasswordForm['contactThird'].value);
    formData.append('password',$changePasswordForm['changeInput'].value)
    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) {
            return;
        }
        if (xhr.status < 200 || xhr.status >= 300) {
            dialog.showSimpleOk('비밀번호', `${xhr.status}`);
            return;
        }
        const response = JSON.parse(xhr.responseText);
        switch (response.result) {
            case 'success':
                dialog.showSimpleOk('비밀번호', '비밀번호를 변경하였습니다.', {
                    onOkCallback: () => {
                        location.href = "/user/login";
                    }
                });
                break;
            case'failure_duplicate':
                dialog.showSimpleOk('비밀번호', '입력하신 비밀번호가 기존과 동일합니다. 다른 비밀번호를 입력해주세요.');
                break;
            case'failure':
                dialog.showSimpleOk('비밀번호', '입력하신 비밀번호가 올바르지 않습니다. 다시 입력해 주세요.');
                break;
            default:
                dialog.showSimpleOk('비밀번호', '알수없는 오류');
                break;
        }
    };
    xhr.open('PATCH', '/user/findInfo/changePassword');
    xhr.send(formData);

}