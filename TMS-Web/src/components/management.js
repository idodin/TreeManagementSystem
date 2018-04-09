import axios from 'axios'
var config = require('../../config')
window.map = null;
window.rLocation = 'dsfsdf';
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  nameNew: 'tms',
  data () {
    return {
      user: '',
      forecastSelect: null,
      updateSelect: null,
      ne : '',
      sw : '',
      stats: [],
      citiesStats: [],
      userType: '',
      rectBounds: {
        north: 45.695066,
        south: 45.495619,
        east: -73.578198,
        west: -73.608229
      },
      newTree: '',
      requestTrees: '',
      newLat: '',
      treeID: 'oldONe',
      ids: [],
      statusForecast: [
        { value: null, text: 'Status', disabled: true },
        { value: 'Healthy', text: 'Healthy' },
        { value: 'Cut', text: 'Cut' },
        { value: 'Diseased', text: 'Diseased' }
      ],
      fields:[{
        key: 'id',
        sortable: true
      },{
        key: 'species',
        sortable: true
      },{
        key: 'city',
        sortable: true
      },{
        key: 'status',
        sortable: true
      },{
        key: 'latitude',
        sortable: true
      },{
        key: 'longitude',
        sortable: true
      },
    ],
    trees: [{
      id: '1',
      species: 'fl',
      city: 'toronto',
      status: 'diseased',
      latitude: 45.495619,
      longitude: -73.608229
    }, {
      id: '2',
      species: 'fl',
      city: 'montreal',
      status: 'healthy',
      latitude: 45.415066,
      longitude: -73.478198
    }, {
      id: '3',
      species: 'fn',
      city: 'vanc',
      status: 'diseased',
      latitude: 45.595066,
      longitude: -73.378198
    },{
      id: '4',
      species: 'fn',
      city: 'oman',
      status: 'to_be_cut',
      latitude: 45.695066,
      longitude: -73.878198
      //lastone
    }, {
      id: '5',
      species: 'fm',
      city: 'toronto',
      status: 'cut_down',
      latitude: 45.402986,
      longitude: -73.586569
    }, {
      id: '6',
      species: 'fm',
      city: 'vanc',
      status: 'healthy',
      latitude: 45.398233,
      longitude: -73.784037
    }, {
      id: '7',
      species: 'fx',
      city: 'montreal',
      status: 'to_be_cut',
      latitude: 45.695066,
      longitude: -73.578198
    }],
    front: 'ALDhk',
    newLong: '',
    newIDS: [],
    listTreesError: '',
    mapName: this.name + "-map",
    markerCoordinates: [{
      latitude: 45.502986,
      longitude: -73.586569
    }, {
      latitude: 45.498233,
      longitude: -73.584037
    }],
    filterTrees: [],
    bounds: null,
    markers: [],
    rectangle: '',
    rectTrees: []
  }
},
created: function () {

  AXIOS.get(`/trees`)
  .then(response => {
    // JSON responses are automatically parsed.
    this.requestTrees = response.data;
  })
  .catch(e => {
    this.listTreesError = e;
  });
},
methods: {
  updateTrees: function(rectTrees, status) {
			  console.log("updateTree called")
			  var treeIDs= [1,2,3,4];
			  status = "HEALTHY";
//				  rectTrees.forEach((tree) =>{
//					  treeIDs.push(tree.id);
//				  });

			  AXIOS.post('/updateTrees/?treeIDs=' + treeIDs + '&status='+ status, {}, {})
			  .then(response => {
				  //this.findAllTrees();
				  this.trees= response.data;
		          this.errorMessage = ''
		        console.log("came in therehr")
			  }).catch(e => {
				  var errorMsg = e.response.data.message
		          console.log(errorMsg)
		          this.errorMessage = errorMsg
			  })

	},
  findAllTrees: function(){
    console.log("yea i was here");
    AXIOS.get(`/trees`)
    .then(response => {
      console.log("inside axios");
      // JSON responses are automatically parsed.
      this.trees = response.data
    })
    .catch(e => {
      this.listTreesError = e;
    });
  },

  pins : function(tree){
    var image;
    if(tree.status == 'diseased'){
      image = '../static/forest_yellow.png'
    }else if(tree.status == 'to_be_cut'){
      image = '../static/forest_purple.png'
    }else if(tree.status == 'cut_down'){
      image = '../static/forest_red.png'
    }else{
      image = '../static/forest_green.png'
    }

    var id, sp, ci, st, la, lo;
    id = tree.id;
    sp = tree.species;
    ci = tree.city;
    st = tree.status;
    la = tree.latitude;
    lo = tree.longitude;

    var contentString = '<table class="mapWindow">'  +
    '<tr>' +
    '<td>ID</td>' +
    '<td>'+id+'</td>' +
    '</tr>' +
    '<tr>'+
    '<td>Species</td>'+
    '<td>' + sp + '</td>' +
    '</tr>' +
    '<tr>' +
    '<td>City</td>' +
    '<td>' + ci + '</td>' +
    '</tr>' +
    '<tr>' +
    '<td>Status</td>' +
    '<td>' + st + '</td>' +
    '</tr>' +
    '<tr>' +
    '<td>Longitude</td>' +
    '<td>' + lo + '</td>' +
    '</tr>' +
    '<tr>' +
    '<td>Latitude</td>' +
    '<td>' + '      '+la + '</td>' +
    '</tr>' +
    '</table>'+
    '<button>BUTT</button>'

    var infowindow = new google.maps.InfoWindow({
      content: contentString
    });

    const position = new google.maps.LatLng(tree.latitude, tree.longitude);
    const marker = new google.maps.Marker({
      position,
      map: this.map,
      icon: image,
    });

    marker.addListener('click', function() {
      infowindow.open(this.map, marker);
    });

    this.markers.push(marker)
    this.map.fitBounds(this.bounds.extend(position))
  },
  emptyList : function() {
    this.ids = [];
  },
  testM : function() {
    console.log("testing mounted")
  },
  printThis: function(){
    for(var k = 0; k<this.markers.length; k++){
      this.markers[k].setMap(null);
    }
    this.markers = [];
    for(var i = 0; i<this.trees.length; i++){
      if(this.ids.includes(this.trees[i].city) || this.ids.includes(this.trees[i].species) || this.ids.includes(this.trees[i].status)){
        this.pins(this.trees[i]);
      }
    }
  },
  listTrees: function(){
    this.filterTrees = [];
    this.trees.forEach((tree) => {
      if(this.ids.includes(tree.city) || this.ids.includes(tree.species) || this.ids.includes(tree.status)){
        this.filterTrees.push(tree);
      }
    });
  },
  clear: function(){
    this.filterTrees = [];
    this.ids = [];
  },
  forecasteTrees: function (){
    this.rectTrees = [];
    var ne = this.rectangle.getBounds().getNorthEast();
    var sw = this.rectangle.getBounds().getSouthWest();

    this.filterTrees.forEach((tree) => {
      if(tree.latitude < ne.lat() && tree.latitude > sw.lat() && tree.longitude < ne.lng() && tree.longitude > sw.lng()){
        console.log(tree.id)
        this.rectTrees.push(tree);
      }

    });
  },
  updateStats : function(){
      var healthy = 0;
      var diseased = 0;
      var to_be_cut = 0;
      var cut_down = 0;
      this.filterTrees.forEach((tree) => {
        if(tree.status == 'healthy'){healthy ++}
        if(tree.status == 'diseased'){diseased ++}
        if(tree.status == 'to_be_cut'){to_be_cut ++}
        if(tree.status == 'cut_down'){cut_down ++}
      });
      this.stats = [["healthy", healthy], ["diseased", diseased], ["to_be_cut", to_be_cut], ["cut_down", cut_down]]
    },
    updateCities : function(){
      var toronto = 0;
      var montreal = 0;
      var vanc = 0;
      var oman = 0;
      this.filterTrees.forEach((tree) => {
        if(tree.city == 'toronto'){toronto ++}
        if(tree.city == 'montreal'){montreal ++}
        if(tree.city == 'vanc'){vanc ++}
        if(tree.city == 'oman'){oman ++}
      });
      this.citiesStats = [["toronto", toronto], ["montreal", montreal], ["vanc", vanc], ["oman", oman]]

    }
},

