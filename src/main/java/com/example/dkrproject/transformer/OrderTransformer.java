package com.example.dkrproject.transformer;

import com.example.dkrproject.dto.OrderDTO;
import com.example.dkrproject.model.Order;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class OrderTransformer {

    public OrderDTO transformOrderToDTO(final Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate().format(DateTimeFormatter.ISO_DATE))
                .dateToReturn(order.getDateToReturn().format(DateTimeFormatter.ISO_DATE))
                .isReturned(order.getIsReturned())
                .bookId(order.getBook().getId())
                .userId(order.getUser().getId())
                .build();
    }
}
