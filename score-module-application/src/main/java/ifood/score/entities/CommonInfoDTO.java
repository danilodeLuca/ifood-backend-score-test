package ifood.score.entities;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CommonInfoDTO {

    protected Integer size;
    protected BigDecimal totalPrice;

    CommonInfoDTO(Integer size, BigDecimal total) {
        this.size = size;
        this.totalPrice = total;
    }
}
