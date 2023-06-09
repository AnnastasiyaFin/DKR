package com.example.dkrproject.controller;

import com.example.dkrproject.dto.OrderDTO;
import com.example.dkrproject.exception.ResourceNotFoundException;
import com.example.dkrproject.model.Order;
import com.example.dkrproject.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
@Tag(name="Бронирование книг", description="Получение данных о бронированиях книг")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return new ResponseEntity<>(orderService.getOrders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable("id") long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(orderService.findOrderDTOById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> addOrder(@Valid @RequestBody OrderDTO order) throws ResourceNotFoundException {
        return new ResponseEntity<>(orderService.save(order), HttpStatus.CREATED);
    }

    @PutMapping("/extend")
    public ResponseEntity<OrderDTO> extendOrder(@RequestParam long id, @RequestParam String date) throws ResourceNotFoundException {
        OrderDTO updateOrder = orderService.extendOrder(id, date);
        return ResponseEntity.ok().body(updateOrder);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<OrderDTO> cancelOrder(@PathVariable("id") long id) throws ResourceNotFoundException {
        OrderDTO updateOrder = orderService.cancelOrder(id);
        return ResponseEntity.ok().body(updateOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable("id") long id) throws ResourceNotFoundException {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
