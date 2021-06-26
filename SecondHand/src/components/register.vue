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
        <div class="loginmenu">
          <i class="el-icon-user" style="margin-bottom: 50px;"/>
          <el-form :model="stu" ref="ruleForm" status-icon label-width="80px" :rules="rules">
            <el-form-item label="用户名" prop="username" style="margin-right: 20px;margin-bottom: 15px;">
              <el-input v-model="stu.username"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password" style="margin-right: 20px;margin-bottom: 15px;">
              <el-input v-model="stu.password" type="password"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="checkPass"style="margin-right: 20px;margin-bottom: 15px;">
              <el-input type="password" v-model="stu.checkPass" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="学号" prop="id" style="margin-right: 20px;margin-bottom: 20px;">
              <el-input v-model.number="stu.id" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="电话" prop="tel" style="margin-right: 20px;margin-bottom: 20px;">
              <el-input v-model.number="stu.tel" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="具体地址" prop="loc" style="margin-right: 20px;margin-bottom: 20px;">
              <el-input v-model="stu.loc" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="所住校区" style="margin-right: 20px;margin-bottom: 20px;" >
              <el-select v-model="stu.region" placeholder="校区">
                <el-option label="健翔桥" value="jx"></el-option>
                <el-option label="小营" value="xy"></el-option>
                <el-option label="沙河" value="sh"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" style="width:480px;margin-left: -80px;margin-bottom: 10px;" @click="submitForm('ruleForm')">注册</el-button>
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
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.stu.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      var checkNumber = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('学号不能为空'));
        }
        setTimeout(() => {
          if (!Number.isInteger(value)) {
            callback(new Error('请输入数字'));
          } else {
            if (value.toString().length != 10) {
              callback(new Error('学号长度应为10位'));
            } else {
              callback();
            }
          }
        }, 800);
      };
      var checkTel = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('电话号不能为空'));
        }
        setTimeout(() => {
          if (!Number.isInteger(value)) {
            callback(new Error('请输入数字'));
          } else {
            if (value.toString().length != 11) {
              callback(new Error('电话号码应长度为11位'));
            } else {
              callback();
            }
          }
        }, 800);
      };
      return{

        stu:{
          id:'',
          username:'',
          password:'',
          checkPass: '',
          tel:'',
          region:'jx',
          loc:''
        },

        rules:{
          username: [
            { required: true, message: '请填写用户名', trigger: 'blur' },
          ],
          password: [
            { required: true, message: '请填写密码', trigger: 'blur' }
          ],
          checkPass:[
            { required: true, validator: validatePass, trigger: 'blur' }
          ],
          id:[
            { required: true, validator: checkNumber, trigger: 'blur' }
          ],
          tel:[
            { required: true, validator: checkTel, trigger: 'blur'}
          ],
        }
      };
    },
    methods: {
      submitForm(formName) {
        const _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            axios.post('http://localhost:8181/student/save',_this.stu).then((resp)=>{
              if(resp.data.message != "failed"){
                console.log(resp);
                this.$router.push('/login');
              }
              else{
                console.log(resp);
                alert("创建失败，或许因为学号以被使用");
              }
            });
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      back(){
        this.$router.push('/login');
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
    height: 700px;
    font-size: 100px;
  }
  .loginmenu{
    margin-left: auto;
    margin-right: auto;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    border-radius: 30px;
    background-color: #E9EEF3;
    width: 480px;
  }
</style>
