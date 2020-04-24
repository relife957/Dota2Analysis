import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: Home,
    meta:{
      title: "Dota2阵容推荐首页"
    }
  },
  {
    path: "/about",
    name: "about",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue")
  },
  {
    path: "/recommend",
    name: "recommend",
    component: () => import("../views/Recommend.vue"),
    meta:{
      title: "Dota2阵容推荐"
    }
  },
  {
    path: "/history",
    name: "history",
    component: () => import("../views/History.vue"),
    meta:{
      title: "Dota2阵容推荐历史查询"
    }
  },
  {
    path: "/openApi",
    name: "openApi",
    component: () => import("../views/OpenApi.vue"),
    meta:{
      title: "Dota2阵容推荐开放平台"
    }
  },
  {
    path: "/dataManage",
    name: "dataManage",
    component: () => import("../views/DataManage.vue"),
    meta:{
      title: "Dota2阵容推荐数据管理平台"
    }
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

const defaultTitle = 'Dota2阵容推荐'
router.beforeEach((to, from, next) => {
  document.title = to.meta.title ? to.meta.title : defaultTitle
  next()
})


export default router;
