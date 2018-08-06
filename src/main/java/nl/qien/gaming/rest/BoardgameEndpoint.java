package nl.qien.gaming.rest;

import com.github.javafaker.Faker;
import nl.qien.gaming.domain.Boardgame;
import nl.qien.gaming.persistence.BoardgameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Component
@Path("/boardgames")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BoardgameEndpoint {

    @Autowired
    private BoardgameRepository repo;

    @GET
    public Response list() {
        return Response.ok(this.repo.findAll()).build();
    }

    @GET
    @Path("{id}")
    public Response findById(@PathParam("id") long id) {
        Optional<Boardgame> optionalBoardgame=  this.repo.findById(id);

        if(optionalBoardgame.isPresent()) {
            Boardgame boardgame = optionalBoardgame.get();
            return Response.ok(boardgame).build();
        }
        else {
          return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @PostConstruct
    public void addSomeTestdata() {
        final Faker faker = new Faker();
        for(int i = 0;i<5;i++) {

            Boardgame boardgame = new Boardgame();
            boardgame.setName(faker.esports().game());
            boardgame.setHasDice(faker.bool().bool());
            boardgame.setMaxPlayers(faker.number().numberBetween(0,6));

            this.repo.save(boardgame);

        }
    }
    
}
