package com.dh.msusers.repository.feign;

import com.dh.msusers.model.Bill;
import com.dh.msusers.repository.IBillsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BillsFeignRepository implements IBillsRepository {

    private final IBillsFeignClient feignClient;

    @Override
    public List<Bill> findCustomerById(String id) {
        ResponseEntity<List<Bill>> responseEntity = feignClient.findByCustomer(id);
        return responseEntity.getBody();
    }
}
