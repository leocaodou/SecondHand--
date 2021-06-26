<template>
  <div>
    <el-container>
      <el-main max-heigth="800px">
        <el-table
          v-if="isLoad"
          :data="goods"
          style="width: 100%"
          height="800px"
          @selection-change="handleSelectionChange">
          <el-table-column
                type="selection"
                width="55"/>
          <el-table-column
            label="图片" prop="img" width="120px">
            <template slot-scope="scope">
              <img style="width:100px; max-heigth: 100px; display: block;margin: 0 px;" :src="scope.row.url">
            </template>
          </el-table-column>
          <el-table-column
            label="商品名称"
            prop="name"
            style="font-size: 100px;"
            align="center"
            width="200px">
          </el-table-column>
          <el-table-column
            label="商家"
            prop="username"
            width="150px">
           <!-- <template slot-scope="scope">
              <a @click="scope.row.">
            </template> -->
          </el-table-column>
          <el-table-column
            label="库存量"
            prop="num"
            width="80px">
          </el-table-column>
          <el-table-column
            label="商店评价"
            prop="score"
            align="center"
            width="200px">
            <template slot-scope="scope">
              <el-rate
                v-model="scope.row.score"
                disabled
                show-score
                text-color="#ff9900"
                score-template="{value}">
              </el-rate>
            </template>
          </el-table-column>
          <el-table-column
            label="单品价格"
            prop="price"
            width="180px">
            <template slot-scope="scope">
              ¥<span style="color: crimson;font-size: 30px;">{{scope.row.price}}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="已选数量"
            width="200px">
            <template slot-scope="scope">
              <template>
                <el-input-number v-if="scope.row.num != 0" v-model="scope.row.count" :min="1" :max="scope.row.num" size="mini" @change="resum"></el-input-number>
                <spen v-else style="color: red;">已卖完，请删除</spen>
              </template>
            </template>
          </el-table-column>
          <el-table-column
            label="金额"
            prop="price"
            width="180px">
            <template slot-scope="scope">
              ¥<span style="color: crimson;font-size: 30px;">{{scope.row.price*scope.row.count}}</span>
            </template>
          </el-table-column>
          <el-table-column
            align="right">
            <template slot-scope="scope">
              <el-button
                type="danger"
                @click="deleteCart(scope.$index)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-main>
      <el-footer style="margin-top: -15px;">
        <div style="text-align: right;">
          ¥<span style="color: red;font-size: 30px;">{{sum}}</span>
          <el-button v-if="sum!=0" type="warning" style="font-size: 30px;" @click="balance()">结算</el-button>
          <el-button v-else type="info" style="font-size: 30px;" disabled>结算</el-button>
        </div>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
  const axios = require('axios');
  var padaDate = function(value) {
    return value < 10 ? "0" + value : value;
  };
  export default {
    props:['fatherData'],
    async created() {
      const _this = this;
      await axios.get('http://localhost:8181/cart/findBuyerCart/' + _this.father).then(resp=>{
        _this.goods = resp.data;
      });
      for(let i = 0;i < _this.goods.length;i++){
        await axios.get('http://localhost:8181/goods/findById/' + _this.goods[i].goodsid).then(resp=>{
          _this.goods[i].url = 'http://localhost:8181/Image/getImage/' + _this.goods[i].goodsid;
          _this.goods[i].name = resp.data.name;
          _this.goods[i].num = resp.data.num;
          _this.goods[i].price = resp.data.price;
        });
      }
      for(let i = 0;i < _this.goods.length;i++){
        await axios.get('http://localhost:8181/student/findById/' + _this.goods[i].sellerid).then(resp=>{
          _this.goods[i].username = resp.data.username;
          _this.goods[i].score = resp.data.score;
        });
      }
      _this.isLoad = true;
    },
    data(){
      return{
        father: this.fatherData,
        isLoad:false,
        goods:[
          {
            id:1,
            name:'asd',
            username:'allla',
            image:'//img10.360buyimg.com/n7/jfs/t1/140760/8/18744/68771/5fd9c2abEe8fcfb9d/5909ebb2af0b1a43.jpg',
            score:5,
            price:123,
            num:10,
            count:4,
          },
          {
            id:2,
            name:'qwe',
            username:'asdwq',
            image:'//img13.360buyimg.com/n7/jfs/t1/175803/32/8898/60995/60993f9fE36480b84/cee58d0daf59e168.jpg',
            score:3,
            price:5234,
            num:12,
            count:10,
          },
          {
            id:3,
            name:'zxc',
            username:'adwqwe',
            image:'//img11.360buyimg.com/n7/jfs/t1/95128/18/5450/152970/5ded9966E5d766026/94b490f21ee67d62.jpg',
            score:2,
            price:234,
            num:1,
            count:1,
          },
          {
            id:4,
            name:'sdf',
            username:'adqwe',
            image:'//img12.360buyimg.com/n7/jfs/t1/195554/13/2677/119655/609c9b5dE88a6eba5/1dc4b332f03dbc23.jpg',
            score:4,
            price:12,
            num:3,
            count:2,
          },
          {
            id:5,
            name:'sdf',
            username:'adqwe',
            image:'//img12.360buyimg.com/n7/jfs/t1/195554/13/2677/119655/609c9b5dE88a6eba5/1dc4b332f03dbc23.jpg',
            score:4,
            price:12,
            num:3,
            count:1,
          },
          {
            id:6,
            name:'sdf',
            username:'adqwe',
            image:'//img12.360buyimg.com/n7/jfs/t1/195554/13/2677/119655/609c9b5dE88a6eba5/1dc4b332f03dbc23.jpg',
            score:4,
            price:12,
            num:3,
            count:1,
          },
          {
            id:7,
            name:'sdsadf',
            username:'adqweqwe',
            image:'//img12.360buyimg.com/n7/jfs/t1/195554/13/2677/119655/609c9b5dE88a6eba5/1dc4b332f03dbc23.jpg',
            score:4,
            price:12,
            num:3,
            count:2,
          },
        ],
        multipleSelection: [],
        sum:0,
      }
    },
    methods:{
      toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
        var sum = 0;
        for (var i=0;i<this.multipleSelection.length;i++)
        {
          sum = sum + this.multipleSelection[i].price * this.multipleSelection[i].count;
        }
        this.sum = sum;
      },
      resum(){
        var sum = 0;
        for (var i=0;i<this.multipleSelection.length;i++)
        {
          sum = sum + this.multipleSelection[i].price * this.multipleSelection[i].count;
        }
        this.sum = sum;
      },
      async deleteCart(index){
        let _this = this;
        _this.isLoad = false;
        await axios.delete('http://localhost:8181/cart/deleteById/' + _this.father + '/' + _this.goods[index].goodsid).then(resp=>{
          this.$message({
                    message: '删除成功',
                    type: 'success'
                  });
        });
        await axios.get('http://localhost:8181/cart/findBuyerCart/' + _this.father).then(resp=>{
          _this.goods = resp.data;
        });
        for(let i = 0;i < _this.goods.length;i++){
          await axios.get('http://localhost:8181/goods/findById/' + _this.goods[i].goodsid).then(resp=>{
            _this.goods[i].url = 'http://localhost:8181/Image/getImage/' + _this.goods[i].goodsid;
            _this.goods[i].name = resp.data.name;
            _this.goods[i].num = resp.data.num;
            _this.goods[i].price = resp.data.price;
          });
        }
        for(let i = 0;i < _this.goods.length;i++){
          await axios.get('http://localhost:8181/student/findById/' + _this.goods[i].sellerid).then(resp=>{
            _this.goods[i].username = resp.data.username;
            _this.goods[i].score = resp.data.score;
          });
        }
        await axios.get('http://localhost:8181/cart/count/' + _this.father).then(resp=>{
          var num1 = resp.data;
          this.$emit('getCartNum',num1);
        });
        _this.isLoad = true;
      },
      getDateTime(){
        var date = new Date();
              var year = date.getFullYear(); //存储年
              var month = padaDate(date.getMonth() + 1); //存储月份
              var day = padaDate(date.getDate()); //存储日期
              var hours = padaDate(date.getHours()); //存储时
              var minutes = padaDate(date.getMinutes()); //存储分
              var seconds = padaDate(date.getSeconds()); //存储秒
              //返回格式化后的日期
              return (year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds);
      },
      async balance(){
        let _this = this;
        for (var i=0;i<this.multipleSelection.length;i++){
          var bus = {};
          bus.sellerid = this.multipleSelection[i].sellerid;
          bus.buyerid = this.multipleSelection[i].buyerid;
          bus.goodsid = this.multipleSelection[i].goodsid;
          bus.date = this.getDateTime();
          bus.count = this.multipleSelection[i].count;
          await axios.post('http://localhost:8181/business/save',bus).then(resp=>{
            this.$message({
                      message: '购买成功',
                      type: 'success'
                    });
          });
          _this.isLoad = false;
          await axios.delete('http://localhost:8181/cart/deleteById/' + _this.father + '/' + bus.goodsid).then(resp=>{});
          await axios.get('http://localhost:8181/goods/findById/' + bus.goodsid ).then(resp=>{
            resp.data.num = resp.data.num - bus.count;
            axios.post('http://localhost:8181/goods/save/',resp.data).then(resp=>{});
          });
        }
        await axios.get('http://localhost:8181/cart/findBuyerCart/' + _this.father).then(resp=>{
          _this.goods = resp.data;
        });
        for(let i = 0;i < _this.goods.length;i++){
          await axios.get('http://localhost:8181/goods/findById/' + _this.goods[i].goodsid).then(resp=>{
            _this.goods[i].url = 'http://localhost:8181/Image/getImage/' + _this.goods[i].goodsid;
            _this.goods[i].name = resp.data.name;
            _this.goods[i].num = resp.data.num;
            _this.goods[i].price = resp.data.price;
          });
        }
        for(let i = 0;i < _this.goods.length;i++){
          await axios.get('http://localhost:8181/student/findById/' + _this.goods[i].sellerid).then(resp=>{
            _this.goods[i].username = resp.data.username;
            _this.goods[i].score = resp.data.score;
          });
        }
        await axios.get('http://localhost:8181/cart/count/' + _this.father).then(resp=>{
          var num1 = resp.data;
          this.$emit('getCartNum',num1);
        });
        this.multipleSelection = {};
        this.sum = 0;
        _this.isLoad = true;
      }
    }
  }
</script>

<style>
</style>
