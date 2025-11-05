package com.paulohenriquesf.qrcodegenerator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class QrcodegeneratorApplication implements CommandLineRunner {

	@Autowired
	private QRCodeService qrCodeService;

	public static void main(String[] args) {
		SpringApplication.run(QrcodegeneratorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// URL pública do seu site
		String url = "https://paulohenriquesf-dev.github.io/riscos-do-tabagismo/";

		// Caminho onde o QR deve ser salvo
		String outputPath = "../docs/assets/qrcode.png";

		qrCodeService.generateQRCode(url, outputPath, 800, 800);
		System.out.println("QR Code gerado em: " + outputPath);
	}
}
