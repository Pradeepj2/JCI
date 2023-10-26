package com.jci.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.FileOutputStream;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;

import javassist.bytecode.stackmap.BasicBlock.Catch;


public class PdfGenerator {
	
	public String htmlToPdf(String processedHtml) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		try {
			PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
			DefaultFontProvider defaultFontProvider = new DefaultFontProvider(false,true,false);
			ConverterProperties converterProperties = new ConverterProperties();
			converterProperties.setFontProvider(defaultFontProvider);
			
			HtmlConverter.convertToPdf(processedHtml, pdfWriter,converterProperties);
			
			FileOutputStream fileOutputStream = new FileOutputStream("");
			byteArrayOutputStream.writeTo(fileOutputStream);
			byteArrayOutputStream.close();
			
			fileOutputStream.flush();
			fileOutputStream.close();
			return null;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
		
	}


}