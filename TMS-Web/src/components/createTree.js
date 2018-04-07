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
      errorMessage: '',
      newSpecies: [],
      trees:[],
      treeHeight: '',
      treeDiameter: '',
      datePlanted: '2018-01-01',
      xCoord: '',
      yCoord: '',
      description: '',
      treeStatus: '',
      locationType: null,
      treeStatus: null,
      treeSpecies: null,
      treeMunicipality: null,
      species: [],
      speciesSelection: [],
      municipalities: [],
      municipalitiesSelection: [],
      locationsSelection: [
        { value: null, text: 'Location', disabled: true },
        { value: 'Residential', text: 'Residential' },
        { value: 'Institutional', text: 'Institutional' },
        { value: 'Municipal', text: 'Municipal' }
      ],
      statusSelection: [
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
		  this.speciesSelection.push({ value: null, text: 'Species', disabled: true })
		  for (var i=0; i<this.species.length;i++) {
			  var name = this.species[i].name
	    	  this.speciesSelection.push({ value: name, text: name })
	      }
		  }).catch(e => {
			  var errorMsg = e.response.data.message
	          console.log(errorMsg)
	          this.errorMessage = errorMsg
	      })
	  AXIOS.get('/municipalities/').then(response => {
		  this.municipalities = response.data
		  this.municipalitiesSelection.push({value: null, text: 'Municipalities', disabled: true})
	      for (var i=0; i<this.municipalities.length;i++) {
			  var name = this.municipalities[i].name
	    	  this.municipalitiesSelection.push( { value: name, text: name })
	      }
		  }).catch(e => {
			  var errorMsg = e.response.data.message
	          console.log(errorMsg)
	          this.errorMessage = errorMsg
		  })
	  AXIOS.get('/trees/').then(response => {
		  this.trees = response.data
		  }).catch(e => {
			  var errorMsg = e.response.data.message
	          console.log(errorMsg)
	          this.errorMessage = errorMsg
		  })
  },
  
  methods: {
	  createTree: function (height, diameter, datePlanted, x, y, description, species, municipality) {
		  AXIOS.post('/trees/?height=' + height + '&diameter=' + diameter + '&datePlanted=' + datePlanted + '&x=' + x
				  + '&y=' + y +'&description=' + description + '&species=' + species + '&municipality=' + municipality, {}, {})
		  .then(response => {
			  this.trees.push(response.data)
	          this.treeHeight = ''
	          this.treeDiameter = ''
	          this.datePlanted = '2018-01-01'
	          this.xCoord = ''
	          this.yCoord = ''
	          this.description = ''
	          this.treeSpecies = null
	          this.treeMunicipality = null
	          this.errorMessage = ''
		  }).catch(e => {
			  var errorMsg = e.response.data.message
	          console.log(errorMsg)
	          this.errorMessage = errorMsg
		  })
	  },
	  findAllTrees: function() {
		  AXIOS.get('/trees').then(response => {
			  this.trees = response.data
		  }).catch(e => {
			  var errorMsg = e.response.data.message
	          console.log(errorMsg)
	          this.errorMessage = errorMsg
		  })
	  },
	  createSpecies: function(speciesName, carbonCon, oxygenProd) {
		  AXIOS.post('/species/' + speciesName + '?&carbonConsumption=' + carbonCon + '&oxygenProduction=' + oxygenProd)
		  .then(response => {
			  this.species.push(response.data)
			  this.newSpecies.push(response.data)
		  }).catch(e => {
			  var errorMsg = e.response.data.message
	          console.log(errorMsg)
	          this.errorMessage = errorMsg
		  })
	  }
	}
  
  //...
}
