<template>
  <div class="loginmenu">
    <i class="el-icon-view" style="margin-bottom: 20px;font-size: 80px; "/>
    <el-form :model="admin" ref="ruleForm"  status-icon label-width="80px" :rules="rules">
      <el-form-item label="账号" prop="id" style="margin-right: 20px;margin-bottom: 40px;">
        <el-input v-model.number="admin.id"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password" style="margin-right: 20px;margin-bottom: 20px;">
        <el-input v-model="admin.password" type="password"></el-input>
      </el-form-item>
      <el-form-item label="验证码" prop="ValidateCode" style="margin-right: 20px;margin-bottom: 20px;">
        <el-input v-model="admin.ValidateCode" style="width: 80%;"></el-input>
        <span @click="roll()"><img :src="imageurl"/></span>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')" style="width:480px;margin-left: -80px;margin-bottom: 30px;">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script scoped>
  const axios = require('axios');
  export default{
    data(){
      return{
        imageurl:'http://localhost:8181/code/getCodeImage',
        admin:{
          id: null,
          password:'',
          ValidateCode:'',
        },
        rules:{
          id: [
            { required: true, message: '请输入学号', trigger: 'blur' },
          ],
          password: [
            { required: true, message: '请填写密码', trigger: 'blur' }
          ],
          ValidateCode: [
            { required:true, message: '请输入验证码', trigger: 'blur'}
          ],

        }
      };
    },
    methods: {
      submitForm(formName) {
        const _this=this;
        var code = '';
        this.$refs[formName].validate((valid) => {
          if (valid) {
            axios.get('http://localhost:8181/code/getCode').then(function(resp){
              code = resp.data;
              if(code == _this.admin.ValidateCode)
              {
                axios.post('http://localhost:8181/login/findAdmin',{
                  id:_this.admin.id,
                  password: _this.admin.password,
                }).then((resp)=>{
                  if(resp.data.message!='failed')
                    _this.$router.push({
                      path:'/administ',
                      name: 'administ',
                      params:{
                        id:resp.data.data.id,
                        },
                      });
                  else
                    alert('没有该用户或则密码错误');
                });
              }
              else{
                alert('验证码错误');
              }
            });
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      roll(){
        axios.post('http://localhost:8181/code/roll').then(function(resp){});
        var num=Math.ceil(Math.random()*10);
        this.imageurl = "http://localhost:8181/code/getCodeImage?" + num;
      }
    }
  }
</script>

<style>
  .loginmenu{
    margin-left: auto;
    margin-right: auto;
    text-align: center;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    border-radius: 30px;
    background-color: #d8d8d8;
    width: 480px;
    margin-top: 100px;
  }
</style>
