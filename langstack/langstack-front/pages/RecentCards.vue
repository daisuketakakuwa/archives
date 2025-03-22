<template>
    <div>
        <h2 class="my-5">RECENT CARDS</h2>
        <CardTable :items.sync="recentCards" />
    </div>
</template>
<script lang="ts">
import { Vue, Component } from "nuxt-property-decorator";
import Card from "@/domains/card/Card";
import ServiceFactory from "@/infras/ServiceFactory";
import CardService from "@/domains/card/CardService";

@Component
export default class RecentCards extends Vue {

    cardService!: CardService;

    recentCards: Card[] = [];

    async fetch() {
        this.cardService = await ServiceFactory.getCardService();
        this.recentCards = await this.cardService.getRecentCards();
    }

}

</script>