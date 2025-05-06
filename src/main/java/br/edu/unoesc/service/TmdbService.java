package br.edu.unoesc.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.unoesc.DTO.FilmeDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class TmdbService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    private final String TMDB_API_URL = "https://api.themoviedb.org/3";
    private final OkHttpClient client = new OkHttpClient();

    public List<FilmeDTO> buscarFilmesPorTitulo(String titulo) throws IOException {
        String url = TMDB_API_URL + "/search/movie?api_key=" + apiKey + "&language=pt-BR&query=" + titulo;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Falha ao buscar filmes: " + response);
            }

            String responseBody = response.body().string();
            Map<String, Object> jsonResponse = new ObjectMapper().readValue(responseBody, Map.class);
            List<Map<String, Object>> filmesData = (List<Map<String, Object>>) jsonResponse.get("results");

            List<FilmeDTO> filmes = new ArrayList<>();
            for (Map<String, Object> filmeData : filmesData) {
                FilmeDTO filmeDTO = new FilmeDTO(
                        null, 
                        true, 
                        0L,
                        (String) filmeData.get("title"),
                        (String) filmeData.get("overview"),
                        String.valueOf(filmeData.get("vote_average")),
                        parseDate((String) filmeData.get("release_date")),
                        new ArrayList<>()
                );
                filmes.add(filmeDTO);
            }

            return filmes;
        }
    }

    public FilmeDTO buscarFilmePorId(Integer tmdbId) throws IOException {
        String url = TMDB_API_URL + "/movie/" + tmdbId + "?api_key=" + apiKey + "&language=pt-BR";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Falha ao buscar filme: " + response);
            }

            String responseBody = response.body().string();
            Map<String, Object> dados = new ObjectMapper().readValue(responseBody, Map.class);

            return new FilmeDTO(
                    null,
                    true, 
                    0L, 
                    (String) dados.get("title"),
                    (String) dados.get("overview"),
                    String.valueOf(dados.get("vote_average")),
                    parseDate((String) dados.get("release_date")),
                    new ArrayList<>()
            );
        }
    }

    private Date parseDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }
}
