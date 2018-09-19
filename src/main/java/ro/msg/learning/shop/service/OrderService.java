package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.dto.InitialOrderDTO;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.exception.OrderHandlingException;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.repository.OrderDetailRepository;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.utils.CurrentLocationStrategy;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final CurrentLocationStrategy currentLocationStrategy;

    private static final String DEFAULT_USERNAME = "ctirrey0";

    /**
     * Create a Order entity with given input
     *
     * @param initialOrderDTO OrderDTO - input data
     * @return Order order
     * @throws OrderHandlingException - if the order could not be created or if there is no possibility to ship the requested products
     */
    @Transactional
    public Order createOrder(InitialOrderDTO initialOrderDTO) throws OrderHandlingException {
        try {
            //save first the order, with no shipping details
            Customer customer = customerRepository.findByUsername(DEFAULT_USERNAME);
            Order order = new Order(customer, initialOrderDTO.getAddress(), initialOrderDTO.getDate());
            orderRepository.save(order);
            for (ProductDTO productDTO : initialOrderDTO.getProducts()) {
                Product product = productRepository.findById(productDTO.getId()).orElse(null);
                if(product == null) {
                    throw new OrderHandlingException("Order product not found");
                }
                orderDetailRepository.save(
                        new OrderDetail(new OrderDetailId(order, product), productDTO.getQuantity()));
            }
            //ship products for current order
            order = orderRepository.findById(order.getId()).orElse(null);
            if (order == null) {
                throw new OrderHandlingException("Order could not be created");
            }
            currentLocationStrategy.getCurrentLocationStrategy().fetchShippingDetailsForOrder(order);
            return orderRepository.findById(order.getId()).orElse(null);
        } catch (Exception e) {
            throw new OrderHandlingException(e.getCause() + e.getMessage());
        }
    }
}