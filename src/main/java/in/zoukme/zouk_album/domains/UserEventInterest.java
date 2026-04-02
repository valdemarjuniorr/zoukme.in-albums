package in.zoukme.zouk_album.domains;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

import in.zoukme.zouk_album.domains.users.User;

import java.time.LocalDateTime;

@Table("user_event_interest")
public record UserEventInterest(
    @Id Long id,
    AggregateReference<User, Long> userId,
    AggregateReference<Event, Long> eventId,
    Interest interest,
    LocalDateTime createdAt) {

  public UserEventInterest(Long userId, Long eventId, Interest interestType) {
    this(null, AggregateReference.to(userId), AggregateReference.to(eventId), interestType, LocalDateTime.now());
  }

  public enum Interest {
    INTERESTED, GOING
  }

  public Boolean isSame(Interest interest) {
    return this.interest.equals(interest);
  }
}
