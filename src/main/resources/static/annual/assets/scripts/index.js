const $annualForm = document.getElementById('annualForm');
const $today = $annualForm.querySelector(':scope > .menu-bar > .today > .today');
const addBtn = $annualForm.querySelector(':scope > .menu-bar > .button-container > button');
const $modal = document.getElementById('modal');
const $closeBtn = document.getElementById('closeModal');
const date = new Date();

$today.textContent = "";
$today.textContent = new Date().toLocaleDateString('ko-KR');

addBtn.addEventListener('click', () => {
    $modal.classList.add('visible');
});

$closeBtn.addEventListener('click', () => {
    $modal.classList.remove('visible');
});

document.getElementById('saveEvent').addEventListener('click', (e) => {
    e.preventDefault();
    const startDate = $annualForm[name='start-date'].value;
    const endDate = $annualForm[name='end-date'].value;
    const startDateString = $annualForm[name='start-date'].value.split('-');
    const endDateString = $annualForm[name='end-date'].value.split('-');
    const betweenDay = (new Date(startDateString[0],
        Number(startDateString[1])-1,
        startDateString[2]).getTime() - new Date(endDateString[0],
        Number(endDateString[1])-1, endDateString[2]).getTime())/1000/60/60/24;
    const annualRadio = $annualForm[name='annual'];

    const xhr = new XMLHttpRequest();
    const formData = new FormData();

    if (startDate === '') {
        alert('시작 날짜를 입력해 주세요.');
        startDate.select();
        startDate.focus();
        return;
    }
    const today = new Date();
    today.setHours(0, 0, 0, 0);

    const selectedDate = new Date($annualForm[name='start-date'].value);
    selectedDate.setHours(0, 0, 0, 0);

    if (selectedDate < today) {
        alert('시작 날짜는 오늘보다 이전일 수 없습니다.');
        $annualForm.querySelector("[name='start-date']").focus();
        return;
    }
    if (startDate === '') {
        alert('시작 날짜를 입력해 주세요.');
        startDate.select();
        startDate.focus();
    }
    if (startDate > endDate) {
        alert('날짜를 다시 입력해주세요');
        $annualForm.querySelector("[name='start-date']").focus();
        return;
    }
    if (endDate === '') {
        alert('종료 날짜를 입력해 주세요.');
        endDate.select();
        endDate.focus();
        return;
    }
    if ($annualForm[name='content'].value === '') {
        alert('사유를 입력해 주세요.')
        $annualForm[name='content'].select();
        $annualForm[name='content'].focus();
        return;
    }

    formData.append('startDate', startDate);
    formData.append('endDate', endDate);
    formData.append('days', Math.abs(betweenDay).toString());
    formData.append('content', $annualForm[name='content'].value);
    formData.append('status', 'PENDING');
    annualRadio.forEach((node) => {
        if (node.checked) {
            formData.append('category', annualRadio.value.toUpperCase());
        }
    });

    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) {
            return;
        }
        if (xhr.status < 200 || xhr.status >= 300) {
            return;
        }
        const response = JSON.parse(xhr.responseText);
        console.log(response);
        switch (response.result) {
            case 'failure':
                alert(`알수 없는 오류로 에러가 발생했습니다. ${xhr.status}`);
                return;
            case 'failure_duplicate_date':
                alert(`이미 접수되었습니다.`);
                return;
            case 'failure_duplicate_month':
                alert('한달에 한 번만 신청할 수 있습니다.');
                return;
            case 'failure_weekend_not_allowed':
                alert('시작일 혹은 종료일이 주말인 경우 신청이 불가능합니다');
                return;
            case 'success':
                alert(`연차접수가 완료되었습니다.`);
                location.href = "/";
        }
    };
    xhr.open('POST', '/request');
    xhr.send(formData);
});

const radios = document.querySelectorAll('input[name="option"]');

radios.forEach(radio => {
    radio.addEventListener('change', () => {
    const checkedButton = document.querySelector('input[type="radio"]:checked');
        if (checkedButton) {
            if (checkedButton.value === 'my') {
                showCalendar(checkedButton.value)
            } else if (checkedButton.value === 'all') {
                showCalendar(checkedButton.value)
            }
        }
    });
});


//개인정보 수정