computed: {
  cities () {
    return [...new Set(this.trees.map(p => p.city))]
  },
  speciess (){
    return [...new Set(this.trees.map(p => p.species))]
  },
  statuses (){
    return [...new Set(this.trees.map(p => p.status))]
  }
},
watch: {
  ids : function(val){
    this.printThis();
    this.listTrees();
    this.updateStats();
    this.updateCities();
  }
},
mounted: function () {
  this.filterTrees = this.trees;
  this.updateStats();
  this.updateCities();
  this.newIDs = this.ids;
  this.bounds = new google.maps.LatLngBounds();
  const element = document.getElementById(this.mapName)
  const mapCentre = this.markerCoordinates[0]


  const options = {
    center: new google.maps.LatLng(mapCentre.latitude, mapCentre.longitude),
    styles: [
      {
        "elementType": "geometry",
        "stylers": [
          {
            "color": "#1d2c4d"
          }
        ]
      },
      {
        "elementType": "labels",
        "stylers": [
          {
            "visibility": "off"
          }
        ]
      },
      {
        "elementType": "labels.text.fill",
        "stylers": [
          {
            "color": "#8ec3b9"
          }
        ]
      },
      {
        "elementType": "labels.text.stroke",
        "stylers": [
          {
            "color": "#1a3646"
          }
        ]
      },
      {
        "featureType": "administrative.country",
        "elementType": "geometry.stroke",
        "stylers": [
          {
            "color": "#4b6878"
          }
        ]
      },
      {
        "featureType": "administrative.land_parcel",
        "stylers": [
          {
            "visibility": "off"
          }
        ]
      },
      {
        "featureType": "administrative.land_parcel",
        "elementType": "labels.text.fill",
        "stylers": [
          {
            "color": "#64779e"
          }
        ]
      },
      {
        "featureType": "administrative.neighborhood",
        "stylers": [
          {
            "visibility": "off"
          }
        ]
      },
      {
        "featureType": "administrative.province",
        "elementType": "geometry.stroke",
        "stylers": [
          {
            "color": "#4b6878"
          }
        ]
      },
      {
        "featureType": "landscape.man_made",
        "elementType": "geometry.stroke",
        "stylers": [
          {
            "color": "#334e87"
          }
        ]
      },
      {
        "featureType": "landscape.natural",
        "elementType": "geometry",
        "stylers": [
          {
            "color": "#023e58"
          }
        ]
      },
      {
        "featureType": "poi",
        "elementType": "geometry",
        "stylers": [
          {
            "color": "#283d6a"
          }
        ]
      },
      {
        "featureType": "poi",
        "elementType": "labels.text.fill",
        "stylers": [
          {
            "color": "#6f9ba5"
          }
        ]
      },
      {
        "featureType": "poi",
        "elementType": "labels.text.stroke",
        "stylers": [
          {
            "color": "#1d2c4d"
          }
        ]
      },
      {
        "featureType": "poi.park",
        "elementType": "geometry.fill",
        "stylers": [
          {
            "color": "#023e58"
          }
        ]
      },
      {
        "featureType": "poi.park",
        "elementType": "labels.text.fill",
        "stylers": [
          {
            "color": "#3C7680"
          }
        ]
      },
      {
        "featureType": "road",
        "elementType": "geometry",
        "stylers": [
          {
            "color": "#304a7d"
          }
        ]
      },
      {
        "featureType": "road",
        "elementType": "labels.text.fill",
        "stylers": [
          {
            "color": "#98a5be"
          }
        ]
      },
      {
        "featureType": "road",
        "elementType": "labels.text.stroke",
        "stylers": [
          {
            "color": "#1d2c4d"
          }
        ]
      },
      {
        "featureType": "road.highway",
        "elementType": "geometry",
        "stylers": [
          {
            "color": "#2c6675"
          }
        ]
      },
      {
        "featureType": "road.highway",
        "elementType": "geometry.stroke",
        "stylers": [
          {
            "color": "#255763"
          }
        ]
      },
      {
        "featureType": "road.highway",
        "elementType": "labels.text.fill",
        "stylers": [
          {
            "color": "#b0d5ce"
          }
        ]
      },
      {
        "featureType": "road.highway",
        "elementType": "labels.text.stroke",
        "stylers": [
          {
            "color": "#023e58"
          }
        ]
      },
      {
        "featureType": "transit",
        "elementType": "labels.text.fill",
        "stylers": [
          {
            "color": "#98a5be"
          }
        ]
      },
      {
        "featureType": "transit",
        "elementType": "labels.text.stroke",
        "stylers": [
          {
            "color": "#1d2c4d"
          }
        ]
      },
      {
        "featureType": "transit.line",
        "elementType": "geometry.fill",
        "stylers": [
          {
            "color": "#283d6a"
          }
        ]
      },
      {
        "featureType": "transit.station",
        "elementType": "geometry",
        "stylers": [
          {
            "color": "#3a4762"
          }
        ]
      },
      {
        "featureType": "water",
        "elementType": "geometry",
        "stylers": [
          {
            "color": "#0e1626"
          }
        ]
      },
      {
        "featureType": "water",
        "elementType": "labels.text.fill",
        "stylers": [
          {
            "color": "#4e6d70"
          }
        ]
      }
    ]
  }
  this.map = new google.maps.Map(element, options);

  this.rectangle = new google.maps.Rectangle({
    bounds: this.rectBounds,
    editable: true,
    draggable: true
  });
  this.rectangle.setMap(this.map);

  this.filterTrees.forEach((tree) => {
    var image;
    if(tree.status == 'diseased'){
      image = '../static/forest_yello.png'
    }else if(tree.status == 'to_be_cut'){
      image = '../static/forest_purple.png'
    }else if(tree.status == 'cut_down'){
      image = '../static/forest_red.png'
    }else{
      image = '../static/forest_green.png'
    }

    var id, sp, ci, st, la, lo;
    id = tree.id;
    sp = tree.species;
    ci = tree.city;
    st = tree.status;
    la = tree.latitude;
    lo = tree.longitude;

    var contentString = '<table class="mapWindow">'  +
    '<tr>' +
    '<td>ID</td>' +
    '<td>'+id+'</td>' +
    '</tr>' +
    '<tr>'+
    '<td>Species</td>'+
    '<td>' + sp + '</td>' +
    '</tr>' +
    '<tr>' +
    '<td>City</td>' +
    '<td>' + ci + '</td>' +
    '</tr>' +
    '<tr>' +
    '<td>Status</td>' +
    '<td>' + st + '</td>' +
    '</tr>' +
    '<tr>' +
    '<td>Longitude</td>' +
    '<td>' + lo + '</td>' +
    '</tr>' +
    '<tr>' +
    '<td>Latitude</td>' +
    '<td>' + '      '+la + '</td>' +
    '</tr>' +
    '</table>'+
    '<button>BUTT</button>'

    var infowindow = new google.maps.InfoWindow({
      content: contentString
    });

    const position = new google.maps.LatLng(tree.latitude, tree.longitude);
    const marker = new google.maps.Marker({
      position,
      map: this.map,
      icon: image
    });

    marker.addListener('click', function() {
      infowindow.open(this.map, marker);
    });

    var tempLocation = this.rLocation;
    this.map.addListener('rightclick', function(e) {
      rLocation = e.latLng.lat();
    });


    this.markers.push(marker)
    this.map.fitBounds(this.bounds.extend(position))
  });
}
//...
}
