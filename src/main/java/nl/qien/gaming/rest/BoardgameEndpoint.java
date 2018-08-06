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
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Optional;

@RestController
@RequestMapping("api/boardgames")
public class BoardgameEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(BoardgameEndpoint.class);

    @Autowired
    private BoardgameRepository repo;

    @GetMapping
    public ResponseEntity<Iterable<Boardgame>> list() {
        return ResponseEntity.ok(this.repo.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Boardgame> findById(@PathVariable long id) {
        Optional<Boardgame> optionalBoardgame=  this.repo.findById(id);

        if(optionalBoardgame.isPresent()) {
            Boardgame boardgame = optionalBoardgame.get();
            return ResponseEntity.ok(boardgame);
        }
        else {
          return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "docx", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public  ResponseEntity<Resource> generateDocxFile(@Autowired HttpServletRequest request) throws Docx4JException {
        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
        mainDocumentPart.addStyledParagraphOfText("Title", "Hello World!");
        mainDocumentPart.addParagraphOfText("Welcome To Baeldung");
        File exportFile = new File("welcome.docx");
        wordPackage.save(exportFile);

        // Try to determine file's content type
        String contentType = request.getServletContext().getMimeType(exportFile.getAbsolutePath());

        contentType = "application/octet-stream";
        Resource r = new FileSystemResource(exportFile);


        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + exportFile.getName() + "\"")
                .body(r);
    }

    @PostMapping
    public ResponseEntity<Boardgame> create(Boardgame boardgame) {
        this.repo.save(boardgame);

        return ResponseEntity.ok(boardgame);
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
