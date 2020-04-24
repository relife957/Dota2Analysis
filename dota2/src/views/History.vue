<template>
<div>
  <span style="font-size: 1.3em; font-style: oblique 40deg;">历史查询列表 </span>
<div style="float:right">
<a-date-picker
      :disabledDate="disabledStartDate"
      format="YYYY-MM-DD"
      v-model="startValue"
      placeholder="开始日期"
      @openChange="handleStartOpenChange"
      @ok="refresh"
    />
    <a-date-picker
      :disabledDate="disabledEndDate"
      format="YYYY-MM-DD"
      placeholder="结束日期"
      v-model="endValue"
      :open="endOpen"
      @openChange="handleEndOpenChange"
      @ok="refresh"
    />
</div>
<br>
  <a-table
    :columns="columns"
    :rowKey="record => record.id"
    :dataSource="data"
    :pagination="pagination"
    :loading="loading"
    @change="handleTableChange"
  >
    
  </a-table>
</div>
</template>
<script>
import request from "../util/request";

import { Table ,DatePicker
} from "ant-design-vue";
var moment = require('moment')

const columns = [
  {
    title: "查询阵容",
    dataIndex: "input",
    width: "30%",
    sorter: (a, b) => a.input.length - b.input.length,
    sortDirections: ['ascend' | 'descend'],
  },
  {
    title: "推荐阵容",
    dataIndex: "output",
    width: "40%"
  },
  {
    title: "查询日期",
    dataIndex: "addTime",
    sorter: (a, b) => this.toDate(a.addTime)>this.toDate(b.addTime),
    sortDirections: ['ascend' | 'descend'],
  }
];

export default {
  components: {
    ATable: Table,
    ADatePicker: DatePicker
  },
  mounted() {
    this.fetch();
  },
  data() {
    return {
      data: [],
      pagination: {
        pageSize : 12
      },
      loading: false,
      columns,
      startValue: null,
      endValue: null,
      endOpen: false,
    };
  },

  methods: {
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
      var start_date = "2020-04-01"
      var end_date = "2020-04-30"
      if(this.startValue){
        start_date = moment(this.startValue).format("YYYY-MM-DD");
      }
      if(this.endValue){
        end_date = moment(this.endValue).format("YYYY-MM-DD");
      }
      request({
        url: "http://localhost:8080/history/query",
        method: "get",
        params: {
          start_date: start_date,
          end_date: end_date
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
        }).catch(error=>{
          this.loading = false;
          this.data = [];
        });
    },
    toDate(s){
      s = s.replace(/-/g,"/");
      return new Date(s);
    },
    refresh() {
      console.log(start_date)
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
    },
  }
};
</script>