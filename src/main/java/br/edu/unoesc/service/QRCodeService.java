package br.edu.unoesc.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.unoesc.DTO.LocacaoDTO;
import br.edu.unoesc.model.Locacao;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRCodeService {

    private static final String QR_CODE_API_URL = "https://api.apgy.in/qr/";

    public String gerarQrCode(Object objeto) {
        try {
            Map<String, String> dataMap = new HashMap<>();

            if (objeto instanceof Locacao locacao) {
                dataMap.put("cpf", locacao.getCpf());
                dataMap.put("telefone", locacao.getTelefone());
                dataMap.put("dataLocacao", locacao.getDataLocacao() != null ? locacao.getDataLocacao().toString() : "N/A");
                dataMap.put("dataDevolucao", locacao.getDataDevolucao() != null ? locacao.getDataDevolucao().toString() : "N/A");
            } else if (objeto instanceof LocacaoDTO dto) {
                dataMap.put("cpf", dto.cpf());
                dataMap.put("telefone", dto.telefone());
                dataMap.put("dataLocacao", dto.dataLocacao() != null ? dto.dataLocacao().toString() : "N/A");
                dataMap.put("dataDevolucao", dto.dataDevolucao() != null ? dto.dataDevolucao().toString() : "N/A");
            } else {
                throw new IllegalArgumentException("Tipo de objeto inválido. Esperado: Locacao ou LocacaoDTO.");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonData = objectMapper.writeValueAsString(dataMap);
            String encodedJson = URLEncoder.encode(jsonData, StandardCharsets.UTF_8);

            String requestUrl = UriComponentsBuilder.fromUri(URI.create(QR_CODE_API_URL))
                    .queryParam("data", encodedJson)
                    .queryParam("size", "300")
                    .toUriString();

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    requestUrl,
                    HttpMethod.GET,
                    null,
                    byte[].class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                byte[] qrCodeData = response.getBody();
                return "data:image/png;base64," + Base64.getEncoder().encodeToString(qrCodeData);
            } else {
                throw new RuntimeException("Erro ao gerar QR Code. Status: " + response.getStatusCode());
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar QR Code: " + e.getMessage(), e);
        }
    }

}