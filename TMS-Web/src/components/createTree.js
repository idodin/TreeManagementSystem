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
      datePlanted: '01-01-2018',
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

  }
  //...
}
