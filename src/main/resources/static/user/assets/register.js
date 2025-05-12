const $registerForm = document.getElementById('registerForm');

$registerForm.onsubmit = (e) => {
    e.preventDefault();
    
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('email', $registerForm['email'].value);
    formData.append('password', $registerForm['password'].value);
    formData.append('name', $registerForm['name'].value);

    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) {
            return;
        }
        if (xhr.status < 200 || xhr.status >= 300) {
            
            return;
        }

        const res = JSON.parse(xhr.statusText);
        console.log(res);
        
    };
    xhr.open('POST', '/register')
    xhr.send(formData);
}