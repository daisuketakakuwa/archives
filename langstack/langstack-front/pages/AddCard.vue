<template>
  <div>
    <h1 class="mb-5">ADD CARD PAGE</h1>
    <v-row>
      <v-col>
        <v-btn @click="switchGenreForm" class="mb-2">
          ADD {{ genreFormText }} GENRE
        </v-btn>
        <v-text-field
          v-model="addForm.genreName"
          v-if="isNewGenreForm"
          outlined 
          placeholder="GENRE"
        />
        <v-select
          v-model="addForm.genreId"
          v-else
          outlined
          :items="genres"
          item-value="id"
          item-text="name"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-text-field v-model="addForm.title" outlined placeholder="TITLE" />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-textarea v-model="addForm.content" outlined placeholder="CONTENT" />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-file-input placeholder="IMAGE" @change="imageChange" />
        <v-img v-if="imagePreview" :src="imagePreview" />
      </v-col>
    </v-row>
    <v-row align="center">
      <v-col class="text-center">
        <v-btn @click="addCard">ADD</v-btn>
      </v-col>
    </v-row>
    <AppDialog
      :dialog.sync="showDialog"
      headerTitle="NOTIFICATION"
      :hideCancel="true"
      maxWidth="800px"
      @ok="backToHome"
     >
      {{ dialogMessage }}
     </AppDialog>
  </div>
</template>
<script lang="ts">
import { Vue, Component } from "nuxt-property-decorator";

import CardAddForm from "@/domains/card/CardAddForm";
import ServiceFactory from "@/infras/ServiceFactory";
import CardService from "@/domains/card/CardService";
import GenreService from "@/domains/genre/GenreService";
import Genre from "@/domains/genre/Genre";

@Component
export default class AddCard extends Vue {
  genreService!: GenreService;
  cardService!: CardService;

  addForm: CardAddForm = new CardAddForm();
  genres: Genre[] = [];

  showDialog = false;
  isNewGenreForm = false;
  genreFormText = "NEW";
  dialogMessage = "";

  imagePreview: string | ArrayBuffer | null = "";

  async fetch() {
    this.cardService = await ServiceFactory.getCardService();
    this.genreService = await ServiceFactory.getGenreService();
    this.genres = await this.genreService.getAllGenre();
  }

  addCard() {
    const possibleToHitApi =
      this.addForm.content && 
      this.addForm.title &&
      (this.addForm.genreId || this.addForm.genreName);

    this.dialogMessage = possibleToHitApi
      ? "COMPLETED ADDING YOUR KNOWLEDGE"
      : "INVALID INPUT"
      ;
    
    this.showDialog = true;

    if (possibleToHitApi) {
      this.cardService.addCard(this.addForm);
      
    } 
    
  }

  backToHome() {
    this.$router.push("/");
  }

  switchGenreForm() {
    this.isNewGenreForm = !this.isNewGenreForm;
    this.genreFormText = this.isNewGenreForm ? "EXISTING" : "NEW";
    if (this.isNewGenreForm) {
      this.addForm.genreId = "";
    } else {
      this.addForm.genreName = "";
    }
  }

  // MEMO：BASE64エンコードの手順
  // 1. FileReader()を生成
  // 2. readAsDataURLでファイルを読み込む
  // 3. load完了したら読込結果(result)を取得するイベントを作成
  // ※ 3のresult=dataURL。そのままpreviewに使える。
  // ※ サーバに送信する場合は頭文字部分(/data~~~~)を切り抜く。]
  imageChange(file: File) {
    // 写真が削除された場合のnullチェック
    if (file !== undefined && file !== null) {
      const fr = new FileReader();
      fr.readAsDataURL(file);
      fr.addEventListener("load", () => {
        this.imagePreview = fr.result;
        this.addForm.imageDataUrl = fr.result;
      });
    } else {
      this.imagePreview = "";
      this.addForm.imageDataUrl = "";
    }
  }

}
</script>
