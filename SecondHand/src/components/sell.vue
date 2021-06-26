<template>
  <div>
    <el-table
        v-if="isSellingOK"
        :data="selling"
        style="width: 1250px;margin-left: auto;margin-right: auto;">
        <el-table-column
          label="图片" prop="img" width="120px">
          <template slot-scope="scope">
            <img style="width:100px; max-heigth: 100px; display: block;margin: 0 px;" :src="scope.row.url + '?' + rad" >
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
          label="单品价格"
          prop="price"
          width="250px">
          <template slot-scope="scope">
            ¥<span style="color: crimson;font-size: 30px;">{{scope.row.price}}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="剩余数量"
          width="200px"
          prop="num">
        </el-table-column>
        <el-table-column
        align="right">
          <template slot="header">
            <el-button type="warning" @click="addgoodsVisible=true">添加二手商品</el-button>
          </template>
          <template slot-scope="scope">
            <el-button
              type="primary"
              @click="updateDialog(scope.$index)">修改</el-button>
            <el-button
              type="danger"
              @click="delete(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      
      
      <el-dialog title="添加商品" :visible.sync="addgoodsVisible">
        <el-upload
        	class="avatar-uploader"
        	:action="UploadUrl()"
        	:show-file-list="false"
          :on-change="Imagechange"
        	:before-upload="beforeAvatarUpload"
          :auto-upload="false"
          :limit="1"
          ref="upload"
        >
        	<img v-if="imageUrl" :src="imageUrl" class="avatar" />
        	<i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
        <el-form :model="goods">
          <el-form-item label="商品名称" >
            <el-input v-model="goods.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="价格">
            <el-input v-model="goods.price" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="数量">
            <el-input v-model="goods.num" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="二手商品描述">
            <el-input
              type="textarea"
              v-model="goods.des"
              maxlength="100"
              show-word-limit
            />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="addgoodsVisible = false">取 消</el-button>
          <el-button type="primary" @click="Upload()">确 定</el-button>
        </div>
      </el-dialog>

      <el-dialog title="修改商品信息" :visible.sync="updategoodsVisible">
        <el-upload
        	class="avatar-uploader"
        	:action="UploadUrl2()"
        	:show-file-list="false"
          :on-change="Imagechange2"
        	:before-upload="beforeAvatarUpload"
          :auto-upload="false"
          :limit="1"
          ref="upload2"
        >
        	<img :src="curimageUrl" class="avatar" />
        </el-upload>
        <el-form :model="curselling">
          <el-form-item label="商品名称" >
            <el-input v-model="curselling.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="价格">
            <el-input v-model="curselling.price" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="数量">
            <el-input v-model="curselling.num" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="二手商品描述">
            <el-input
              type="textarea"
              v-model="curselling.des"
              maxlength="100"
              show-word-limit
            />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="updategoodsVisible = false">取 消</el-button>
          <el-button type="primary" @click="Update()">确 定</el-button>
        </div>
      </el-dialog>
  </div>
</template>

<script>
  const axios = require('axios');
  export default{
    props:['fatherData'],
    async created() {
      console.log(1);
      this.rad = Math.ceil(Math.random()*10);
      const _this = this;
      await axios.get('http://localhost:8181/goods/findBySellerIdWithout0/' + _this.father).then(resp=>{
        _this.selling = resp.data;
      });
      for(let i = 0 ;i < _this.selling.length;i++){
        _this.selling[i].url = 'http://localhost:8181/Image/getImage/' + _this.selling[i].goodsid;
      }
      _this.isSellingOK = true;
    },
    data() {
      return {
        addgoodsVisible:false,
        imageUrl:'',
        rad:0,
        isSellingOK:false,
        father:this.fatherData,
        updategoodsVisible:false,
        ImgisChanged:false,
        curimageUrl:'',
        goods:{
          name:'',
          price:'',
          id:null,
          num:null,
          des:''
        },
        curselling:{},
        respgoodsid:null,
        selling:[
          {
            id:1,
            name:'asd',
            image:'//img10.360buyimg.com/n7/jfs/t1/140760/8/18744/68771/5fd9c2abEe8fcfb9d/5909ebb2af0b1a43.jpg',
            price:123,
            num:10,
          }
        ]
      }
    },
    methods:{
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
      UploadUrl(){
        return 'http://localhost:8181/Image/upload/' + this.respgoodsid;
      },
      UploadUrl2(){
        return 'http://localhost:8181/Image/upload/' + this.curselling.goodsid;
      },
      Imagechange(file, fileList) {
        var _this = this;
        var event = event || window.event;
        if(event.target.files != undefined){
          var file = event.target.files[0];
          var reader = new FileReader();
          //转base64
          reader.onload = function(e) {
           _this.imageUrl = e.target.result;
          }
          reader.readAsDataURL(file);
        }
      },
      async Upload(){
        const _this = this;
        _this.goods.id = _this.father;
        await axios.post('http://localhost:8181/goods/save/',_this.goods).then(resp=>{
          console.log(resp);
          _this.respgoodsid =  resp.data.data.goodsid;
        });
        _this.$refs.upload.submit();

        _this.addgoodsVisible = false;
      },
      updateDialog(index){
        this.curselling = JSON.parse(JSON.stringify(this.selling[index]));
        this.updategoodsVisible = true;
        this.curimageUrl = this.selling[index].url;
      },
      Imagechange2(file, fileList) {
        var _this = this;
        var event = event || window.event;
        if(event.target.files != undefined){
          this.ImgisChanged = true;
          var file = event.target.files[0];
          var reader = new FileReader();
          //转base64
          reader.onload = function(e) {
           _this.curimageUrl = e.target.result;
          }
          reader.readAsDataURL(file);
        }
      },
      async delete(index){
        this.curselling = JSON.parse(JSON.stringify(this.selling[index]));
        this.curselling.num = 0;
        await axios.post('http://localhost:8181/goods/save/',_this.curselling).then(resp=>{
          if(resp.data.message == 'success'){
            this.$message({
              message: '删除成功',
              type: 'success'
            });
          }
        });
        _this.isSellingOK = false;
        await axios.get('http://localhost:8181/goods/findBySellerIdWithout0/' + _this.father).then(resp=>{
          _this.selling = resp.data;
        });
        for(let i = 0 ;i < _this.selling.length;i++){
          _this.selling[i].url = 'http://localhost:8181/Image/getImage/' + _this.selling[i].goodsid;
        }
        _this.isSellingOK = true;
      },
      async Update(){
        delete this.curselling.url;
        const _this = this;
        await axios.post('http://localhost:8181/goods/save/',_this.curselling).then(resp=>{
          if(_this.ImgisChanged)
            _this.$refs.upload2.submit();
          if(resp.data.message == 'success'){
            this.$message({
              message: '成功',
              type: 'success'
            });
          }
          _this.updategoodsVisible = false;
        });
        this.rad = Math.ceil(Math.random()*10);
        _this.isSellingOK = false;
        await axios.get('http://localhost:8181/goods/findBySellerIdWithout0/' + _this.father).then(resp=>{
          _this.selling = resp.data;
        });
        for(let i = 0 ;i < _this.selling.length;i++){
          _this.selling[i].url = 'http://localhost:8181/Image/getImage/' + _this.selling[i].goodsid;
        }
        _this.isSellingOK = true;
      }
    },
  }
</script>

<style>
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
