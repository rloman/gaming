package nl.qien.gaming.rest;

import com.github.javafaker.Faker;
import nl.qien.gaming.domain.Boardgame;
import nl.qien.gaming.persistence.BoardgameRepository;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.Optional;

@Component
@Path("/boardgames")
public class BoardgameEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(BoardgameEndpoint.class);

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
    @Produces(MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public  ResponseEntity<?> generateDocxFile(@Autowired HttpServletRequest request) throws Docx4JException {
        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
        mainDocumentPart.addStyledParagraphOfText("Title", "Hello World!");
        mainDocumentPart.addParagraphOfText("Welcome To Baeldung");
        File exportFile = new File("welcome.docx");
        wordPackage.save(exportFile);

        // Try to determine file's content type
        String contentType = null;// request.getServletContext().getMimeType(exportFile.getAbsolutePath());

        contentType = "application/octet-stream";


        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + exportFile.getName() + "\"")
                .body(exportFile);
    }

    @POST
    public Response create(Boardgame boardgame) {
        this.repo.save(boardgame);

        return Response.accepted(boardgame).build();
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
