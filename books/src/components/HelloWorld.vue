<template>
  <div class="hello">
    <h1>aaaaaaaaaaaa</h1>
    <table>
      <tr>
        <td>编号</td>
        <td>图书名字</td>
        <td>作者</td>
      </tr>
      <tr v-for="item in books">
        <td>{{item.id}}</td>
        <td>{{item.name}}</td>
        <td>{{item.pname}}</td>
      </tr>
    </table>
  </div>
</template>

<script>
const axios = require('axios');
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      books:[
        {
          id:542,
          name:'呀哈哈',
          Pname:'草斗'
        },
        {
          id:122,
          name:'aa呀哈哈',
          Pname:'sd草斗'
        }
      ]
    }
  },
  created(){
    var _this = this
    axios.get('http://localhost:8181/book/findAll').then(function(resp){
      console.log(resp)
      _this.books = resp.data
    })
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
