package com.paulohenriquesf.qrcodegenerator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/qr")
public class QRCodeController {

	/**
	 * Gera um QR Code em memória e retorna como PNG.
	 */
	@GetMapping(produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> generateQrPng(@RequestParam String text,
			@RequestParam(required = false, defaultValue = "512") int size) throws Exception {

		// Evita “null” ou strings vazias
		String payload = text == null ? "" : text.strip();
		if (payload.isEmpty()) {
			payload = "about:blank";
		}

		// Configurações do QR (margem menor; charset correto)
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
		hints.put(EncodeHintType.MARGIN, 1);

		BitMatrix matrix = new MultiFormatWriter().encode(payload, BarcodeFormat.QR_CODE, size, size, hints);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(matrix, "PNG", out);

		return ResponseEntity.ok().header(HttpHeaders.CACHE_CONTROL, "no-store") // evita cache chato no browser
				.contentType(MediaType.IMAGE_PNG).body(out.toByteArray());
	}
}
