package nl.qien.gaming.service;

import nl.qien.gaming.domain.Boardgame;
import nl.qien.gaming.persistence.BoardgameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class BoardgameService {

    @Autowired
    private BoardgameRepository repo;

    public Iterable<Boardgame> list() {

        return this.repo.findAll();

    }

    public Optional<Boardgame> findById(long id) {
        return this.repo.findById(id);
    }


    public Boardgame create(Boardgame newGame) {
        return this.repo.save(newGame);
    }
}
