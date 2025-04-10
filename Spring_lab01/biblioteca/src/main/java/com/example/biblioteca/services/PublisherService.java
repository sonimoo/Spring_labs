package com.example.biblioteca.services;

import com.example.biblioteca.dtos.PublisherDTO;
import com.example.biblioteca.entities.Publisher;
import com.example.biblioteca.repositories.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public List<PublisherDTO> getAllPublishers() {
        return publisherRepository.findAll().stream()
                .map(publisher -> new PublisherDTO(publisher.getId(), publisher.getName(),
                        publisher.getBooks().stream().map(book -> book.getId()).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    public PublisherDTO getPublisherById(Long id) {
        return publisherRepository.findById(id)
                .map(publisher -> new PublisherDTO(publisher.getId(), publisher.getName(),
                        publisher.getBooks().stream().map(book -> book.getId()).collect(Collectors.toList())))
                .orElse(null);
    }

    public PublisherDTO createPublisher(PublisherDTO publisherDTO) {
        Publisher publisher = new Publisher();
        publisher.setName(publisherDTO.getName());
        publisher = publisherRepository.save(publisher);
        return new PublisherDTO(publisher.getId(), publisher.getName(), List.of());
    }

    public PublisherDTO updatePublisher(Long id, PublisherDTO publisherDTO) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow();
        publisher.setName(publisherDTO.getName());
        publisher = publisherRepository.save(publisher);
        return new PublisherDTO(publisher.getId(), publisher.getName(), List.of());
    }

    public void deletePublisher(Long id) {
        publisherRepository.deleteById(id);
    }
}
