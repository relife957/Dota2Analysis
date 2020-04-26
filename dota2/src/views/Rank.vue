<template>
  <div>
    <span
      style="font-size: 1.3em; font-style: oblique 40deg;"
    >{{start_date}}日 - {{end_date}}日查询次数排行榜 ：</span>
<div>
  TOP{{inputValue}} :
    <a-input-number
      :min="1"
      :max="20"
      :defaultValue="10"
      style="marginLeft: 16px"
      v-model="inputValue"
    /> &#12288;&#12288;
    <a-button type="primary" @click="refresh">
      刷新
    </a-button>
</div>
    <div style="float:right">
      <a-date-picker
        :disabledDate="disabledStartDate"
        format="YYYY-MM-DD"
        v-model="startValue"
        placeholder="开始日期"
        :defaultValue="moment(start_date, dateFormat)"
        @openChange="handleStartOpenChange"
      />
      <a-date-picker
        :disabledDate="disabledEndDate"
        format="YYYY-MM-DD"
        placeholder="结束日期"
        v-model="endValue"
        :open="endOpen"
        :defaultValue="moment(end_date, dateFormat)"
        @openChange="handleEndOpenChange"
      />
    </div>
    <br />
    <a-table
      :columns="columns"
      :rowKey="record => record.rankNumber"
      :dataSource="data"
      :pagination="pagination"
      :loading="loading"
      @change="handleTableChange"
    >
      <span slot="qteams" slot-scope="queryTeam">
        <a-popover v-for="hero in queryTeam" :title="hero.cnName" :key="hero.heroId">
          <template slot="content">
            <a-card hoverable type="inner">
              <img alt="example" :src="hero.img" slot="cover" />
              <a-card-meta :title="hero.name">
                <template slot="description">
                  <div>
                    <a-tag
                      color="#2db7f5"
                      v-for="item in hero.cnRoles.split(',')"
                      :key="item"
                    >{{item}}</a-tag>
                  </div>
                </template>
              </a-card-meta>
            </a-card>
          </template>
          <a-button :style="{'background-color': color_map[hero.cnAttr]}">{{hero.cnName}}</a-button>&nbsp;&nbsp;&nbsp;&nbsp;
        </a-popover>
      </span>

      <span slot="rteams" slot-scope="recommendTeam">
        <a-popover v-for="hero in recommendTeam" :title="hero.cnName" :key="hero.heroId">
          <template slot="content">
            <a-card hoverable type="inner">
              <img alt="example" :src="hero.img" slot="cover" />
              <a-card-meta :title="hero.name">
                <template slot="description">
                  <div>
                    <a-tag
                      color="#2db7f5"
                      v-for="item in hero.cnRoles.split(',')"
                      :key="item"
                    >{{item}}</a-tag>
                  </div>
                </template>
              </a-card-meta>
            </a-card>
          </template>
          <a-button :style="{'background-color': color_map[hero.cnAttr]}">{{hero.cnName}}</a-button>&nbsp;&nbsp;&nbsp;&nbsp;
        </a-popover>
      </span>
    </a-table>
  </div>
</template>
<script>
import request from "../util/request";

import {
  Table,
  DatePicker,
  Tag,
  Popover,
  Card,
  InputNumber
} from "ant-design-vue";
var moment = require("moment");
const Meta = Card.Meta;

const columns = [
  {
    title: "排行",
    dataIndex: "rankNumber",
    width: "10%",
    sorter: (a, b) => a.rankNumber - b.rankNumber,
    sortDirections: ["ascend" | "descend"]
  },
  {
    title: "查询阵容",
    dataIndex: "queryTeam",
    width: "40%",
    scopedSlots: { customRender: "qteams" }
  },
  {
    title: "推荐阵容",
    dataIndex: "recommendTeam",
    width: "40%",
    scopedSlots: { customRender: "rteams" }
  },
  {
    title: "查询次数",
    dataIndex: "count",
    width: "10%",
    sorter: (a, b) => a.count - b.count,
    sortDirections: ["ascend" | "descend"]
  }
];

export default {
  components: {
    ATable: Table,
    ADatePicker: DatePicker,
    ATag: Tag,
    APopover: Popover,
    ACard: Card,
    ACardMeta: Meta,
    AInputNumber: InputNumber
  },
  mounted() {
    this.fetch();
  },
  data() {
    return {
      data: [],
      pagination: {
        pageSize: 12
      },
      loading: false,
      columns,
      startValue: null,
      endValue: null,
      start_date: "2020-04-01",
      end_date: "2020-04-30",
      endOpen: false,
      color_map: {
        敏捷: "#F5FFFA",
        力量: "#F0FFF0",
        智力: "#E0FFFF"
      },
      inputValue: 10,
      dateFormat : 'YYYY-MM-DD'
    };
  },

  methods: {
    moment,
    handleTableChange(pagination, filters, sorter) {
      console.log(pagination);
      const pager = { ...this.pagination };
      pager.current = pagination.current;
      this.pagination = pager;
      this.fetch({
        results: pagination.pageSize,
        page: pagination.current,
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...filters
      });
    },
    fetch(params = {}) {
      this.loading = true;
      if (this.startValue) {
        this.start_date = moment(this.startValue).format("YYYY-MM-DD");
      }
      if (this.endValue) {
        this.end_date = moment(this.endValue).format("YYYY-MM-DD");
      }
      request({
        url: "http://localhost:8080/rank/dateRank",
        method: "get",
        params: {
          start_date: this.start_date,
          end_date: this.end_date,
          top: this.inputValue
        }
      })
        .then(response => response.data)
        .then(body => {
          const pagination = { ...this.pagination };
          // Read total count from server

          this.loading = false;
          this.data = body;
          pagination.total = this.data.length;
          this.pagination = pagination;
        })
        .catch(error => {
          this.loading = false;
          this.data = [];
        });
    },
    toDate(s) {
      s = s.replace(/-/g, "/");
      return new Date(s);
    },
    refresh() {
      console.log(this.start_date);
      this.fetch();
    },
    disabledStartDate(startValue) {
      const endValue = this.endValue;
      if (!startValue || !endValue) {
        return false;
      }
      return startValue.valueOf() > endValue.valueOf();
    },
    disabledEndDate(endValue) {
      const startValue = this.startValue;
      if (!endValue || !startValue) {
        return false;
      }
      return startValue.valueOf() >= endValue.valueOf();
    },
    handleStartOpenChange(open) {
      if (!open) {
        this.endOpen = true;
      }
    },
    handleEndOpenChange(open) {
      this.endOpen = open;
    }
  }
};
</script>