package com.example.smallbusinessmanagement.service;

import com.example.smallbusinessmanagement.dto.*;
import com.example.smallbusinessmanagement.model.Customer;
import com.example.smallbusinessmanagement.model.Sale;
import com.example.smallbusinessmanagement.repository.CustomerRepository;
import com.example.smallbusinessmanagement.repository.SaleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;

    public Customer createCustomer(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setFullName(request.getFullName());
        customer.setPhone(request.getPhone());
        customer.setEmail(request.getEmail());
        return customerRepository.save(customer);
    }

    public CustomerSalesResponse getCustomerSales(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Клиент не найден"));

        List<Sale> sales = saleRepository.findByCustomerId(customerId);

        BigDecimal totalSpent = sales.stream()
                .map(s -> s.getItems().stream()
                        .map(i -> i.getSellingPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Маппинг сущностей Sale в SaleResponse (простейший пример)
        List<SaleResponse> saleResponses = sales.stream()
                .map(s -> {
                    SaleResponse sr = new SaleResponse();
                    sr.setId(s.getId());
                    sr.setDate(s.getDate());
                    // customerInfo можно установить, например:
                    sr.setCustomer(new CustomerInfo(customer.getId(), customer.getFullName(), customer.getPhone(), customer.getEmail()));

                    // Маппинг позиций продажи
                    List<SaleItemResponse> items = s.getItems().stream()
                            .map(i -> {
                                SaleItemResponse sir = new SaleItemResponse();
                                sir.setProductName(i.getProduct().getName());
                                sir.setQuantity(i.getQuantity());
                                sir.setPricePerUnit(i.getSellingPrice());
                                sir.setTotal(i.getSellingPrice().multiply(BigDecimal.valueOf(i.getQuantity())));
                                return sir;
                            })
                            .toList();

                    sr.setItems(items);
                    sr.setTotal(items.stream().map(SaleItemResponse::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add));
                    sr.setDiscount(s.getDiscount());
                    sr.setPaymentMethod(s.getPaymentMethod());

                    return sr;
                }).toList();

        return new CustomerSalesResponse(
                customer.getId(),
                customer.getFullName(),
                sales.size(),
                totalSpent,
                saleResponses // передаём список продаж
        );
    }
    public List<CustomerInfo> getAllCustomerInfos() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::mapToCustomerInfo)
                .toList();
    }

    private CustomerInfo mapToCustomerInfo(Customer customer) {
        CustomerInfo info = new CustomerInfo();
        info.setId(customer.getId());
        info.setFullName(customer.getFullName());
        info.setPhone(customer.getPhone());
        info.setEmail(customer.getEmail());
        return info;
    }
}



