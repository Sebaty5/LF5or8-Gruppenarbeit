class Adresse {
    constructor (strasse, hausnr, plz, ort) {
        this.strasse = strasse;
        this.hausnr = hausnr;
        this.plz = plz;
        this.ort = ort;
    }
}
class Kaufvertrag {
    constructor (kaeufer, verkaeufer, ware, zahlungsmittel) {
        this.kaeufer = kaeufer;
        this.verkaeufer = verkaeufer;
        this.ware = ware;
        this.zahlungsmittel = zahlungsmittel;
    }
}
class Vertragspartner {
    constructor (ausweisNr,vorname,nachname,adresse) {
        this.ausweisNr = ausweisNr;
        this.vorname = vorname;
        this.nachname = nachname;
        this.adresse = adresse;
    }
}
class Ware {
    constructor (id, bezeichnung, beschreibung, preis, besonderheiten, maengel) {
        this.id = id;
        this.bezeichnung = bezeichnung;
        this.beschreibung = beschreibung;
        this.preis = preis;
        this.besonderheiten = besonderheiten;
        this.maengel = maengel;
    }
}
function getAll() {
    const url = "http://localhost:8080/api/v1/getAllVertragspartner";

    fetch(url)
        .then((response) => response.json())
        .then((data) => {
            console.log("All items:", data);
            changeElement('result',data);
        })
        .catch((error) => {
            console.error("Error fetching data:", error);
        });
}

function getById() {
    var id = document.getElementById("idInputField").value;
    const url = "http://localhost:8080/api/v1/getVertragspartner/" + id;
    fetch(url)
        .then((response) => response.text())
        .then((data) => {
            console.log("Item:", data);
            if(data.includes("NullPointerException")) {
                changeElement("result","Error: NullPointerException was thrown.");
            } else {
            changeElement("result",data);
            }})
        .catch((error) => {
            console.error("Error fetching data:", error);
        });
}


function create() {
    const url = "http://localhost:8080/api/v1/createVertragspartner";

    const data = convertToVertragspartner(
        document.getElementById('ausweisnr'),
        document.getElementById('vorname'),
        document.getElementById('nachname'),
        document.getElementById('strasse'),
        document.getElementById('hausnr'),
        document.getElementById('plz'),
        document.getElementById('ort'));

    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => response.json())
        .then((data) => {
            console.log("Created item:", data);
        })
        .catch((error) => {
            console.error("Error creating data:", error);
        });

}
function deleteById(id) {
    const url = "localhost:8080/api/v1/delete/" + id;
    fetch(url)
        .then((response) => response.json())
        .then((data) => {
            console.log("Item:", data, "deleted.");})
        .catch((error) => {
            console.error("Error fetching data:", error);
        });


}

function changeElement(elementId, newValue) {
    document.getElementById(elementId).innerHTML = newValue;
}



function convertToVertragspartner(ausweisnr,vorname,nachname,strasse,hausnr,plz,ort) {
    const currentAdresse = new Adresse(strasse,hausnr,plz,ort);
    console.log("data converting to Class instance...");
    return new Vertragspartner(ausweisnr, vorname, nachname, currentAdresse);
}