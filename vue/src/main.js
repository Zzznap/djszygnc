import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import tinymce from 'tinymce'
import VueTinymce from '@packy-tang/vue-tinymce'
import * as echarts from 'echarts'; 
Vue.prototype.$echarts = echarts; 

Vue.prototype.$tinymce = tinymce
Vue.use(VueTinymce)
// import '../src/mock'
Vue.config.productionTip = false
Vue.use(ElementUI,axios);
axios.defaults.baseURL='http://localhost:8080/'
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

axios.interceptors.request.use(
  config => {
    if (localStorage.getItem('Authorization')) {
      config.headers.Authorization = sessionStorage.getItem('Authorization');
      console.log(config.headers.Authorization);
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  });

  axios.interceptors.response.use(response=> {
    // 对响应数据做点什么
    if(response.data==""){
      console.log("token已经失效")
      router.replace('/user')
    }
    else
      return response
  }
  );