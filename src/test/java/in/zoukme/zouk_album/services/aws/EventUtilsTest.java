package in.zoukme.zouk_album.services.aws;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EventUtilsTest {

  @Test
  void getEventFolderName() {
    var eventFolderName = EventUtils.getEventFolderName("Elite Samba Congress 2025");
    Assertions.assertThat(eventFolderName)
        .isEqualTo("next-events/elite-samba-congress-2025");
  }

  @ParameterizedTest
  @ValueSource(strings = {"", " "})
  void getEventFolderNameWithEmptyTitle(String title) {
    Assertions.assertThatIllegalArgumentException()
        .isThrownBy(() -> EventUtils.getEventFolderName(title));
  }

  @Test
  void getEventFolderNameWithNullTitle() {
    Assertions.assertThatIllegalArgumentException()
        .isThrownBy(() -> EventUtils.getEventFolderName(null));
  }
}
