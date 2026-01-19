export const validateName = (name: string): boolean => {
    const trimmed = name.trim();
    // Allow letters (including PL diacritics), spaces and hyphens
    const namePattern = /^[A-Za-zĄĆĘŁŃÓŚŹŻąćęłńóśźż\-\s]+$/u;
    // Must contain at least 2 letters (ignoring spaces/hyphens)
    const lettersCount = (trimmed.replace(/[\-\s]/g, '').match(/[A-Za-zĄĆĘŁŃÓŚŹŻąćęłńóśźż]/gu) || []).length;

    return lettersCount >= 2 && namePattern.test(trimmed);
}

export const validateUsername = (username: string): boolean => {
    const trimmed = username.trim();
    // Allow letters, digits, underscore, hyphen, dot; must start with letter or digit
    const validPattern = /^[a-zA-Z0-9][a-zA-Z0-9._-]*$/;
    const minLength = trimmed.length >= 3;
    const maxLength = trimmed.length <= 30;

    return minLength && maxLength && validPattern.test(trimmed);
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
    // Special character optional to reduce friction
    // const hasSpecialChar = /[!@#$%^&*()_+{}\[\]:;"'<>,.?\/~`\\|-]/.test(trimmed);

    return minLength && hasLowercase && hasUppercase && hasDigit;
}

export const validateConfirmedPassword = (confirmedPassword: string, password: string): boolean => {
    return confirmedPassword.trim() !== '' && confirmedPassword === password;
}