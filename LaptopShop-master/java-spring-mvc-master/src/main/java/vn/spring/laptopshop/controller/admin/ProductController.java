package vn.spring.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.spring.laptopshop.domain.Product;
import vn.spring.laptopshop.service.FeedbackService;
import vn.spring.laptopshop.service.ProductService;
import vn.spring.laptopshop.service.UploadService;

@Controller
public class ProductController {
  private final ProductService productService;
  private final UploadService uploadService;
  private final FeedbackService feedbackService ;

  public ProductController(ProductService productService, UploadService uploadService , FeedbackService feedbackService ) {
    this.productService = productService;
    this.uploadService = uploadService;
    this.feedbackService = feedbackService ;
  }

  @RequestMapping("/admin/product")
  public String getProduct(Model model , @RequestParam(value = "page", defaultValue = "1") int page ) {
    Pageable pageable = PageRequest.of(page-1, 2); //4 : số phần tử trong trag
    Page<Product> products = this.productService.fetchProducts(pageable);
    List<Product> listProducts = products.getContent() ;
    model.addAttribute("products", listProducts );
    model.addAttribute("currentPage" , page) ;
    model.addAttribute("totalPages" , products.getTotalPages()) ;

    return "admin/product/show";
  }

  @GetMapping("/admin/product/{id}")
  public String getProductDetailPage(Model model, @PathVariable long id) {
    Product product = this.productService.getProductById(id).get();
    model.addAttribute("product", product);
    model.addAttribute("id", id);
    return "admin/product/detail";
  }

  @GetMapping("/admin/product/create") // GET
  public String getCreateProductPage(Model model) {
    model.addAttribute("newProduct", new Product());
    return "admin/product/create";
  }

  @PostMapping(value = "/admin/product/create")
  public String handleCreateProduct(Model model,
      @ModelAttribute("newProduct") @Valid Product pr, BindingResult newProductBindingResult,
      @RequestParam("springFile") MultipartFile file) {
    // Validate
    if (newProductBindingResult.hasErrors()) {
      return "/admin/product/create";
    }
    String image = this.uploadService.handleSaveUploadFile(file, "product");
    pr.setImage(image);

    // save
    this.productService.createProduct(pr);
    return "redirect:/admin/product";
  }

  @RequestMapping("/admin/product/update/{id}")
  public String getUpdateProductDetailPage(Model model, @PathVariable long id) {
        Optional<Product> currentProduct = this.productService.getProductById(id);
        if (currentProduct.isPresent()) {
            model.addAttribute("newProduct", currentProduct.get());
        } else {
            model.addAttribute("newProduct", new Product());
        }
        return "admin/product/update";
    }

  @PostMapping("/admin/product/update")
  public String postUpdateUser(Model model, @ModelAttribute("newProduct") @Valid Product product,
      BindingResult newProductBindingResult, @RequestParam("springFile") MultipartFile file) {
        if (newProductBindingResult.hasErrors()) {
          model.addAttribute("newProduct", product); // Truyền lại dữ liệu đã nhập
          return "admin/product/update";
        }

      Optional<Product> currentProductOptional = this.productService.getProductById(product.getId());
        if (currentProductOptional.isPresent()) {
            Product currentProduct = currentProductOptional.get();
            if (!file.isEmpty()) {
                String image = this.uploadService.handleSaveUploadFile(file, "product");
                currentProduct.setImage(image);
            }
            currentProduct.setName(product.getName());
            currentProduct.setPrice(product.getPrice());
            currentProduct.setDetailDesc(product.getDetailDesc());
            currentProduct.setShortDesc(product.getShortDesc());
            currentProduct.setQuantity(product.getQuantity());
            currentProduct.setFactory(product.getFactory());
            currentProduct.setTarget(product.getTarget());

            this.productService.createProduct(currentProduct);
        } else {
            model.addAttribute("error", "Product not found");
            return "admin/product/update";
        }
        return "redirect:/admin/product";
    }

  @GetMapping("/admin/product/delete/{id}")
  public String getDeleteProductPage(Model model, @PathVariable long id) {
    model.addAttribute("id", id);
    model.addAttribute("newProduct", new Product());
    return "admin/product/delete";
  }

  @PostMapping("/admin/product/delete")
  public String postDeleteProduct(Model model, @ModelAttribute("newProduct") Product product) {
    this.productService.deleteProduct(product.getId());
    return "redirect:/admin/product";
  }

}