package in.zoukme.zouk_album.controllers.about;

import org.springframework.util.StringUtils;

record ContactForm(String fullName, String email, String mobile, String subject, String message) {

    public ContactForm {
        if (!StringUtils.hasText(fullName)) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        if (!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!StringUtils.hasText(mobile)) {
            throw new IllegalArgumentException("mobile cannot be empty");
        }
        if (!StringUtils.hasText(subject)) {
            throw new IllegalArgumentException("subject cannot be empty");
        }
        if (!StringUtils.hasText(message)) {
            throw new IllegalArgumentException("message cannot be empty");
        }
    }
}
