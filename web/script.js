
function getAll() {
const url = "localhost:8080/api/v1/getAll";

fetch(url)
    .then((response) => response.json())
    .then((data) => {
        console.log("All items:", data);
    })
    .catch((error) => {
        console.error("Error fetching data:", error);
    });
}

function getById(id) {
    const url = "localhost:8080/api/v1/get/" + id;
    fetch(url)
        .then((response) => response.json())
        .then((data) => {
            console.log("Item:", data);
        })
        .catch((error) => {
            console.error("Error fetching data:", error);
        });

}

function create() {
    const url = "localhost:8080/api/v1/create";
    const data = {
        id: "New Item",
        name: "This is a new item.",
    };

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
deleteById()();

function deleteById() {
   return undefined;
}
