const $listTable = document.getElementById('listTable');

const loadLists = () => {
    const $tbody = $listTable.querySelector(':scope > tbody');
    $tbody.innerHTML = '';
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE){
            return;
        }
        if (xhr.status < 200 || xhr.status >= 300) {
            alert(`[${xhr.status}] 메모를 불러오지 못하였습니다. 잠시 후 다시 시도해 주세요`)
            return;
        }
        const lists = JSON.parse(xhr.responseText)
        console.log(lists);
        let tbodyHtml = '';
        for (const list of lists) {
            const createdAtArray = list['createdAt'].split('T');
            tbodyHtml += `
            <tr data-id="${list['id']}">
                    <td>${list['id']}</td>
                    <td>${list['name']}</td>
                    <td class="content">${list['content']}</td>
                    <td>${createdAtArray[0]}</td>
                    <td>${list['days']}일</td>
                    <td>${list['reviewedByName']}</td>
                    <td class="status">${list['status']}</td>
                    <td class="action">
                        <button name="approval" class="-button-purple button" type="button">승인</button>
                        <button name="cancel" class="-button-red button" type="button">취소</button>
                    </td>
                </tr>
                `
        }
        console.log($tbody);
        $tbody.innerHTML = tbodyHtml;
        $tbody.querySelectorAll('button[name="approval"]').forEach(($btn) => {
            $btn.addEventListener('click', () => tableAction($btn, '승인'));
        });
        $tbody.querySelectorAll('button[name="cancel"]').forEach(($btn) => {
            $btn.addEventListener('click', () => tableAction($btn, '취소'));
        })
    };
    xhr.open('GET', '/admin/lists');
    xhr.send();
};

const tableAction = ($btn, actionType) => {
    const $tr = $btn.closest('tr');
    const id = $tr.dataset.id;
    const $actionTd = $tr.querySelector('.action');
    const $statusTd = $tr.querySelector('.status');
    
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('id', id);
    formData.append('action', actionType);
    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) {
            return;
        }
        if (xhr.status < 200 || xhr.status >= 300) {
            alert(`[${xhr.status}] 변경 실패`);
            return;
        }

        if (actionType == '승인') {
            $actionTd.textContent = '승인 완료';
            $statusTd.textContent = 'APPROVED';
        } else {
            $actionTd.textContent = '승인 거부';
            $statusTd.textContent = 'REJECTED';
        }
    };
    xhr.open('PATCH', '/admin/list');
    xhr.send(formData);
}
loadLists();