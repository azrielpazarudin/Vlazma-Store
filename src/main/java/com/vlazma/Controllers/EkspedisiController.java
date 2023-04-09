package com.vlazma.Controllers;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vlazma.Utils.ROBody;
import com.vlazma.Utils.RORequest;

@RestController
@RequestMapping("/vlazma")
public class EkspedisiController {
    @Autowired
    private RORequest request;
    @GetMapping("/province")
    public Object getProvince() throws IOException {

        return request.provinceRequest().body().string();
    }

    @GetMapping("/province/{id}")
    public Object getProvince(@PathVariable String id) throws IOException {

        return request.provinceRequest(id).body().string();
    }

    @GetMapping("/city")
    public Object getCity() throws IOException{
        return request.cityRequest().body().string();
    }

    @GetMapping("/city/{id}")
    public Object getCity(@PathVariable int id) throws IOException{
        return request.cityRequest(id).body().string();
    }

    @GetMapping("/city-by-province/{province}")
    public Object getCity(@PathVariable String province) throws IOException{
        return request.cityRequest(province).body().string();
    }

    @PostMapping("/cost")
    public Object cost(@RequestBody ROBody body) throws IOException{
        return request.costRequest(body).body().string();
    }
}
