<template>
  <div class="container">
    <div class="topContainer">
      <div class="textAlignRight">
        <el-switch
          v-model="page.status2"
          active-value="未接单"
          inactive-value="已接单"
          active-text="隐藏已做单"
          inactive-text="显示已做单"
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
        <div>
          <img class="title-logo" src="../../assets/img/e.jpg" />
        </div>
        <div>
          <div class="centerContent centerFlex">
            <div class="columnContent">
              <div>接单员：{{ item.cUserName }}</div>
              <div>接单日期：{{ item.cTime }}</div>
            </div>
            <div v-if="item.status2 == '已接单'" class="columnContent">
              <div>做单员：{{ item.claimUserName }}</div>
              <div>做单日期：{{ item.claimTime }}</div>
            </div>
            <div>说明：{{ item.description }}</div>
          </div>
          <div class="operta centerContent centerFlex">
            <template v-if="item.status2 == '未接单' && item.status1 == '有效'">
              <div>
                <el-button type="warning" round @click="handleJieDan(item.id)">
                  做单
                </el-button>
              </div>
              <div>
                <el-button type="primary" round @click="detail(item)">
                  查看详情
                </el-button>
              </div>
            </template>
          </div>
        </div>
        <div class="rowListStatus">
          <p v-if="item.status1 == '无效'" class="unFinished">无效</p>
          <p v-else-if="item.status2 == '已接单'" class="unAdmission">已做单</p>
          <p v-else class="finished">未做单</p>
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
    <el-dialog title="详情" :visible.sync="dialogVisible" width="500px">
      <div class="textHeight">
       百度网盘地址： <a  target="_blank" :href="row.bdUrl" >{{row.bdUrl}}</a>
       </div>
      <div class="textHeight">
        <div>网盘提取码：</div>
        {{ row.bdSecret }}
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">关 闭</el-button>
        <el-button @click="handleJieDan(row.id)" type="primary">做 单</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { list, claim } from "../../api/orders";
export default {
  name: "tabstable",
  data() {
    return {
      dialogVisible: false,
      row: {},
      page: {
        page: 1,
        limit: 10,
        status1: "有效",
        status2: "未接单",
      },
      pageTotal: 0,
      loading: false,
      tableData: [],
      userData: {},
    };
  },
  created() {
    this.userData = JSON.parse(localStorage.getItem("ms_user"));
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
      if (this.page.status2 == "已接单") {
        page.status2=null;
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
    detail(item) {
      this.row = item;
      this.dialogVisible = true;
    },
    changeStatus() {
      this.getData();
    },
    handleJieDan(id) {
      claim({ id, user: this.userData.id }).then((res) => {
        this.getData();
        if (res.code === 0) {
          this.$message.success("做单成功！");
          this.dialogVisible=false;
        }
      });
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
