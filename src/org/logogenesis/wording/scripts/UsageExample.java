package org.logogenesis.wording.scripts;

import java.io.IOException;

import org.logogenesis.wording.WordFormRecognizer;
import org.logogenesis.wording.HolisticWordingSegmenter;
import org.logogenesis.wording.ReductionistWordingSegmenter;
import org.logogenesis.wording.WordingSegmenter;
import org.logogenesis.wording.io.WordingChart;
import org.logogenesis.wording.io.WordingChartFactory;
import org.logogenesis.wording.io.atag.AtagWordingChartFactory;
import org.logogenesis.wording.io.hal.HalWordingChartFactory;

/**
 * Segments a wording using a wording segmenter.
 * 
 * @author Daniel Couto-Vale
 */
public class UsageExample {

	public static void main(String[] args) throws IOException {
		String wording = "Unser Prozess ermöglicht es uns, Projekte schnell herzustellen. "
				+ "Wir stellen Projekte schnell her.";
		illustrateUsage(wording, new HalWordingChartFactory());
		illustrateUsage(wording, new AtagWordingChartFactory());
	}

	private static void illustrateUsage(String wording, WordingChartFactory factory) {
		// Segments the wording with a holistic theory
		WordFormRecognizer wordRecognizer = new WordFormRecognizer(factory);
		wordRecognizer.addType("Unser", 1);
		wordRecognizer.addType("Prozess", 2);
		wordRecognizer.addType("ermöglicht", 3);
		wordRecognizer.addType("ermöglicht", 4);
		wordRecognizer.addType("es", 5);
		wordRecognizer.addType("uns", 6);
		wordRecognizer.addType("Projekte", 7);
		wordRecognizer.addType("schnell", 8);
		wordRecognizer.addType("her", 9);
		wordRecognizer.addType("zu", 10);
		wordRecognizer.addType("stellen", 11);
		wordRecognizer.addType("stellen", 12);
		wordRecognizer.addType("stellen", 13);
		wordRecognizer.addType("stellen", 14);
		wordRecognizer.addType(" ", 15);
		wordRecognizer.addType(", ", 16);
		wordRecognizer.addType(".", 17);
		wordRecognizer.addType("Wir", 18);
		WordingSegmenter holisticSegmenter = new HolisticWordingSegmenter(wordRecognizer);
		WordingChart holisticChart = holisticSegmenter.segmentWording(wording);
		System.out.println("HOLISTIC CHART");
		System.out.println(holisticChart);

		// Segments the wording with a reductionist theory
		System.out.println("REDUCTIONIST CHART");
		WordingSegmenter reductionistSegmenter = new ReductionistWordingSegmenter(factory);
		WordingChart reductionistChart = reductionistSegmenter.segmentWording(wording);
		System.out.println(reductionistChart);
	}

}
