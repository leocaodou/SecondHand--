<template>
  <div>
    <el-container>
      <el-header class = "title1" height="auto">
        <div>
          <el-button type="primary" icon="el-icon-back" style="font-size: 25px;margin-bottom: 10px;" @click="back()" circle></el-button>
          <span><i class="el-icon-goods"  ></i>校园二手平台登陆界面</span>
        </div>
      </el-header>
      <el-menu class="el-main">
        <div class="loginmenu1">
          <i class="el-icon-user" style="margin-bottom: 50px;"/>
          <el-form :model="stu" ref="ruleForm"  status-icon label-width="80px" :rules="rules">
            <el-form-item label="学号" prop="id" style="margin-right: 20px;margin-bottom: 40px;">
              <el-input v-model.number="stu.id"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password" style="margin-right: 20px;margin-bottom: 20px;">
              <el-input v-model="stu.password" type="password"></el-input>
            </el-form-item>
            <el-form-item label="验证码" prop="ValidateCode" style="margin-right: 20px;margin-bottom: 20px;">
              <el-input v-model="stu.ValidateCode" style="width: 80%;"></el-input>
              <span @click="roll()"><img :src="imageurl"/></span>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')" style="width:480px;margin-left: -80px;margin-bottom: 10px;">登录</el-button>
              <el-button type="success" @click="toregister()" style="width:480px;margin-left: -80px;margin-bottom: 10px;">注册</el-button>
              <el-button type="warning" @click="toadmin()" style="width:480px;margin-left: -80px;margin-bottom: 10px;">管理员登录</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-menu>
    </el-container>
  </div>
</template>

<script scoped>
  const axios = require('axios');
  export default{
    data(){
      return{
        imageurl:'http://localhost:8181/code/getCodeImage',
        stu:{
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
              if(code == _this.stu.ValidateCode)
              {
                axios.post('http://localhost:8181/login/findByUsernameAndPassword',{
                  id:_this.stu.id,
                  password: _this.stu.password,
                }).then((resp)=>{
                  if(resp.data.message!='failed')
                    _this.$router.push({
                      path:'/menu/goods',
                      name: 'goods',
                      params:{
                        id:resp.data.data.id,
                        username:resp.data.data.username,
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
      back(){
        this.$router.push('/menu');
      },
      toregister(){
        this.$router.push('/register');
      },
      toadmin(){
        this.$router.push('/admin');
      },
      roll(){
        axios.post('http://localhost:8181/code/roll').then(function(resp){});
        var num=Math.ceil(Math.random()*10);
        this.imageurl = "http://localhost:8181/code/getCodeImage?" + num;
      }
    }
  }
</script>

<style scoped>
  .title1{
     text-align:left;
     box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
     font-size: 50px;
     background-color: #ffffff;
     color: #16a9f5;
     line-height: 20px;
     margin-bottom: 10px;
  }
  .el-main {
    color: #333;
    text-align: center;
    line-height: 0px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04) ;
    height: 600px;
    font-size: 100px;
  }
  .loginmenu1{
    margin-left: auto;
    margin-right: auto;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    border-radius: 30px;
    background-color: #E9EEF3;
    width: 480px;
  }
</style>
