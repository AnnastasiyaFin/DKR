package com.example.dkrproject.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OrderDTO {

    private Long id;
    private String orderDate;
    @NotEmpty(message = "Date to return should not be null")
    private String dateToReturn;
    private Boolean isReturned;
    @NotEmpty(message = "UserID should not be null")
    private Long userId;
    @NotEmpty(message = "BookId should not be null")
    private Long bookId;
}
