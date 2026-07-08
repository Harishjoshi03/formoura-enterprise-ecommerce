package com.formoura.cart.serviceImp;

import com.formoura.cart.client.ProductClient;
import com.formoura.cart.dto.ProductDto;
import com.formoura.cart.dto.request.CartRequest;
import com.formoura.cart.dto.request.UpdateCartRequest;
import com.formoura.cart.dto.response.CartResponse;
import com.formoura.cart.entity.Cart;
import com.formoura.cart.entity.CartItem;
import com.formoura.cart.entity.CartStatus;
import com.formoura.cart.mapper.CartMapper;
import com.formoura.cart.repository.CartItemRepository;
import com.formoura.cart.repository.CartRepository;
import com.formoura.cart.service.CartService;
import com.formoura.exception.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final ProductClient productClient;

    private final CartMapper mapper;

    @Override
    public CartResponse addToCart(Long userId,
                                  CartRequest request) {

        Cart cart = cartRepository
                .findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseGet(() -> {

                    Cart newCart = new Cart();

                    newCart.setUserId(userId);

                    newCart.setStatus(CartStatus.ACTIVE);

                    newCart.setActive(true);

                    return cartRepository.save(newCart);

                });

        ProductDto product =
                productClient.getProduct(request.getProductId());

        CartItem item = cartItemRepository
                .findByCartIdAndProductId(
                        cart.getId(),
                        request.getProductId())
                .orElse(null);

        if (item == null) {

            item = CartItem.builder()
                    .cart(cart)
                    .productId(product.getId())
                    .productName(product.getProductName())
                    .imageUrl(product.getImageUrl())
                    .price(product.getPrice())
                    .quantity(request.getQuantity())
                    .totalPrice(product.getPrice()
                            .multiply(BigDecimal.valueOf(request.getQuantity())))
                    .build();

        } else {

            item.setQuantity(
                    item.getQuantity() + request.getQuantity());

            item.setTotalPrice(
                    item.getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity())));

        }

        cartItemRepository.save(item);

        calculateCart(cart);

        return mapper.toResponse(cartRepository.save(cart));

    }

    @Override
    public CartResponse updateCartItem(Long cartItemId,
                                       UpdateCartRequest request) {

        CartItem item = cartItemRepository.findById(cartItemId)

                .orElseThrow(() ->
                        new BusinessException("Cart Item Not Found"));

        item.setQuantity(request.getQuantity());

        item.setTotalPrice(
                item.getPrice()
                        .multiply(BigDecimal.valueOf(request.getQuantity())));

        cartItemRepository.save(item);

        calculateCart(item.getCart());

        return mapper.toResponse(
                cartRepository.save(item.getCart()));

    }

    @Override
    public CartResponse getUserCart(Long userId) {

        Cart cart = cartRepository
                .findByUserIdAndStatus(userId, CartStatus.ACTIVE)

                .orElseThrow(() ->
                        new BusinessException("Cart Not Found"));

        return mapper.toResponse(cart);

    }

    @Override
    public void removeCartItem(Long cartItemId) {

        CartItem item = cartItemRepository.findById(cartItemId)

                .orElseThrow(() ->
                        new BusinessException("Cart Item Not Found"));

        Cart cart = item.getCart();

        cartItemRepository.delete(item);

        calculateCart(cart);

        cartRepository.save(cart);

    }

    @Override
    public void clearCart(Long userId) {

        Cart cart = cartRepository
                .findByUserIdAndStatus(userId, CartStatus.ACTIVE)

                .orElseThrow(() ->
                        new BusinessException("Cart Not Found"));

        cartItemRepository.deleteAll(cart.getCartItems());

        cart.setTotalAmount(BigDecimal.ZERO);

        cart.setTotalItems(0);

        cartRepository.save(cart);

    }

    private void calculateCart(Cart cart) {

        BigDecimal total = BigDecimal.ZERO;

        int items = 0;

        for (CartItem item : cart.getCartItems()) {

            total = total.add(item.getTotalPrice());

            items += item.getQuantity();

        }

        cart.setTotalAmount(total);

        cart.setTotalItems(items);

    }

}