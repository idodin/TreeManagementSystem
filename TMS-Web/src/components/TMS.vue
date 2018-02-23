<template>
<div id="treemanagement">
    <h2>Tree</h2>
    <table>
      <tr v-for="tree in trees">
          <td>{{ tree.id }}</td>
          <td>{{ tree.species.name }}</td>
      </tr>
      <tr>
          <td>
              <button>Update Trees</button>
          </td>
      </tr>
    </table>
    <p>
      <span style="color:red">Error: Message text comes here</span>
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
</style>