<template>
  <div class="container">
    <div class="topContainer">
      <div class="textAlignRight">
        <el-switch
          v-model="page.status3"
          active-value="未完成"
          inactive-value="已完成"
          active-text="隐藏已完成"
          inactive-text="显示已完成"
          active-color="#1ba784"
          @click.native="changeStatus"
          style="margin-right: 20px"
        ></el-switch>
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
        <div style="background-color: #fefdf8">
          <img class="title-logo" src="../../assets/img/a.jpg" />
        </div>
        <div>
          <div class="centerContent centerFlex">
              <div class="columnContent">
                <div>提取码：{{ item.bdSecret }}</div>
                <div>百度网盘地址： <a  target="_blank" :href="item.bdUrl" >{{item.bdUrl}}</a></div>
              </div>
            <div class="columnContent">
              <div>接单员：{{ item.cUserName }}</div>
              <div>做单日期：{{ item.claimTime }}</div>
            </div>
            <div class="columnContent">
              <div>说明：{{ item.description }}</div>
            </div>
          </div>
          <div class="operta centerFlex">
            <el-switch
              v-if="item.status3 == '未完成'"
              v-model="item.status3"
              active-value="已完成"
              inactive-value="未完成"
              active-text="完成"
              inactive-text=""
              active-color="#1ba784"
              @change="changStatus(item)"
            ></el-switch>
          </div>
        </div>
        <div class="rowListStatus">
          <p v-if="item.status1 == '无效'" class="unFinished">无效</p>
          <p v-else-if="item.status3 == '未完成'" class="unAdmission">未完成</p>
          <p v-else class="finished">已完成</p>
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
import { list, update } from "../../api/orders";
export default {
  name: "baseform",
  data() {
    return {
      page: {
        page: 1,
        limit: 10,
        status1: "有效",
        status3: "未完成",
        claimUserId: null,
      },
      pageTotal: 0,
      loading: false,
      tableData: [],
      userData: {},
    };
  },
  created() {
    this.userData = JSON.parse(localStorage.getItem("ms_user"));
    this.page.claimUserId = this.userData.id;
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
        page.claimUserId = this.page.claimUserId;
        page.limit = this.page.limit;
      } else {
        page = Object.assign({}, this.page);
      }
      if (this.page.status3 == "已完成") {
        page.status3 = null;
      }
      list(page)
        .then((res) => {
          this.loading = false;
          let { data, count } = res;
          this.tableData = data;
          this.pageTotal = count;
        })
        .catch(() => {
          this.loading = false;
        });
    },
    changStatus(item) {
      item.status3 = "未完成";
      this.$confirm("确定完成了单子内容吗？", "提示", {
        type: "warning",
      }).then(() => {
        update({ ...item, status3: "已完成" }).then((res) => {
          this.getData();
        });
      });
    },
    changeStatus() {
      this.getData();
    },
    handleSizeChange(val) {
      this.$set(this.page, "limit", val);
      this.getData();
    },
    handlePageChange(val) {
      this.$set(this.page, "page", val);
      this.getData();
    },
  },
};
</script>
