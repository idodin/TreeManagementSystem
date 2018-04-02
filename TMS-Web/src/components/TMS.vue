<template>
  <div id="treemanagement">
    <div class="topnav">
      <h2 style="padding-top: 7px; padding-left: 15px; float: left; font-weight: bolder"> ♧ TreePLE ♧</h2>
      <a href="#about">About</a>
      <a href="#news">News</a>
      <a href="#contact">Contact</a>
      <a class="active" href="/#/">Home</a>
    </div>
  </br>

  <div id="createTree">

  </div>
  <button id="updateButton" @click="findAllTrees()">List absolutely all Trees</button>
  <div id="tableDiv">
    <table border="1" align="center">
      <tr v-for="tree in trees">
        <td>{{ tree.id }}</td>
        <td>{{ tree.diameter }}</td>
      </tr>
    </table>
  </div></br>
  <p>
    <!-- <span v-if="listTreesError" style="color:red">Error: {{listTreesError}} </span> -->
  </p>

  <input type="text" v-model="newLat" placeholder="New Lat">
  <input type="text" v-model="newLong" placeholder="New Long">
  <p>{{newLat}}, {{newLong}}</p>
  <button id="updateButton" @click="pins(newLat, newLong)">Locate Trees</button>
  <div class="google-map" v-bind:id="mapName"></div>
</div>
</template>

<script src="./management.js"></script>

<style scoped>
#treemanagement {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  color: #90e6c0;
  background: #f4f9f4;
  position: absolute; /*position: fixed;*/
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;

}
#updateButton{
  margin: 0 auto;
}
#tableDiv{
  overflow-y: auto;
  height: 150px;
  display: inline-block;
}
h2{
  color: white;
}
.google-map {
  width: 800px;
  height: 600px;
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

#createTree{
  margin: auto;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  width: 400px;
  height: 200px;
  background: #f7f9fc;
}
</style>
