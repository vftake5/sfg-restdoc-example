package guru.springframework.sfgrestdocexample.bootstrap;

import guru.springframework.sfgrestdocexample.domain.Beer;
import guru.springframework.sfgrestdocexample.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner
{
	private final BeerRepository beerRepository;

	public BeerLoader(BeerRepository beerRepository)
	{
		this.beerRepository = beerRepository;
	}

	@Override
	public void run(String... args) throws Exception
	{
		loadBeerObjects();
	}

	private void loadBeerObjects()
	{
		if (beerRepository.count() == 0)
		{
			beerRepository.save(Beer.builder()
					.beerName("Soproni Ászok")
					.beerType("Pilsner")
					.quantityToBrew(200)
					.minOnHand(20)
					.upc(3376767676L)
					.price(new BigDecimal("211"))
				.build());

			beerRepository.save(Beer.builder()
				.beerName("Dreher")
				.beerType("Pilsner")
				.quantityToBrew(200)
				.minOnHand(20)
				.upc(3376767675L)
				.price(new BigDecimal("220"))
				.build());

			System.out.println("Default upload beer: " + beerRepository.count());
		}

		System.out.println("Existing beer: " + beerRepository.count());
	}
}
