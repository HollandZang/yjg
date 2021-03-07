<template>
  <div class="container">
    <div class="topContainer">
      <div class="textAlignRight">
       <el-switch
          v-model="page.status1"
          active-value="有效"
          inactive-value="无效"
          active-text="隐藏无效"
          inactive-text="显示无效"
          active-color="#1ba784"
          @click.native="changeStatus"
        ></el-switch>
       </div>
    </div>
    <div class="centerContainer" v-loading="loading">
      <div v-if="tableData.length < 1" class="noData">暂无数据</div>
      <div v-for="(item, index) in tableData" :key="index" class="rowList">
        <div>
        <img class="title-logo" src="../../assets/img/e.jpg" />
        </div>
        <div>
          <div class="centerContent centerFlex">
            <div class="columnContent">
              <div>提取码：{{ item.bdSecret }}</div>
              <div>百度网盘地址： <a  target="_blank" :href="item.bdUrl" >{{item.bdUrl}}</a></div>
            </div>
            <div class="columnContent">
              <div>接单员：{{ item.cUserName }}</div>
              <div>接单日期：{{ item.cTime }}</div>
            </div>
            <div>说明：{{ item.description }}</div>
          </div>
          <div class="operta centerFlex">
          </div>
        </div>
        <div class="rowListStatus">
          <p v-if="item.status1 == '无效'" class="unFinished">无效</p>
          <p v-else class="finished">有效</p>
        </div>
      </div>
    </div>
    <div class="pagination">
      <el-pagination
        background
        layout="total, sizes, prev, pager, next, jumper"
        :current-page="page.page"
        :page-size="page.limit"
        :total="pageTotal"
        @size-change="handleSizeChange"
        @current-change="handlePageChange"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
import {list } from "../../api/orders";
export default {
  name: "basetable",
  data() {
    return {
      page: {
        page: 1,
        limit: 10,
        status1: "有效",
        cUserId: null,
      },
      pageTotal: 0,
      loading: false,
      tableData: [],
    };
  },
  mounted() {
    this.getData();
  },
  methods: {
    // 获取 easy-mock 的模拟数据
    getData() {
      this.loading = true;
      let page = {};
      if (this.page.status1 == "无效") {
        page.page = this.page.page;
        page.limit = this.page.limit;
      } else {
        page = Object.assign({}, this.page);
      }
      list(page).then((res) => {
        this.loading = false;
        let { data, count } = res;
        this.tableData = data;
        this.pageTotal = count;
      }).catch(() => {
          this.loading = false;
        });;
    },
    changeStatus() {
      this.getData();
    },
    handleSizeChange(val) {
      this.$set(this.page, "limit", val);
      this.getData();
    },
    // 分页导航
    handlePageChange(val) {
      this.$set(this.page, "page", val);
      this.getData();
    },
  },
};
</script>
