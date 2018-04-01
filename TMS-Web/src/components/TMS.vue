<template>
<div id="treemanagement">
    <h2>Tree</h2>
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

<style>
#treemanagement {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  color: #90e6c0;
  background: #90b297;
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
}
</style>
