import axios from "~/infras/AxiosFactory";
import Card from "@/domains/card/Card";
import CardAddForm from "@/domains/card/CardAddForm";
import CardUpdateForm from "@/domains/card/CardUpdateForm";
import CardService from "@/domains/card/CardService";
import InitResponse from "@/domains/response/InitResponse";

export default class CardServiceImpl implements CardService {
  addCard(request: CardAddForm): void {
    axios.post('/card/add', request);
  }
  updateCard(id: string, request: CardUpdateForm): void {
    axios.post('/card/update/' + id, request);
  }
  deleteCard(id: string): void {
    axios.delete('/card/delete/' + id);
  }
  async init(): Promise<InitResponse> {
    return (await axios.get('/card/init')).data;
  }
  async search(keyword: string): Promise<Card[]> {
    return (await axios.get("/card/search?keyword=" + keyword)).data
  }
  async getRecentCards(): Promise<Card[]> {
    return (await axios.get("/card/recent-cards")).data;
  }
  async getCardsByGenre(genreId: string): Promise<Card[]> {
    return (await axios.get("/card/" + genreId)).data;
  }
}
