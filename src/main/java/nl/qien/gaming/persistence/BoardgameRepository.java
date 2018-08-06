package nl.qien.gaming.persistence;

import nl.qien.gaming.domain.Boardgame;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardgameRepository extends CrudRepository<Boardgame, Long> {


    Boardgame findByName(String name);

    Iterable<Boardgame> findAllByMaxPlayersOrderByMaxPlayersAsc(int maxPlayers);


    Iterable<Boardgame> findAllByMaxPlayersAndName(int maxPlayer, String name);


}
