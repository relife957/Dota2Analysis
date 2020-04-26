<template>
  <a-layout id="components-layout-demo-top" class="layout">
    <a-layout-header>
      <div class="logo"/>
      <a-menu
        theme="dark"
        mode="horizontal"
        :defaultSelectedKeys="[route_map[this.$router.currentRoute.path]]"
        :style="{ lineHeight: '64px' }"
        :selectedKeys="[route_map[this.$router.currentRoute.path]]"
      >
        <a-menu-item key="1">
          <router-link to="/">项目介绍</router-link>
        </a-menu-item>
        <a-menu-item key="2">
          <router-link to="/recommend">阵容推荐</router-link>
        </a-menu-item>
        <a-menu-item key="3">
          <router-link to="/rank">排行榜</router-link>
        </a-menu-item>
        <a-menu-item key="4">
          <router-link to="/history">历史查询</router-link>
        </a-menu-item>
        <a-menu-item key="5">
          <router-link to="/openApi">开放平台</router-link>
        </a-menu-item>
        <a-menu-item key="6">
          <router-link to="/dataManage">数据管理平台</router-link>
        </a-menu-item>
      </a-menu>
    </a-layout-header>
    <a-layout-content style="padding: 0 50px">
      <div :style="{ background: '#fff', padding: '24px', minHeight: '280px' }">
        <router-view/>
      </div>
    </a-layout-content>
    <a-layout-footer style="text-align: center">Dota2阵容推荐系统 ©2020 Created by Wang Man Rong </a-layout-footer>
  </a-layout>
</template>
<script>
export default {
  
  data() {
    return {
       route_map: {
         "/": '1',
         "/recommend":'2',
         "/rank":'3',
         "/history": '4',
         "/openApi":'5',
         "/dataManage":'6'
       }
    }
  },

  mounted(){
    console.log(this.$router.currentRoute.path);
  }
};

</script>
<style>
#components-layout-demo-top .logo {
 width: 120px;
  height: 31px;
  /* background: rgba(255, 255, 255, 0.2); */
  background: url('./assets/title.jpg') no-repeat center 0;
  background-size: 100px,30px;
  margin: 16px 24px 16px 0;
  float: left;
}
</style>
