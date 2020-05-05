import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'https://' + config.dev.host 
var backendUrl = 'https://' + config.dev.backendHost 

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  nameNew: 'tms',
  data () {
    return {
      loggedUser: currUser.userName,
      go: loggedin,
      errorMessage: '',
      newSpecies: [],
      trees:[],
      newTree: [],
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
      municipalitiesSelection: [
        { value: null, text: 'Municipality', disabled: true },
        { value: 'Montreal_Est', text: 'Montreal_Est' },
        { value: 'Montreal_Ouest', text: 'Montreal_Ouest' },
        { value: 'Montreal', text: 'Montreal' },
        { value: 'Westmount', text: 'Westmount' },
        { value: 'Hampstead', text: 'Hampstead' },
        { value: 'Cte_St_Luc', text: 'Cte_St_Luc' },
        { value: 'Dorval', text: 'Dorval' },
        { value: 'Point_Clair', text: 'Point_Clair' },
        { value: 'Ile_Dorval', text: 'Ile_Dorval' },
        { value: 'Dollard', text: 'Dollard' },
        { value: 'Kirkland', text: 'Kirkland' },
        { value: 'Beaconsfield', text: 'Beaconsfield' },
        { value: 'Baie_dUrfe', text: 'Baie_dUrfe' },
        { value: 'Ste_Anne', text: 'Ste_Anne' }
      ],
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
        { value: 'Diseased', text: 'Diseased' },
        { value: 'toBeCut', text: 'To Be Cut' }
      ],
      speciesName: '',
      speciesCarbon: '',
      speciesOxygen: '',
      municipalityName: '',
      municipalityId: '',
      forecastNum: [],
      treeIds: ''
    }
  },

  created: function () {
	  AXIOS.get('/species/').then(response => {
		  this.species = response.data
		  this.speciesSelection.push({ value: null, text: 'Species', disabled: true },
				  {value: 'other', text: 'other'})
		  for (var i=0; i<this.species.length;i++) {
			  var name = this.species[i].name
	    	  this.speciesSelection.push({ value: name, text: name })
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
	  createTree: function (height, diameter, datePlanted, x, y, description, location, status, species, municipality) {
		  console.log("createTree called")
		  console.log(this.loggedUser)
		  AXIOS.post('/trees/?height=' + height + '&diameter=' + diameter + '&datePlanted=' + datePlanted + '&x=' + x
				  + '&y=' + y +'&description=' + description + '&location=' + location + '&status=' + status + '&species=' + species
				  + '&municipality=' + municipality + '&loggedUser=' + this.loggedUser, {}, {})
		  .then(response => {
			  this.trees.push(response.data)
			  this.newTree.push(response.data)
	          this.treeHeight = ''
	          this.treeDiameter = ''
	          this.datePlanted = '2018-01-01'
	          this.xCoord = ''
	          this.yCoord = ''
	          this.description = ''
	          this.treeSpecies = null
	          this.treeStatus = null
	          this.locationType = null
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
		  AXIOS.post('/species/' + speciesName + '?&carbonConsumption=' + carbonCon + '&oxygenProduction=' + oxygenProd, {}, {})
		  .then(response => {
			  var sp = response.data
			  this.species.push(sp)
			  this.speciesSelection.push({value: sp.name, text: sp.name})
			  this.speciesName = ''
			  this.speciesCarbon = ''
			  this.speciesOxygen = ''
		  }).catch(e => {
			  var errorMsg = e.response.data.message
	          console.log(errorMsg)
	          this.errorMessage = errorMsg
		  })
	  },
	  createForecast: function(statusChange) {
		  for (var i = 0; i< this.trees.length; i++) {
			  this.treeIds += '&treeIds=' + this.trees[i].id
		  }
		  console.log(this.treeIds)
		  AXIOS.get('/forecasts/?' + this.treeIds + '&status=' + statusChange, {}, {}).then(response => {
			  this.forecastNum = response.data
		  }).catch(e => {
			  var errorMsg = e.response.data.message
	          console.log(errorMsg)
	          this.errorMessage = errorMsg
		  })
	  }
	}

  //...
}
