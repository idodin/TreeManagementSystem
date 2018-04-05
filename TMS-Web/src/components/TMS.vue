<template>
  <div id="treemanagement">
    <div class="topnav">
      <h2 style="padding-top: 7px; padding-left: 15px; float: left; font-weight: bolder"> ♧ TreePLE ♧</h2>
      <a href="#about">Forecast</a>
      <a class="active" href="#news">Visualizer</a>
      <a href="/#/create">Create</a>
      <a href="/#/home">Home</a>
    </div>
    <br />
    <br />
    <button @click="clear()">clear</button>
    <button @click="testRequest()">testTrees</button>
    <div style="display:inline">

      <b-card id="card">
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
      <br />
      <div id="list">
        <template>
          <b-table striped hover :items="filterTrees" :fields="fields"></b-table>
        </template>
        <!-- <table>

        <tr v-for="tree in trees">
        <template v-if="ids.includes(tree.city)">
        <td>{{ tree.id }}</td>
        <td>{{ tree.species }}</td>
        <td>{{ tree.city }}</td>
      </template>
    </tr>
  </table> -->
</div>
<br />
</div>
<div class="google-map" v-bind:id="mapName"></div>
</div>
</template>

<script src="./management.js"></script>

<style scoped>
#treemanagement {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  color: black;
  background: #f4f9f4;
  position: absolute; /*position: fixed;*/
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;

}

#list{
  margin: auto;
  float: left;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  width: 700px;
  height: 215px;
  background: #f7f9fc;
  border-radius: 0.5em;
  margin-left: 132px;
  margin-bottom: 30px;
  overflow-y: auto;
  overflow-x: auto;
}

#card{
  float: right;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  width: 280px;
  height: 217px;
  background: #f7f9fc;
  border-radius: 0.5em;
  margin-right: 132px;
  margin-top: 24px;
  overflow-y: auto;
}

#stack{
  height: 130px;
  overflow-y: auto;
  margin-top: 10px;
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

h2{
  color: white;
}
.google-map {
  width: 1000px;
  height: 400px;
  margin: auto;
  background: gray;
  margin-bottom: 100px;
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
  background-color: #4CAF50;
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
