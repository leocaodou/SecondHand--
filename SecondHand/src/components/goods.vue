<template>
  <div>
    <div>
      <el-carousel v-if="CarouselIsOk" trigger="click" height="150px" style="width: 800px; margin-left: auto;margin-right: auto;">
        <el-carousel-item v-for="(item,index) in first" :key="item.goodsid">
          <el-row>
            <el-col :span="6">
              <el-image :src="item.url" :preview-src-list="getlist(item.url)" style="width:150px; max-heigth: 150px; display: block;margin: 0 px;"/>
            </el-col>
            <el-col :span="18">
              <h1>{{item.name}}  仅要{{item.price}}元!</h1>
              <el-input-number v-model="numberTop[index]" :min="0" :max="item.num" size="mini"></el-input-number>
              <el-button
                @click="openDrawer(1,index)">查看详情</el-button>
              <el-button
                type="primary"
                @click="addCartTop(index)">加入购物车</el-button>
            </el-col>
          </el-row>
        </el-carousel-item>
      </el-carousel>
      <span style="font-size: 15px;">大家都想买的商品</span>
    </div>


    <el-input placeholder="请输入内容" v-model="search" class="input-with-select">
      </el-select>
      <el-button slot="append" icon="el-icon-search" @click="toSearch()"></el-button>
    </el-input>

    <el-table
        :data="goods"
        style="width: 100%"
        v-if="isAreaOk">
        <el-table-column
          label="图片" prop="img" width="120px">
          <template slot-scope="scope">
            <el-image style="width:100px; max-heigth: 100px; display: block;margin: 0 px;" :src="scope.row.url" :preview-src-list="getlist(scope.row.url)"/>
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
          width="150px"
          >
          <template slot-scope="scope">
            {{scope.row.username}}
          </template>
        </el-table-column>
        <el-table-column
          label="库存量"
          prop="num"
          width="80px">
        </el-table-column>
        <el-table-column
          label="商店评价"
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
          width="250px">
          <template slot-scope="scope">
            ¥<span style="color: crimson;font-size: 30px;">{{scope.row.price}}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="选购数量"
          width="200px">
          <template slot-scope="scope">
            <template>
              <el-input-number v-model.number="number[scope.$index]" :min="0" :max="scope.row.num" size="mini" controls-position="right"></el-input-number>
            </template>
          </template>
        </el-table-column>
        <el-table-column
          align="right">
          <template slot-scope="scope">
            <el-button
              @click="openDrawer(2,scope.$index)">查看详情</el-button>
            <el-button
              type="primary"
              @click="addCart(scope.$index)">加入购物车</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        background
        layout="prev, pager, next"
        :page-size = "4"
        :total="total"
        @current-change="page">
      </el-pagination>



      <el-drawer
        :visible.sync="drawer"
        :before-close="handleClose"
        size="38.2%"
        style="overflow: auto;"
        >
        <span slot="title"><h1>{{curGoods.name}}</h1></span>
        <el-row :gutter="20">
          <el-col :span="7"><el-image :src="curGoods.url" :preview-src-list="getlist(curGoods.url)" style="width:170px; max-heigth: 170px; display: block;margin: 0 px;"/></el-col>
          <el-col :span="13">
            <div style="background-color: lightgray;">&nbsp;&nbsp;&nbsp;价 格 :¥
              <span style="color: red;font-size: 30px;">{{curGoods.price}}</span>
            </div>
            <div >&nbsp;&nbsp;&nbsp;商家:{{curGoods.username}}</div>
            <div>
              <el-row >
                <el-col :span="8">&nbsp;&nbsp;&nbsp;商家评价:</el-col>
                <el-col :span="16">
                  <el-rate
                    v-model="curGoods.score"
                    disabled
                    show-score
                    text-color="#ff9900"
                    score-template="{value}">
                  </el-rate>
                </el-col>
              </el-row>
            </div>
            <el-input-number v-if="first != 1" v-model="number[numberIndex]" :min="0" :max="curGoods.num" size="mini"></el-input-number>
            <el-input-number v-else v-model="numberTop[numberIndex]" :min="0" :max="curGoods.num" size="mini"></el-input-number>
            <el-button
              v-if="first != 1"
              type="primary"
              @click="addCart(numberIndex)"
              style="width: 80%;margin-top: 10px;">加入购物车
            </el-button>
            <el-button
              v-else
              type="primary"
              @click="addCartTop(numberIndex)"
              style="width: 80%;margin-top: 10px;">加入购物车
            </el-button>
          </el-col>
        </el-row>
        <el-divider>商品描述</el-divider>
        <div class="des">&nbsp;&nbsp;&nbsp;&nbsp;{{curGoods.des}}</div>
        <el-divider>商品评论<el-button type="warning" size="mini" @click="Visible=true">我也来说一句</el-button></el-divider>
        <div v-if="loaddrawer" v-for="item in comments" :key="item.Commentid" class="comment">
          <el-row :gutter="10">
            <el-col :span="6" style="font-size: 20px; color: #767788;text-align: left;">
              <i class="el-icon-user" >{{item.username}}:</i>
            </el-col>
            <el-col :span="13">
              <div style="font-size: 16px;text-align: left;border-color:#000;border-width:1px;border-style:solid;margin: 2px;height: 63px;padding: 2px;">
                {{item.content}}
              </div>
            </el-col>
            <el-col :span="5">
              <div style="text-align: right;color: #99A9BF;font-size: 15px; margin-top: 28px;">{{dataFormat(item.time)}}</div>
            </el-col>
          </el-row>
        </div>
        <div style="text-align: center; margin-top: -10px;">
          <el-pagination
            layout="prev, pager, next"
            :page-size = "4"
            :total="CommentsTotal"
            @current-change="commentspage">
          </el-pagination>
        </div>
        <el-dialog title="评论" :visible.sync="Visible" append-to-body>
          <el-form>
            <el-form-item label="评论内容" >
              <el-input
                type="textarea"
                placeholder="发表一个友善的评论吧"
                v-model="CommentContent"
                maxlength="20"
                show-word-limit
                :autosize="{ minRows: 2, maxRows: 4}"
              />
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="Visible = false">取 消</el-button>
            <el-button type="primary" @click="submitComment()">确 定</el-button>
          </div>
        </el-dialog>
      </el-drawer>
  </div>
