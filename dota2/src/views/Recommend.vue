<template>
  <div>
    <div class="headerImg" />
    <br />
    <br />

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
      <a-spin v-if="fetching" slot="notFoundContent" size="small" />
      <Option v-for="d in data" :key="d.value">{{d.text}}</Option>
    </a-select>
    <br />
    <br />

    <a-button
      type="primary"
      icon="search"
      style="margin: auto;display: block;"
      size="large"
      @click="recommend"
    >推荐</a-button>
    <br />
    <a-row type="flex">
      <a-col :span="6" v-for="hero in heroes" :key="hero.heroId">
        <a-card hoverable style="width: 250px" type="inner">
          <img alt="example" :src="hero.img" slot="cover" />
          <a-card-meta :title="hero.cnName">
            <template slot="description">
              <div >
                <a-tag color="#2db7f5" v-for="item in hero.cnRoles.split(',')" :key="item" >
                  {{item}}</a-tag>
              </div>
              <!-- <div v-for="item in hero.cnRoles.split(',')" :key="item">

                <a-popover title="Title" trigger="hover">
                  <a-button type="primary">{{item}}</a-button>
                </a-popover>

              </div>-->
            </template>
          </a-card-meta>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>
<script>
import request from "../util/request";
import {
  Select,
  Spin,
  notification,
  Button,
  Card,
  Row,
  Col,
  Tag
} from "ant-design-vue";
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
    ACardMeta: Meta,
    ARow: Row,
    ACol: Col,
    ATag: Tag
  },
  data() {
    this.lastFetchId = 0;
    this.fetchUser = debounce(this.fetchUser, 800);

    return {
      data: [],
      value: [],
      fetching: false,
      heroes: []
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
    recommend() {
      let params = "";
      const len = this.value.length;
      for (let i = 0; i < len; i++) {
        if (i != this.value - 1) {
          params = params.concat(this.value[i]).concat(" ");
        } else {
          params = params.concat(this.value[i]);
        }
      }
      request({
        url: "http://localhost:8080/team/recommend",
        methods: "get",
        params: {
          teams: params
        }
      })
        .then(response => response.data)
        .then(body => {
          this.heroes = body;
          this.deal_data();
  
        });
    },
    deal_data() {
      for (let i = 0; i < this.heroes.length; i++) {
        var hero = this.heroes[i];
        hero.cnRoles = hero.cnRoles.replace(/\[|]/g, "").replace(/\"/g,"");
        this.heroes[i] = hero;
      }
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

