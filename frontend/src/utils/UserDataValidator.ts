export const validateName = (name: string): boolean => {
    const trimmed = name.trim();
    const onlyLetters = /^[A-Za-zĄĆĘŁŃÓŚŹŻąćęłńóśźż]+$/u;

    return trimmed.length >= 2 && onlyLetters.test(trimmed);
}

export const validateUsername = (username: string): boolean => {
    const trimmed = username.trim();
    const validPattern = /^[a-zA-Z0-9_-]+$/;

    return trimmed.length >= 3 && validPattern.test(trimmed);
}

export const validateEmail = (email: string): boolean => {
    const trimmed = email.trim();
    const pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return pattern.test(trimmed);
}

export const validatePassword = (password: string): boolean => {
    const trimmed = password.trim();

    const minLength = trimmed.length >= 8;
    const hasLowercase = /[a-z]/.test(trimmed);
    const hasUppercase = /[A-Z]/.test(trimmed);
    const hasDigit = /\d/.test(trimmed);
    const hasSpecialChar = /[!@#$%^&*()_+{}\[\]:;"'<>,.?/~`\\|-]/.test(trimmed);

    return minLength && hasLowercase && hasUppercase && hasDigit && hasSpecialChar;
}

export const validateConfirmedPassword = (confirmedPassword: string, password: string): boolean => {
    return confirmedPassword === password;
}