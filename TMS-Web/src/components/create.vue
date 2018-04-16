<template>
  <div id="hello" v-if="go==='true'">
    <div class="topnav">
      <h2 style="padding-top: 7px; padding-left: 15px; float: left; font-weight: bolder"> ♧ TreePLE ♧</h2>

      <a href="#about">Hello {{loggedUser}}</a>
      <a href="/#/app">Visualizer</a>
      <a class="active" href="/#/create/">Create</a>
      <a href="/#/home">Home</a>
    </div>
    <!-- <h1 style="padding-top: 7px; padding-left: 15px; font-size: 15pt; float: left ">hello {{loggedUser}}</h1> -->
    <br /><br /><br />
    <h3>add a tree ♧ </h3>
    <br /><br /><br /><br />
    <span> {{errorMessage}} </span>
<!-- <h5>{{ trees }}</h5> -->
    <div id="create">
          <input type="number" v-model="treeHeight" placeholder="Height">
          <input type="number" v-model="treeDiameter" placeholder="Diameter">
          <input type="text" v-model="xCoord" placeholder="longitude">
          <input type="text" v-model="yCoord" placeholder="latitude">
          <input type="text" v-model="description" placeholder="description"></br>

		  <b-form-select id="typeMenu" type="text" v-model="treeSpecies" :options="speciesSelection" class="mb-3"/>
		  <b-form-select id="typeMenu" v-model="treeMunicipality" :options="municipalitiesSelection" class="mb-3"/>
          <b-form-select id="typeMenu" v-model="locationType" :options="locationsSelection" class="mb-3"/>
          <b-form-select id="typeMenu" v-model="treeStatus" :options="statusSelection" class="mb-3"/>
          <input type="date" style="width:200px; height:40px; color:grey"v-model="datePlanted" placeholder="datePlanted">
          <br /><br />
          <b-button @click="createTree(treeHeight, treeDiameter, datePlanted, xCoord, yCoord, description, locationType, treeStatus, treeSpecies, treeMunicipality)">add tree</b-button>



    </div>
    <div v-if="treeSpecies === 'other'" id="createBox">
    	<input type="text" v-model="speciesName" placeholder="Name">
    	<input type="number" v-model="speciesCarbon" placeholder="Carbon Consumption">
    	<input type="number" v-model="speciesOxygen" placeholder="Oxygen Production">
    	<b-button @click="createSpecies(speciesName, speciesCarbon, speciesOxygen)">Add Species</b-button>
    </div>

  </div>
</template>

<script src="./createTree.js"></script>

<style scoped>
#hello {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  color: black;
  position: absolute; /*position: fixed;*/
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}
#create{
  margin: auto;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  width: 850px;
  height: 215px;
  background: #f7f9fc;
  border-radius: 0.5em;
}
h2{
  color: white;
}
ul {
  list-style-type: none;
  padding: 0;
  padding-top: 5px;
}
li {
  display: inline-block;
  margin: 0 10px;
  color: white;
  font-weight: bold;
  background-color: blue;
}
.topnav {
    background-color: #333;
    overflow: hidden;
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
/* Style the links inside the navigation bar */
.topnav a {
    float: right;
    color: #f2f2f2;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
    font-size: 17px;
}
/* Change the color of links on hover */
.topnav a:hover {
    background-color: #ddd;
    color: black;
}
/* Add a color to the active/current link */
.topnav a.active {
    background-color: #6496e5;;
    color: white;
}
#trees{
  position: fixed;
  bottom: 0;
  width: 100%;
}
#typeMenu{
  margin-top: 13px;
  box-sizing: border-box;
	width: 140px;
  height: 40px;
	border: 0.15em solid #808080;
	border-radius: 0.5em;
  text-align: center;
  color: grey;
}
input {
	box-sizing: border-box;
	width: 160px;
  	height: 40px;
	/*padding: 1em;*/
	border: 0.15em solid #808080;
	border-radius: 0.5em;
	text-align: center;
  	margin-top: 20px;
}
.createTable{
  margin-left: 20px;
}
::-webkit-input-placeholder {
   color: #808080;
   text-align: center;
}
:-moz-placeholder {
   color: #808080;
   text-align: center;
}
</style>
