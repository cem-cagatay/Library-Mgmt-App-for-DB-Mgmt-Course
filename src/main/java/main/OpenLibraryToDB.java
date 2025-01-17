package main;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import database.DatabaseConnection;
import database.DatabaseHandler;

public class OpenLibraryToDB {

    // List of subjects to fetch books from
    private static final String[] SUBJECTS = {"love", "fiction", "textbooks"};

    public static void main(String[] args) {
        try {
            for (String subject : SUBJECTS) {
                String apiUrl = "https://openlibrary.org/subjects/" + subject + ".json?limit=300";
                fetchBooksFromSubject(apiUrl, subject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void fetchBooksFromSubject(String apiUrl, String subject) throws Exception {
        String jsonResponse = getJSONResponse(apiUrl);
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray works = jsonObject.getJSONArray("works");

        for (int i = 0; i < works.length(); i++) {
            JSONObject book = works.getJSONObject(i);

            // Skip books with multiple authors
            JSONArray authors = book.getJSONArray("authors");
            if (authors.length() != 1) continue;

            String bookId = book.getString("key").replace("/works/", "");
            String title = book.getString("title");
            int firstPublishYear = book.has("first_publish_year") ? book.getInt("first_publish_year") : 0;
            String authorId = authors.getJSONObject(0).getString("key").replace("/authors/", "");

            try (Connection conn = DatabaseConnection.getConnection()) {
            	if (!DatabaseHandler.isAuthorExists(conn, authorId)) {
                    fetchAndInsertAuthorDetails(authorId); // Ensure author exists
                }

                if (!DatabaseHandler.isBookExists(conn, bookId)) {
                    DatabaseHandler.insertBook(conn, bookId, authorId, firstPublishYear, title, subject);
                }
            }
        }
    }

    private static void fetchAndInsertAuthorDetails(String authorId) throws Exception {
        String authorApiUrl = "https://openlibrary.org/authors/" + authorId + ".json";
        String jsonResponse = getJSONResponse(authorApiUrl);
        JSONObject author = new JSONObject(jsonResponse);

        String name = author.getString("name");
        String[] nameParts = name.split(" ");
        String firstName = nameParts.length > 0 ? nameParts[0] : "";
        String lastName = nameParts.length > 1 ? nameParts[nameParts.length - 1] : "";

        try (Connection conn = DatabaseConnection.getConnection()) {
            DatabaseHandler.insertAuthor(conn, authorId, firstName, lastName);
        }
    }

    private static String getJSONResponse(String apiUrl) throws Exception {
        StringBuilder response = new StringBuilder();
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }

        return response.toString();
    }
}