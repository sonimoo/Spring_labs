package com.example.biblioteca.services;

import com.example.biblioteca.daos.PublisherDao;
import com.example.biblioteca.dtos.PublisherDTO;
import com.example.biblioteca.entities.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublisherService {
    private final PublisherDao publisherDao;

    public List<PublisherDTO> getAllPublishers() {
        return publisherDao.findAll().stream()
                .map(publisher -> new PublisherDTO(publisher.getId(), publisher.getName(), List.of()))
                .collect(Collectors.toList());
    }

    public PublisherDTO getPublisherById(Long id) {
        Publisher publisher = publisherDao.findById(id);
        if (publisher == null) {
            throw new RuntimeException("Publisher not found");
        }
        return new PublisherDTO(publisher.getId(), publisher.getName(), List.of());
    }

    public PublisherDTO createPublisher(PublisherDTO publisherDTO) {
        Publisher publisher = new Publisher();
        publisher.setName(publisherDTO.getName());
        publisherDao.save(publisher);
        return new PublisherDTO(publisher.getId(), publisher.getName(), List.of());
    }

    public PublisherDTO updatePublisher(Long id, PublisherDTO publisherDTO) {
        Publisher publisher = publisherDao.findById(id);
        if (publisher == null) {
            throw new RuntimeException("Publisher not found");
        }

        publisher.setName(publisherDTO.getName());
        publisherDao.update(publisher);

        return new PublisherDTO(publisher.getId(), publisher.getName(), List.of());
    }

    public void deletePublisher(Long id) {
        publisherDao.delete(id);
    }
}
