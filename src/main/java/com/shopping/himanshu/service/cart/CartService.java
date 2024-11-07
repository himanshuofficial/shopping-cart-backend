package com.shopping.himanshu.service.cart;


import com.shopping.himanshu.exceptions.ResourceNotFound;
import com.shopping.himanshu.model.Cart;
import com.shopping.himanshu.repository.CartItemRepository;
import com.shopping.himanshu.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements CartInt {

    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);


    @Override
    public Cart getCart(Long id) throws ResourceNotFound {
        Cart cart = cartRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepo.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long id) throws ResourceNotFound {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepo.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) throws ResourceNotFound {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Long initializeNewCart() {
        Cart newCart = new Cart();
        Long newCartId = cartIdGenerator.incrementAndGet();
        newCart.setId(newCartId);
        return cartRepo.save(newCart).getId();
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepo.findByUserId(userId);
    }
}
