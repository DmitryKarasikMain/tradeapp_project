package dmitry_karasik.demo.controller;

import dmitry_karasik.demo.dto.ProductDTO;
import dmitry_karasik.demo.entity.Product;
import dmitry_karasik.demo.entity.User;
import dmitry_karasik.demo.entity.Vendor;
import dmitry_karasik.demo.repository.ProductRepository;
import dmitry_karasik.demo.repository.UserRepository;
import dmitry_karasik.demo.repository.VendorRepository;
import dmitry_karasik.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    private final UserRepository userRepository;
    private final UserService userService;

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
    public String addProduct(ProductDTO request) {
        Product product = request.toEntity();
        product.setVendor(vendorRepository.findById(request.getVendorId()).get());
        productRepository.save(product);
        return "redirect:/products/";
    }

    @PostMapping("addToFavourites")
    @PreAuthorize("hasAuthority('USER')")
    public String addToFavourite(Long productId, Authentication authentication) {
        var productOptional = productRepository.findById(productId);
        var user = (User) authentication.getPrincipal();
        user = (User) userService.loadUserByUsername(user.getUsername());
        if (!user.getFavoriteProducts().contains(productOptional.get())) {
            user.getFavoriteProducts().add(productOptional.get());
            userService.save(user);
        } else {
            user.getFavoriteProducts().remove(productOptional.get());
            userService.save(user);
        }
        return "redirect:/products/";
    }
}
