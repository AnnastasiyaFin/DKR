package com.example.dkrproject.service;

import com.example.dkrproject.exception.ResourceNotFoundException;
import com.example.dkrproject.model.ReaderCard;
import com.example.dkrproject.repository.ReaderCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReaderCardService {

    @Autowired
    private ReaderCardRepository readerCardRepository;

    public ReaderCard getCartByUserId(final long userId) throws ResourceNotFoundException {
        return (ReaderCard) readerCardRepository.findReaderCardByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Reader card not found for this userId :: " + userId));
    }

    public ReaderCard getCardById(final long id) throws ResourceNotFoundException {
        return readerCardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reader card not found for this id :: " + id));
    }
}
