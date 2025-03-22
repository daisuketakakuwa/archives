<template>
  <v-dialog v-model="showDialog_">
    <v-card>
      <v-btn class="ma-3" color="primary" @click="switchEditMode">
        <v-icon>mdi-pencil</v-icon>
        <span v-if="editMode">(EDIT MODE)</span>
      </v-btn>
      <v-btn v-if="editMode" class="ma-3" color="primary" @click="updateCard">
        <v-icon>mdi-cloud-upload</v-icon>
        <span class="ml-2">UPDATE</span>
      </v-btn>
      <v-btn class="ma-3" color="#F00" @click="deleteCard">
        <v-icon color="white">mdi-trash-can</v-icon>
      </v-btn>
      <v-container>
        <v-row>
          <v-col cols="6">
            <h3 class="mt-3">
              <v-chip class="mr-3" color="primary">TITLE</v-chip>
              <v-text-field v-if="editMode" v-model="editTitle" />
              <p v-else>{{ card_.title }}</p>
            </h3>
            <h3 class="mt-3">
              <v-chip class="mr-3" color="primary">GENRE</v-chip>{{ card_.genre.name }}
            </h3>
            <h3 class="mt-3"><v-chip color="primary">CONTENT</v-chip></h3>
            <v-textarea v-if="editMode" v-model="editContent" outlined />
            <!-- v-text内だと改行コード「\n」で改行される -->
            <div v-else class="mt-5 ml-5" style="white-space: pre-wrap;" v-text="card_.content" />
          </v-col>
          <v-col cols="6">
            <v-img :src="card_.imageUrl" width="550px" length="440px" />
          </v-col>
        </v-row>
      </v-container>
    </v-card>
    <AppDialog
      :dialog="completeUpdateOrDelete"
      headerTitle="NOTIFICATION"
      :hideCancel="true"
      maxWidth="800px"
      @ok="backToHome"
    >
      {{ dialogMessage }}
    </AppDialog>
  </v-dialog>
</template>
<script lang="ts">
import { Vue, Component, PropSync, Watch } from "nuxt-property-decorator";
import Card from "@/domains/card/Card";
import CardUpdateForm from "@/domains/card/CardUpdateForm";
import CardService from "@/domains/card/CardService";
import ServiceFactory from "@/infras/ServiceFactory";

@Component
export default class CardDialog extends Vue {

  cardService!: CardService;

  @PropSync("showDialog", { type: Boolean, default: false })
  showDialog_!: boolean;

  // MEMO：Propで受け取るのは「Object」じゃないといけないぽい
  @PropSync("card", { type: Object, default: new Card() })
  card_!: Card;

  async fetch() {
    this.cardService = await ServiceFactory.getCardService();
  }

  editMode = false;
  completeUpdateOrDelete = false;

  editTitle = "";
  editContent = "";
  dialogMessage = "";

  @Watch('showDialog_')
  resetEditForm() {
    if (this.showDialog_) {
      this.editMode = false;
      this.editTitle = this.card_.title;
      this.editContent = this.card_.content;
    }
  }

  switchEditMode() {
    this.editMode = !this.editMode;
  }


  backToHome() {
    // 既にTOP画面の場合はリロードする
    if(this.$route.path === '/') {
      location.reload();
    } else {
      this.$router.push("/");
    }
  }

  updateCard() {
    this.cardService.updateCard(this.card_.id, new CardUpdateForm(this.editTitle, this.editContent));
    this.dialogMessage = "COMPLETED TO UPDATE CARD";
    this.completeUpdateOrDelete = true;
  }

  deleteCard() {
    this.cardService.deleteCard(this.card_.id);
    this.dialogMessage = "COMPLETED TO DELETE CARD";
    this.completeUpdateOrDelete = true;
  }

}
</script>
