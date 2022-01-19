package guru.springframework.sfgrestdocexample.web.mappers;

import guru.springframework.sfgrestdocexample.domain.Beer;
import guru.springframework.sfgrestdocexample.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper
{
	BeerDto beerToBeerDto(Beer beer);

	Beer beerDtoToBeer(BeerDto beerDto);
}
