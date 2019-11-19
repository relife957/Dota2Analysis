<template>
  <div >
    <div class="headerImg"/>
    <br>
    <br>

    <a-select
      mode="multiple"
      :value="value"
      placeholder="输入选择你要查询的阵容"
      style="width: 70%;margin: 0 auto;display: block"
      :filterOption="false"
      @search="fetchUser"
      @change="handleChange"
      :notFoundContent="fetching ? undefined : null"
      size="large"
    >
      <a-spin v-if="fetching" slot="notFoundContent" size="small"/>
      <Option v-for="d in data" :key="d.value">{{d.text}}</Option>
    </a-select>
    <br>
    <br>

    <a-button type="primary" icon="search" style="margin: auto;display: block;" size="large" @click="recommend">推荐</a-button>
    <br/>
    <a-row type="flex">
      <a-col :span=6  v-for="hero in heroes" :key="hero.heroId" >
        <a-card hoverable style="width: 250px" type="inner">
          <img
            alt="example"
            :src= hero.img
            slot="cover"
          >
          <Meta :title=hero.cnName :description=hero.cnRoles />
        </a-card>
      </a-col>
      
    </a-row>
  </div>
</template>
<script>
import request from "../util/request";
import { Select, Spin, notification, Button, Card,Row,Col } from "ant-design-vue";
const Option = Select.Option;
const Meta = Card.Meta;
import debounce from "lodash/debounce";
export default {
  components: {
    ASelect: Select,
    ASpin: Spin,
    Option,
    AButton: Button,
    ACard: Card,
    Meta,
    ARow: Row,
    ACol: Col
  },
  data() {
    this.lastFetchId = 0;
    this.fetchUser = debounce(this.fetchUser, 800);

    return {
      data: [],
      value: [],
      fetching: false,
      heroes: [],
      // note: {
        // backgroundImage: "url(" + require("../assets/background.png") + ")",
        // backgroundRepeat: "no-repeat",
        // backgroundSize: "100% 100%"
      // }
    };
  },
  methods: {
    fetchUser(value) {
      if (this.value.length == 4) {
        this.openNotification(
          "超过查询输入上限",
          "本推荐系统只允许输入1-4个英雄"
        );
        return;
      }
      this.lastFetchId += 1;
      const fetchId = this.lastFetchId;
      this.data = [];
      this.fetching = true;
      const url = "http://localhost:8080/heroinfo/query?keyname=".concat(value);
      request({
        url: url,
        methods: "get"
      })
        .then(response => response.data)
        .then(body => {
          if (fetchId !== this.lastFetchId) {
            // for fetch callback order
            return;
          }
          const data = body.map(hero => ({
            text: `${hero.cnName} `,
            value: hero.cnName
          }));
          this.data = data;
          this.fetching = false;
        });
    },
    handleChange(value) {
      Object.assign(this, {
        value,
        data: [],
        fetching: false
      });
    },
    openNotification(msg, des) {
      notification.open({
        message: msg,
        description: des,
        style: {
          width: "600px",
          marginLeft: `${335 - 600}px`
        }
      });
    },
    recommend(){
        console.log(this.value);
        let params = "";
        const len = this.value.length;
        for(let i = 0 ; i < len;i++){
          if(i != this.value-1){
            params = params.concat(this.value[i]).concat(" ")
          }else{
            params = params.concat(this.value[i]);
          }
        }
        console.log(params);
        request({
          url: "http://localhost:8080/team/recommend",
          methods: "get",
          params: {
            teams: params
          }
        })
        .then(response => response.data)
        .then(body => {
          console.log(body);
          this.heroes = body;
        })
    }
  }
};
</script>
<style scoped>
.headerImg {
  background: url("https://www.dota2.com.cn/cover/190429/layer3.png?v=201905130")
    no-repeat center 0;
  width: 100%;
  height: 235px;
  background-color: blueviolet;
  margin: auto;
  text-align: center;
}

</style>

