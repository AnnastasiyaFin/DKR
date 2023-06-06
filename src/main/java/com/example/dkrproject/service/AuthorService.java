package com.example.dkrproject.service;

import com.example.dkrproject.exception.ResourceNotFoundException;
import com.example.dkrproject.model.Author;
import com.example.dkrproject.repository.AuthorRepository;
import com.example.dkrproject.dto.AuthorDTO;
import com.example.dkrproject.transformer.AuthorTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorTransformer authorTransformer;

    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorByName(String name) {
        return authorRepository.findByName(name).stream().findFirst().orElse(null);
    }

    public Author save(AuthorDTO authorRequest) {
        Author author = authorTransformer.transformAuthorRequest(authorRequest);
        return authorRepository.save(author);
    }

    public Author updateAuthor(long id, AuthorDTO authorRequest) throws ResourceNotFoundException {
        Author author = authorTransformer.transformAuthorRequest(authorRequest);
        authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found for this id :: " + id));
        author.setId(id);
        return authorRepository.save(author);
    }

    public void deleteAuthor(long id) {
        authorRepository.deleteById(id);
    }

}
