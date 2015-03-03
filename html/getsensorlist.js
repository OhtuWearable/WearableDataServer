function displaySensorlist() {
    var list = document.getElementById("sensorlist");

    for (var sensor_name in result) {
        var node = document.createElement("li");
        var textnode = document.createTextNode(sensor_name + ": " + result[sensor_name]);
        node.appendChild(textnode);
        list.appendChild(node);
    }
}

var result = {};

var request = new XMLHttpRequest();
request.open('GET', 'http://localhost:8080/feeds', true);

request.onload = function () {
    if (request.status >= 200 && request.status < 400) {
        // Success!
        result = JSON.parse(request.responseText);
        displaySensorlist();
    } else {
    }
};
request.send();
