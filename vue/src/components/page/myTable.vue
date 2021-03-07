<template>
  <div class="container">
    <div class="topContainer">
      <div class="textAlignRight">
        <!-- <el-input placeholder="请输入内容" v-model="input" clearable> </el-input> -->
        <el-switch
          v-model="page.status1"
          active-value="有效"
          inactive-value="无效"
          active-text="隐藏无效"
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
        <div style="background-color:#fefdf8">
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
              <div>接单日期：{{ item.cTime }}</div>
            </div>
            <div>说明：{{ item.description }}</div>
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
        label-width="130px"
        class="demo-ruleForm"
      >
        <el-form-item label="百度网盘地址" prop="bdUrl">
          <el-input v-model.trim="ruleForm.bdUrl"></el-input>
        </el-form-item>
        <el-form-item label="百度网盘提取码" prop="bdSecret">
          <el-input v-model.trim="ruleForm.bdSecret"></el-input>
        </el-form-item>
        <el-form-item label="说明" prop="description">
          <el-input type="textarea" v-model.trim="ruleForm.description" maxlength="30"
           show-word-limit></el-input>
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
        bdSecret: [{ required: true, message: "请输入百度网盘提取码", trigger: "blur" },
            { min: 1, max: 4, message: '提取码输入有误', trigger: 'blur' }],
        description: [{ required: true, message: "请填写说明", trigger: "blur" }],
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
      if (this.page.status1 == "无效") {
        page.page = this.page.page;
        page.limit = this.page.limit;
        page.cUserId = this.page.cUserId;
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
    addOrder() {
      this.dialogVisible = true;
    },
    changStatus(item) {
      item.status1='有效';
      this.$confirm("确定要修改状态吗？", "提示", {
        type: "warning",
      })
        .then(() => {
          update({...item,status1:'无效'}).then((res) => {
            this.getData();
          });
        })
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
