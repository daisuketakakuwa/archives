import axiosModule from "@/infras/AxiosFactory";

import Genre from "@/domains/genre/Genre";
import GenreService from "@/domains/genre/GenreService";

export default class GenreServiceStub implements GenreService {
  async getAllGenre(): Promise<Genre[]> {
    return (await axiosModule.get("/data/genre/allGenres.json"))
      .data as Genre[];
  }
}
