import Activity from "@/domains/activity/Activity";
import Card from "@/domains/card/Card";
import Genre from "@/domains/genre/Genre";

export default class InitResponse {
    constructor(
        public theLastFiveCards: Card[] = [],
        public theLastFiveDaysActivity: Activity[] = [],
        public allGenres: Genre[] = [],
        public allCardCount: number = 0
    ) {}
}