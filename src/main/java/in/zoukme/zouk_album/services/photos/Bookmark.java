package in.zoukme.zouk_album.services.photos;

public class Bookmark implements SocialInteraction {
    @Override
    public String text() {
        return "Salvo!";
    }

    @Override
    public String style() {
        return "solid";
    }
}
