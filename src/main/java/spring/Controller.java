package spring;

import Kaufvertrag.businessInterfaces.IVertragspartner;
import Kaufvertrag.dataLayer.businessClasses.Vertragspartner;
import Kaufvertrag.dataLayer.dataAccessObjects.DataLayerManager;
import Kaufvertrag.dataLayer.dataAccessObjects.IDataLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
public class Controller {

    public IDataLayer datalayer = DataLayerManager.getInstance().getDataLayer();


    @GetMapping("/getVertragspartner/{id}")
    ResponseEntity<IVertragspartner> getVertragspartner(@PathVariable Integer id) {
        return ResponseEntity.ok(datalayer.getDaoVertragspartner().read(String.valueOf(id)));

    }

    @GetMapping("/getAllVertragspartner")
    ResponseEntity<List<IVertragspartner>> getAllVertragspartner() {
        return ResponseEntity.ok(datalayer.getDaoVertragspartner().readAll());
    }
    @GetMapping("/deleteVertragspartner/{id}")
    ResponseEntity<String> deleteVertragspartner(@PathVariable Integer id) {
        datalayer.getDaoVertragspartner().delete(String.valueOf(id));
        try {
            IVertragspartner iv = datalayer.getDaoVertragspartner().read(String.valueOf(id));
            if(iv == null) { throw new Exception();}
            return ResponseEntity.ok("Deletion Failed.");
        } catch (Exception e) {
            return ResponseEntity.ok("Successfully deleted.");
        }
    }

    @GetMapping("/test")
    ResponseEntity<String> test() {
        return ResponseEntity.ok("" +
                "<h1>es funktioniert!</h1>" +
                "<ul>" +
                "<li><a href=\"https://google.de\">Hier gehts zu google</a></li>" +
                "</ul>");

    }


    @PostMapping("/createVertragspartner")
    ResponseEntity<String> createVertragspartner(@RequestBody Vertragspartner vertragspartner) {
        datalayer.getDaoVertragspartner().create(vertragspartner);
        return ResponseEntity.ok("Successfully created.");
    }
}
