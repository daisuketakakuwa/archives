<template>
  <v-app>
    <v-app-bar absolute>
        <v-img
          src="/data/langstack-logo.png"
          max-height="100"
          max-width="300"
          style="cursor: pointer"
          @click="moveToHome"
        />

      <!-- こいつを書くことで両端の要素をはじ寄せできる -->
      <div class="flex-grow-1"></div>

      <v-btn color="primary"
        ><nuxt-link class="white--text" style="text-decoration: none;" to="AddCard">ADD CARD</nuxt-link></v-btn
      >
      <!-- denseで入力フィールドの高さが低くなる！ -->
      <v-text-field
        class="mt-7 ml-5"
        dense
        outlined　prepend-inner-icon="mdi-magnify"
        placeholder="KEYWORD"
        v-model="searchKeyword"
      />
      <v-btn color="primary" class="white--text ml-5" @click="moveToSearchResultPage">
        SEARCH
      </v-btn>
    </v-app-bar>
    <v-main class="mt-15">
      <v-container>
        <Nuxt />
      </v-container>
    </v-main>
  </v-app>
</template>
<script lang="ts">
import { Vue, Component } from "nuxt-property-decorator";

@Component
export default class Default extends Vue {

  searchKeyword = "";

  moveToHome() {
    this.searchKeyword = "";
    this.$router.push("/");
  }

  moveToSearchResultPage() {
    this.$router.push("/SearchResult?keyword=" + this.searchKeyword);
  }

}

</script>