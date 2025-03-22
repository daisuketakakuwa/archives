<template>
  <div>
    <!-- 行クリックは @click:row を使う。引数には item が入る。 -->
    <v-data-table
      :headers="headers"
      :items="items_"
      @click:row="showCardDialog"
    >
    </v-data-table>
    <CardDialog :card.sync="card" :showDialog.sync="showDialog"/>
  </div>
</template>
<script lang="ts">
import { Vue, Component, PropSync } from "nuxt-property-decorator";
import Card from "@/domains/card/Card";

@Component
export default class CardTable extends Vue {
  @PropSync("items", { type: Array, default: [] })
  items_!: Card[];

  showDialog = false;
  card: Card = new Card();

  showCardDialog(card: Card) {
    this.card = card;
    this.showDialog = true;
  }

  headers = [
    {
      text: "DATE",
      value: "postDate"
    },
    {
      text: "GENRE",
      value: "genre.name"
    },
    {
      text: "TITLE",
      value: "title"
    }
  ];
}
</script>
