package ru.ezatoloka.mediaservice.domain.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class VideoDurationCalculationServiceImpl implements VideoDurationCalculationService {

	private static final Random RANDOM = new Random();
	private static final long MS_IN_SEC = 1000;
	private static final int MAX_CALC_SECS = 10;
	private static final int MAX_VIDE0_DURATION_SECS = 60;

	@Override
	public Integer calc() throws InterruptedException {
		Thread.sleep(MS_IN_SEC * (RANDOM.nextInt(MAX_CALC_SECS) + 1L));
		return RANDOM.nextInt(MAX_VIDE0_DURATION_SECS + 1);
	}

}
