package in.zoukme.zouk_album.clients.pagbank;

import com.fasterxml.jackson.annotation.JsonProperty;
import in.zoukme.zouk_album.clients.pagbank.domain.PaymentType;
import java.util.List;

class PaymentMethodsConfig {

  @JsonProperty("config_options")
  private List<ConfigOptions> configOptions;

  private PaymentMethodsConfig(List<ConfigOptions> installmentLimit) {
    this.configOptions = installmentLimit;
  }

  public PaymentType getType() {
    return PaymentType.CREDIT_CARD;
  }

  private record ConfigOptions(String option, String value) {}

  public static PaymentMethodsConfig installmentLimitTen() {
    return new PaymentMethodsConfig(List.of(new ConfigOptions("INSTALLMENTS_LIMIT", "10")));
  }
}
