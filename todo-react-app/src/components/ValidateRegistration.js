const validateName = (name) => {
    if (!name || name.trim() === '') {
        return 'Name is required';
    }
    return '';
};

const validateEmail = (email) => {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    if (!email) {
        return 'Email is required';
    } else if (!emailRegex.test(email)) {
        return 'Invalid email format';
    }
    return '';
};

const validatePassword = (password) => {
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    if (!password) {
        return 'Password is required';
    } else if (password.length < 8) {
        return 'Password must be at least 8 characters long';
    } else if (!passwordRegex.test(password)) {
        return 'Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character';
    }
    return '';
};

export { validateName, validateEmail, validatePassword };
