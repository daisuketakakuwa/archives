import CardService from "@/domains/card/CardService";
import CardServiceStub from "@/domains/card/CardServiceStub";
import CardServiceImpl from "@/domains/card/CardServiceImpl";
import GenreService from "@/domains/genre/GenreService";
import GenreServiceStub from "@/domains/genre/GenreServiceStub";
import GenreServiceImpl from "@/domains/genre/GenreServiceImpl";

import ConfigLoader from "@/domains/config/Config";

export default class ServiceFactory {
  // MEMO：Singletonとして各Serviceクラスを定義
  private static cardService: CardService;
  private static genreService: GenreService;

  // MEMO：Config.stubがtrueの場合に、Stubクラスをnewして返す。逆もないし。
  static async getCardService() {
    if (!this.cardService) {
      this.cardService = (await this.isStub())
        ? new CardServiceStub()
        : new CardServiceImpl();
    }
    return this.cardService;
  }

  static async getGenreService() {
    if (!this.genreService) {
      this.genreService = (await this.isStub())
        ? new GenreServiceStub()
        : new GenreServiceImpl();
    }
    return this.genreService;
  }

  // MEMO：Configファイルのstub値を参照するメソッド切りだす ※呼び出し先がasyncなのでawaitする
  private static async isStub() {
    return (await ConfigLoader.getConfig()).isStub;
  }
}
