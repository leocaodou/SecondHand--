<template>
  <div style="text-align: left;">
    <div>
      <el-radio-group v-model="buy">
        <el-radio-button label="1">买得</el-radio-button>
        <el-radio-button label="2">卖出</el-radio-button>
      </el-radio-group>
    </div>
    <el-table
        v-if="buy=='1' && list1isOK"
        :data="list1"
        style="width: 1250px;margin-left: auto;margin-right: auto;">
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
          label="单品价格"
          prop="price"
          width="180px">
          <template slot-scope="scope">
            ¥<span style="color: crimson;font-size: 30px;">{{scope.row.price}}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="购买数量"
          width="100px"
          prop="count">
          <template slot-scope="scope">
            ×{{scope.row.count}}
          </template>
        </el-table-column>
        <el-table-column
          label="总消费"
          width="200px"
        >
        <template slot-scope="scope">
          ¥<span style="color: crimson;font-size: 30px;">{{scope.row.count * scope.row.price}}</span>
        </template>
        </el-table-column>
        <el-table-column
        label="购买时间"
        width="200px"
        prop="date">
        </el-table-column>
        <el-table-column
          align="right">
          <template slot-scope="scope">
            <el-button
              v-if="scope.row.iscomment == '0'"
              type="primary"
              @click="commentVisible = true,curgoodindex = scope.$index">评论</el-button>
            <span v-else> 已评论</span>
          </template>
        </el-table-column>
      </el-table>


      <el-table
          v-else-if="buy != '1' && list2isOK"
          :data="list2"
          style="width: 1250px;margin-left: auto;margin-right: auto;">
          <el-table-column
            label="图片" width="120px">
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
            label="买家"
            prop="username"
            width="150px">
           <!-- <template slot-scope="scope">
              <a @click="scope.row.">
            </template> -->
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
            label="卖出数量"
            width="200px"
            prop="count">
            <template slot-scope="scope">
              ×{{scope.row.count}}
            </template>
          </el-table-column>
          <el-table-column
            label="总收入"
            width="200px"
          >
          <template slot-scope="scope">
            ¥<span style="color: crimson;font-size: 30px;">{{scope.row.count*scope.row.price}}</span>
          </template>
          </el-table-column>
          <el-table-column
          label="卖出时间"
          width="200px"
          prop="date">
          </el-table-column>

        </el-table>

        <el-dialog title="评论" :visible.sync="commentVisible">
          <el-form>
            <el-rate
              v-model="comment.score"
              show-text>
            </el-rate>
            <el-form-item label="评论内容" >
              <el-input
                type="textarea"
                placeholder="发表一个友善的评论吧"
                v-model="comment.content"
                maxlength="20"
                show-word-limit
                :autosize="{ minRows: 2, maxRows: 4}"
              />
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="commentVisible = false">取 消</el-button>
            <el-button type="primary" @click="submitComment()">确 定</el-button>
          </div>
        </el-dialog>
  </div>
</template>

<script>
  const axios = require('axios');
  var padaDate = function(value) {
    return value < 10 ? "0" + value : value;
  };
  export default{
    props:['fatherData'],
    async created() {
      const _this = this;
      await axios.get('http://localhost:8181/business/findBuyerBusiness/' + _this.father).then(resp=>{
        _this.list1 = resp.data;
      });
      for(let i = 0;i < _this.list1.length;i++){
        _this.list1[i].url = 'http://localhost:8181/Image/getImage/'+_this.list1[i].goodsid;
      }
      for(let i = 0;i < _this.list1.length;i++){
        await axios.get('http://localhost:8181/goods/findById/' + _this.list1[i].goodsid).then(resp=>{
          _this.list1[i].name = resp.data.name;
          _this.list1[i].price = resp.data.price;
        });
      }
      for(let i = 0;i < _this.list1.length;i++){
        await axios.get('http://localhost:8181/student/findById/' + _this.list1[i].sellerid).then(resp=>{
          _this.list1[i].username = resp.data.username;
        });
      }
      _this.list1isOK = 1;
      await axios.get('http://localhost:8181/business/findSellerBusiness/' + _this.father).then(resp=>{
        _this.list2 = resp.data;
      });
      for(let i = 0;i < _this.list2.length;i++){
        _this.list2[i].url = 'http://localhost:8181/Image/getImage/'+_this.list2[i].goodsid;
      }
      for(let i = 0;i < _this.list2.length;i++){
        await axios.get('http://localhost:8181/goods/findById/' + _this.list2[i].goodsid).then(resp=>{
          _this.list2[i].name = resp.data.name;
          _this.list2[i].price = resp.data.price;
        });
      }
      for(let i = 0;i < _this.list2.length;i++){
        await axios.get('http://localhost:8181/student/findById/' + _this.list2[i].buyerid).then(resp=>{
          _this.list2[i].username = resp.data.username;
        });
      }
      _this.list2isOK = 1;
    },
    data() {
      return{
        buy:'1',
        list1isOK:0,
        list2isOK:0,
        curgoodindex: 0,
        commentVisible: false,
        father:this.fatherData,
        list1:[
        ],
        list2:[
        ],
        comment:{
          content:'',
          score:0,
        }
      }
    },
    methods:{
      dataFormat(value) {
        var year = value.substr(0, 4)
        var month = value.substr(5, 2)
        var day = value.substr(8, 2)
        var hour = value.substr(11, 2)
        var min = value.substr(14, 2)
        var second = value.substr(17,2)
        return year + "-" + month + "-" + day + " " + hour + ":" + min + ":"+second
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
      submitComment(){
        var _this = this;
        console.log(this.father);
        var comment1 = {};
        comment1.content = this.comment.content;
        comment1.id = this.father;
        comment1.time = this.getDateTime();
        comment1.goodsid = this.list1[this.curgoodindex].goodsid;
        comment1.sellerid = this.list1[this.curgoodindex].sellerid;
        comment1.score = this.comment.score;
        console.log(comment1);
        this.list1[this.curgoodindex].iscomment = 1;
        var business = this.list1[this.curgoodindex]
        axios.post('http://localhost:8181/comments/save',comment1).then(resp=>{
          this.$message({
                    message: '评论成功',
                    type: 'success'
                  });
          _this.comment.content = '';
          _this.comment.score = 0;
          _this.Visible=false;
        });
      },
    }
  }
</script>

<style>
</style>
