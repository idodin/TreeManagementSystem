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
      trees: [],
      newTree: '',
      newLat: '',
      front: 'ALDhk',
      newLong: '',
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
    register : function(cityName) {
      // Declare all variables
      var i, tabcontent, tablinks;

      // Get all elements with class="tabcontent" and hide them
      tabcontent = document.getElementsByClassName("tabcontent");
      for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
      }

      // Get all elements with class="tablinks" and remove the class "active"
      tablinks = document.getElementsByClassName("tablinks");
      for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
      }

      // Show the current tab, and add an "active" class to the button that opened the tab
      document.getElementById(cityName).style.display = "block";
      evt.currentTarget.className += " active";
    }
	}
  //...
}
