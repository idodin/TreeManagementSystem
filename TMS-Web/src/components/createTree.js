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
      treeHeight: '',
      treeDiameter: '',
      datePlanted: '2018-01-01',
      xCoord: '',
      yCoord: '',
      description: '',
      treeStatus: '',
      municipality: '',
      locationType: null,
      treeSpecies: '',
      locations: [
        { value: null, text: 'Location Type', disabled: true },
        { value: 'Residential', text: 'Residential' },
        { value: 'Institutional', text: 'Institutional' },
        { value: 'Municipal', text: 'Municipal' }
      ],
    }
  },
  methods: {
	  createTree: function(height, diameter, datePlanted, x, y, description) {
			      AXIOS.post('/trees/?height=' + height + '&diameter=' + diameter + '&date=' + datePlanted + '&x' + x + '&y' + y +'&description' + description + {}, {})
			        .then(response => {
			          // JSON responses are automatically parsed.
			          this.trees.push(response.data)
			          this.treeHeight = ''
			          this.treeDiameter = ''
			          this.datePlanted = ''  
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

  }
  //...
}
