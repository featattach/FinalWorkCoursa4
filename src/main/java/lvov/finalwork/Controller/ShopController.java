package lvov.finalwork.Controller;


import lombok.extern.slf4j.Slf4j;
import lvov.finalwork.entity.Shop;
import lvov.finalwork.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Slf4j
@Controller
public class ShopController {
    @Autowired
    private ShopRepository shopRepository;

    @GetMapping("/shops")
    public ModelAndView getAllShops() {
        log.info("/shops -> connection");
        ModelAndView mav = new ModelAndView("list-shop");
        mav.addObject("shops", shopRepository.findAll());
        return mav;
    }

    @GetMapping("/addShopForm")
    public ModelAndView addShopForm() {
        ModelAndView mav = new ModelAndView("add-shop-form");
        Shop shop = new Shop();
        mav.addObject("shop", shop);
        return mav;
    }

    @PostMapping("/saveShop")
    public String saveShop(@ModelAttribute Shop shop) {
        shopRepository.save(shop);

        return "redirect:/shops";
    }

    @GetMapping("/showUpdateShopForm")
    public ModelAndView showUpdateShopForm(@RequestParam Long shopId) {
        ModelAndView mav = new ModelAndView("add-shop-form");
        Optional<Shop> optionalShop = shopRepository.findById(shopId);
        Shop shop = new Shop();
        if (optionalShop.isPresent()) {
            shop = optionalShop.get();
        }
        mav.addObject("shop", shop);
        return mav;
    }

    @GetMapping("/deleteShop")
    public String deleteShop(@RequestParam Long shopId) {
        shopRepository.deleteById(shopId);
        return "redirect:/shops";
    }
}
