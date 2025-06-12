package in.zoukme.zouk_album.services.aws.ses;

public record Email(String to, String from, String subject, String body) {}
