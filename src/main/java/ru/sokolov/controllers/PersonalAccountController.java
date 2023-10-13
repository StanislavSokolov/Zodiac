package ru.sokolov.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sokolov.com.*;
import ru.sokolov.models.Item;
import ru.sokolov.models.Product;
import ru.sokolov.models.Stock;
import ru.sokolov.models.User;
import ru.sokolov.services.ItemService;
import ru.sokolov.services.ProductService;
import ru.sokolov.services.StockService;
import ru.sokolov.services.UserService;
import ru.sokolov.util.UserValidatorTokenOzon;
import ru.sokolov.util.UserValidatorTokenWB;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/account")
public class PersonalAccountController {
    @Autowired
    private final UserService userService;
    private final ProductService productService;
    private final ItemService itemService;
    private final StockService stockService;
    private final UserValidatorTokenWB userValidatorTokenWB;
    private final UserValidatorTokenOzon userValidatorTokenOzon;

    public PersonalAccountController(UserService userService, ProductService productService, ItemService itemService, StockService stockService, UserValidatorTokenWB userValidatorTokenWB, UserValidatorTokenOzon userValidatorTokenOzon) {
        this.userService = userService;
        this.productService = productService;
        this.itemService = itemService;
        this.stockService = stockService;
        this.userValidatorTokenWB = userValidatorTokenWB;
        this.userValidatorTokenOzon = userValidatorTokenOzon;
    }

    @GetMapping("/information")
    public String itemPage(@RequestParam("shop") String shop,
                           @RequestParam(value = "subject", required = false) String subject,
                           @RequestParam(value = "supplierArticle", required = false) String supplierArticle,
                           @ModelAttribute("user") User user,
                           Model model,
                           @CookieValue(value = "Authorization", required = false) String authorization,
                           @CookieValue(value = "Client", required = false) String client) {

        if (!Auth.getAuthorization(authorization, client)) return "auth/authorization";

        User userDB = userService.findOne(Integer.valueOf(client));

        model.addAttribute("shops", Auth.getShops(userDB));
        model.addAttribute("activeShop", shop);

        if (subject != null) {

            System.out.println(subject);

            List<Product> productList = productService.findBySubject(subject);
            System.out.println(productList.size());
            Product product = productList.get(0);
            model.addAttribute("product", product.getSubject());

            return "account/productCard";
        } else if (supplierArticle != null) {
            Product product = productService.findBySupplierArticle(supplierArticle).get(0);
            model.addAttribute("product", product.getSubject() + " арт. (" + product.getSupplierArticle() + ")");

            return "account/productCard";
        } else {
            model.addAttribute("product", "Товар");

            return "account/productCard";
        }

//        if (subject != null) return getProduct(); else getItem();

    }

    @GetMapping("/stock")
    public String stockPage(@RequestParam("shop") String shop,
                            @RequestParam(value = "sort", required = false) String sort,
                            @ModelAttribute("user") User user,
                            Model model,
                            @CookieValue(value = "Authorization", required = false) String authorization,
                            @CookieValue(value = "Client", required = false) String client,
                            HttpServletResponse httpServletResponse) {

        if (!Auth.getAuthorization(authorization, client)) return "auth/authorization";

        User userDB = userService.findOne(Integer.valueOf(client));

        model.addAttribute("shops", Auth.getShops(userDB));
        model.addAttribute("user", userDB);
        model.addAttribute("activeShop", shop);

//        model.addAttribute("stock", stockService.findAll());

        ArrayList<StockToShow> stocksToShow = new ArrayList<>();
        int countForColor = 0;
        for (Product product: productService.findAll()) {
            List<Stock> stocksList = product.getStocks();
            if (!stocksList.isEmpty()) {
                int quantity = 0, quantityFull = 0, inWayFromClient = 0;
                for (Stock stock: stocksList) {
                    quantity = quantity + stock.getQuantity();
                    quantityFull = quantityFull + stock.getQuantityFull();
                    inWayFromClient = inWayFromClient + stock.getInWayFromClient();
                }
                if ((quantity != 0) || (quantityFull != 0)) {
                    countForColor++;
                    stocksToShow.add(new StockToShow(product.getSubject(), product.getSupplierArticle(), quantity, quantityFull, inWayFromClient, countForColor % 2));
                }
            }
        }
        if (sort != null) {
            if (sort.equals("subject")) stocksToShow.sort((o1, o2) -> o1.getSubject().compareTo(o2.getSubject()));
            if (sort.equals("quantity")) stocksToShow.sort((o1, o2) -> o2.getQuantity() - o1.getQuantity());
            if (sort.equals("quantityFull")) stocksToShow.sort((o1, o2) -> (o2.getQuantityFull() - o2.getQuantity()) - (o1.getQuantityFull() - o1.getQuantity()));
            if (sort.equals("inWayFromClient")) stocksToShow.sort((o1, o2) -> o2.getInWayFromClient() - o1.getInWayFromClient());
        } else
            stocksToShow.sort((o1, o2) -> o1.getSubject().compareTo(o2.getSubject()));

        model.addAttribute("stocksToShow", stocksToShow);

        return "account/stock";
    }

