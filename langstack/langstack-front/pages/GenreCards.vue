<template>
    <div>
        <h2 class="my-5">GENRE「{{genreName}}」</h2>
        <CardTable :items.sync="genreCards" />
    </div>
</template>
<script lang="ts">
import { Vue, Component } from "nuxt-property-decorator";
import Card from "@/domains/card/Card";
import ServiceFactory from "@/infras/ServiceFactory";
import CardService from "@/domains/card/CardService";

@Component
export default class GenreCards extends Vue {

    cardService!: CardService;

    genreId = "";
    genreName = "";

    genreCards: Card[] = [];

    async fetch() {
        this.cardService = await ServiceFactory.getCardService();
        this.genreId = this.$route.query.genreId as string;
        this.genreName = this.$route.query.genreName as string;
        this.genreCards = await this.cardService.getCardsByGenre(this.genreId);
    }

}

</script>