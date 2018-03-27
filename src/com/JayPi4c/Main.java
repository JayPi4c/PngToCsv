package com.JayPi4c;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Main {

	public static final String lineSeparator = System.getProperty("line.separator");

	public static void main(String args[]) throws IOException {
		/*
		 * File checking = new File(getExecutionPath() + "/0"); if (!checking.exists())
		 * { System.out.println("Bitte den Ausführungsordner überprüfen!"); System.out.
		 * println("Es scheint, als sei das Programm in einem falschen Ordner gestartet worden!"
		 * ); System.out.println("Exiting..."); System.exit(0); }
		 */

		File finalFile = new File("location");

		File allInOneFile = new File(getExecutionPath() + "/allData.csv");
		if (!allInOneFile.exists())
			allInOneFile.createNewFile();

		BufferedWriter allWriter = new BufferedWriter(new FileWriter(allInOneFile));

		for (int i = 0; i <= 7; i++) {
			File directory = new File(getExecutionPath() + "/" + i);
			if (!directory.exists()) {
				System.out.println("Der Ordner '" + directory.getAbsolutePath() + "' existiert nicht!");
				continue;
			}
			System.out.println("Directory " + directory.getAbsolutePath() + "; number of files inside: "
					+ directory.list().length);
			System.out.println();

			finalFile = new File(directory.getParentFile().getAbsolutePath() + "/" + i + ".csv");

			if (!finalFile.exists())
				finalFile.createNewFile();

			BufferedWriter BW = new BufferedWriter(new FileWriter(finalFile));
			for (int j = 1; j <= directory.list().length; j++) {
				String path = directory.getPath() + "/" + i + "IMG" + j + ".JPG";
				System.out.println("expected Path: " + path);
				File f = new File(path);

				BufferedImage img = ImageIO.read(f);
				// BufferedImage grayScale = getGrayScaleImage(img);

				BW.write(i + "");
				allWriter.write(i + "");

				for (int x = 0; x < img.getWidth(); x++) {
					for (int y = 0; y < img.getHeight(); y++) {
						int rgb = img.getRGB(x, y);
						int r = (rgb >> 16) & 0xFF;
						int g = (rgb >> 8) & 0xFF;
						int b = (rgb & 0xFF);
						int gray = (r + g + b) / 3;
						String text = gray + "";
						BW.write("," + text);
						allWriter.write("," + text);
					}
				}
				BW.newLine();
				allWriter.newLine();

			}
			BW.close();
		}
		allWriter.close();
		if (allInOneFile.length() > 0)
			shuffle(allInOneFile);
		else
			System.out.println("'allData.csv' is Empty");

	}

	public static String getExecutionPath() {
		String absolutePath = new File(".").getAbsolutePath();
		File file = new File(absolutePath);
		absolutePath = file.getParentFile().toString();
		return absolutePath;
	}

	public static void shuffle(File f) throws IOException {
		int numberOfLines = getNumberOfLines(f);

		File finalFile = new File(f.getParentFile().getAbsolutePath() + "/shuffledFile.csv");
		finalFile.createNewFile();

		ArrayList<Integer> pool = new ArrayList<>();
		for (int i = 0; i < numberOfLines; i++)
			pool.add(i);

		BufferedWriter BW = new BufferedWriter(new FileWriter(finalFile));
		while (pool.size() > 0) {
			BW.write(getLineOfFile(pool.remove((int) (Math.random() * pool.size())), f));
			BW.newLine();
		}

		BW.close();
	}

	public static String getLineOfFile(int line, File f) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(f));
		for (int i = 0; i < line; i++)
			br.readLine();
		String s = br.readLine();
		br.close();
		return s;
	}

	public static int getNumberOfLines(File f) throws IOException {
		BufferedReader BR = new BufferedReader(new FileReader(f));
		int numberOfLines = 0;
		while (BR.readLine() != null)
			numberOfLines++;
		BR.close();
		return numberOfLines;
	}
}