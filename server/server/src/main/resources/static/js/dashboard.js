var map;
var heatmap;
var samples = [];
var mode = 'co2';

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 16,
        center: {lat: 48.2029932, lng: 16.3719508},
        mapTypeId: google.maps.MapTypeId.ROADMAP
    });

    heatmap = new google.maps.visualization.HeatmapLayer({
        dissipating: false,
        data: getData(),
        radius: 0.005,
        map: map
    });

    $.get('/api/samples', updateHeatmap);
    $.get('/api/symptoms', updateCircles);
}

function getData() {
    var min = Infinity, max = -Infinity;
    for (var i = 0; i < samples.length; i++) {
        min = Math.min(min, samples[i].data[mode]);
        max = Math.max(max, samples[i].data[mode]);
    }

    return new google.maps.MVCArray(samples.map(function (sample) {
        var location = sample.location;
        return {
            location: new google.maps.LatLng(location.latitude, location.longitude),
            weight: (sample.data[mode] - min) / (max - min)
        };
    }));
}

function updateHeatmap(data) {
    samples = data;
    heatmap.set('data', getData());
}

function updateCircles(symptoms) {
    var j = 0;
    var int = setInterval(function () {
        if (j >= symptoms.length) {
            clearInterval(int);
            return;
        }

        var symptom = symptoms[j++];
        var location = symptom.location;

        var circle = new google.maps.Circle({
            strokeColor: '#FF0000',
            strokeOpacity: 0,
            strokeWeight: 2,
            fillColor: '#FF0000',
            fillOpacity: 0,
            map: map,
            center: {
                lat: location.latitude,
                lng: location.longitude
            },
            radius: 220
        });

        var i = 0;
        var interval = setInterval(function () {
            if (i++ > 20) {
                clearInterval(interval);
            } else {
                circle.set('radius', 220 - i * 10);
                circle.set('fillOpacity', i * (0.35 / 20));
                circle.set('strokeOpacity', i * (0.8 / 20));
            }
        }, 50);
    }, 500);
}
