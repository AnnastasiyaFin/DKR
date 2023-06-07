package com.example.dkrproject.service;

import com.example.dkrproject.dto.OrderDTO;
import com.example.dkrproject.exception.ResourceNotFoundException;
import com.example.dkrproject.model.Order;
import com.example.dkrproject.repository.OrderRepository;
import com.example.dkrproject.transformer.OrderTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private OrderTransformer orderTransformer;

    public List<OrderDTO> getOrders() {
        List<Order> orderList = orderRepository.findAll();
        return orderList.stream().map(orderTransformer::transformOrderToDTO).toList();
    }

    public OrderDTO save(OrderDTO order) throws ResourceNotFoundException {
        Order newOrder = Order.builder().orderDate(LocalDate.now())
                .dateToReturn(LocalDate.parse(order.getDateToReturn()))
                .isReturned(false)
                .book(bookService.findById(order.getBookId()))
                .user(userService.qetUserById(order.getUserId()))
                .build();
        newOrder = orderRepository.save(newOrder);
        bookService.updateAmount(order.getBookId(), "remove", 1);
        return orderTransformer.transformOrderToDTO(newOrder);
    }

    public Order findById(long orderId) throws ResourceNotFoundException {
        return orderRepository.findById(orderId).stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));
    }

    public OrderDTO findOrderDTOById(long orderId) throws ResourceNotFoundException {
        return orderTransformer.transformOrderToDTO(findById(orderId));
    }

    public OrderDTO extendOrder(long id, String dateToReturn) throws ResourceNotFoundException {
        Order orderUpd = findById(id);
        orderUpd.setIsReturned(false);
        orderUpd.setDateToReturn(LocalDate.parse(dateToReturn));
        return orderTransformer.transformOrderToDTO(orderRepository.save(orderUpd));
    }

    public void deleteOrder(long id) throws ResourceNotFoundException {
        Order orderUpd = findById(id);
        if (!orderUpd.getIsReturned()) {
            bookService.updateAmount(orderUpd.getBook().getId(), "add", 1);
        }
        orderRepository.deleteById(id);
    }

    public OrderDTO cancelOrder(long id) throws ResourceNotFoundException {
        Order orderUpd = findById(id);
        orderUpd.setIsReturned(true);
        OrderDTO orderDTO = orderTransformer.transformOrderToDTO(orderRepository.save(orderUpd));
        bookService.updateAmount(orderUpd.getBook().getId(), "add", 1);
        return orderDTO;
    }

    public void fillOrdersDB(OrderDTO order) throws ResourceNotFoundException {
        Order newOrder = Order.builder().orderDate(LocalDate.parse(order.getOrderDate()))
                .dateToReturn(LocalDate.parse(order.getDateToReturn()))
                .isReturned(order.getIsReturned())
                .book(bookService.findById(order.getBookId()))
                .user(userService.qetUserById(order.getUserId()))
                .build();
        orderRepository.save(newOrder);
    }
}
