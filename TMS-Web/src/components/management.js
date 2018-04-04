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
      myMaps: [],
      cit: 'vanc',
      trees: [{
        id: '1',
        species: 'fl',
        city: 'toronto',
        latitude: 45.495619,
        longitude: -73.608229
      }, {
        id: '2',
        species: 'fl',
        city: 'montreal',
        latitude: 45.415066,
        longitude: -73.478198
      }, {
        id: '3',
        species: 'fn',
        city: 'vanc',
        latitude: 45.595066,
        longitude: -73.378198
      },{
        id: '4',
        species: 'fn',
        city: 'montreal',
        latitude: 45.695066,
        longitude: -73.878198
        //lastone
      }, {
        id: '5',
        species: 'fm',
        city: 'toronto',
        latitude: 45.402986,
        longitude: -73.586569
      }, {
        id: '6',
        species: 'fm',
        city: 'vanc',
        latitude: 45.398233,
        longitude: -73.784037
      }, {
        id: '7',
        species: 'fx',
        city: 'vanc',
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
      bounds: null,
      markers: []
    }
  },

  mounted: function () {
    this.newIDs = this.ids;
    this.bounds = new google.maps.LatLngBounds();
    const element = document.getElementById(this.mapName)
    const mapCentre = this.markerCoordinates[0]
    const options = {
      center: new google.maps.LatLng(mapCentre.latitude, mapCentre.longitude)
    }
    this.map = new google.maps.Map(element, options);
    this.markerCoordinates.forEach((coord) => {
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
	  AXIOS.get(`/trees`)
	 .then(response => {
		// JSON responses are automatically parsed.
		this.trees = response.data
	 })
		.catch(e => {
			this.listTreesError = e;
		});
	 },
	methods: {
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
    addCity: function(ci){
      this.trees.push({
        id: '82',
        species: 'fg',
        city: ci,
        latitude: 46.495619,
        longitude: -75.608229
      })
    }
	},

  computed: {
    cities () {
      return [...new Set(this.trees.map(p => p.city))]
    },
    maPins: function(){
      var idList = this.ids;
      return this.trees.filter(function(item){
        return idList.includes(item.id);
      })
    }
  },
  watch: {
    ids : function(val){
      this.printThis();
    }
  }
  //...
}
