package com.shopping.himanshu.service.product;

import com.shopping.himanshu.exceptions.ProductNotFound;
import com.shopping.himanshu.model.Category;
import com.shopping.himanshu.model.Product;
import com.shopping.himanshu.repository.CategoryRepository;
import com.shopping.himanshu.repository.ProductRepository;
import com.shopping.himanshu.request.AddProductRequest;
import com.shopping.himanshu.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductServiceInt {

//    @Autowired
    private final ProductRepository productRepo; // if we add final requiredargsconstrocutor will autowire the stuff

    private final CategoryRepository categoryRepo;

    @Override
    public List<Product> listAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        return productRepo.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductByBrand(String category, String brand) {
        return null;
    }

    @Override
    public List<Product> getProductByName(String name) {
        return null;
    }

    @Override
    public Product addProduct(AddProductRequest request) {
        // check if category is found in the DB
        // if yes set it as the new product category otherwise then save it
        // as a new category
        // then set it as category and sace product
//        Category category = Optional.ofNullable(categoryRepo.findByName(request.getCategory().getName()))
//                .orElseGet(() -> {
//                    Category newCategory = new Category(request.getCategory().getName());
//                    return categoryRepo.save(newCategory);
//                });
//        request.setCategory(category)
        Category category = Optional.ofNullable(categoryRepo.findByName(request.getCategory().getName())).orElseGet(() ->
                {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepo.save(newCategory);
                });
        request.setCategory(category);
        return productRepo.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                request.getInventory(),
                category
        );
    }
    @Override
    public Product getProductById(Long id) throws ProductNotFound {
        return productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFound("Product not found"));
    }

//    private Product UpdateExisitingProduct(Product existingProduct, ProductUpdateRequest request) {
//        existingProduct.setName(request.getName());
//    }

    @Override
    public void deleteProductById(Long id) {
        productRepo.findById(id)
                .ifPresentOrElse(productRepo::delete, () -> {
                    try {
                        throw new ProductNotFound("Product doesn't exists");
                    } catch (ProductNotFound e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) throws ProductNotFound {
        return productRepo.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepo :: save)
                .orElseThrow(() -> new ProductNotFound("Product not found"));

//        Product product = productRepo.findById(productId);

    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepo.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }
}
