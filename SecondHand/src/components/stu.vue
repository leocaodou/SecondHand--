<template>
  <div>
    <el-container>
      <el-header height="110px" class="header">
        <el-row>
          <el-col :span="6">
            <div style="font-weight:bold">
              <i class="el-icon-user"/>{{stu.username}}
              <i v-if="stu.isbusiness" class="el-icon-shopping-bag-1" style="font-size: 25px;color: gold;"/>
            </div>
            <div style="color: #7c899a;">
              <div>
                <div style="font-size: 20px;text-align: left;">
                  电话:{{stu.tel}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;校区:
                  <span v-if="stu.region=='jx'">健翔桥</span>
                  <span v-else-if="stu.region=='xy'">小营</span>
                  <span v-else>沙河</span>
                </div>
              </div>
              <div style="font-size: 20px;text-align: left;">
                学号:{{stu.id}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <span v-if="stu.loc!=''">详细地址:{{stu.loc}}</span>
              </div>
            </div>
          </el-col>
          <el-col :span="6" style="border-right: #000000 1px solid;height:104px;text-align: center;">
            <div><el-button @click="updateform()" type="primary" size="medium" style="margin-top: -10px;">修改信息</el-button></div>
            <div v-if="!stu.isbusiness" style="margin-top: -10px;"><el-button type="warning" size="medium" @click="aplyVisible=true">申请为商家</el-button></div>
            <div v-else style="margin-top: -10px;"><el-button type="infor" size="medium"disabled>申请为商家</el-button></div>
          </el-col>
          <el-col :span="12" style="font-size: 40px;">
            <div><span style="color:orangered;">积&nbsp;&nbsp;&nbsp;分:{{score}}</span></div>
            <span style="text-align: right;color: #42B983;">已卖出:¥{{earn}}</span>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <div style="text-align: center;">
          <el-menu
            default-active="/menu/stu/list"
            mode="horizontal"
            background-color="#efefef"
            text-color="#262626"
            active-text-color="#3089cc"
            :router="true"
            class="stumenu">
            <el-menu-item index="/menu/stu/list">订单</el-menu-item>
            <el-menu-item v-if="stu.isbusiness" index="/menu/stu/sell">正在出售中的二手商品</el-menu-item>
            <el-tooltip v-else effect="light" content="请先申请成为一名商家" placement="top">
              <el-menu-item index="/menu/stu/sell" disabled>正在出售中的二手商品</el-menu-item>
            </el-tooltip>
          </el-menu>
        </div>
        <router-view v-bind:fatherData="father"></router-view>
      </el-main>
    </el-container>
    
    <el-dialog title="修改信息" :visible.sync="updateVisible" :close-on-click-modal="false">
      <el-form :model="stutemple" ref="ruleForm" :rules="rules">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="stutemple.username"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="tel">
          <el-input v-model.number="stutemple.tel" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="所住校区" style="margin-right: 20px;margin-bottom: 20px;" >
          <el-select v-model="stutemple.region" placeholder="校区">
            <el-option label="健翔桥" value="jx"></el-option>
            <el-option label="小营" value="xy"></el-option>
            <el-option label="沙河" value="sh"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="具体地址" prop="loc">
          <el-input v-model="stutemple.loc" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="updateVisible = false;">取 消</el-button>
        <el-button type="primary" @click="Update('ruleForm')">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="申请成为商家" :visible.sync="aplyVisible" :close-on-click-modal="false">
      <el-upload
      	class="avatar-uploader"
      	:action="UploadLicenseUrl()"
      	:show-file-list="false"
        :on-change="Imagechange1"
      	:before-upload="beforeAvatarUpload"
        :auto-upload="false"
        :limit="1"
        ref="upload"
      >
      	<img v-if="imageUrl1" :src="imageUrl1" class="avatar" />
      	<i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <el-upload
      	class="avatar-uploader"
      	:action="UploadIdentityUrl()"
      	:show-file-list="false"
        :on-change="Imagechange2"
      	:before-upload="beforeAvatarUpload"
        :auto-upload="false"
        :limit="1"
        ref="upload2"
      >
      	<img v-if="imageUrl2" :src="imageUrl2" class="avatar" />
      	<i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <span style="text-align: left;">银行卡号：</span><el-input v-model="banknumber" autocomplete="off"></el-input>
      <div slot="footer" class="dialog-footer">
        <el-button @click="aplyVisible = false;">取 消</el-button>
        <el-button type="primary" @click="aplyfor()">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  const axios = require('axios');
  export default {
    props:['fatherData'],
    created(){
      let _this = this;
      axios.get('http://localhost:8181/student/findById/' + _this.father).then(resp=>{
        _this.stu = resp.data;
      });
      axios.get('http://localhost:8181/business/getScore/' + _this.father).then(resp=>{
        _this.score = resp.data;
      });
      axios.get('http://localhost:8181/business/getEarn/' + _this.father).then(resp=>{
        console.log(resp);
        if(resp.data == '')
          _this.earn = 0;
        else
          _this.earn = resp.data;
      });
    },
    data() {
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
        father:this.fatherData,
        updateVisible:false,
        aplyVisible:false,
        score:0,
        banknumber:'',
        earn:0,
        aplyid:null,
        imageUrl1:'',
        imageUrl2:'',
        stu:{
          id: 2018011252,
          username:'leocaodou',
          region:'jx',
          loc:'',
          tel:18090697657,
          isbusiness:true,
        },
        stutemple:{},
        rules:{
          username: [
            { required: true, message: '请填写用户名', trigger: 'blur' },
          ],
          tel:[
            { required: true, validator: checkTel, trigger: 'blur'}
          ],
        }
      }
    },
    methods:{
      Update(formName){
        let _this = this;
        this.$refs[formName].validate((valid) => {
          if(valid){
            axios.put('http://localhost:8181/student/update/',_this.stutemple).then(resp=>{
              if(resp.data == 'success'){
                _this.$message({
                          message: '修改成功',
                          type: 'success'
                        });
                _this.stu = JSON.parse(JSON.stringify(_this.stutemple));
                _this.updateVisible = false;
              }
              else{
                _this.$message.error('修改失败');
                _this.updateVisible = false;
              }
            });
          }
          else{
            console.log(_this.stutemple);
            console.log(_this.stu);
            console.log('error submit!!');
            return false;
          }
        })
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();

      },
      UploadLicenseUrl(){
        return 'http://localhost:8181/Image/uploadLicense/' + this.aplyid;
      },
      UploadIdentityUrl(){
        return 'http://localhost:8181/Image/uploadIdentity/' + this.aplyid;
      },
      updateform(){
        this.stutemple = JSON.parse(JSON.stringify(this.stu));
        this.stutemple.tel = parseInt(this.stutemple.tel);
        this.updateVisible = true;
      },
      Imagechange1(file, fileList) {
        var _this = this;
        var event = event || window.event;
        if(event.target.files != undefined){
          var file = event.target.files[0];
          var reader = new FileReader();
          //转base64
          reader.onload = function(e) {
           _this.imageUrl1 = e.target.result;
          }
          reader.readAsDataURL(file);
        }
      },
      Imagechange2(file, fileList) {
        var _this = this;
        var event = event || window.event;
        if(event.target.files != undefined){
          var file = event.target.files[0];
          var reader = new FileReader();
          //转base64
          reader.onload = function(e) {
           _this.imageUrl2 = e.target.result;
          }
          reader.readAsDataURL(file);
        }
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt5M = file.size / 1024 / 1024 < 5;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt5M) {
          this.$message.error('上传头像图片大小不能超过 5MB!');
        }
        return isJPG && isLt5M;
      },
      async aplyfor(){
        const _this = this;
        var aplyy ={};
        aplyy.stuid = _this.stu.id,
        aplyy.banknumber = _this.banknumber,
        console.log(aplyy)
        await axios.post('http://localhost:8181/aply/save/',aplyy).then(resp=>{
          _this.aplyid = resp.data.id;
        });
        _this.$refs.upload.submit();
        _this.$refs.upload2.submit();
        _this.aplyVisible = false;
      },
    }
  }
</script>

<style>
  .personal{

  }
  .header{
    text-align: left;
    font-size: 40px;
    margin: 10px;
    border-radius: 10px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

  }
  .stumenu
  {
    width: 250px;
    margin-right: auto;
    margin-left: auto;
    margin-top: -15px;
  }
  .avatar-uploader .el-upload {
     border: 1px dashed #d9d9d9;
     border-radius: 6px;
     cursor: pointer;
     position: relative;
     overflow: hidden;
   }
   .avatar-uploader .el-upload:hover {
     border-color: #409EFF;
   }
   .avatar-uploader-icon {
     font-size: 28px;
     color: #8c939d;
     width: 178px;
     height: 178px;
     line-height: 178px;
     text-align: center;
   }
   .avatar {
     width: 178px;
     height: 178px;
     display: block;
   }
</style>
