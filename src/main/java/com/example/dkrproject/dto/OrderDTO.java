package com.example.dkrproject.dto;

import jakarta.validation.constraints.Min;
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
    @Min(value = 1, message = "Enter readerCardID")
    private Long readerCardId;
    @Min(value = 1,message = "Enter bookId")
    private Long bookId;
}
