<template>
  <div>
    <h1>REVISE TOP PAGE</h1>
    <div class="mt-5" v-for="genre in genres" v-bind:key="genre.id">
      <v-btn @click="toSwipeCardPage(genre)">{{ genre.name }}</v-btn>
    </div>
  </div>
</template>
<script lang="ts">
import { Vue, Component } from "nuxt-property-decorator";

import ServiceFactory from "@/infras/ServiceFactory";
import GenreService from "@/domains/genre/GenreService";

import Genre from "@/domains/genre/Genre";

@Component
export default class ReviseTop extends Vue {
  genreService!: GenreService;

  genres: Genre[] = [];

  async fetch() {
    this.genreService = await ServiceFactory.getGenreService();
    this.genres = await this.genreService.getAllGenre();
  }

  toSwipeCardPage(genre: Genre) {
    // MEMO：queryでgenreIdを渡す
    this.$router.push({
      name: "SwipeCardForm",
      query: {
        genreId: genre.id,
        genreName: genre.name
      }
    });
  }
}
</script>
