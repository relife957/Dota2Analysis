<template>
  <div class="home" :style="home">
    <div class="welcome" />
    <ul>
      <li style="font-size: 1.3em; font-style: oblique 40deg;">
        <p>这是一个实现了Dota2内置的基于大数据分析的阵容推荐的网页版</p>
      </li>
      <li>
        <a-carousel arrows autoplay>
          <div slot="prevArrow" class="custom-slick-arrow" style="left: 10px;zIndex: 1">
            <a-icon type="left-circle" />
          </div>
          <div slot="nextArrow" class="custom-slick-arrow" style="right: 10px">
            <a-icon type="right-circle" />
          </div>
          <div class="car">
            <h3>阵容推荐:</h3>
            <p>
              通过对数十万场的5V5对战中的胜利方的阵容的分析,通过
              <code>Apriori</code>算法,
              <br />挖掘对阵中的频繁集,然后生成关联规则,根据用户输入的已有阵容,推荐出最佳的5个英雄.
            </p>
            <br/>
          </div>
          <div class="car">
            <h3>历史查询:</h3>
            <p>
              查询历史推荐的阵容，可通过时间进行筛选输出。
            </p>
            <br/>
          </div>
          <div class="car">
            <h3>开放平台:</h3>
            <p>
              提供推荐系统使用的API接口的开放平台
            </p>
            <br/>
          </div>
          <div class="car">
            <h3>数据管理平台:</h3>
            <p>
              提供推荐系统使用的后端数据管理平台
            </p>
            <br/>
          </div>
        </a-carousel>
      </li>
    </ul>
  </div>
</template>

<script>
// @ is an alias to /src
import { Carousel, Icon } from "ant-design-vue";
export default {
  name: "home",
  components: {
    ACarousel: Carousel,
    AIcon: Icon
  },
  data() {
    return {
      home: {
        // backgroundImage: "url(" + require("../assets/background.png") + ")",
        // backgroundRepeat: "no-repeat",
        // backgroundSize: "100% 100%",
      }
    };
  }
};
</script>
<style scoped>
.home {
  font-family: Georgia, sans-serif;
  margin-top: 4em;
  text-align: center;
}

.welcome {
  height: 350px;
  background: url(../assets/index.jpg) no-repeat center 0;
  background-size: 700px 350px;
}

.car {
  font-size: 1.3em;
}

ul {
  font-size: 1.5em;
  margin-top: 1.8em;
  list-style: none;
  line-height: 1.5em;
}

/* For demo */
.ant-carousel >>> .slick-slide {
  text-align: center;
  height: 300px;
  line-height: 2.5;
  background: rgba(249, 250, 248, 0.664);
  overflow: hidden;
}

.ant-carousel >>> .custom-slick-arrow {
  width: 25px;
  height: 25px;
  font-size: 40px;
  color: rgb(85, 10, 10);
  background-color: rgba(31, 45, 61, 0.11);
  opacity: 0.3;
}

.ant-carousel >>> .custom-slick-arrow:before {
  display: none;
}

.ant-carousel >>> .custom-slick-arrow:hover {
  opacity: 0.5;
}

.ant-carousel >>> .slick-slide h3 {
  color: rgb(6, 7, 10);
}
</style>