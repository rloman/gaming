package nl.qien.gaming.service;

import nl.qien.gaming.persistence.BoardgameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BoardgameService {

    @Autowired
    private BoardgameRepository repo;

    @PostConstruct
    public void init() {
    }

}
