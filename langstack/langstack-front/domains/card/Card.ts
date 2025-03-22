import Genre from "@/domains/genre/Genre";

export default class Card {
  constructor(
    public id: string = "",
    public title: string = "",
    public content: string = "",
    public imageUrl: string = "",
    public postDate: string = "",
    public genre: Genre = new Genre()
  ) {}
}
