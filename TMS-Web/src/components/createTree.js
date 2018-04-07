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
      yCoord: '',
      description: '',
      treeStatus: '',
      municipality: '',
      locationType: null,
      Status: null,
      treeSpecies: '',
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
  methods: {
	  createTree: function(height, diameter, datePlanted, x, y, description) {
	      AXIOS.post('/trees/?height=' + height + '&diameter=' + diameter + '&datePlanted=' + datePlanted + '&x=' + x + '&y=' + y +'&description=' + description + {}, {})
	        .then(response => {
	          // JSON responses are automatically parsed.
	          //this.newTrees.push(response.data)
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
		    	}).catch(e => {
			      this.errorTree = e
			    })
		    },
		}
  //...
}
