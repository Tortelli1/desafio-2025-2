package br.edu.unoesc.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.edu.unoesc.model.Locacao;

import java.util.Base64;

@Service
public class QRCodeService {

    private static final String QR_CODE_API_URL = "https://api.apgy.in/qr/";

    public String gerarQrCode(Locacao locacao) {
        String urlQRCode = "http://localhost:8080//locacoes/" + locacao.getId();
        String requestUrl = UriComponentsBuilder.fromUriString(QR_CODE_API_URL)
                .queryParam("data", urlQRCode)
                .queryParam("size", 300)
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
            throw new RuntimeException("Erro ao gerar QR Code.");
        }
    }
}
