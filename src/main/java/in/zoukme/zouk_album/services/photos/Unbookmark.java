package in.zoukme.zouk_album.services.photos;

public class Unbookmark implements SocialInteraction {
    @Override
    public String text() {
        return "Salvar";
    }

    @Override
    public String style() {
        return "regular";
    }
}
