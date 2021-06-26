<template>
    <div class="" id="box">
        <ul>
            <li v-for="v in goodList">
                <img v-bind:src="v.imageUrl" alt="">
                <div class="goodnamed" >{{v.name}}</div>
                <div class="goodprice" >{{v.price}}元</div>
                <el-button style="line-height: 20px" @click="shoppingcar(v.name)">加入购物车</el-button>
            </li>
        </ul>
        <el-dialog :visible.sync=dialogFormVisible>
            <el-image :src=gooddetails.imageUrl></el-image>
            <div style="height: 20px">{{gooddetails.name}}</div>
            <div style="height: 20px">{{gooddetails.price}}</div>
            <el-input-number v-model=gooddetails.sum :min="1" :max="100" label="描述文字"></el-input-number>
        </el-dialog>
    </div>



</template>

<script>
    export default {
        name: "Role",
        data() {
            return {
                goodList:[
                  {
                    name:'asd',
                    price:'asd',
                    imageUrl:'asd',
                  },
                  {
                    name:'asd',
                    price:'asd',
                    imageUrl:'asd',
                  },
                  {
                    name:'asd',
                    price:'asd',
                    imageUrl:'asd',
                  }
                ],
                gooddetails:{
                    name:'',
                    price:'',
                    imageUrl:'',
                    sum:0
                },
                dialogFormVisible: false
            }

        },
        methods:{
            HandleGoodList(){
                handleGoodList().then(res=>{
                    const resp = res.data;
                    this.goodList=resp.data;
                })
            },
            shoppingcar(name){
                this.gooddetails.name=name;
                this.dialogFormVisible=true;
            }
        },
        created() {
            console.log(this.goodList);
        }
    }
</script>

<style scoped>
    *{
        padding: 0;
        margin: 0;
    }
    #box ul{
        list-style: none;
        display: flex;
        flex-wrap: wrap;
        height: 300px;
        font-size: 0;
    }
    #box li{
        padding: 3px;
        margin-right: 5px;
        border: 1px solid #eee;
        height: 370px;
        background-color: #B3C0D1;
        margin-bottom: 5px;
        font-size: 16px;
    }
    #box img{
        width: 200px;
        height: 150px;

    }
    .goodnamed{
        height: 20px;
        line-height: 20px;
    }
    .goodprice{
        height: 20px;
        line-height: 20px;
    }




</style>
