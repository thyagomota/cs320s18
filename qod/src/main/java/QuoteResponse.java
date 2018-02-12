public class QuoteResponse {
    private Success success;
    private Contents contents;

    public Success getSuccess() {
        return success;
    }

    public Contents getContents() {
        return contents;
    }

    @Override
    public String toString() {
        return "success: " + success + "; contents: " + contents;
    }
}

/*
"success": {
        "total": 1
    },
    "contents": {
        "quotes": [
            {
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
            }
        ],
        "copyright": "2017-19 theysaidso.com"
    }
 */
