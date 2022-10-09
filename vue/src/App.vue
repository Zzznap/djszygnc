
<template>
  <!-- <div id="app">

    <nav>
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </nav>
    <router-view/>
  </div> -->
  <!-- <HomeView></HomeView> -->
  <div >
  <router-view/>

  </div>
  <!-- <AboutView></AboutView> -->
</template>

<script>
import HomeView from "./views/HomeView.vue";
import AboutView from "./views/AboutView.vue"
import UserView from "./views/UserView.vue";
export default {
    components:{
      HomeView,
      AboutView,
      UserView
    }
}
</script>
<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
