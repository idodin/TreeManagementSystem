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
      treeID: '',
      ids: [],
      trees: [{
        id: '1',
        species: 'fl',
        city: 'toronto'
      }, {
        id: '2',
        species: 'fl',
        city: 'montreal'
      }, {
        id: '3',
        species: 'fn',
        city: 'vanc'
      },{
        id: '4',
        species: 'fn',
        city: 'montreal'
      }, {
        id: '5',
        species: 'fm',
        city: 'toronto'
      }, {
        id: '6',
        species: 'fm',
        city: 'vanc'
      }, {
        id: '7',
        species: 'fx',
        city: 'vanc'
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
      }, {
        latitude: 45.495066,
        longitude: -73.578198
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
        // icon: iconBase + 'if_firefox_png_148659.png'
      });
      this.markers.push(marker)
      this.map.fitBounds(this.bounds.extend(position))
    },
    emptyList : function() {
      this.ids = [];
    }
	},

  computed: {
    noDupList: function(){
      return this.ids.filter(function(item){
        return 1 === 1;
      });
    }
  }
  //...
}
