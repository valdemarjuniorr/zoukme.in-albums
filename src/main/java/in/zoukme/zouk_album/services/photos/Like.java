package in.zoukme.zouk_album.services.photos;

class Like implements SocialInteraction {
    @Override
    public String text() {
        return "Curtida!";
    }

    @Override
    public String style() {
        return "solid";
    }
}
