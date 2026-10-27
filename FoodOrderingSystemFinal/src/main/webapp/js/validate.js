window.onload = function() {
    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
        registerForm.onsubmit = function() {
            const pwd = document.getElementById('pwd').value;
            const rePwd = document.getElementById('rePwd').value;
            if (pwd.length < 6) {
                alert('The password length cannot be less than 6 characters');
                return false;
            }
            if (pwd !== rePwd) {
                alert('The passwords entered twice are inconsistent');
                return false;
            }
            return true;
        }
    }
}