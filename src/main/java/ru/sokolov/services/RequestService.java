package ru.sokolov.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sokolov.models.Request;
import ru.sokolov.repositories.RequestRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RequestService {

    private final RequestRepository requestRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<Request> findAll() { return requestRepository.findAll(); }

    @Transactional
    public void delete(int id) { requestRepository.deleteById(id); }
}
