package nl.qien.gaming;

import nl.qien.gaming.domain.Boardgame;
import nl.qien.gaming.persistence.BoardgameRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class BoardgameEndpoint {

    @Autowired
    private BoardgameRepository repo;

    public Boardgame findById(long id) {
        Optional<Boardgame> optionalBoardgame=  this.repo.findById(id);

        if(optionalBoardgame.isPresent()) {
            return optionalBoardgame.get();
        }
        else {
            // afronden met een 404 foutcode ... of iets in die richting ...
            return null; // ver bad for now
        }
    }
}
