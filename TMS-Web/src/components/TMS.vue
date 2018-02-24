<template>
<div id="treemanagement">
    <h2>Tree</h2>
        <button id="updateButton">Update Trees</button>
        </br></br></br>
      <div id="tableDiv">
      <table border="1" align="center">
      <tr v-for="tree in trees">
          <td>{{ tree.id }}</td>
          <td>{{ tree.species.name }}</td>
      </tr>
    </table>
    </div></br>
    <p>
    <span v-if="listTreesError" style="color:red">Error: {{listTreesError}} </span>
    </p>
  </div>
</template>

<script src="./management.js">

</script>

<style>
#treemanagement {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #90e6c0;
    background: #204723;
  }
#updateButton{
	margin: 0 auto;
}
#tableDiv{
	overflow-y: auto;
	height: 150px;
	display: inline-block;
}
</style>