import axios from 'axios'
import Vue from 'vue';
import VueRouter from 'vue-router';
Vue.use(VueRouter);
var config = require('../../config')
window.currUser= '';
window.loggedin= 'false';
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {

  data () {
    return {
      errorMessage: '',
	  users: [],
	  nameNew: 'tms',
	  inputToken: '',
      msg: 'Front Page',
      username: "",
      password: "",
      isScientist: "not_scientist"
    }
  },
  mounted: function(){
    const d  = document.getElementById('def').click();
  },
  methods: {
    register : function(evt, cityName) {
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
    },

    createUser: function(username, password, inputToken){
    	var errorMsg
    	console.log("create user")
    	AXIOS.post('/users/' + username+ '?password='+password+ '&inputToken='+inputToken, {}, {})
    	.then(response => {
    		console.log("enter block")
			  this.users.push(response.data)
			  currUser= response.data
			  loggedin= 'true'
			  console.log(currUser)
	          this.errorMessage = errorMsg
	          window.location.href = "http://ecse312-9.ece.mcgill.ca:8087/#/create/";

		  }).catch(e => {
			  errorMsg = e.response.data.message
			  console.log("catch error")
	          console.log(errorMsg)
	          this.errorMessage = errorMsg
		  })
    },

    login: function(username, password){
    	var errorMsg
    	console.log("login user")
    	AXIOS.get('/user/' + username+ '?password='+password, {}, {})
    	.then(response => {
    		console.log("enter block")
			  currUser= response.data
			  loggedin= 'true'
			  console.log(currUser)
	          this.errorMessage = errorMsg
	          window.location.href = "http://ecse312-9.ece.mcgill.ca:8087/#/create/";

		  }).catch(e => {
			  console.log("catch error")
			  errorMsg = e.response.data.message
	          console.log(errorMsg)
	          this.errorMessage = errorMsg
		  })
    },
  }
  //...
}
