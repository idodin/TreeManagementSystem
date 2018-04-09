import axios from 'axios'
var config = require('../../config')

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
      userType: '',
      newTree: '',
      newLat: '',
      treeID: 'oldONe',
      ids: [],
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
        status: 'to be cut',
        latitude: 45.695066,
        longitude: -73.878198
        //lastone
      }, {
        id: '5',
        species: 'fm',
        city: 'toronto',
        status: 'cut down',
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
        city: 'hamra',
        status: 'to be cut',
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
      map: null,
      filterTrees: [],
      bounds: null,
      markers: []
    }
  },

  mounted: function () {
    this.filterTrees = this.trees;
    this.newIDs = this.ids;
    this.bounds = new google.maps.LatLngBounds();
    const element = document.getElementById(this.mapName)
    const mapCentre = this.markerCoordinates[0]
    const options = {
      center: new google.maps.LatLng(mapCentre.latitude, mapCentre.longitude)
    }
    this.map = new google.maps.Map(element, options);
    this.filterTrees.forEach((coord) => {
      const position = new google.maps.LatLng(coord.latitude, coord.longitude);
      const marker = new google.maps.Marker({
        position,
        map: this.map
      });
      this.markers.push(marker)
      this.map.fitBounds(this.bounds.extend(position))
    });
  },

  created: function () {

	 //  AXIOS.get(`/trees`)
	 // .then(response => {
		// // JSON responses are automatically parsed.
		// this.trees = response.data
	 // })
		// .catch(e => {
		// 	this.listTreesError = e;
		// });
	 },
	methods: {
		findAllTrees: function(){
      console.log("yea i was here");
			AXIOS.get(`/trees`)
			.then(response => {
				// JSON responses are automatically parsed.
				this.trees = response.data
			})
			.catch(e => {
				this.listTreesError = e;
			});
		},

    pins : function(lat, long){
      const position = new google.maps.LatLng(lat, long);
      const marker = new google.maps.Marker({
        position,
        map: this.map,
      });
      this.markers.push(marker)
      this.map.fitBounds(this.bounds.extend(position))
    },
    emptyList : function() {
      this.ids = [];
    },
    printThis: function(){
      console.log("this is for template change")
      for(var k = 0; k<this.markers.length; k++){
        this.markers[k].setMap(null);
      }

      for(var i = 0; i<this.trees.length; i++){
        if(this.ids.includes(this.trees[i].city)){
          this.pins(this.trees[i].latitude, this.trees[i].longitude);
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
    },
    startTrees (){
      return cities.concat(species)
    }
  },
  watch: {
    ids : function(val){
      this.printThis();
      this.listTrees();
    }
  }
  //...
}
