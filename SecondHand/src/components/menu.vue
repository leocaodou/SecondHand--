<template>
  <div>
    <el-container>
      <el-aside width="200px">
        <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">
          <el-radio-button :label="false">展开</el-radio-button>
          <el-radio-button :label="true">收起</el-radio-button>
        </el-radio-group>
        <el-menu default-active="/menu/goods" :collapse="isCollapse" :router="true" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
          <el-menu-item index="/menu/goods">
            <i class="el-icon-goods"></i>
            <span slot="title">商品</span>
          </el-menu-item>
          <el-menu-item index="/menu/cart"  @click="islogin()">
            <el-badge :value=cartnum class="item">
              <i class="el-icon-shopping-cart-1"></i>
            </el-badge>
              <span slot="title">购物车</span>
          </el-menu-item>
          <el-menu-item index="/menu/stu" @click="islogin()">
            <i class="el-icon-s-custom"></i>
            <span slot="title">个人中心</span>
          </el-menu-item>
        </el-menu>
      </el-aside>


      <el-container>
        <el-header class="head" style="height: 60px;">
          <i class="el-icon-goods"  ></i>校园二手平台
          <span v-if="stu.id == -1" style="float:right; font-size: 100px;">
            <el-link type="warning" @click="tologin() "><i class="el-icon-user-solid el-icon--left"></i>您还没有登录</el-link>
          </span>
          <span v-else style="float:right; font-size: 20px;color:#072179;margin-top: 20px;">
            欢迎回来！{{this.stu.username}}
          </span>
        </el-header>
        <el-main>
          <router-view v-bind:fatherData="stu.id" @getCartNum="setCartNum"></router-view>
        </el-main>
      </el-container>
    </el-container>


  </div>
</template>

<script>
const axios = require('axios');
export default {
  data() {
        return {
          isCollapse: true,
          cartnum: 0,
          drawer: false,
          stu:{
            id: -1,
            username:'leocaodou',
          },
        }
      },
      created(){
        let _this = this;
        if(this.$route.params.id != undefined){
          this.stu.id = this.$route.params.id;
          this.stu.username = this.$route.params.username;
          axios.get('http://localhost:8181/cart/count/' + _this.stu.id).then(resp=>{
            var num1 = resp.data;
            _this.cartnum = num1;
          });
        }

      },
      methods: {
        tologin(){
          this.$router.push('/login');
        },
        page(currentpage){

        },
        commentpage(currentpage){

        },
        handleClose(done) {
          this.$confirm('确认关闭？')
            .then(_ => {
              done();
            })
            .catch(_ => {});
        },
        islogin(){
          if(this.stu.id == -1)
            this.$router.push('/login');

        },
        setCartNum(num){
          this.cartnum = num;
        }
      }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .el-menu-vertical-demo:not(.el-menu--collapse) {
      width: 200px;
      min-height: 400px;
    }
  .el-carousel__item h3 {
      color: #475669;
      font-size: 14px;
      opacity: 0.75;
      line-height: 150px;
      margin: 0;
    }

    .el-carousel__item:nth-child(2n) {
       background-color: #99a9bf;
    }

    .el-carousel__item:nth-child(2n+1) {
       background-color: #d3dce6;
    }
  .head{
     text-align:left;
     box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
     font-size: 50px;
     background-color: #ffffff;
     color: #16a9f5;
     line-height: 20px;
     margin-bottom: 10px;
  }
  .titlemenu{
    font-size: 70px;
  }
  .el-main {
    color: #030e33;
    text-align: center;
    height: 100%;
    font-size: 20px;
    padding: 10px;
    height: 100%;
  }
  .item {
    margin-top: 0px;
    margin-right: 0px;
  }
</style>
