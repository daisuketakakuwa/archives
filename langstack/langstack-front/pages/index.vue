<template>
  <div>
    <v-container>
      <!-- 縦に要素を並べたい場合はv-colの中にv-rowを！ -->
      <!-- https://www.paveway.info/entry/2021/02/20/vuetifyjs_gridcellspan -->
      <v-row>
        <!-- 画面左半分 -->
        <v-col cols="6">
          <v-row>
            <v-col cols="12">
              <div class="border-lined pa-3 half-top-height">
                <h2 class="black--text mb-5">
                  <nuxt-link to="/RecentCards">RECENT CARDS</nuxt-link>
                </h2>
                <p
                  class="text-h6 ml-3"
                  v-for="card in theLastFiveCards"
                  :key="card.title"
                >
                  <v-btn @click="showCardDialog(card)">
                    {{ card.title }}
                  </v-btn>
                </p>
                <CardDialog :card.sync="card" :showDialog.sync="showDialog" />
              </div>
            </v-col>
            <v-col cols="12">
              <div class="border-lined pa-3 half-top-height">
                <h2 class="mb-5">GENRES</h2>
                <v-btn
                  class="black white--text text-h6 ml-2 mt-2"
                  @click="moveToGenrePage(genre.id, genre.name)"
                  v-for="genre in allGenres"
                  :key="genre.id"
                  >{{ genre.name }}</v-btn
                >
              </div>
            </v-col>
          </v-row>
        </v-col>
        <!-- 画面右半分 -->
        <v-col cols="6">
          <v-row>
            <v-col cols="12">
              <div class="pa-3 half-top-height text-parent">
                <!-- text-${position}でp要素内の左右テキスト位置指定〇
                　　　https://vuetifyjs.com/en/styles/text-and-typography/#alignment 
                     ただ「text-center」は、position: absoluteにすると無効化される。
                     absoluteになるとブロック要素ではなくなるからか。
                     ★text-centerはブロック要素(p要素)に対して有効〇★
                -->
                <p class="text-h1 text-child-1">{{ allCardCount }}</p>
                <p class="text-h5 text-child-2">total cards you added</p>
              </div>
            </v-col>
            <v-col cols="12">
              <div class="pa-6 half-top-height text-parent">
                <v-row>
                  <v-col
                    cols="12"
                    v-for="activity in theLastFiveDaysActivity"
                    :key="activity.date"
                  >
                    <div class="text-h6 d-inline-block center-string">
                      {{ activity.date }}
                    </div>
                    <div
                      class="primary d-inline-block ml-3"
                      :style="
                        `width:${activity.cards}0px;max-width:350px;height:30px`
                      "
                    ></div>
                    <div class="text-h6 d-inline-block center-string">
                      {{ activity.cards }}
                    </div>
                  </v-col>
                </v-row>
                <p class="text-h5 text-child-3">
                  your activity【the last 5 days】
                </p>
              </div>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>
<script lang="ts">
import { Vue, Component } from "nuxt-property-decorator";
import Activity from "@/domains/activity/Activity";
import Card from "@/domains/card/Card";
import Genre from "@/domains/genre/Genre";
import CardService from "@/domains/card/CardService";
import ServiceFactory from "@/infras/ServiceFactory";
import InitResponse from "@/domains/response/InitResponse";

@Component
export default class Top extends Vue {
  cardService!: CardService;

  theLastFiveCards: Card[] = [];
  theLastFiveDaysActivity: Activity[] = [];
  allGenres: Genre[] = [];
  allCardCount = 0;

  initResponse!: InitResponse;

  showDialog = false;
  card: Card = new Card();

  async fetch() {
    this.cardService = await ServiceFactory.getCardService();
    this.initResponse = await this.cardService.init();
    this.theLastFiveCards = this.initResponse.theLastFiveCards;
    this.theLastFiveDaysActivity = this.initResponse.theLastFiveDaysActivity;
    this.allGenres = this.initResponse.allGenres;
    this.allCardCount = this.initResponse.allCardCount;
  }

  moveToGenrePage(genreId: string, genreName: string) {
    this.$router.push(
      "/GenreCards?genreId=" + genreId + "&genreName=" + genreName
    );
  }

  showCardDialog(card: Card) {
    this.card = card;
    this.showDialog = true;
  }
}
</script>

<style scoped>
.center-string {
  vertical-align: top;
}

.half-top-height {
  height: 40vh;
}

.border-lined {
  border: 7px solid #4dd0e1;
  border-radius: 10px;
}

/* 要素を下に固定するやり方
  1. 親要素を「position: relative」にする ※positionはdefault値：static
  2. 下に固定した子要素に「position: absolute」＆「bottom: 0, margin: 0, padding: 0」
*/
.text-parent {
  position: relative;
}

.text-child-1 {
  position: absolute;
  top: 35%;
  left: 35%;
}

.text-child-2 {
  position: absolute;
  top: 80%;
  left: 30%;
}

.text-child-3 {
  position: absolute;
  top: 90%;
  left: 25%;
}
</style>
