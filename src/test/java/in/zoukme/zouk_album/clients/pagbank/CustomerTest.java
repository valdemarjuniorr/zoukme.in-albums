package in.zoukme.zouk_album.clients.pagbank;

import in.zoukme.zouk_album.clients.pagbank.domain.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomerTest {

  @Test
  void getPhoneNumber() {
    var phone = new Customer.Phone("47988594002");
    Assertions.assertThat(phone.country()).isEqualTo("+55");
    Assertions.assertThat(phone.area()).isEqualTo("47");
    Assertions.assertThat(phone.number()).isEqualTo("988594002");
  }
}
