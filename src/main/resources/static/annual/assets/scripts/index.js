const $annualForm = document.getElementById('annualForm');
const $today = $annualForm.querySelector(':scope > .menu-bar > .today > .today');

$today.textContent = "";
$today.textContent = new Date().toLocaleDateString('ko-KR');

const addBtn = $annualForm.querySelector(':scope > .menu-bar > .button-container');

const openBtn = document.querySelector('.-button-red');
const modal = document.getElementById('modal');
const closeBtn = document.getElementById('closeModal');

openBtn.addEventListener('click', () => {
    modal.classList.add('visible');
});

closeBtn.addEventListener('click', () => {
    modal.classList.remove('visible');
});

// 저장 버튼 (예시로 제목만 콘솔에 찍기)
document.getElementById('saveEvent').addEventListener('click', () => {
    const title = document.getElementById('eventTitle').value;
    if (title) {
        console.log('저장할 일정 제목:', title);
        modal.classList.remove('visible');
    } else {
        alert('일정 제목을 입력하세요.');
    }
});
