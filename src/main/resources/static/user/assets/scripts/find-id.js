const $findIdForm = document.getElementById('findIdForm');

const nameRegex = new RegExp('^[a-zA-Z가-힣]{2,6}$');
const contactRegex = new RegExp('^\\d{4}$');
$findIdForm.onsubmit=(e)=>{
    e.preventDefault();
    const $findIdLabel = $findIdForm.querySelector('.--object-label:has(input[name="name"])');
    const $findContactMvnoLabel = $findIdForm.querySelector('.--object-label:has(select[name="contactMvno"])');
    const $findContactSecondLabel = $findIdForm.querySelector('.--object-label:has(input[name="contactSecond"])');

    const $findContactThirdLabel = $findIdForm.querySelector('.--object-label:has(input[name="contactThird"])');

    const $labels = [$findIdLabel, $findContactSecondLabel, $findContactThirdLabel, $findContactMvnoLabel];

    $labels.forEach(($label) => $label.setVisible(false));
    if ($findIdForm['name'].value === '') {
        $findIdLabel.setVisible(true, '이름을 입력해주세요.');
        $findIdForm['name'].focus();
        return;
    }
    if (!nameRegex.test($findIdForm['name'].value)) {
        $findIdLabel.setVisible(true, '올바른 형식으로 입력해주세요.');
        $findIdForm['name'].focus();
        return;
    }
    if ($findIdForm['contactMvno'].value === '-1') {
        $findContactMvnoLabel.setVisible(true, '통신사를 선택해주세요.');
        return;
    }
    if ($findIdForm['contactSecond'].value === '' ) {
        $findContactSecondLabel.setVisible(true, '전화번호를 입력해주세요.');
        $findIdForm['contactSecond'].focus();
        return;
    }
    if (!contactRegex.test($findIdForm['contactSecond'].value)) {
        $findContactSecondLabel.setVisible(true, '올바른 형식으로 입력해주세요.');
        $findIdForm['contactSecond'].focus();
        return;
    }
    if ($findIdForm['contactThird'].value === '') {
        $findContactSecondLabel.setVisible(true, '전화번호를 입력해주세요.');
        $findIdForm['contactThird'].focus();
        return;
    }
    if (!contactRegex.test($findIdForm['contactThird'].value)) {
        $findContactThirdLabel.setVisible(true, '올바른 형식으로 입력해주세요.');
        $findIdForm['contactThird'].focus();
        return;
    }

    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('name', $findIdForm['name'].value);
    formData.append('contactMvno', $findIdForm['contactMvno'].value);
    formData.append('contactFirst', $findIdForm['contactFirst'].value);
    formData.append('contactSecond', $findIdForm['contactSecond'].value);
    formData.append('contactThird', $findIdForm['contactThird'].value);
    xhr.onreadystatechange=()=>{
        if(xhr.readyState !== XMLHttpRequest.DONE){
            return;
        }
        if (xhr.status < 200 || xhr.status >= 300){
            dialog.showSimpleOk('아이디 찾기', `${xhr.status}`);
            return;
        }
        const response = JSON.parse(xhr.responseText)
        switch (response.result) {
            case 'success':
                dialog.showSimpleOk('아이디 찾기', `${response.findEmail}`,{
                    onOkCallback:()=>{
                        location.href = "/user/login";}
                });
                break;
            case 'failure':
                dialog.showSimpleOk('아이디 찾기', '찾기 실패');
                break;
            default:
                dialog.showSimpleOk('아이디 찾기', '예기치 않은 오류가 발생했습니다.');
                break;
        }
    };
    xhr.open('POST','/user/find/find-id');
    xhr.send(formData);
}