<template>
  <div id="treemanagement">
    <div class="topnav">
      <h2 style="padding-top: 7px; padding-left: 15px; float: left; font-weight: bolder"> ♧ TreePLE ♧</h2>
      <a href="#about">Hello {{tmsUser}}</a>
      <a class="active" href="#news">Visualizer</a>
      <a href="/#/create">Create</a>
      <a href="/#/home">Home</a>
    </div>
    <br />
    <br />
    <div id="charts">
      <b-card id="card1">
        <h6>Data Charts</h6>
        <b-tabs>
          <b-tab title="Status" active>
            <pie-chart width="500px" height="250px":donut="true" :data="stats" :colors="['#2db563', '#a72cb5', '#ede438', '#d62f2f']"></pie-chart>
          </b-tab>
          <b-tab title="Municipality">
            <pie-chart width="500px" height="250px":donut="true" :data="citiesStats"></pie-chart>
          </b-tab>

        </b-tabs>
      </b-card>
      <b-card id="card">
        <h6>Filters</h6>
        <b-tabs>
          <b-tab title="City" active>
            <div id="stack">
              <b-form-group>
                <b-form-checkbox-group buttons v-model="ids" stacked :options="cities">
                </b-form-checkbox-group>
              </b-form-group>
            </div>
          </b-tab>
          <b-tab title="Species" >
            <div id="stack">
              <b-form-group>
                <b-form-checkbox-group buttons v-model="ids" stacked :options="speciess">
                </b-form-checkbox-group>
              </b-form-group>
            </div>
          </b-tab>
          <b-tab title="Status">
            <div id="stack">
              <b-form-group>
                <b-form-checkbox-group buttons v-model="ids" stacked :options="statuses">
                </b-form-checkbox-group>
              </b-form-group>
            </div>
          </b-tab>
        </b-tabs>
      </b-card>
    </div>
    <br /> <br />


      <br />
      <div id="list">
        <b-table striped hover :items="filterTrees" :fields="fields"></b-table>
      </div>
      <br />


    <div class="google-map" v-bind:id="mapName"></div>
    <button id="updateButton" style="width:250px; height:60px;"@click="forecasteTrees()">Highlight trees on the map then click here to select them</button>
   <br />
    <div v-if="rectTrees.length != 0">
      <span>IDs of Selected Trees: </span>
      <h6 v-for="tree in rectTrees">{{tree.id}}</h6>
    </div>
   <br /><br />
    <div id="forecast">
      <b-card id="cardForecast">
        <h3>forecasts</h3>
        <span>What if the selected trees were</span>
        <b-form-select style="width:120px; margin-top:17px;" id="typeMenu" v-model="forecastSelect" :options="statusForecast" class="mb-3"/>
        <b-tabs>
          <b-tab title="Carbon Consumption" active>
            <br />
            <h5> If the selected trees were {{forecastSelect}}</h5>
             <button id="updateButton" @click="createCarbonForecast(forecastSelect)">then...</button>
             <h5>carbon consumption changed by {{forecastNum}} %</h5>
          </b-tab>
          <b-tab title="Oxygen Production">
            <br />
            <h5> If the selected trees were {{forecastSelect}}</h5>
             <button id="updateButton" @click="createOxygenForecast(forecastSelect)">then...</button>
             <h5>oxygen production changed by {{forecastNum}} %</h5>
          </b-tab>
          <b-tab title="Biodiverstiy Index">
            <br />
            <h5> If the selected trees were {{forecastSelect}}</h5>
             <button id="updateButton" @click="createBioForecast(forecastSelect)">then...</button>
             <h5>biodiversity changed by {{forecastNum}} number of species</h5>
          </b-tab>
        </b-tabs>
      </b-card>
    </div>
    <br />

    <div id="updateTrees">
      <br />
      <h3>Update the status of selected trees</h3>
      <span>change the status of the selected trees to: </span>
      <b-form-select style="width:120px; margin-top:17px;" id="typeMenu" v-model="updateSelect" :options="statusForecast" class="mb-3"/>
      <br />
      <button id="updateButton" @click="updateTrees(updateSelect)">Update</button>
    </div>
  </div>
</template>

<script src="./management.js"></script>

<style scoped>
#treemanagement {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  color: black;
  position: absolute; /*position: fixed;*/
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

#forecast{
  display: inline-block;
  width: 860px;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  border-radius: 0.5em;
  background: #f7f9fc;
}

#cardForecast{
  background: #f7f9fc;
  height: 350px;
}

#updateTrees{
  display: inline-block;
  width: 860px;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  border-radius: 0.5em;
  background: #f7f9fc;
  margin-top: 30px;
  margin-bottom: 50px;
}

#charts{
  display: inline-block;
}

#stackForecast{
  float: right;
  margin-top: 20px;
}

#statusChart{
  padding-top: 20px;
  padding-bottom: 20px;
  float: left;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  background: #f7f9fc;
  border-radius: 0.5em;
}

#citiesChart{
  float: right;
  /*margin-right: 50px;*/
}
#list{
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  width: 860px;
  height: 215px;
  background: #f7f9fc;
  border-radius: 0.5em;
  float: center;
  display: inline-block;
  margin-bottom: 30px;
  overflow-y: auto;
  overflow-x: auto;
  position: relative;
}

#card{
  float: right;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  width: 300px;
  height: 360px;
  background: #f7f9fc;
  border-radius: 0.5em;
  margin-right: 32px;
  margin-left: 30px;
  margin-top: 24px;
  overflow-y: auto;
}

#card1{
  float: left;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  width: 530px;
  height: 360px;
  background: #f7f9fc;
  border-radius: 0.5em;
  margin-left: 50px;
  margin-top: 24px;
}

#stack{
  margin-top: 40px;
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

::-webkit-scrollbar {
  width: 0px;  /* remove scrollbar space */
  background: transparent;  /* optional: just make scrollbar invisible */
}
/* optional: show position indicator in red */
::-webkit-scrollbar-thumb {
  background: #FF0000;
}
#oneButs{
  background: #4f4f4f;
}

#updateButton{
  box-sizing: border-box;
	
	border: 10px solid #0c0c0c;
	border-radius: 0.5em;
  background-color: #4d564d;
  color: white;
  border: 10px;
  outline: 20px;
  cursor: pointer;
  margin-right: 30px;
  transition: 0.3s;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  margin-bottom: 10px;
  margin-top: 10px;
  width: 120px;
}

h2{
  color: white;
}
.google-map {
  width: 860px;
  height: 430px;
  margin: auto;
  background: gray;
  margin-bottom: 50px;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
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
  background-color: #6496e5;
  color: white;
}

.gm-style .gm-style-iw {
  background-color: #3C61AD !important;
  top: 0 !important;
  left: 0 !important;
  width: 100% !important;
  height: 100% !important;
  min-height: 120px !important;
  padding-top: 10px;
  display: block !important;
}

/*style the p tag*/
.gm-style .gm-style-iw #google-popup p{
  padding: 10px;
}


/*style the arrow*/
.gm-style div div div div div div div div {
  background-color: #3C61AD !important;
  padding: 0;
  margin: 0;
  padding: 0;
  top: 0;
  color: #fff;
  font-size: 16px;
}

/*style the link*/
.gm-style div div div div div div div div a {
  color: #f1f1f1;
  font-weight: bold;
}



</style>
