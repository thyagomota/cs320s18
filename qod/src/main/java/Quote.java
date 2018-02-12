import java.util.Arrays;

public class Quote {
    String quote;
    String length;
    String[] tags;
    String category;
    String date;
    String permalink;
    String title;
    String background;
    String id;

    public String getQuote() {
        return quote;
    }

    public String getLength() {
        return length;
    }

    public String[] getTags() {
        return tags;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getTitle() {
        return title;
    }

    public String getBackground() {
        return background;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "quote='" + quote + '\'' +
                ", length='" + length + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", category='" + category + '\'' +
                ", date='" + date + '\'' +
                ", permalink='" + permalink + '\'' +
                ", title='" + title + '\'' +
                ", background='" + background + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}


/*
"quote": "In matters of style, swim with the current; in matters of principle, stand like a rock.",
                "length": "87",
                "author": "Thomas Jefferson",
                "tags": [
                    "inspire",
                    "principles",
                    "style"
                ],
                "category": "inspire",
                "date": "2018-02-12",
                "permalink": "https://theysaidso.com/quote/qU1jon5EZodrqiCdXv5GwQeF/768307-thomas-jefferson-in-matters-of-style-swim-with-the-current-in-matters-of-princip",
                "title": "Inspiring Quote of the day",
                "background": "https://theysaidso.com/img/bgs/man_on_the_mountain.jpg",
                "id": "qU1jon5EZodrqiCdXv5GwQeF"
 */
