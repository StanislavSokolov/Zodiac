package ru.sokolov.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sokolov.com.*;
import ru.sokolov.models.*;
import ru.sokolov.services.*;
import ru.sokolov.util.UserValidatorTokenOzon;
import ru.sokolov.util.UserValidatorTokenWB;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private final YearService yearService;
    private final UserValidatorTokenWB userValidatorTokenWB;
    private final UserValidatorTokenOzon userValidatorTokenOzon;

    public PersonalAccountController(UserService userService, ProductService productService, ItemService itemService, StockService stockService, YearService yearService, UserValidatorTokenWB userValidatorTokenWB, UserValidatorTokenOzon userValidatorTokenOzon) {
        this.userService = userService;
        this.productService = productService;
        this.itemService = itemService;
        this.stockService = stockService;
        this.yearService = yearService;
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

            List<Product> productList = productService.findBySubject(subject);
            Product product = productList.get(0);
            model.addAttribute("product", product.getSubject());

            return "account/productCard";
        } else if (supplierArticle != null) {
            Product product = productService.findBySupplierArticle(supplierArticle).get(0);
            model.addAttribute("product", product);
            model.addAttribute("name", product.getSubject() + " арт. (" + product.getSupplierArticle() + ")");

            return "account/productCard";
        } else {
            model.addAttribute("product", "Товар");

            return "account/productCard";
        }

    }

    @GetMapping("/stock")
    public String stockPage(@RequestParam("shop") String shop,
                            @RequestParam(value = "sort", required = false) String sort,
                            @ModelAttribute("user") User user,
                            Model model,
                            @CookieValue(value = "Authorization", required = false) String authorization,
                            @CookieValue(value = "Client", required = false) String client,
                            HttpServletResponse httpServletResponse,
                            HttpServletRequest httpServletRequest) {

//        System.out.println(httpServletRequest.getRequestURI() + shop + sort);
        if (!Auth.getAuthorization(authorization, client)) {
//            model.addAttribute("stringRequestURI", httpServletRequest.getRequestURI());
            return "auth/authorization";
        }

        User userDB = userService.findOne(Integer.valueOf(client));

        model.addAttribute("shops", Auth.getShops(userDB));
        model.addAttribute("user", userDB);
        model.addAttribute("activeShop", shop);

        ArrayList<StockToShow> stocksToShow = new ArrayList<>();
        int countForColor = 0;
        List<Product> productList = productService.findAll();
        for (Product product: productList) {
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
                    stocksList.sort((o1, o2) -> o2.getQuantity() - o1.getQuantity());
                    stocksToShow.add(new StockToShow(product.getSubject(), product.getSupplierArticle(), quantity, quantityFull, inWayFromClient, countForColor % 2, stocksList));
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

        for(int i = 0; i < stocksToShow.size(); i++) {
            stocksToShow.get(i).setColor(i % 2);
        }

        for(int i = 0; i < stocksToShow.size(); i++) {
            if (!stocksToShow.get(i).isCoincidence()) {
                stocksToShow.get(i).setCoincidence(true);
                ArrayList<StockToShow> stockCoincidence = new ArrayList<>();
                stockCoincidence.add(stocksToShow.get(i));
                for (int j = i + 1; j < stocksToShow.size() - 1; j++) {
                    if (stocksToShow.get(i).getSubject().equals(stocksToShow.get(j).getSubject())) {
                        stocksToShow.get(j).setCoincidence(true);
                        stockCoincidence.add(stocksToShow.get(j));
                    }
                }
                for (int j = 0; j < stockCoincidence.size(); j++) {
                    if (j == 0) {
                        for (Stock s: stockCoincidence.get(j).getStocks()) {
                            stockCoincidence.get(j).getStocksAll().add(new Stock(s.getWarehouseName(), s.getQuantity(), s.getQuantityFull(), s.getInWayFromClient(), s.getOwner()));
                        }
                    } else {
                        for (int a = 0; a < stockCoincidence.get(j).getStocks().size(); a++) {
                            boolean coincidence = false;
                            for (int b = 0; b < stockCoincidence.get(0).getStocksAll().size(); b++) {
                                if (stockCoincidence.get(0).getStocksAll().get(b).getWarehouseName().equals(stockCoincidence.get(j).getStocks().get(a).getWarehouseName())) {
                                    stockCoincidence.get(0).getStocksAll().get(b).setQuantity(stockCoincidence.get(0).getStocksAll().get(b).getQuantity() + stockCoincidence.get(j).getStocks().get(a).getQuantity());
                                    stockCoincidence.get(0).getStocksAll().get(b).setQuantityFull(stockCoincidence.get(0).getStocksAll().get(b).getQuantityFull() + stockCoincidence.get(j).getStocks().get(a).getQuantityFull());
                                    stockCoincidence.get(0).getStocksAll().get(b).setInWayFromClient(stockCoincidence.get(0).getStocksAll().get(b).getInWayFromClient() + stockCoincidence.get(j).getStocks().get(a).getInWayFromClient());
                                    coincidence = true;
                                }
                            }
                            if (!coincidence) stockCoincidence.get(0).getStocksAll().add(new Stock(stockCoincidence.get(j).getStocks().get(a).getWarehouseName(),
                                    stockCoincidence.get(j).getStocks().get(a).getQuantity(),
                                    stockCoincidence.get(j).getStocks().get(a).getQuantityFull(),
                                    stockCoincidence.get(j).getStocks().get(a).getInWayFromClient(),
                                    stockCoincidence.get(j).getStocks().get(a).getOwner()));
                        }
                    }
                }
                stockCoincidence.get(0).getStocksAll().sort((o1, o2) -> o2.getQuantity() - o1.getQuantity());
                for (int j = 1; j < stockCoincidence.size(); j++) {
                    for (int k = 0; k < stockCoincidence.get(0).getStocksAll().size(); k++) {
                        stockCoincidence.get(j).setStocksAll(stockCoincidence.get(0).getStocksAll());
                    }
                }
            }
        }

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
        // i = 0 (только сегодня), i = -6 (последняя неделя)


        for (int i = -50; i < 1; i++) {
            List<Year> years = yearService.findByCdate(Data.getData(i));
            if (!years.isEmpty())
                daysToShow.add(new DayToShow(years.get(0).getOrders(),
                        years.get(0).getSales(),
                        years.get(0).getReturns(),
                        0,
                        Data.getData(i)));
        }

        if (!daysToShow.isEmpty()) {
            if (daysToShow.get(daysToShow.size() - 1).getDate().equals(Data.getDataCurrent())) model.addAttribute("today", daysToShow.get(daysToShow.size() - 1));
            else model.addAttribute("today", new DayToShow(0, 0, 0 , 0, Data.getDataCurrent()));
        }
        model.addAttribute("daysToShow", daysToShow);

        ArrayList<ItemToShow> itemsToShow = new ArrayList<>();
        int countForColor = 0;
        for (Product product: productService.findAll()) {
            List<Item> itemList = product.getItems();
            if (!itemList.isEmpty()) {
                int ordered = 0, sold = 0, cancelled = 0;
                ArrayList<WareHouse> wareHouses = new ArrayList<>();
                for (Item item: itemList) {
                    if (item.getCdate().equals(Data.getData(0))) {
                        if (item.getStatus().equals("ordered")) ordered++;
//                        if (item.getStatus().equals("sold")) sold++;
                        if (item.getStatus().equals("cancelled")) cancelled++;
                        if (wareHouses.isEmpty()) {
                            wareHouses.add(0, new WareHouse(item.getWarehouseName(), 1));
                        }
                        else {
                            boolean coincidence = false;
                            for (WareHouse wh: wareHouses) {
                                if (!coincidence) {
                                    if (wh.getName().equals(item.getWarehouseName())) {
                                        wh.setQuantity(wh.getQuantity() + 1);
                                        coincidence = true;
                                    }
                                }
                            }
                            if (!coincidence) wareHouses.add(wareHouses.size(), new WareHouse(item.getWarehouseName(), 1));
                        }

                    }
                    else {
                        if (item.getSdate().equals(Data.getData(0))) {
                            if (item.getStatus().equals("sold")) sold++;
                        }
                    }
                }
//                if ((ordered != 0) || (sold != 0) || (cancelled != 0)) {
                if ((ordered != 0) || (cancelled != 0)) {
                    countForColor++;
                    itemsToShow.add(new ItemToShow(product.getSubject(), product.getSupplierArticle(), ordered, sold, cancelled, countForColor % 2, wareHouses));
                } else if (sold != 0) {
                    wareHouses.add(new WareHouse("Санкт-Петербург", 0));
                    itemsToShow.add(new ItemToShow(product.getSubject(), product.getSupplierArticle(), ordered, sold, cancelled, countForColor % 2, wareHouses));
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

        for(int i = 0; i < itemsToShow.size(); i++) {
            itemsToShow.get(i).setColor(i % 2);
        }

        ArrayList<ItemToShow> its = new ArrayList<>();

        for (int i = 0; i < itemsToShow.size(); i++) {
            if (its.isEmpty()) {
                ArrayList<WareHouse> wareHouses = new ArrayList<>();
                for (WareHouse wh: itemsToShow.get(i).getWareHouses()) wareHouses.add(new WareHouse(wh.getName(), wh.getQuantity()));
                its.add(new ItemToShow(itemsToShow.get(i).getSubject(), wareHouses));
            } else {
                boolean coincidence = false;
                for (int j = 0; j < its.size(); j++) {
                    if (its.get(j).getSubject().equals(itemsToShow.get(i).getSubject())) {
                        for (int a = 0; a < itemsToShow.get(i).getWareHouses().size(); a++) {
                            boolean coincedence2 = false;
                            for (int b = 0; b < its.get(j).getWareHousesAll().size(); b++) {
                                if (its.get(j).getWareHousesAll().get(b).getName().equals(itemsToShow.get(i).getWareHouses().get(a).getName())) {
                                    its.get(j).getWareHousesAll().get(b).setQuantity(its.get(j).getWareHousesAll().get(b).getQuantity() + itemsToShow.get(i).getWareHouses().get(a).getQuantity());
                                    coincedence2 = true;
                                }
                            }
                            if (!coincedence2) its.get(j).getWareHousesAll().add(new WareHouse(itemsToShow.get(i).getWareHouses().get(a).getName(), itemsToShow.get(i).getWareHouses().get(a).getQuantity()));
                        }
                        coincidence = true;
                    }
                }
                if (!coincidence) {
                    ArrayList<WareHouse> wareHouses = new ArrayList<>();
                    for (WareHouse wh: itemsToShow.get(i).getWareHouses()) wareHouses.add(new WareHouse(wh.getName(), wh.getQuantity()));
                    its.add(new ItemToShow(itemsToShow.get(i).getSubject(), wareHouses));
                    System.out.println(its.get(its.size() - 1).getSubject() + " " + its.get(its.size() - 1).getWareHousesAll().size());
                }
            }
        }

        for (ItemToShow i: itemsToShow) {
            for (ItemToShow t: its) {
                if (i.getSubject().equals(t.getSubject())) {
                    i.setWareHousesAll(t.getWareHousesAll());
                }
            }
        }

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
