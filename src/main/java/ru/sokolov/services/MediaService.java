package ru.sokolov.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sokolov.models.Stock;
import ru.sokolov.repositories.MediaRepository;
import ru.sokolov.repositories.StockRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MediaService {

    private final MediaRepository mediaRepository;

    @Autowired
    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

}
