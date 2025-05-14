const $annualForm = document.getElementById('annualForm');
const $today = $annualForm.querySelector(':scope > .menu-bar > .today > .today');
const date = new Date();


const addBtn = $annualForm.querySelector(':scope > .menu-bar > .button-container > button');
const $modal = document.getElementById('modal');
const $closeBtn = document.getElementById('closeModal');
const $eventTitle = document.getElementById('eventTitle')

$today.textContent = "";
$today.textContent = new Date().toLocaleDateString('ko-KR');
addBtn.addEventListener('click', () => {
    $modal.classList.add('visible');
});

$closeBtn.addEventListener('click', () => {
    $modal.classList.remove('visible');
});

// 저장 버튼 (예시로 제목만 콘솔에 찍기)
document.getElementById('saveEvent').addEventListener('click', () => {
    const startDateString = $annualForm[name='start-date'].value.split('-');

    const endDateString = $annualForm[name='end-date'].value.split('-');
    const betweenDay = (new Date(startDateString[0], Number(startDateString[1])-1, startDateString[2]).getTime() - new Date(endDateString[0], Number(endDateString[1])-1, endDateString[2]).getTime())/1000/60/60/24;
    const annualRadio = $annualForm[name='annual'];
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('startDate', $annualForm[name='start-date'].value);
    formData.append('endDate', $annualForm[name='end-date'].value);
    formData.append('days', Math.abs(betweenDay).toString());
    formData.append('content', $annualForm[name='content'].value);
    annualRadio.forEach((node) => {
        if (node.checked) {
            formData.append('category', annualRadio.value);
        }
    });

    formData.append('category', $annualForm[name='half']);

    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) {
            return;
        }
        if (xhr.status < 200 || xhr.status >= 300) {
            alert(xhr.status);
            return;
        }
        const response = JSON.parse(xhr.responseText);
        if (title) {
            console.log('저장할 일정 제목:', title);
            $modal.classList.remove('visible');
        } else {
            alert('일정 제목을 입력하세요.');
        }
    };
    xhr.open('POST', '/request');
    xhr.send(formData);


});
