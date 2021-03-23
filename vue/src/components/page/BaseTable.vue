<template>
  <div class="container">
    <div class="topContainer">
      <div class="textAlignRight">
        <span>接单日期：</span>
        <el-date-picker
          v-model="page.cTime"
          type="date"
          placeholder="请选择接单时间"
          value-format="yyyy-MM-dd"
          @change="getData"
        >
        </el-date-picker>
        <span>筛选条件：</span>
        <el-select v-model="value" placeholder="请选择" @change="updateObj">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
        <el-switch
          v-model="page.status1"
          active-value="有效"
          inactive-value="无效"
          active-text=""
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
              <div>
                百度网盘地址： <a target="_blank" :href="item.bdUrl">{{ item.bdUrl }}</a>
              </div>
            </div>
            <div class="columnContent">
              <div>接单员：{{ item.cUserName }}</div>
              <div>接单日期：{{ item.cTime }}</div>
            </div>
            <div>客户名及补充说明：{{ item.description }}</div>
          </div>
          <div class="operta centerFlex">
            <div v-if="item.status2 === '未接单'" class="missed">未做单</div>
            <div v-else class="missed">
              <div v-if="item.status3 === '未完成'" class="unfinish">
                已做单,但是还{{ item.status3 }}
              </div>
              <div v-else class="finish">{{ item.status3 }}</div>
            </div>
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
import { list } from "../../api/orders";
export default {
  name: "basetable",
  data() {
    return {
      page: {
        page: 1,
        limit: 10,
        status1: "有效",
        status2: null,
        status3: null,
        cTime: null,
      },
      value: "",
      options: [
        {
          value: "无",
          label: "查看所有",
        },
        {
          value: "未接单",
          label: "未做单",
        },
        {
          value: "已接单",
          label: "已做单",
        },
        {
          value: "未完成",
          label: "未完成",
        },
        {
          value: "已完成",
          label: "已完成",
        },
      ],
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
      page = Object.assign({}, this.page);
      if (this.page.status1 == "无效") {
        page.status1 = null;
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
    updateObj() {
      this.page.status2 = null;
      this.page.status3 = null;
      switch (this.value) {
        case "未接单":
          this.page.status2 = "未接单";
          break;
        case "已接单":
          this.page.status2 = "已接单";
          break;
        case "未完成":
          this.page.status3 = "未完成";
          break;
        case "已完成":
          this.page.status3 = "已完成";
          break;
        default:
          break;
      }
      this.getData();
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
