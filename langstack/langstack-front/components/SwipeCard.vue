<template>
  <v-card>
    <h2 class="pa-3">{{ cardItem_.title }}</h2>
    <h3 class="pa-3">{{ cardItem_.content }}</h3>
    <v-img :src="cardItem_.imageUrl" width="300px" length="230px" />
  </v-card>
</template>
<script lang="ts">
import { Component, Vue, PropSync } from "nuxt-property-decorator";
import Card from "@/domains/card/Card";

@Component
export default class SwipeCard extends Vue {
  @PropSync("cardItem", { type: Object, default: new Card() })
  cardItem_!: Card;
}
</script>
