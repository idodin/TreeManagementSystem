<template>
  <div id="hello">
    <div class="topnav">
      <h2 style="padding-top: 7px; padding-left: 15px; float: left; font-weight: bolder"> ♧ TreePLE ♧</h2>
      <a href="../static/app-release-unsigned.apk" download="APK">APK</a>
      <a href="#about">Hello</a>
      <a href="/#/home">Visualizer</a>
      <a href="/#/home/" >Create</a>
      <a class="active" href="/#/home">Home</a>
    </div>
    <br /><br /><br />
    <h3>Someone's sitting in the shade today because...</h3>
    <h3>TreePLE kept the trees alive ♧</h3>
    <br /><br /><br />
	<span> {{errorMessage}} </span>
    <div id="login">
      <div class="tab">
        <button id="def" class="tablinks" @click="register($event, 'one')">Login</button>
        <button class="tablinks" @click="register($event, 'register')">Register</button>
      </div>

      <!-- Tab content -->
      <div id="one" class="tabcontent" style="display:block">
        <input type="text" v-model="username" placeholder="Username">
        <input type="password" v-model="password" placeholder="Password">
        <br /> <br />
        <button id="regButton" @click="login(username, password)">login</button>
      </div>

      <div id="register" class="tabcontent">
        <input type="text" v-model="username" placeholder="Username">
        <input type="password" v-model="password" placeholder="Password">
        <input type="text" style="margin-left:-23px" v-model="inputToken" placeholder="Scientis's token">
        <button id="regButton" style="margin-top:25px" @click="createUser(username, password, inputToken)">register</button>
      </div>
    </div>


  </div>
</template>

<script src="./homePage.js"></script>

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
#login{
  margin: auto;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  width: 400px;
  height: 200px;
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
    background-color: #6496e5;
    color: white;
}
/* Style the tab */
.tab {
    overflow: hidden;
    border: 1px solid #ccc;
    background-color: #f1f1f1;
}
/* Style the buttons that are used to open the tab content */
.tab button {
    background-color: inherit;
    float: left;
    border: 10px;
    outline: none;
    cursor: pointer;
    padding: 14px 16px;
    transition: 0.3s;
    width: 50%;
}
#regButton{
  box-sizing: border-box;
	padding: 1em;
	border: 10px solid #0c0c0c;
	border-radius: 0.5em;
  background-color: #4d564d;
  float: right;
  color: white;
  border: 20px;
  outline: 20px;
  cursor: pointer;
  padding: 4px 6px;
  margin-right: 30px;
  transition: 0.3s;
  width: 30%;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
#trees{
  position: fixed;
  bottom: 0;
  width: 100%;
}
/* Change background color of buttons on hover */
.tab button:hover {
    background-color: #ddd;
}
/* Create an active/current tablink class */
.tab button.active {
    background-color: #ccc;
}
/* Style the tab content */
.tabcontent {
    display: none;
    padding: 6px 12px;
    border-top: none;
}
.tabcontent {
    animation: fadeEffect 1s; /* Fading effect takes 1 second */
}
/* Go from zero to full opacity */
@keyframes fadeEffect {
    from {opacity: 0;}
    to {opacity: 1;}
}
input {
	box-sizing: border-box;
	width: 160px;
  height: 20px;
	padding: 1em;
	border: 0.15em solid #808080;
	border-radius: 0.5em;
	text-align: center;
  margin-top: 20px;
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
