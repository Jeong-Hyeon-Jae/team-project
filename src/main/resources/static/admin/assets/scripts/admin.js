const $listTable = document.getElementById('listTable');
const $select = document.getElementById('sortSelect');

$select.addEventListener('change' ,() => {

})

document.getElementById('sortSelect').addEventListener('change', () => {
    loadLists();
});

function loadLists() {
    const sort = document.getElementById('sortSelect').value; // 내부에서 정렬 값 읽기

    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) return;
        if (xhr.status < 200 || xhr.status >= 300) {
            alert(`[${xhr.status}] 데이터 요청 실패`);
            return;
        }
        const lists = JSON.parse(xhr.responseText);
        const $tbody = document.querySelector('#listTable > tbody');
        $tbody.innerHTML = '';
        for (const list of lists) {
            const createdAtArray = list.createdAt.split('T');
            const actionHtml = list.status === 'APPROVED' ? '승인 완료'
                : list.status === 'REJECTED' ? '승인 거부'
                    : `
                <button name="approval" class="-button-purple button" type="button">승인</button>
                <button name="cancel" class="-button-red button" type="button">취소</button>
            `;
            $tbody.innerHTML += `
                <tr data-id="${list.id}">
                    <td>${list.id}</td>
                    <td>${list.name}</td>
                    <td class="content">${list.content}</td>
                    <td>${createdAtArray[0]}</td>
                    <td>${list.days}일</td>
                    <td>${list.reviewedByName || '확인 전'}</td>
                    <td class="status">${list.status}</td>
                    <td class="action">${actionHtml}</td>
                </tr>
            `;
        }

        $tbody.querySelectorAll('button[name="approval"]').forEach($btn =>
            $btn.addEventListener('click', () => tableAction($btn, '승인'))
        );
        $tbody.querySelectorAll('button[name="cancel"]').forEach($btn =>
            $btn.addEventListener('click', () => tableAction($btn, '취소'))
        );
    };

    xhr.open('GET', `/admin/lists?sort=${sort}`);
    xhr.send();
}

const tableAction = ($btn, actionType) => {
    const $tr = $btn.closest('tr');
    const id = $tr.dataset.id;
    const $actionTd = $tr.querySelector('.action');
    const $statusTd = $tr.querySelector('.status');

    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    const actionStatus = actionType === '승인' ? 'APPROVED' : 'REJECTED';
    formData.append('id', id);
    formData.append('action', actionStatus);

    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) return;

        if (xhr.status < 200 || xhr.status >= 300) {
            alert(`[${xhr.status}] 변경 실패`);
            return;
        }

        $actionTd.textContent = actionType === '승인' ? '승인 완료' : '승인 거부';
        $statusTd.textContent = actionStatus;
        if (typeof calendar !== 'undefined') {
            calendar.refetchEvents();
        }

        loadLists();
    };

    xhr.open('PATCH', '/admin/list');
    xhr.send(formData);
}
loadLists();