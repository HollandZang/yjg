<template>
  <div class="container">
    <div style="display: flex; height: 100%">
      <div style="height: 100%; width: calc(100% - 400px); margin-right: 20px">
        <div class="headTitle">用户列表</div>
        <el-table
          :data="tableData"
          v-loading="loading"
          height="calc(100% - 40px)"
          border
          style="width: 100%"
        >
          <el-table-column prop="name" label="真实姓名"> </el-table-column>
          <el-table-column prop="user" label="登录账号"> </el-table-column>
          <el-table-column prop="pwd" label="登录密码"> </el-table-column>
          <el-table-column prop="role" label="角色名称"> </el-table-column>
        </el-table>
      </div>
      <div>
        <div class="headTitle">新增用户</div>
        <el-form
          :model="ruleForm"
          :rules="rules"
          ref="ruleForm"
          label-width="100px"
          style="width: 360px; margin: 20px"
        >
          <el-form-item label="真实姓名" prop="name">
            <el-input v-model.trim="ruleForm.name"></el-input>
          </el-form-item>
          <el-form-item label="登录账号" prop="user">
            <el-input v-model.trim="ruleForm.user"></el-input>
          </el-form-item>
          <el-form-item label="登录密码" prop="pwd">
            <el-input v-model.trim="ruleForm.pwd"></el-input>
          </el-form-item>
          <el-form-item label="角色名称" prop="role">
            <el-select v-model="ruleForm.role" placeholder="请选择角色名称">
              <el-option label="管理员" value="管理员"></el-option>
              <el-option label="接单员" value="接单员"></el-option>
              <el-option label="做单员" value="做单员"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              @click="submitForm('ruleForm')"
              :loading="bthLoading"
              >新 增</el-button
            >
            <el-button @click="resetForm('ruleForm')">重 置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import { addUser, getAllUser } from "../../api/index";
export default {
  name: "basetable",
  data() {
    return {
      ruleForm: {
        name: "",
        role: "",
        user: "",
        pwd: "",
        role: "",
      },
      loading: false,
      tableData: [],
      bthLoading: false,
      rules: {
        name: [
          { required: true, message: "请输入用户真实姓名", trigger: "blur" },
          { min: 2, max: 5, message: "长度在 2 到 5 个字符", trigger: "blur" },
        ],
        user: [{ required: true, message: "请输入用户名", trigger: "blur" }],
        pwd: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 3, message: "密码长度最少为3位", trigger: "blur" },
        ],
        role: [{ required: true, message: "请选择角色名称", trigger: "change" }],
      },
    };
  },
  mounted() {
    this.getUser();
  },
  methods: {
    getUser() {
      this.loading = true;
      getAllUser()
        .then((res) => {
          this.loading = false;
          let { data, code } = res;
          this.tableData = data;
        })
        .catch(() => {
          this.loading = false;
        });
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.bthLoading = true;
          addUser(this.ruleForm)
            .then((res) => {
              this.bthLoading = false;
              let { code } = res;
              if (code !== 0) {
                this.$message.error("添加失败");
              } else {
                this.$message.success("添加用户成功");
                this.getUser();
              }
              this.resetForm("ruleForm");
            })
            .catch(() => {
              this.bthLoading = false;
            });
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>
