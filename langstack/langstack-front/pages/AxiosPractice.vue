<template>
  <div>
    <h1>AXIOS PRATICE</h1>
    <v-btn @click="get1">axios.get()</v-btn>
    <v-btn @click="get2">axios.get().then()</v-btn>
    <v-btn @click="get3">async-await axios.get()</v-btn>
  </div>
</template>
<script lang="ts">
import { Component, Vue } from "nuxt-property-decorator";
import axios from "axios";

@Component
export default class AxiosPractice extends Vue {
  // 動作確認用にここで直接axiosの設定する
  mounted() {
    // MEMO：これでコール先のホストを指定できる。
    //       yarn devでローカル(http://localhost:3000)にホスティングされる
    //       JSONファイルにアクセスするなら↑のパスを設定すればいいいわけだ。
    axios.defaults.baseURL = "http://localhost:3000";
  }

  get1() {
    // MEMO：axios.----()の戻り値はPromiseオブジェクト
    const getResult = axios.get("/data/axios-practice/get_001");
    alert(getResult);
  }

  get2() {
    // MEMO：then()でPromiseオブジェクト内の値(axiosResponse)を取り出す
    //       then()の引数にコールバック関数を定義
    //       ・引数にPromise<--->内の値がくる
    axios
      .get("/data/axios-practice/get_001.json")
      .then(function(axiosResponse) {
        alert("0. axiosResponse：" + axiosResponse);
        // MEMO：AxiosResponseの中身
        //       1. data
        //       2. status
        //       3. headers ... レスポンスヘッダ
        //       4. config  ... requestのconfig
        alert("1. data.name：" + axiosResponse.data.name);
        alert("1. data.age：" + axiosResponse.data.age);
        alert("2. status：" + axiosResponse.status);
      });
  }

  async get3() {
    // MEMO：awaitでPromiseオブジェクト内の値(axiosResponse)を取り出す
    //       awaitを使うメソッドの先頭にasyncをつける必要がある
    const json = (await axios.get("/data/axios-practice/get_001.json")).data;
    alert("1. data.name：" + json.name);
    alert("1. data.age：" + json.age);
  }
}
</script>
