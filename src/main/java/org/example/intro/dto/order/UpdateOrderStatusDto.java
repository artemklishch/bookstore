package org.example.intro.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.intro.enums.OrderStatus;
import org.example.intro.validation.EnumMatch;

@Data
public class UpdateOrderStatusDto {
    @NotNull(message = "Status field is mandatory")
    @EnumMatch(enumClass = OrderStatus.class)
    private String status;
}
