package com.peram.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * Hello world!
 *
 */
public class QRCodeGenerator {

	private static final String QRCODE_PATH = "C:\\Users\\hp\\Desktop\\QRCode\\";

	public String writeQrCode(PaytmRequestBody paytmrequestBody) throws Exception {
		QRCodeWriter writer = new QRCodeWriter();
		String qrCode = QRCODE_PATH + paytmrequestBody.getUserName() + "-QRCode.png";
		BitMatrix bitMatrix = writer.encode(
				paytmrequestBody.getUserName() + "\n" + paytmrequestBody.getMobileNo() + "\n"
						+ paytmrequestBody.getAccountType() + "\n" + paytmrequestBody.getAccountNo(),
				BarcodeFormat.QR_CODE, 350, 350);

		Path path = FileSystems.getDefault().getPath(qrCode);
		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
		return "QR code successfully generated";
	}

	public String readQrcode(String qrCodeImage) throws Exception {
		BufferedImage bufferedImage = ImageIO.read(new File(qrCodeImage));
		LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedImage);
		BinaryBitmap binaryBitMap = new BinaryBitmap(new HybridBinarizer(luminanceSource));
		Result result = new MultiFormatReader().decode(binaryBitMap);

		return result.getText();
	}

	public static void main(String[] args) throws Exception {
		QRCodeGenerator codeGenerator = new QRCodeGenerator();
		/*System.out.println(
				codeGenerator.writeQrCode(new PaytmRequestBody("Rajasekhar", "9052344235", "Personal", "ICIC000689")));*/
		System.out.println(codeGenerator.readQrcode(QRCODE_PATH+"Rajasekhar-QRCode.png"));
	}
}
