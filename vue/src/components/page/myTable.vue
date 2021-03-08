<template>
  <div class="container">
    <div class="topContainer">
      <div class="textAlignRight">
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
        <el-button type="danger" round @click="addOrder"> 新增接单 </el-button>
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
              <div>
                百度网盘地址： <a target="_blank" :href="item.bdUrl">{{ item.bdUrl }}</a>
              </div>
            </div>
            <div class="columnContent">
              <template v-if="item.status2 === '已接单'">
                <div>做单员：{{ item.claimUserName }}</div>
                <div style="display: flex">
                  <div>做单日期：{{ item.claimTime }}</div>
                  <div style="margin-left: 20px">接单日期：{{ item.cTime }}</div>
                </div>
              </template>
              <template v-else>
                <div>接单员：{{ item.cUserName }}</div>
                <div>接单日期：{{ item.cTime }}</div>
              </template>
            </div>
            <div>客户名及补充说明：{{ item.description }}</div>
          </div>
          <div class="operta centerFlex">
            <el-switch
              v-if="item.status1 == '有效'"
              v-model="item.status1"
              active-value="有效"
              inactive-value="无效"
              active-text="有效"
              inactive-text="无效"
              active-color="#1ba784"
              @change="changStatus(item)"
            ></el-switch>
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
    <el-dialog title="新增接单" :visible.sync="dialogVisible" width="500px">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-suffix=":"
        label-width="140px"
        class="demo-ruleForm"
      >
        <el-form-item label="百度网盘地址" prop="bdUrl">
          <el-input v-model.trim="ruleForm.bdUrl"></el-input>
        </el-form-item>
        <el-form-item label="百度网盘提取码" prop="bdSecret">
          <el-input v-model.trim="ruleForm.bdSecret"></el-input>
        </el-form-item>
        <el-form-item label="客户名及补充说明" prop="description">
          <el-input
            type="textarea"
            v-model.trim="ruleForm.description"
            maxlength="30"
            show-word-limit
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">关 闭</el-button>
        <el-button type="danger" @click="submitForm('ruleForm')" :loading="bthLoading"
          >新 增</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { add, list, update } from "../../api/orders";
export default {
  name: "mytable",
  data() {
    return {
      page: {
        page: 1,
        limit: 10,
        status1: "有效",
        cUserId: null,
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
      bthLoading: false,
      ruleForm: {
        bdUrl: "",
        description: "",
        bdSecret: "",
      },
      rules: {
        bdUrl: [{ required: true, message: "请输入百度网盘地址", trigger: "blur" }],
        bdSecret: [
          { required: true, message: "请输入百度网盘提取码", trigger: "blur" },
          { min: 1, max: 4, message: "提取码输入有误", trigger: "blur" },
        ],
        description: [
          { required: true, message: "请填写客户名及补充说明", trigger: "blur" },
        ],
      },
      dialogVisible: false,
      tableData: [],
      userData: {},
    };
  },
  created() {
    this.userData = JSON.parse(localStorage.getItem("ms_user"));
    this.page.cUserId = this.userData.id;
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
    addOrder() {
      this.dialogVisible = true;
    },
    changStatus(item) {
      item.status1 = "有效";
      this.$confirm("确定要修改状态吗？", "提示", {
        type: "warning",
      }).then(() => {
        update({ ...item, status1: "无效" }).then((res) => {
          this.getData();
        });
      });
    },
    // 触发搜索按钮
    handleSearch() {
      this.$set(this.page, "page", 1);
      this.getData();
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.bthLoading = true;
          add({
            ...this.ruleForm,
            cUserId: this.userData.id,
            cUserName: this.userData.name,
          })
            .then((res) => {
              this.bthLoading = false;
              this.$message.success("新建成功");
              this.$refs.ruleForm.resetFields();
              this.dialogVisible = false;
              this.getData();
            })
            .catch(() => {
              this.bthLoading = false;
            });
        } else {
          return false;
        }
      });
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
