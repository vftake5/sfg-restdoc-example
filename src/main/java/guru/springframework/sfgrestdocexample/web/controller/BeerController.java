package guru.springframework.sfgrestdocexample.web.controller;

import guru.springframework.sfgrestdocexample.web.model.BeerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Validálandó mezők beállítását lásd: BeerDto
 * A Validáció kapcsán került bele egy "@ExceptionHandler" annotációjú eljárás.
 * A class definiálja a kezelt kivételt.
 * A hiba objektumba bekerül a hiba mező szinten meghatározott oka.
 *
 * Client oldalon a kezelését lásd guru.springframework.msscbreweryclient.web.client.BreweryClient.saveNewBeerW(BeerDto beerDto)
 */

@Slf4j
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController
{

	@GetMapping("/{beerId}")
	public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId)
	{
		// TODO megcsinálni
		return new ResponseEntity<>(BeerDto.builder().build(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<UUID> saveNewBeer(@RequestBody @Validated BeerDto beerDto)
	{
		// TODO megcsinálni
		log.debug("Start saveNewBeer...");

		beerDto.setId(UUID.randomUUID());

		System.out.println("BeerDTO: " + beerDto);

		ResponseEntity responseEntity = new ResponseEntity<UUID>(beerDto.getId(), HttpStatus.CREATED);

		System.out.println("ResponseEntity: " + responseEntity.toString());

 		return responseEntity;
	}

	@PutMapping("/{beerId}")
	public ResponseEntity updateBeerById(@PathVariable("beerId") UUID beerId, @RequestBody @Validated BeerDto beerDto)
	{
		// TODO megcsinálni
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{beerId}")
	public ResponseEntity deleteBeerById(@PathVariable("beerId") UUID beerId)
	{
		// TODO megcsinálni
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<List> validationErrorHandler(MethodArgumentNotValidException e)
//	{
//		List<ObjectError> errorList = e.getAllErrors();
//		List<String> error = new ArrayList<>(errorList.size());
//
//		e.getAllErrors().forEach(argNotValid -> {
//			error.add(argNotValid.toString() + " | " + argNotValid.getCode() + " : " + argNotValid.getDefaultMessage());
//		});
//
//		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//	}
}
