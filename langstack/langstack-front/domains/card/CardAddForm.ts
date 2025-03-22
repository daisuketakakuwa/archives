import Genre from "@/domains/genre/Genre";

export default class CardAddForm {
  constructor(
    public id: string = "",
    public genreId = "",
    public genreName = "",
    public title: string = "",
    public content: string = "",
    public imageDataUrl: string | ArrayBuffer | null = ""
  ) {}
}
