package in.zoukme.zouk_album.domains.subevent;

import in.zoukme.zouk_album.domains.Event;
import in.zoukme.zouk_album.domains.SubEvent;
import java.util.List;

public record SubEventWithEvent(Event event, List<SubEvent> subEvents) {}
