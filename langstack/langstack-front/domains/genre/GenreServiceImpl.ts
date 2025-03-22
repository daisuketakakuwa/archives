import axios from "~/infras/AxiosFactory";
import Genre from "@/domains/genre/Genre";
import GenreService from "@/domains/genre/GenreService";

export default class GenreServiceImpl implements GenreService {
  async getAllGenre(): Promise<Genre[]> {
    return (await axios.get('/genre/allGenres')).data;
  }

}