    @GetMapping("/shop")
    public String shopPage(@RequestParam("shop") String shop,
                           @RequestParam(value = "sort", required = false) String sort,
                           @ModelAttribute("user") User user,
                           Model model,
                           @CookieValue(value = "Authorization", required = false) String authorization,
                           @CookieValue(value = "Client", required = false) String client,
                           HttpServletResponse httpServletResponse) {

        if (!Auth.getAuthorization(authorization, client)) return "auth/authorization";

        User userDB = userService.findOne(Integer.valueOf(client));
        model.addAttribute("shops", Auth.getShops(userDB));
        model.addAttribute("user", userDB);
        model.addAttribute("activeShop", shop);



        ArrayList<DayToShow> daysToShow = new ArrayList<>();

        // i - количество дней для показа на календаре
        // i = 0 (только сегодня), i = -6 (оследняя неделя)
        for (int i = -10; i < 1; i++) {
            daysToShow.add(new DayToShow(itemService.findByCdateAndStatus(Data.getData(i),"ordered").size(),
                    itemService.findBySdateAndStatus(Data.getData(i),"sold").size(),
                    itemService.findByCdateAndStatus(Data.getData(i), "cancelled").size(),
                    0,
                    Data.getData(i)));
        }

        model.addAttribute("today", daysToShow.get(daysToShow.size() - 1));
        model.addAttribute("daysToShow", daysToShow);

        ArrayList<ItemToShow> itemsToShow = new ArrayList<>();
        int countForColor = 0;
        for (Product product: productService.findAll()) {
            List<Item> itemList = product.getItems();
            if (!itemList.isEmpty()) {
                int ordered = 0, sold = 0, cancelled = 0;
                for (Item item: itemList) {
                    if ((item.getCdate().equals(Data.getData(0))) || (item.getSdate().equals(Data.getData(0)))) {
                        if (item.getStatus().equals("ordered")) ordered++;
                        if (item.getStatus().equals("sold")) sold++;
                        if (item.getStatus().equals("cancelled")) cancelled++;
                    }
                }
                if ((ordered != 0) || (sold != 0) || (cancelled != 0)) {
                    countForColor++;
                    itemsToShow.add(new ItemToShow(product.getSubject(), product.getSupplierArticle(), ordered, sold, cancelled, countForColor % 2));
                }
            }
        }
        if (sort != null) {
            if (sort.equals("subject")) itemsToShow.sort((o1, o2) -> o1.getSubject().compareTo(o2.getSubject()));
            if (sort.equals("ordered")) itemsToShow.sort((o1, o2) -> o2.getOrdered() - o1.getOrdered());
            if (sort.equals("sold")) itemsToShow.sort((o1, o2) -> o2.getSold() - o1.getSold());
            if (sort.equals("cancelled")) itemsToShow.sort((o1, o2) -> o2.getCancelled() - o1.getCancelled());
        } else
            itemsToShow.sort((o1, o2) -> o1.getSubject().compareTo(o2.getSubject()));
        model.addAttribute("itemsToShow", itemsToShow);

        return "account/shop";
    }

    @GetMapping("/settings")
    public String settingPage(@ModelAttribute("user") User user,
                              Model model,
                              @CookieValue(value = "Authorization", required = false) String authorization,
                              @CookieValue(value = "Client", required = false) String client) {

        if (!Auth.getAuthorization(authorization, client)) return "auth/authorization";

        User userDB = userService.findOne(Integer.valueOf(client));
        model.addAttribute("shops", Auth.getShops(userDB));
        model.addAttribute("user", userDB);

        return "account/settings";
    }

    @GetMapping("/out")
    public String outPage(@ModelAttribute("user") User user,
                          @CookieValue(value = "Authorization", required = false) String authorization,
                          @CookieValue(value = "Client", required = false) String client,
                          HttpServletResponse httpServletResponse) {

        if (!Auth.getAuthorization(authorization, client)) return "auth/authorization";

        Cookie cookieAuthorization = new Cookie("Authorization", "false");
        cookieAuthorization.setMaxAge(0);
        cookieAuthorization.setSecure(true);
        cookieAuthorization.setHttpOnly(true);
        cookieAuthorization.setPath("/");
        httpServletResponse.addCookie(cookieAuthorization);

        Cookie cookieClient = new Cookie("Client", "Qwxs12MM");
        cookieClient.setMaxAge(0);
        cookieClient.setSecure(true);
        cookieClient.setHttpOnly(true);
        cookieClient.setPath("/");
        httpServletResponse.addCookie(cookieClient);

        return "auth/authorization";
    }

    @PostMapping("/settings/wb")
    public String addTokenWB(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @CookieValue(value = "Authorization", required = false) String authorization,
                             @CookieValue(value = "Client", required = false) String client) {
        userValidatorTokenWB.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "account/settings";

        userService.updateTokenWB(Integer.valueOf(client), user);
        return "redirect:/account/settings"; // и переходим в лк (в раздел настройки)
    }

    @PostMapping("/settings/ozon")
    public String addTokenOzon(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                               @CookieValue(value = "Authorization", required = false) String authorization,
                               @CookieValue(value = "Client", required = false) String client) {
        userValidatorTokenOzon.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "/account/settings";

        userService.updateTokenOzon(Integer.valueOf(client), user);
        return "redirect:/account/settings"; // и переходим в лк (в раздел настройки)
    }
}
