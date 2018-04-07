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
      newTrees:[],
      errorTree: '',
      foundTrees:[],
      treeHeight: '',
      treeDiameter: '',
      datePlanted: '2018-01-01',
      xCoord: '',
      spMethod: '',
      yCoord: '',
      description: '',
      treeStatus: '',
      municipality: '',
      locationType: null,
      Status: null,
      treeSpecies: '',
      species: [],
      speciesSelection: [],
      municipalities: [],
      municipalitiesSelection: [],
      locations: [
        { value: null, text: 'Location', disabled: true },
        { value: 'Residential', text: 'Residential' },
        { value: 'Institutional', text: 'Institutional' },
        { value: 'Municipal', text: 'Municipal' }
      ],
      statuses: [
        { value: null, text: 'Status', disabled: true },
        { value: 'Healthy', text: 'Healthy' },
        { value: 'Cut', text: 'Cut' },
        { value: 'Diseased', text: 'Diseased' }
      ],
    }
  },
  created: function () {
	    AXIOS.get('/species/').then(response => {
	      this.species = response.data
	      for (var i=0; i<this.species.length;i++) {
	    	  this.speciesSelection.push({ value: this.species[i], text: this.species[i].name })
	      }
	    }).catch(e => {
	      this.treeError = e
	    })
	    AXIOS.get('/municipalities/').then(response => {
	      this.municipalities = response.data
	      for (var i=0; i<this.municipalities.length;i++) {
	    	  this.municipalitiesSelection.push( { value: this.municipalities[i], text: this.municipalities[i].name })
	      }
	    }).catch(e => {
	      this.errorEvent = e
	    })
	  },
//	  var e = document.getElementById("speciesSelection");
//	  var s = e.options[e.selectedIndex].text;
  methods: {
	  createTree: function(height, diameter, datePlanted, x, y, description, treeSpecies) {
    	  var str = treeSpecies.name
	      AXIOS.post('/trees/?height=' + height + '&diameter=' + diameter + '&datePlanted=' + datePlanted + '&x=' + x + '&y=' + y +'&description=' + description + '&species=' + str + {}, {})
	        .then(response => {
	          // JSON responses are automatically parsed.
	          this.newTrees.push(response.data)
	          this.treeHeight = ''
	          this.treeDiameter = ''
	          this.datePlanted = '2018-01-01'
	          this.xCoord = ''
	          this.yCoord = ''
	          this.description = ''
	          this.treeSpecies = ''
	          this.newTree = ''
	          this.errorTree = ''
	        })
	        .catch(e => {
	          var errorMsg = e.response.data.message
	          console.log(errorMsg)
	          this.errorTree = errorMsg
	        })
	    },
		findAllTrees: function(){
		    AXIOS.get('/trees').then(response => {
		    	this.foundTrees = response.data
		    	console.log(this.foundTrees[0].height)
		    	}).catch(e => {
			      this.errorTree = e
			    })
		    
		    },
		}
  //...
}
