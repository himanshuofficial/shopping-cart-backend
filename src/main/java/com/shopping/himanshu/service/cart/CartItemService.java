package com.shopping.himanshu.service.cart;

import com.shopping.himanshu.exceptions.ResourceNotFound;
import com.shopping.himanshu.model.Cart;
import com.shopping.himanshu.model.CartItem;
import com.shopping.himanshu.model.Product;
import com.shopping.himanshu.repository.CartItemRepository;
import com.shopping.himanshu.repository.CartRepository;
import com.shopping.himanshu.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class CartItemService implements  CartItemServiceInt{

    private final CartItemRepository cartItemRepo;
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CartService cartService;

    @Override
    public void addItemToCart(Long cartId, Long ProductId, int quantity) throws ResourceNotFound {
        // get the cart
        // get the product
        // check if product already in the cart
        // if yes increase quantity
        // if no, intiatise new cart item entry
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(ProductId);
        CartItem cartItem = cart.getItems()
                .stream()
                .filter((item) -> item.getProduct().getId().equals(ProductId))
                .findFirst().orElse(new CartItem());
        if(cartItem.getId() == null) {
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepo.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) throws ResourceNotFound {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = cart.getItems()
                .stream()
                .filter((item) -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFound("Cart not found"));

        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) throws ResourceNotFound {
        Cart cart = cartService.getCart(cartId);
        cart.getItems()
                .stream()
                .filter((item) -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });

        BigDecimal totalAmount = cart.getItems().stream().map(CartItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }
}