</template>

<script>
const axios = require('axios');
var padaDate = function(value) {
  return value < 10 ? "0" + value : value;
};
export default {
  props:['fatherData'],
  async created(){
    const _this = this;
    await axios.get('http://localhost:8181/goods/findAllWithout0/1/4').then(resp=>{
      _this.goods = resp.data.content;
      _this.total = resp.data.totalElements;
    });
    await axios.get('http://localhost:8181/goods/findTop4').then(resp=>{
      _this.first = resp.data;
    })
    for(let i = 0;i < _this.first.length;i++){
      await axios.get('http://localhost:8181/student/findById/' + _this.first[i].id).then(resp=>{
        _this.first[i].username = resp.data.username;
        _this.first[i].score = resp.data.score;
        _this.first[i].url = 'http://localhost:8181/Image/getImage/' + _this.first[i].goodsid;
      })
    }
    for(let i = 0;i < _this.goods.length;i++){
      await axios.get('http://localhost:8181/student/findById/' + _this.goods[i].id).then(resp=>{
        _this.goods[i].username = resp.data.username;
        _this.goods[i].score = resp.data.score;
        _this.goods[i].url = 'http://localhost:8181/Image/getImage/' + _this.goods[i].goodsid;
      })
    }
    for(let i = 0;i < _this.goods.length;i++){
      if(_this.fatherData == -1){
        _this.number[i]=0;
      }
      else{
        await axios.get('http://localhost:8181/cart/findById/' + _this.fatherData + '/' + _this.goods[i].goodsid).then(resp=>{
              if(resp.data.message == 'success'){
                console.log(_this.goods);
                var num = resp.data.data.count;
                _this.number[i] = num;
              }
              else
              {
                _this.number[i] = 0;
              }
            })
      }
    }

    for(let i = 0;i < _this.first.length;i++){
      if(_this.fatherData == -1){
        _this.numberTop[i]=0;
      }
      else{
        await axios.get('http://localhost:8181/cart/findById/' + _this.fatherData + '/' + _this.first[i].goodsid).then(resp=>{
              if(resp.data.message == 'success'){
                var num = resp.data.data.count;
                _this.numberTop[i] = num;
              }
              else
              {
                _this.numberTop[i] = 0;
              }
            })
      }
    }
    _this.CarouselIsOk = true;
    _this.isAreaOk = true;
  },
  data() {
        return {
          drawer: false,
          total: null,
          CommentsTotal: 0,
          father: this.fatherData,
          first:1,
          isAreaOk: false,
          number:[0,0,0,0],
          numberTop:[0,0,0,0],
          numberIndex:0,
          Visible:false,
          CommentContent: '',
          CarouselIsOk:false,
          loaddrawer:true,
          isSearching:false,
          search:'',
          curGoods:{
            goodsid:1,
            name:'asd',
            username:'allla',
            image:'//img10.360buyimg.com/n7/jfs/t1/140760/8/18744/68771/5fd9c2abEe8fcfb9d/5909ebb2af0b1a43.jpg',
            score:5,
            price:123,
            num:10,
            count:0,
            descirbe:'这个东西才用没有多久，但是我认为别人更有用，因此打算出了'
          },
          first:[
            {
              goodsid:1,
              name:'sasd',
              id:1,
              price:123,
              num:10,
            },
            {
              goodsid:2,
              name:'axcsd',
              id:1,
              price:1223,
              num:10,
            },
            {
              goodsid:3,
              name:'asd',
              id:1,
              price:1123,
              num:10,
            },
            {
              goodsid:4,
              name:'asd',
              id:1,
              price:1423,
              num:2,
            },
          ],
          goods:[
            {
              goodsid:1,
              name:'asd',
              id:1,
              score:5,
              price:123,
              num:10,
              count:0,
            },
            {
              goodsid:2,
              name:'qwe',
              id:1,
              score:3,
              price:5234,
              num:12,
              count:0,
            },
            {
              goodsid:3,
              name:'zxc',
              id:1,
              score:2,
              price:234,
              num:1,
              count:0,
            },
            {
              goodsid:4,
              name:'sdf',
              id:1,
              score:4,
              price:12,
              num:3,
              count:0,
            },
            {
              goodsid:5,
              name:'sdf',
              id:1,
              score:4,
              price:12,
              num:3,
              count:0,
            },
            {
              goodsid:6,
              name:'sdf',
              id:1,
              score:4,
              price:12,
              num:3,
              count:0,
            }
           ],
           comments:[
             {
               Commentid:1,
               content:'太贵了，我是学生，便宜点吧！',
               userid: 1234567890,
               username: 'asdw',
               time: '2021-5-14 15:55',
             },
             {
               Commentid:2,
               content:'好东西！！！想买！',
               userid: 1234567890,
               username: '啊十大伟大时代哇啊达瓦萨瓦',
               time: '2021-5-14 15:55',
             },
             {
               Commentid:3,
               content:'针不错啊',
               userid: 1234567890,
               username: 'asdw',
               time: '2021-5-14 15:55',
             },
             {
               Commentid:4,
               content:'隔壁比你便宜，你也降低啊价呗',
               userid: 1234567890,
               username: 'asdw',
               time: '2021-5-14 15:55',
             },
           ]
        }
      },
      methods: {
        tologin(){
          this.$router.push('/login');
        },
        async page(currentpage){
          const _this = this;
          _this.isAreaOk = false;
          if(!_this.isSearching){
            await axios.get('http://localhost:8181/goods/findAllWithout0/' + currentpage +'/4').then(resp=>{
              _this.goods = resp.data.content;
            })

            for(let i = 0;i < _this.goods.length;i++){
              await axios.get('http://localhost:8181/student/findById/' + _this.goods[i].id).then(resp=>{
                _this.goods[i].username = resp.data.username;
                _this.goods[i].url = 'http://localhost:8181/Image/getImage/' + _this.goods[i].goodsid;
              })
            }

            for(let i = 0;i < _this.goods.length;i++){
              if(_this.fatherData == -1){
                _this.number[i]=0;
              }
              else{
                await axios.get('http://localhost:8181/cart/findById/' + _this.fatherData + '/' + _this.goods[i].goodsid).then(resp=>{
                      if(resp.data.message == 'success'){
                        var num = resp.data.data.count;
                        _this.number[i] = num;
                      }
                      else
                      {
                        _this.number[i] = 0;
                      }
                    })
              }
            }
            for(let i = 0;i < _this.goods.length;i++){
              await axios.get('http://localhost:8181/student/findById/' + _this.goods[i].id).then(resp=>{
                _this.goods[i].score = resp.data.score;
              })
            }
          }
          else{
            await axios.get('http://localhost:8181/goods/findByNameWithout0/'+ _this.search + '/' + currentpage +'/4').then(resp=>{
              _this.goods = resp.data.content;
            })
            
            for(let i = 0;i < _this.goods.length;i++){
              await axios.get('http://localhost:8181/student/findById/' + _this.goods[i].id).then(resp=>{
                _this.goods[i].username = resp.data.username;
                _this.goods[i].score = resp.data.score;
                _this.goods[i].url = 'http://localhost:8181/Image/getImage/' + _this.goods[i].goodsid;
              })
            }
            
            for(let i = 0;i < _this.goods.length;i++){
              if(_this.fatherData == -1){
                _this.number[i]=0;
              }
              else{
                await axios.get('http://localhost:8181/cart/findById/' + _this.fatherData + '/' + _this.goods[i].goodsid).then(resp=>{
                      if(resp.data.message == 'success'){
                        var num = resp.data.data.count;
                        _this.number[i] = num;
                      }
                      else
                      {
                        _this.number[i] = 0;
                      }
                    })
              }
            }
          }
          _this.isAreaOk = true;
        },
        async commentspage(currentpage){
          const _this = this;
          this.loaddrawer=false;
          await axios.get('http://localhost:8181/comments/findByGoodsid/' + _this.curGoods.goodsid + '/' + currentpage +'/4').then(resp=>{
            _this.comments = resp.data.content;
          })
          for(let i = 0;i < _this.comments.length;i++){
            await axios.get('http://localhost:8181/student/findById/' + _this.comments[i].id).then(resp=>{
              _this.comments[i].username = resp.data.username;
            })
          }
          this.loaddrawer=true;
        },
        handleClose(done) {
          this.$confirm('确认关闭？')
            .then(_ => {
              done();
            })
            .catch(_ => {});
        },
        async openDrawer(x,index){
          this.first = x;
          if(x == 1)
            this.curGoods = this.first[index];
          else
            this.curGoods = this.goods[index];
          this.numberIndex = index;
          const _this = this;
          await axios.get('http://localhost:8181/comments/findByGoodsid/' + _this.curGoods.goodsid + '/1/4').then(resp=>{
            _this.comments = resp.data.content;
            _this.CommentsTotal = resp.data.totalElements;
          });
          for(let i = 0;i < _this.comments.length;i++){
            await axios.get('http://localhost:8181/student/findById/' + _this.comments[i].id).then(resp=>{
              _this.comments[i].username = resp.data.username;
            })
          }
          this.drawer = true;
        },
        addCart(index){
          var cart = {};
          cart.buyerid = this.father;
          cart.goodsid = this.goods[index].goodsid;
          cart.sellerid = this.goods[index].id;
          cart.count = this.number[index];
          if(cart.count == 0){
            this.$message.error('请先选择数量');
            return;
          }
          else if(cart.buyerid == cart.sellerid){
            this.$message.error('不可以购买自己卖的东西');
            return;
          }
          axios.post('http://localhost:8181/cart/save',cart).then(resp=>{
            console.log(resp);
            if(resp.data.message == 'success'){
              this.$message({
                        message: '添加成功',
                        type: 'success'
                      });
              axios.get('http://localhost:8181/cart/count/' + cart.buyerid).then(resp=>{
                var num1 = resp.data;
                this.$emit('getCartNum',num1);
              });
            }
            else{
              this.$message.error('添加失败');
            }
          });
        },

        addCartTop(index){
          var cart = {};
          cart.buyerid = this.father;
          cart.goodsid = this.first[index].goodsid;
          cart.sellerid = this.first[index].id;
          cart.count = this.numberTop[index];
          if(cart.count == 0){
            this.$message.error('请先选择数量');
            return;
          }
          else if(cart.buyerid == cart.sellerid){
            this.$message.error('不可以购买自己卖的东西');
            return;
          }
          axios.post('http://localhost:8181/cart/save',cart).then(resp=>{
            if(resp.data.message == 'success'){
              this.$message({
                        message: '添加成功',
                        type: 'success'
                      });
              axios.get('http://localhost:8181/cart/count/' + cart.buyerid).then(resp=>{
                var num1 = resp.data;
                this.$emit('getCartNum',num1);
              });
            }
            else{
              this.$message.error('添加失败');
            }
          });
        },
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
        getlist(url){
          var list = new Array;
          list.push(url);
          return list;
        },
        submitComment(){
          var _this = this;
          console.log(this.father);
          if(this.father == -1){
            this.$message.error('请你先登录');
            return;
          }
          var comment = {};
          comment.content = this.CommentContent;
          comment.id = this.father;
          comment.time = this.getDateTime();
          comment.goodsid = this.curGoods.goodsid;
          comment.sellerid = this.curGoods.id;
          axios.post('http://localhost:8181/comments/save',comment).then(resp=>{
            console.log(resp);
            _this.CommentContent = '';
            _this.Visible=false;
          });
        },
        async toSearch(){
          const _this = this;
          if(_this.search != ''){
            _this.isSearching = true;
            _this.isAreaOk = false;
            await axios.get('http://localhost:8181/goods/findByNameWithout0/' + _this.search + '/1/4').then(resp=>{
              _this.goods = resp.data.content;
              _this.total = resp.data.totalElements;
            });
            for(let i = 0;i < _this.goods.length;i++){
              await axios.get('http://localhost:8181/student/findById/' + _this.goods[i].id).then(resp=>{
                _this.goods[i].username = resp.data.username;
                _this.goods[i].score = resp.data.score;
                _this.goods[i].url = 'http://localhost:8181/Image/getImage/' + _this.goods[i].goodsid;
              })
            }
            for(let i = 0;i < _this.goods.length;i++){
              if(_this.fatherData == -1){
                _this.number[i]=0;
              }
              else{
                await axios.get('http://localhost:8181/cart/findById/' + _this.fatherData + '/' + _this.goods[i].goodsid).then(resp=>{
                      if(resp.data.message == 'success'){
                        console.log(_this.goods);
                        var num = resp.data.data.count;
                        _this.number[i] = num;
                      }
                      else
                      {
                        _this.number[i] = 0;
                      }
                    })
              }
            }
            _this.isAreaOk = true;
          }
          else{
            _this.isSearching = false;
            _this.isAreaOk = false;
            await axios.get('http://localhost:8181/goods/findAllWithout0/1/4').then(resp=>{
              _this.goods = resp.data.content;
              _this.total = resp.data.totalElements;
            });
            for(let i = 0;i < _this.goods.length;i++){
              await axios.get('http://localhost:8181/student/findById/' + _this.goods[i].id).then(resp=>{
                _this.goods[i].username = resp.data.username;
                _this.goods[i].score = resp.data.score;
                _this.goods[i].url = 'http://localhost:8181/Image/getImage/' + _this.goods[i].goodsid;
              })
            }
            for(let i = 0;i < _this.goods.length;i++){
              if(_this.fatherData == -1){
                _this.number[i]=0;
              }
              else{
                await axios.get('http://localhost:8181/cart/findById/' + _this.fatherData + '/' + _this.goods[i].goodsid).then(resp=>{
                      if(resp.data.message == 'success'){
                        console.log(_this.goods);
                        var num = resp.data.data.count;
                        _this.number[i] = num;
                      }
                      else
                      {
                        _this.number[i] = 0;
                      }
                    })
              }
            }
            _this.isAreaOk = true;
          }
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
  .input-with-select{
      background-color: #ececec;
      width: 66%;
    }
    .el-carousel__item:nth-child(2n) {
       background-color: #99a9bf;
    }

    .el-carousel__item:nth-child(2n+1) {
       background-color: #d3dce6;
    }
  .titlemenu{
    font-size: 70px;
  }
  .item {
    margin-top: 0px;
    margin-right: 0px;
  }
  .des {
    border-radius: 6px;
    font-size: 20px;
    width:95%;
    height: 200px;
    text-align: left;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    margin-left: auto;
    margin-right: auto;
    padding: 5px;
  }
  .comment{
    border-color:#000;
    border-width:1px;
    border-style:solid;
    margin: 10px;
    height: 75px;
  }
</style>
