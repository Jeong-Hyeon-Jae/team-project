const $annualForm = document.getElementById('annualForm');
const $today = $annualForm.querySelector(':scope > .menu-bar > .today > .today');

const addBtn = $annualForm.querySelector(':scope > .menu-bar > .button-container > button');
const $modal = document.getElementById('modal');
const $closeBtn = document.getElementById('closeModal');
const $eventTitle = document.getElementById('eventTitle');
const $title = $annualForm.querySelector(':scope > .menu-bar > .title')
const date = new Date();

$annualForm.onsubmit = (e) => {
    e.preventDefault();
    const xhr = new XMLHttpRequest();

    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) {
            return;
        }
        if (xhr.status < 200 || xhr.status >= 300) {
            alert(xhr.status);
            return;
        }
        const response = JSON.parse(xhr.responseText);
        console.log(response);
        $title.innerHTML = ''
        $title.innerHTML = `
            반가워요! ${response.name}
        `
    };
    xhr.open('GET', '/request/info');
    xhr.send();
}

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
    if ($annualForm[name='start-date'].value === '') {
        alert('시작 날짜를 입력해 주세요.');
        $annualForm[name='start-date'].select();
        $annualForm[name='start-date'].focus();
    }
    if ($annualForm[name='start-date'] > $annualForm[name="end-date"]) {
        alert('날짜를 다시 입력해주세요');
        $annualForm.querySelector("[name='start-date']").focus();
        return;
    }
    if ($annualForm[name='end-date'].value === '') {
        alert('종료 날짜를 입력해 주세요.');
        $annualForm[name='end-date'].select();
        $annualForm[name='end-date'].focus();
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
        console.log(response);
        switch (response.result) {
            case 'failure':
                alert(`알수 없는 오류로 에러가 발생했습니다. ${xhr.status}`);
                return;
            case 'failure_duplicate_date':
                alert(`이미 접수되었습니다.`)
                return;
            case 'failure_duplicate_month':
                alert('한달에 한 번만 신청할 수 있습니다.')
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