package in.zoukme.zouk_album.controllers.admin;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

record AlbumRequest(
    String title, String description, String city, String eventDate, @RequestParam("file-upload") MultipartFile cover) {}

