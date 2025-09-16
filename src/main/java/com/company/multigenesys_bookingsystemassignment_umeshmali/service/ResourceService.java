package com.company.multigenesys_bookingsystemassignment_umeshmali.service;

import com.company.multigenesys_bookingsystemassignment_umeshmali.entity.ResourceEntity;
import com.company.multigenesys_bookingsystemassignment_umeshmali.repository.ResourceRepository;
import com.company.multigenesys_bookingsystemassignment_umeshmali.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class ResourceService {

    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public Page<ResourceEntity> list(Pageable pageable) {
        return resourceRepository.findAll(pageable);
    }

    public java.util.Optional<ResourceEntity> findById(Long id) {
        return resourceRepository.findById(id);
    }

    public ResourceEntity save(ResourceEntity resource) {
        if (resource == null) {
            throw new IllegalArgumentException("Resource must not be null");
        }
        if (!StringUtils.hasText(resource.getName())) {
            throw new IllegalArgumentException("Resource name is required");
        }
        // additional validations (capacity >= 0, etc) can be added here
        return resourceRepository.save(resource);
    }

    public void deleteById(Long id) {
        if (!resourceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Resource with id " + id + " not found");
        }
        resourceRepository.deleteById(id);
    }
}
