package spring;

import Kaufvertrag.businessInterfaces.IVertragspartner;
import Kaufvertrag.dataLayer.businessClasses.Vertragspartner;
import Kaufvertrag.dataLayer.dataAccessObjects.DataLayerManager;
import Kaufvertrag.dataLayer.dataAccessObjects.IDataLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
public class Controller {
    public DataLayerManager dataLayerManager = DataLayerManager.getInstance();


    @CrossOrigin
    @GetMapping("/getVertragspartner/{id}")
    ResponseEntity<IVertragspartner> getVertragspartner(@PathVariable Integer id) {
        IDataLayer datalayer = dataLayerManager.getDataLayer();
        return ResponseEntity.ok(datalayer.getDaoVertragspartner().read(String.valueOf(id)));

    }

    @GetMapping("/test")
    ResponseEntity<String> test() {
        return ResponseEntity.ok("" +
                "<h1>es funktioniert!</h1>" +
                "<ul>" +
                "<li><a href=\"https://google.de\">Hier gehts zu google</a></li>" +
                "</ul>");

    }
}
