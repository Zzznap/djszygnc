import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)
const originalPush =VueRouter.prototype.push
VueRouter.prototype.push=function push(location){
  return originalPush.call(this,location).catch(err=>err)
}
const routes = [
  {
    name:'home',
    path:'/home',
    beforeEnter:(to,from,next)=>{
        if(to.name!='user'){
            next()
        }
    },
    component:()=>import('../views/HomeView.vue'),
    children:[
      {
        name:'project',
        path:'project/:projectid',
        component:()=>import('../components/MainInfo'),
        children:[
            {
              name:'status',
              path:'status/:statusid',    
              component:()=>import('../components/InfoList')
            }
        ]
      }
    ]
  },
  // {
  //   name:'project',
  //   path:'project/:projectid',
  //   component:()=>import('../components/MainInfo'),
  //   children:[
  //       {
  //         name:'status',
  //         path:'status/:statusid',
  //         component:()=>import('../components/InfoList')
  //       }
  //   ]
  // }, 
  {
    path:'/',
    name:'app',
    redirect:'/user',
    component:()=>import('../App.vue'),
    children:[
      {
        path: 'user',
        name: 'user',
        component: () => import(/* webpackChunkName: "about" */ '../views/UserView.vue'),
        children:[
          {
              path: '',
              name: 'VueLogin',
              component:  ()=>import('../components/userComponents/VueLogin.vue')
          },
          {
            path: 'VueRegister',
            name: 'VueRegister',
            component:  ()=>import('../components/userComponents/VueRegister.vue')
          }
        ]
      }

    ]
  },
      // {
      //   path: '/user',
      //   name: 'user',
      //   component: () => import(/* webpackChunkName: "about" */ '../views/UserView.vue'),
      //   children:[
      //     {
      //         path: '',
      //         name: 'VueLogin',
      //         component: VueLogin
      //     },
      //     {
      //       path: 'VueRegister',
      //       name: 'VueRegister',
      //       component: VueRegister
      //     }
      //   ]
      // }

  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
