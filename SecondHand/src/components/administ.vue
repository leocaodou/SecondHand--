<template>
  <div style="text-align: center;">
    <i class="el-icon-view" style="margin-bottom: 20px;font-size: 80px;"></i>
    <el-tabs type="border-card" style="margin-left: 150px;margin-right: 150px;">
      <el-tab-pane label="商品管理">
        <el-table
            :data="goods"
            style="width: 100%"
            v-if="isAreaOK">
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
              label="单品价格"
              prop="price"
              width="250px">
              <template slot-scope="scope">
                ¥<span style="color: crimson;font-size: 30px;">{{scope.row.price}}</span>
              </template>
            </el-table-column>
            <el-table-column
              align="right">
              <template slot-scope="scope">
                <el-button
                  type="primary"
                  @click="updateDialog(scope.$index)">修改</el-button>
              </template>
            </el-table-column>
          </el-table>
      </el-tab-pane>

      <el-tab-pane label="用户管理">
        <el-table
            :data="stu"
            style="width: 100%"
            v-if="isStuOK">
            <el-table-column
              label="用户名"
              prop="username"
              style="font-size: 100px;"
              align="center"
              width="200px">
            </el-table-column>
            <el-table-column
              label="学号"
              prop="id"
              width="180px"
              align="center">
            </el-table-column>
            <el-table-column
              label="校区"
              width="100px">
              <template slot-scope="scope">
                <span v-if="scope.row.region == 'jx'">健翔桥</span>
                <span v-else-if="scope.row.region == 'xy'">小营</span>
                <span v-else>沙河</span>
              </template>
            </el-table-column>
            <el-table-column
              label="具体位置"
              prop="loc"
              width="200px">
            </el-table-column>
            <el-table-column
              label="电话号码"
              prop="tel"
              width="180px"
              align="center">
            </el-table-column>
            <el-table-column
              label="是否注册为商家"
              width="80px"
              align="center">
              <template slot-scope="scope">
                <span v-if="scope.row.isbusiness == 0">否</span>
                <span v-else>是</span>
              </template>
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
              align="right">
              <template slot-scope="scope">
                <el-button
                  @click="updateform(scope.$index)">修改</el-button>
                <el-button
                  @click="curstuIndex=scope.$index,deleteVisible = true" type="danger">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
      </el-tab-pane>

      <el-tab-pane label="商家申请">
        <el-table
            :data="aply"
            style="width: 100%"
            v-if="isAplyOK">
            <el-table-column
              label="营业执照" prop="img" width="120px">
              <template slot-scope="scope">
                <el-image style="width:100px; max-heigth: 100px; display: block;margin: 0 px;" :src="scope.row.license" :preview-src-list="getlist(scope.row.license)"/>
              </template>
            </el-table-column>
            <el-table-column
              label="身份证" prop="img" width="120px">
              <template slot-scope="scope">
                <el-image style="width:100px; max-heigth: 100px; display: block;margin: 0 px;" :src="scope.row.identity" :preview-src-list="getlist(scope.row.identity)" />
              </template>
            </el-table-column>
            <el-table-column
              label="商家用户名"
              prop="username"
              style="font-size: 100px;"
              align="center"
              width="200px">
            </el-table-column>
            <el-table-column
              label="学号"
              prop="stuid"
              style="font-size: 100px;"
              align="center"
              width="200px">
            </el-table-column>
            <el-table-column
              label="电话"
              prop="tel"
              style="font-size: 100px;"
              align="center"
              width="200px">
            </el-table-column>
            <el-table-column
              label="银行账户"
              prop="banknumber"
              width="80px">
            </el-table-column>
            <el-table-column
              align="right">
              <template slot-scope="scope">
                <el-button
                  @click="approve(scope.$index,1)" type="primary">批准</el-button>
                <el-button
                  @click="approve(scope.$index,0)" type="danger">不批准</el-button>
              </template>
            </el-table-column>
          </el-table>
      </el-tab-pane>
    </el-tabs>


    <el-dialog title="修改商品信息" :visible.sync="updategoodsVisible">
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
      	<img :src="curimageUrl" class="avatar" />
      </el-upload>
      <el-form :model="curgood">
        <el-form-item label="商品名称" >
          <el-input v-model="curgood.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="价格">
          <el-input v-model="curgood.price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="数量">
          <el-input v-model="curgood.num" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="二手商品描述">
          <el-input
            type="textarea"
            v-model="curgood.des"
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
        <el-button type="primary" @click="Updatestu('ruleForm')">确 定</el-button>
      </div>
    </el-dialog>


    <el-dialog
      title="提示"
      :visible.sync="deleteVisible"
      width="40%"
      >
      <h1>确定要删除此用户？此操作将会删除其所有信息(包括发布的产品和订单)</h1>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deleteVisible = false">取 消</el-button>
        <el-button type="primary" @click="deleteAll()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script scoped>
  const axios = require('axios');
  export default{
    async created() {
      const _this = this;
      await axios.get('http://localhost:8181/goods/findAll').then(resp=>{
        _this.goods = resp.data;
      });
      for(let i = 0;i < _this.goods.length;i++){
        await axios.get('http://localhost:8181/student/findById/' + _this.goods[i].id).then(resp=>{
          _this.goods[i].username = resp.data.username;
          _this.goods[i].url = 'http://localhost:8181/Image/getImage/' + _this.goods[i].goodsid;
        })
      }
      this.isAreaOK = true;
      await axios.get('http://localhost:8181/student/findAll').then(resp=>{
        _this.stu = resp.data;
      });
      this.isStuOK = true;

      await axios.get('http://localhost:8181/aply/findAll').then(resp=>{
        _this.aply = resp.data;
      });
      for(let i = 0;i < _this.aply.length;i++){
        _this.aply[i].license = 'http://localhost:8181/Image/getLicenseImage/' + _this.aply[i].id;
        _this.aply[i].identity = 'http://localhost:8181/Image/getIdentityImage/' + _this.aply[i].id;
        await axios.get('http://localhost:8181/student/findById/' + _this.aply[i].stuid).then(resp=>{
          _this.aply[i].username = resp.data.username;
          _this.aply[i].tel = resp.data.tel;
        })
      }
      this.isAplyOK = true;
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
      return {
        isAreaOK:false,
        isStuOK:false,
        isAplyOK:false,
        updategoodsVisible:false,
        updateVisible:false,
        deleteVisible:false,
        curimageUrl:'',
        curstuIndex:0,
        rad:0,
        stu:[],
        goods:[],
        aply:[],
        curgood:{},
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
      getlist(url){
        var list = new Array;
        list.push(url);
        return list;
      },
      UploadUrl(){
        return 'http://localhost:8181/Image/upload/' + this.curgood.goodsid;
      },
      Imagechange(file, fileList) {
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
      updateDialog(index){
        this.curgood = JSON.parse(JSON.stringify(this.goods[index]));
        this.updategoodsVisible = true;
        this.curimageUrl = this.goods[index].url;
      },
      updateform(index){
        this.curstuIndex = index;
        this.stutemple = JSON.parse(JSON.stringify(this.stu[index]));
        this.stutemple.tel = parseInt(this.stutemple.tel);
        this.updateVisible = true;
      },
      Updatestu(formName){
        let _this = this;
        this.$refs[formName].validate((valid) => {
          if(valid){
            axios.put('http://localhost:8181/student/update/',_this.stutemple).then(resp=>{
              if(resp.data == 'success'){
                _this.$message({
                          message: '修改成功',
                          type: 'success'
                        });
                _this.stu[_this.curstuIndex] = JSON.parse(JSON.stringify(_this.stutemple));
                _this.updateVisible = false;
              }
              else{
                _this.$message.error('修改失败');
                _this.updateVisible = false;
              }
            });
          }
          else{
            console.log('error submit!!');
            return false;
          }
        })
      },
      async deleteAll(){
        const _this = this;
        await axios.delete('http://localhost:8181/aply/deleteBystuid/' + _this.stu[_this.curstuIndex].id).then(resp=>{});
        await axios.delete('http://localhost:8181/business/deleteBysellerid/' + _this.stu[_this.curstuIndex].id).then(resp=>{});
        await axios.delete('http://localhost:8181/business/deleteBybuyerid/' + _this.stu[_this.curstuIndex].id).then(resp=>{});
        await axios.delete('http://localhost:8181/cart/deleteBysellerid/' + _this.stu[_this.curstuIndex].id).then(resp=>{});
        await axios.delete('http://localhost:8181/cart/deleteBybuyerid/' + _this.stu[_this.curstuIndex].id).then(resp=>{});
        await axios.delete('http://localhost:8181/comments/deleteByid/' + _this.stu[_this.curstuIndex].id).then(resp=>{});
        await axios.delete('http://localhost:8181/comments/deleteBysellerid/' + _this.stu[_this.curstuIndex].id).then(resp=>{});
        await axios.delete('http://localhost:8181/goods/deleteByid/' + _this.stu[_this.curstuIndex].id).then(resp=>{});
        await axios.delete('http://localhost:8181/student/deleteByid/' + _this.stu[_this.curstuIndex].id).then(resp=>{});
        this.isStuOK = false;
        await axios.get('http://localhost:8181/student/findAll').then(resp=>{
          _this.stu = resp.data;
        });
        this.isStuOK = true;
        this.deleteVisible=false;
      },
      async Update(){
        delete this.curgood.url;
        const _this = this;
        await axios.post('http://localhost:8181/goods/save/',_this.curgood).then(resp=>{
          if(_this.ImgisChanged)
            _this.$refs.upload.submit();
          if(resp.data.message == 'success'){
            this.$message({
              message: '修改成功',
              type: 'success'
            });
          }
          _this.updategoodsVisible = false;
        });
        this.rad = Math.ceil(Math.random()*10);
        _this.isAreaOK = false;
        await axios.get('http://localhost:8181/goods/findAll').then(resp=>{
          _this.goods = resp.data;
        });
        for(let i = 0 ;i < _this.goods.length;i++){
          _this.goods[i].username = resp.data.username;
          _this.goods[i].url = 'http://localhost:8181/Image/getImage/' + _this.goods[i].goodsid;
        }
        _this.isAreaOK = true;
      },
      async approve(index,i){
        const _this = this;
        var stuapp={};

        if(i == 1){
          await axios.get('http://localhost:8181/student/findById/'+ _this.aply[index].stuid).then(resp=>{
            console.log(resp.data);
            stuapp = resp.data;
          });
          stuapp.isbusiness = 1;

          await axios.put('http://localhost:8181/student/update',stuapp).then(resp=>{
            this.$message({
              message: '批准成功',
              type: 'success'
            });
          });
        }
        console.log(stuapp);
        await axios.delete('http://localhost:8181/aply/deleteBystuid/' + stuapp.id).then(resp=>{});
        this.isAplyOK = false;
        await axios.get('http://localhost:8181/aply/findAll').then(resp=>{
          _this.aply = resp.data;
        });
        for(let i = 0;i < _this.aply.length;i++){
          _this.aply[i].license = 'http://localhost:8181/Image/getLicenseImage/' + _this.aply[i].id;
          _this.aply[i].identity = 'http://localhost:8181/Image/getIdentityImage/' + _this.aply[i].id;
          await axios.get('http://localhost:8181/student/findById/' + _this.aply[i].stuid).then(resp=>{
            _this.aply[i].username = resp.data.username;
            _this.aply[i].tel = resp.data.tel;
          })
        }
        this.isAplyOK = true;
      }
    }
  }
</script>

<style>
</style>
