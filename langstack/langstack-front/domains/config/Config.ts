import axios from "@/infras/AxiosFactory";

// MEMO：Configという「受け皿」作成
export class Config {
  public isStub = false;
  public apiPath = "";
}

// Config
class ConfigLoader {
  // MEMO：Singleton(private)
  private static config: Config;

  // MEMO：Configオブジェクトを外部から参照できるように
  static async getConfig() {
    // MEMO：一番最初に初期化
    if (!this.config) {
      this.config = (await axios.get("/config/config.json")).data;
      // MEMO：Configファイルを初期化時にロードする時に、axios設定もしてあげる
      axios.defaults.baseURL = this.config.apiPath;
    }
    return this.config;
  }
}

export default ConfigLoader;
