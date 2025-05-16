const $findIdForm = document.getElementById('findIdForm');

$findIdForm.onsubmit=(e)=>{
    e.preventDefault();
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
                break;
        }
    };
    xhr.open('POST','/user/findInfo/findId');
    xhr.send(formData);
}