package nl.qien.gaming.rest;

import com.github.javafaker.Faker;
import nl.qien.gaming.domain.Boardgame;
import nl.qien.gaming.domain.Player;
import nl.qien.gaming.messages.Message;
import nl.qien.gaming.persistence.BoardgameRepository;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
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

    @GET
    @Path("docx")
    public void generateDocxFile() throws Docx4JException {
        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
        mainDocumentPart.addStyledParagraphOfText("Title", "Hello World!");
        mainDocumentPart.addParagraphOfText("Welcome To Baeldung");
        File exportFile = new File("welcome.docx");
        wordPackage.save(exportFile);
    }

    @POST
    public Response create(Boardgame boardgame) {
        this.repo.save(boardgame);

        return Response.accepted(boardgame).build();
    }


    // this is for UPDATING a row
    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") long id, Boardgame input) {

        // first fetch the to be updated
        Optional<Boardgame> optionalToBeUpdated = this.repo.findById(id);

        if(optionalToBeUpdated.isPresent()) {

            Boardgame tobeUpdated = optionalToBeUpdated.get();
            tobeUpdated.setName(input.getName());
            tobeUpdated.setHasDice(input.isHasDice());
            tobeUpdated.setMaxPlayers(input.getMaxPlayers());

            this.repo.save(tobeUpdated); // done

            return Response.accepted(tobeUpdated).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteById(@PathParam("id") long id) {

        // first fetch the to be deleted
        Optional<Boardgame> optionalVictim = this.repo.findById(id);

        if(optionalVictim.isPresent()) {

          this.repo.deleteById(id);

          Message message = new Message(200, "gelukt!");

            return Response.ok(message).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @PostConstruct
    public void addSomeTestdata() {
        Faker faker = new Faker();
        for(int i = 0;i<5;i++) {

            Boardgame boardgame = new Boardgame();
            boardgame.setName(faker.esports().game());
            boardgame.setHasDice(faker.bool().bool());
            boardgame.setMaxPlayers(faker.number().numberBetween(0,6));

            Player p = new Player();
            p.setName("Raymie");

            boardgame.addPlayer(p);

            this.repo.save(boardgame);
        }
    }
}
