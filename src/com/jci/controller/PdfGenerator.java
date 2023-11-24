package com.jci.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import javax.print.attribute.standard.DialogOwner;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceGray;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.GrayColor;

public class PdfGenerator {

	public void generatePdf(String jciRefNo, String millNameString, int millCode, Double qty, String cropyear,
			List<Object[]> priceList, List<Object[]> compList, String fileName , String deliveryType , String contractDate) throws DocumentException, IOException {
         
		String FolderName = fileName.split("invoice")[0];
		String path = "C:\\Users\\pradeep.rathor\\Desktop\\JCI-PHASE2\\JCI-CMS\\target\\"+FolderName;
		
		final File theDir = new File(path);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        
        path += "\\"+fileName;

		
		PdfWriter pdfWriter = new PdfWriter(path);
		PdfDocument pdfDocument = new PdfDocument(pdfWriter);

		pdfDocument.setDefaultPageSize(PageSize.A4);

		Document document = new Document(pdfDocument);
		float fullWidth = PageSize.A4.getWidth();
		float equalThreeColumWidth = fullWidth / 3f;
		float columnHalfWidth = fullWidth * 0.5f;
		float columnWidth60 = fullWidth * 0.60f;
		float columnWidth20 = fullWidth * 0.20f;
		float widthOfTwoColumn[] = { columnHalfWidth, columnHalfWidth };
		float widthOfThreeCoulmn[] = { columnWidth60, columnWidth20, columnWidth20 };
		float widthOfThreeEqualCoulmn[] = { equalThreeColumWidth, equalThreeColumWidth, equalThreeColumWidth };
		float widthOfHeader[] = { columnWidth20, columnWidth60, columnWidth20 };

		Table table = new Table(widthOfTwoColumn);

		Table headerTable = new Table(widthOfHeader);
		Image logoImage = new Image(ImageDataFactory
				.create("C:\\Users\\pradeep.rathor\\Desktop\\JCI-PHASE2\\JCI-CMS\\WebContent\\assets\\img\\logo5.png"));
		logoImage.setWidth(100);
		logoImage.setHeight(100);

		Paragraph headerParagraph = new Paragraph()
				.add(new Text("The Jute Corporation Of India").setBold().setFontSize(13)).add("\n")
				.add(new Text("Regd. & Head Office : 15N, Nellie Sengupta Sarani").setFontSize(9)).add("\n")
				.add(new Text("7th Floor, Kolkata - 700 087").setFontSize(9)).add("\n")
				.add(new Text("Corporate Identification Number : U17232WB1971GOI027958").setFontSize(9))
				.setTextAlignment(TextAlignment.CENTER).setMargin(5f);

		headerTable.addCell(new Cell().add(logoImage).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT)
				.setPadding(0));
		headerTable.addCell(new Cell().add(headerParagraph).setBorder(Border.NO_BORDER).setPadding(0));
		headerTable.addCell(new Cell().add("").setBorder(Border.NO_BORDER));

		table.addCell(new Cell().add(new Paragraph().add(new Text("No, ").setBold()).add(new Text(jciRefNo)))
				.setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT));

		table.addCell(new Cell().add(new Paragraph().add(new Text("Date : ")).add(contractDate)).setBold()
				.setBorder(Border.NO_BORDER)).setTextAlignment(TextAlignment.RIGHT);

		Color grayColor = new DeviceGray(0.5f);

		table.addCell(new Cell()
				.add(new Paragraph().add(new Text("To, ").setBold())
						.add(new Text(millNameString).setFontColor(grayColor)))
				.setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT));

		Paragraph subHeading = new Paragraph(new Text("Sub : ").setBold())
				.add(new Text("Sale of row jute under B.Twill Linkage sale").setUnderline())
				.setTextAlignment(TextAlignment.CENTER).setMarginTop(45);

		Paragraph RefParagraph = new Paragraph(new Text("Ref No : ").setBold()).add(new Text(
				"Jute(Mktg)/42/2004 dt. 01-12-2022, 07-12-2022 and 09-12-2022 of Dy. Director (Mktg), Office of the Jute Commissioner(MoT), Kolkata against PCO dtd. 01-12-2022, 05-12-2022 and 09-12-2022")
				.setUnderline()).setTextAlignment(TextAlignment.CENTER);

		Paragraph messageParagraph = new Paragraph().add("Dear Sir(s)").add("\n")
				.add("We have this day sold to you " + qty + " quintals of raw jute / Mesta under linkage of "
						+ cropyear
						+ " Crop of the following variety and grades at prices and terms and conditions specified .")
				.add("\n").add("The details of sale is as under : ");
