import Genre from "@/domains/genre/Genre";

export default interface GenreService {
  getAllGenre(): Promise<Genre[]>;
}
