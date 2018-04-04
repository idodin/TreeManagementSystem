<template>
  <div id="treemanagement">
    <div class="topnav">
      <h2 style="padding-top: 7px; padding-left: 15px; float: left; font-weight: bolder"> ♧ TreePLE ♧</h2>
      <a href="#about">Forecast</a>
      <a class="active" href="#news">Visualizer</a>
      <a href="/#/create">Create</a>
      <a href="/#/">Home</a>
    </div>
    <br />
    <h6>List of trees</h6>
    <h5>{{myMaps}}</h5>



        <b-card id="card">
          <b-tabs>
            <b-tab title="first" active>
              <div>
                <b-form-group label="Button style checkboxes">
                  <b-form-checkbox-group buttons v-model="ids" name="butons1" :options="cities">
                  </b-form-checkbox-group>
                </b-form-group>
              </div>
            </b-tab>
            <b-tab title="second" >
              <br>I'm the second tab content
            </b-tab>
            <b-tab title="disabled">
              <br>Disabled tab!
            </b-tab>
          </b-tabs>
        </b-card>


    <br />
    <div id="list">
      <table>
        <tr v-for="tree in trees">
          <template v-if="ids.includes(tree.city)">
            <td>{{ tree.id }}</td>
            <td>{{ tree.species }}</td>
            <td>{{ tree.city }}</td>
          </template>
        </tr>
      </table>
    </div>
    <template v-for="tree in cities">
      <input type="checkbox" id="tree" :value="tree" v-model="ids">
      <label for="tree">{{tree}}</label>
    </template>
    <input type="text" v-model="newTree" />
    <button @click="addCity(newTree)">Add Tree</button>
    <h4>{{cities}}</h4>
    <button @click="emptyList()">Enmpty list</button>
    <br />
    <span v-if="ids.length > 0">Checked names: {{ ids }}</span>
    <br />

    <br />
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
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  width: 850px;
  height: 215px;
  background: #f7f9fc;
  border-radius: 0.5em;
}

#filters{
  margin: auto;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  width: 850px;
  height: 215px;
  background: #f7f9fc;
  border-radius: 0.5em;
}

#card{
  margin: auto;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  width: 850px;
  background: #f7f9fc;
  border-radius: 0.5em;
}

#oneButs{
  background: #4f4f4f;
}

h2{
  color: white;
}
.google-map {
  width: 1000px;
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

</style>
