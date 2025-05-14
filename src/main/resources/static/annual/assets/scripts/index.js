const $annualForm = document.getElementById('annualForm');
const $today = $annualForm.querySelector(':scope > .menu-bar > .today > .today');

const addBtn = $annualForm.querySelector(':scope > .menu-bar > .button-container > button');
const $modal = document.getElementById('modal');
const $closeBtn = document.getElementById('closeModal');
const $eventTitle = document.getElementById('eventTitle');
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
    const startDateString = $annualForm[name='start-date'].value.split('-');

    const endDateString = $annualForm[name='end-date'].value.split('-');
    const betweenDay = (new Date(startDateString[0],
        Number(startDateString[1])-1,
        startDateString[2]).getTime() - new Date(endDateString[0],
        Number(endDateString[1])-1, endDateString[2]).getTime())/1000/60/60/24;
    const annualRadio = $annualForm[name='annual'];
    const xhr = new XMLHttpRequest();
    const formData = new FormData();

    if ($annualForm[name='start-date'].value === '') {
        alert('시작 날짜를 입력해 주세요.');
        $annualForm[name='start-date'].select();
        $annualForm[name='start-date'].focus();
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
    if ($annualForm[name='end-date'].value === '') {
        alert('종료 날짜를 입력해 주세요.');
        $annualForm[name='end-date'].select();
        $annualForm[name='start-date'].focus();
        return;
    }
    if ($annualForm[name='content'].value === '') {
        alert('사유를 입력해 주세요.')
        $annualForm[name='content'].select();
        $annualForm[name='content'].focus();
        return;
    }

    formData.append('startDate', $annualForm[name='start-date'].value);
    formData.append('endDate', $annualForm[name='end-date'].value);
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
            alert(xhr.status);
            return;
        }
        const response = JSON.parse(xhr.responseText);
        switch (response) {
            case 'failure':
                alert(`알수 없는 오류로 에러가 발생했습니다. ${xhr.status}`);
                break;
            case 'success':
                alert(`입력된 연차가 제출되었습니다.`);
                break;
            default:
                location.href='/user/login';
                return;
        }
    };
    xhr.open('POST', '/request');
    xhr.send(formData);
});


