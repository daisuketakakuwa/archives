<template>
  <div class="ma-5">
    <!-- <h1>{{ genreName }}</h1>
    <swipe-card
      v-touch:swipe.left="swipeLeft"
      v-touch:swipe.right="swipeRight"
      :cardItem.sync="cardItem"
    /> -->
  </div>
</template>
<script lang="ts">
import Card from "@/domains/card/Card";
import { Component, Vue } from "nuxt-property-decorator";
// MEMO：nuxt-property-decoratorのVueとは違う
import Vuee from "vue";
import Vue2TouchEvents from "vue2-touch-events";

import ServiceFactory from "@/infras/ServiceFactory";
import CardService from "@/domains/card/CardService";

// MEMO：Vue2TouchEventsを「"vue"のVue.use()」で、class外のscript内で呼び出す。
Vuee.use(Vue2TouchEvents);

@Component
export default class SwipeCardForm extends Vue {
  cardService!: CardService;

  cardItem: Card = new Card();
  cardItems: Card[] = [];

  // MEMO：queryを受け取る
  genreId!: string;
  genreName!: string;

  // async fetch() {
  //   this.genreId = this.$route.query.genreId as string;
  //   this.genreName = this.$route.query.genreName as string;
  //   this.cardService = await ServiceFactory.getCardService();
  //   this.cardItems = await this.cardService.getCardsByGenre(this.genreId);
  //   this.cardItem = this.cardItems[0];
  // }

  // swipeLeft() {
  //   if (this.cardItem.index + 1 === this.cardItems.length) {
  //     this.cardItem = this.cardItems[0];
  //   } else {
  //     this.cardItem = this.cardItems[this.cardItem.index + 1];
  //   }
  // }
  // swipeRight() {
  //   if (this.cardItem.index === 0) {
  //     this.cardItem = this.cardItems[this.cardItems.length - 1];
  //   } else {
  //     this.cardItem = this.cardItems[this.cardItem.index - 1];
  //   }
  // }
}
</script>