//
//		Paragraph deliveryTypeParagraph = new Paragraph().add(new Text("Mill Delivery").setBold())
//				.setBorderBottom(new SolidBorder(1)).setWidth(columnWidth20)
//				.setTextAlignment(TextAlignment.CENTER);

		Paragraph innerParagraph = new Paragraph().add(new Text(deliveryType).setBold())
				.setBorderBottom(new SolidBorder(1)).setWidth(columnWidth20).setTextAlignment(TextAlignment.CENTER);

		Paragraph deliveryTypeParagraph = new Paragraph().setTextAlignment(TextAlignment.RIGHT);
		deliveryTypeParagraph.add(innerParagraph);

		Table distributionTable = new Table(widthOfThreeCoulmn).setMargin(10f);
		distributionTable.addCell(new Cell().add("Jute Variety").setBold()).setTextAlignment(TextAlignment.CENTER);
		distributionTable.addCell(new Cell().add("Quantity (Qtls.)").setBold()).setTextAlignment(TextAlignment.CENTER);
		distributionTable.addCell(new Cell().add("Price (Rs./Qtl.)").setBold()).setTextAlignment(TextAlignment.CENTER);

		double totelSum = 0.0;
		double totalContractedprice = 0.0;

		for (int i = 0; i < compList.size(); i++) {
			Object[] rObject1 = compList.get(i);
			Object[] rObject2 = priceList.get(0);
			DecimalFormat decimalFormat = new DecimalFormat("#.##");
			Double composition = ((double) (rObject1[1]) / 100) * qty;
			String formattedNumber = decimalFormat.format(composition);

	
			double formattedDouble = Double.parseDouble(formattedNumber);
			totelSum += formattedDouble;
			Double price = ((BigDecimal) rObject2[i]).doubleValue();
			
			totalContractedprice += formattedDouble * price;
			
			distributionTable.addCell(new Cell().add((String) rObject1[0])).setTextAlignment(TextAlignment.CENTER);
			distributionTable.addCell(new Cell().add(formattedDouble + "").setTextAlignment(TextAlignment.CENTER));

			distributionTable.addCell(new Cell().add(price + "").setTextAlignment(TextAlignment.CENTER));
		}
		
		int finalPrice = (int) totalContractedprice;

		distributionTable.addCell(new Cell().add("Total").setBold());
		distributionTable.addCell(new Cell().add(totelSum + "").setBold());
		distributionTable.addCell(new Cell().add(finalPrice+" Rs"));

		Paragraph footer = new Paragraph("2252-7027 / 7028 / 6952 / 6779 / 6770 / 6773 / 7108 / 6776")
				.setTextAlignment(TextAlignment.CENTER).setFontSize(10);
		Table footerTable = new Table(widthOfThreeEqualCoulmn).setBorder(Border.NO_BORDER);

		footerTable.addCell(new Cell().add(new Paragraph("E-mail : jci@jcimail.in")).setBold()
				.setBorder(Border.NO_BORDER).setFontSize(8)).setMarginLeft(0).setTextAlignment(TextAlignment.LEFT);
		footerTable.addCell(
				new Cell().add(new Paragraph().add(new Text("Fax : ").setBold()).add(" 91-033-2252-1771 / 6890 / 6951"))
						.setBorder(Border.NO_BORDER).setFontSize(8));

		footerTable
				.addCell(new Cell().add(new Paragraph().add(new Text("Website : ").setBold()).add(" www.jutecorp.in"))
						.setBorder(Border.NO_BORDER).setFontSize(8))
				.setTextAlignment(TextAlignment.RIGHT);

		document.add(headerTable);
		document.add(new Paragraph("\n"));
		document.add(table);
		document.add(subHeading);
		document.add(new Paragraph("\n"));
		document.add(RefParagraph);
		document.add(new Paragraph("\n"));
		document.add(messageParagraph);
		document.add(new Paragraph("\n"));
		document.add(deliveryTypeParagraph);
		document.add(distributionTable);
		document.add(new Paragraph("\n"));
		document.add(new Paragraph("").setBorder(new SolidBorder(Color.GRAY, 1))).setFixedPosition(0,
				PageSize.A4.getHeight(), 1);
		document.add(footer);
		document.add(footerTable);

		document.close();

	}
}
