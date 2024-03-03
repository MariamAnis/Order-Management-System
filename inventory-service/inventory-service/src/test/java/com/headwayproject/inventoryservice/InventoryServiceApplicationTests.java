package com.headwayproject.inventoryservice;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.headwayproject.inventoryservice.controller.CategoryController;
import com.headwayproject.inventoryservice.controller.ProductController;
import com.headwayproject.inventoryservice.dto.CategoryDto;
import com.headwayproject.inventoryservice.dto.ProductDto;
import com.headwayproject.inventoryservice.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class InventoryServiceApplicationTests {

	@Autowired
	@MockBean
	private MockMvc mockMvc;
	@Autowired
	@MockBean
	ProductController productController;

	@Autowired
	@MockBean
	CategoryController categoryController;

	@Test
	void contextLoads() {
	}

	@Test
	public void addProductTest() throws Exception {
		ProductDto product = createProduct();
		ObjectMapper map =new ObjectMapper();
		String json= map.writeValueAsString(product);
		this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/products").contentType(MediaType.APPLICATION_JSON).content(json);
		this.mockMvc.perform(builder).andExpect(status().isOk());

	}

	@Test void deleteProductTest() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/products/{id}",14);
		this.mockMvc.perform(builder)
				.andExpect(status().isOk());
	}

	@Test
	void getProductsTest() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/products");
		this.mockMvc.perform(builder)
				.andExpect(status().isOk());
	}

	@Test
	void getProductByIdTest()throws Exception{
		this.mockMvc=MockMvcBuilders.standaloneSetup(productController).build();
		MockHttpServletRequestBuilder builder = get("/products/{id}",1);
		this.mockMvc.perform(builder).andExpect(status().isOk());
	}

	@Test
	void editProductTest()throws Exception{
		ProductDto product = createProduct();
		ObjectMapper map =new ObjectMapper();
		String json=map.writeValueAsString(product);
		this.mockMvc=MockMvcBuilders.standaloneSetup(productController).build();
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/products/{id}",1).contentType(MediaType.APPLICATION_JSON).content(json);
		this.mockMvc.perform(builder).andExpect(status().isOk());
	}
	public ProductDto createProduct(){
		ProductDto product = new ProductDto();
		Category category = new Category(3,"sweets");
		product.setName("Cadbury");
		product.setCategory(category);
		product.setQuantity(50);
		product.setPrice(30);
		product.setDescription("chocolate");
		return product;
	}

	/// Category controller tests
	@Test void getAllCategoriesTest() throws Exception{
		this.mockMvc=MockMvcBuilders.standaloneSetup(categoryController).build();
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/categories");
		this.mockMvc.perform(builder).andExpect(status().isOk());
	}
	@Test void getCategoryByIdTest()throws Exception{
		this.mockMvc=MockMvcBuilders.standaloneSetup(categoryController).build();
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/categories/{id}",1);
		this.mockMvc.perform(builder).andExpect(status().isOk());
	}
	@Test void addCategoryTest() throws Exception{
		ObjectMapper map = new ObjectMapper();
		String json = map.writeValueAsString(createCategory());

		this.mockMvc=MockMvcBuilders.standaloneSetup(categoryController).build();
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/categories").contentType(MediaType.APPLICATION_JSON)
				.content(json);
		this.mockMvc.perform(builder).andExpect(status().isOk());
	}
	@Test void deleteCategoryTest()throws Exception{
		this.mockMvc=MockMvcBuilders.standaloneSetup(categoryController).build();
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/categories/{id}",2);
		this.mockMvc.perform(builder).andExpect(status().isOk());
	}
	public CategoryDto createCategory(){
		return new CategoryDto("Fruits");
	}
}
