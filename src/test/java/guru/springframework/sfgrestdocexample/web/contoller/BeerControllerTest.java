package guru.springframework.sfgrestdocexample.web.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfgrestdocexample.domain.Beer;
import guru.springframework.sfgrestdocexample.repositories.BeerRepository;
import guru.springframework.sfgrestdocexample.web.controller.BeerController;
import guru.springframework.sfgrestdocexample.web.model.BeerDto;
import guru.springframework.sfgrestdocexample.web.model.BeerStyleEnum;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "guru.springframework.sfgrestdocexample.web.mappers")
class BeerControllerTest
{

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	BeerRepository beerRepository;

	@Test
	void getBeerById() throws Exception
	{
		given(beerRepository.findById(any())).willReturn(Optional.of(Beer.builder().build()));

		mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID())
				.param("isCold", "yes")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("v1/beer-get",
					pathParameters(
						parameterWithName("beerId").description("A lekérdezett sör azonosítója - UUID")
					),
					requestParameters(
						parameterWithName("isCold").description("Hideg legye-e a sör?")
					),
					responseFields(
						fieldWithPath("id").description("Sör egyedi azonosítója (UUID)"),
						fieldWithPath("version").description("Record version"),
						fieldWithPath("createdDate").description("Létrehozás dátuma"),
						fieldWithPath("lastModifiedDate").description("Utolsó módosítás dátuma"),
						fieldWithPath("beerName").description("Sör neve"),
						fieldWithPath("beerType").description("Sör típusa"),
						fieldWithPath("upc").description("Sör UPC"),
						fieldWithPath("price").description("Ár"),
						fieldWithPath("quantityOnHand").description("Készlet")

					)));
	}

	@Test
	void saveNewBeer() throws Exception
	{
		val beerDto = getValidBeerDto();
		String beerDtoJson = objectMapper.writeValueAsString(beerDto);

		ConstraintedFields fields = new ConstraintedFields(BeerDto.class);

		mockMvc.perform(org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post("/api/v1/beer/")
			.contentType(MediaType.APPLICATION_JSON)
			.content(beerDtoJson))
			.andExpect(status().isCreated())
			.andDo(
				document("v1/beer-new",
					requestFields(
						fields.withPath("id").ignored(),
						fields.withPath("version").ignored(),
						fields.withPath("createdDate").ignored(),
						fields.withPath("lastModifiedDate").ignored(),
						fields.withPath("beerName").description("Sör neve"),
						fields.withPath("beerType").description("Sör típusa"),
						fields.withPath("upc").description("Sör UPC"),
						fields.withPath("price").description("Ár"),
						fields.withPath("quantityOnHand").ignored()
					)));
	}

	@Test
	void updateBeerById() throws Exception
	{
		BeerDto beerDto = BeerDto.builder().beerName("New beer").upc(1L).beerType(BeerStyleEnum.PILSNER).price(new BigDecimal("120")).build();String beerDtoJson = objectMapper.writeValueAsString(beerDto);

		mockMvc.perform(org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put("/api/v1/beer/" + UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON)
				.content(beerDtoJson))
			.andExpect(status().isNoContent());
	}

	BeerDto getValidBeerDto()
	{
		return BeerDto.builder()
			.beerName("New beer")
			.upc(1L)
			.beerType(BeerStyleEnum.PILSNER)
			.price(new BigDecimal("120"))
			.build();
	}

	private static class ConstraintedFields
	{
		private final ConstraintDescriptions constraintDescriptions;

		ConstraintedFields(Class<?> input )
		{
			this.constraintDescriptions = new ConstraintDescriptions(input);
		}

		private FieldDescriptor withPath(String path)
		{
			return fieldWithPath(path)
					.attributes(key("constraints")
						.value(StringUtils.collectionToDelimitedString(this.constraintDescriptions.descriptionsForProperty(path), ". ")));
		}
	}
}