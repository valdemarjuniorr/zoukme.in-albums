package in.zoukme.zouk_album.services.photos;

class Unlike implements SocialInteraction {
    @Override
    public String text() {
        return "Curtir";
    }

    @Override
    public String style() {
        return "regular";
    }
}
