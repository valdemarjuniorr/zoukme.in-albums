package in.zoukme.zouk_album.domains;

import java.math.BigDecimal;
import java.util.Objects;

public record SumPriceTotalTransaction(BigDecimal sumPrice, Integer total) {

  public SumPriceTotalTransaction createZeroValues() {
    return new SumPriceTotalTransaction(BigDecimal.ZERO, 0);
  }

  public Boolean isZero() {
    return Objects.isNull(sumPrice) || Objects.isNull(total);
  }
}
