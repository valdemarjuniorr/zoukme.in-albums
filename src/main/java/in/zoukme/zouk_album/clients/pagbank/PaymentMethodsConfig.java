package in.zoukme.zouk_album.clients.pagbank;

import com.fasterxml.jackson.annotation.JsonProperty;
import in.zoukme.zouk_album.clients.pagbank.domain.PaymentType;
import java.util.List;

class PaymentMethodsConfig {

  @JsonProperty("config_options")
  private List<ConfigOptions> configOptions;

  private PaymentType type = PaymentType.CREDIT_CARD;

  private PaymentMethodsConfig(List<ConfigOptions> installmentLimit) {
    this.configOptions = installmentLimit;
  }

  public PaymentType getType() {
    return type;
  }

  private record ConfigOptions(String option, String value) {}

  public static PaymentMethodsConfig installmentLimitThree() {
    return new PaymentMethodsConfig(List.of(new ConfigOptions("INSTALLMENTS_LIMIT", "3")));
  }
}
