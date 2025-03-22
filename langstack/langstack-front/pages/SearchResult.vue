<template>
    <div>
        <h2 class="my-5">SEARCHED BY "{{ keyword }}"</h2>
        <CardTable :items.sync="searchResultCards" />
    </div>
</template>
<script lang="ts">
import { Vue, Component } from "nuxt-property-decorator";
import ServiceFactory from "@/infras/ServiceFactory";
import CardService from "@/domains/card/CardService";
import Card from "@/domains/card/Card";

@Component
export default class SearchResult extends Vue {

    cardService!: CardService;

    keyword = "";
    searchResultCards: Card[] = [];

    async fetch() {
        this.cardService = await ServiceFactory.getCardService();
        this.keyword = this.$route.query.keyword as string;
        this.searchResultCards = await this.cardService.search(this.keyword);
    }

}

</script>