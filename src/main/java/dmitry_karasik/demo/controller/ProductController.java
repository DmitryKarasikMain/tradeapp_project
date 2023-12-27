package dmitry_karasik.demo.controller;

import dmitry_karasik.demo.dto.ProductDTO;
import dmitry_karasik.demo.entity.Product;
import dmitry_karasik.demo.entity.Vendor;
import dmitry_karasik.demo.repository.ProductRepository;
import dmitry_karasik.demo.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/products/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final VendorRepository vendorRepository;

    @GetMapping
    public String products(Model model) {
        model.addAttribute("productsList", productRepository.findAll());
        model.addAttribute("vendorsList", vendorRepository.findAll());
        return "products";
    }

    @GetMapping("{productId}")
    public String product(@PathVariable Long productId, Model model) {
        model.addAttribute("product", productRepository.findById(productId).get());
        return "productInfo";
    }

    @PostMapping("add")
    public String addProduct(ProductDTO request, Model model) {
        model.addAttribute("vendor", vendorRepository.findById(request.getVendorId()));
        Product product = request.toEntity();
        productRepository.save(product);
        return "redirect:/products/";
    }
}
