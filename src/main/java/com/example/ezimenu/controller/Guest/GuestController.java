package com.example.ezimenu.controller.Guest;

import com.example.ezimenu.dto.*;
import com.example.ezimenu.entity.*;
import com.example.ezimenu.service.serviceimpl.*;
import com.example.ezimenu.service.transer.MapperService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping(value = "/guest")
public class GuestController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    EateryService eateryService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    DishService dishService;
    @Autowired
    TableDinnerService tableDinnerService;
    @Autowired
    MapperService mapperService;
    @Autowired
    NotifyService notifyService;

    @GetMapping(value = "/test-ntn")
    public ResponseEntity<?> testSGuest(){
        return ResponseEntity.status(HttpStatus.OK).body("Test Guest");
    }
    @GetMapping(value = "/menu/table-dinner/{tableDinnerId}")
    public ResponseEntity<?> getMenu(@PathVariable int tableDinnerId, @RequestParam(name = "search", defaultValue = "") String search,
                                     @RequestParam(name = "cateId", defaultValue = "0") Integer cateId) {
        Eatery eatery = eateryService.findByTableDinnerId(tableDinnerId);
        if(eatery == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have no access.");

        int id = eatery.getId();
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("user");
        HttpHeaders headers = new HttpHeaders();
//        headers.add("Cookie", "JSESSIONID=" + session.getId()); // Truyền JSESSIONID qua cookie
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String apiGetAllDishUrl = "http://localhost:8080/eatery/{id}/dishes";
        String apiGetAllCategoryUrl = "http://localhost:8080/eatery/{id}/categories";

        ResponseEntity<?> dishResponse = restTemplate.getForEntity(apiGetAllDishUrl,List.class,id);
        if (dishResponse.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving dishes");
        }
        List<DishDto> dishDtoList = (List<DishDto>) dishResponse.getBody();

        ResponseEntity<?> categoryResponse = restTemplate.getForEntity(apiGetAllCategoryUrl,List.class,id);
        if (categoryResponse.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving categories");
        }
        List<CategoryDto> categoryDtoList = (List<CategoryDto>) categoryResponse.getBody();

        Map<String, Object> menuData = new HashMap<>();
        menuData.put("dishes", dishDtoList);
        menuData.put("categories", categoryDtoList);

        return ResponseEntity.ok(menuData);
    }

    @GetMapping(value = "/orders/table-dinner/{tableDinnerId}")
    public ResponseEntity<?> getOrderByTableDinnerId(@PathVariable int tableDinnerId){
        TableDinner tableDinner = tableDinnerService.findById(tableDinnerId);
        if(tableDinner == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not found eatery.");

        List<Order> orderPendingList = orderService.findAllByTableDinnerIdAndStatus(tableDinnerId,-1);
        Order orderPending = new Order();
        if(orderPendingList.isEmpty()){
            orderPending.setTableDinner(tableDinner);
            orderService.saveOrder(orderPending);
        }
        else orderPending = orderPendingList.get(0);
        OrderDto orderPendingDto = orderPending.toDto();

        List<Order> orderSentList = orderService.findAllByTableDinnerIdAndStatus(tableDinnerId,0);
        List<OrderDto> orderSentDtoList = new ArrayList<>();
        for(Order order : orderSentList){
            orderSentDtoList.add(order.toDto());
        }

        List<Order> orderReceivedList = orderService.findAllByTableDinnerIdAndStatus(tableDinnerId,1);
        List<OrderDto> orderReceivedDtoList = new ArrayList<>();
        for(Order order : orderReceivedList){
            orderReceivedDtoList.add(order.toDto());
        }

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("orderPending", orderPendingDto);
        responseData.put("orderSentList",orderSentDtoList);
        responseData.put("orderReceivedList",orderReceivedDtoList);
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @PutMapping(value = "/order/{id}/update")
    public ResponseEntity<?> updateOrder(@PathVariable int id,@RequestParam(name = "description") String description){
        Order order = orderService.findById(id);
        if(order == null ){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found order.");
        }
        if(order.getStatus()!=-1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't update because order has sent.");
        }
        order.setDescription(description);
        orderService.saveOrder(order);
        return ResponseEntity.status(HttpStatus.OK).body(order.toDto());
    }

    @PostMapping(value = "/order-item/add")
    public ResponseEntity<?> addOrderItemById(@RequestBody OrderItemDto orderItemDto){
        int orderId = orderItemDto.getOrderId();
        int dishId = orderItemDto.getDishId();
        int quantity = orderItemDto.getQuantity();
        System.out.println("????");

        Order order = orderService.findById(orderId);
        if(order == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't add because order null.");
        }
        if(order.getStatus()!=-1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't add because order has sent.");
        }
        Dish dish = dishService.findById(dishId);
        if(dish==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't add because dish null.");
        }
        if(dish.isStatus()==false){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't add because dish is not served.");
        }
        if(quantity < 1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't add because quantity not positive.");
        }
        if(order.getTableDinner().getEatery().getId() != dish.getCategory().getEatery().getId()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't add because order and dish not same eatery.");
        }
        OrderItem existOrderItem = orderItemService.findByOrderIdAndDishId(orderId,dishId);
        System.out.println("hahaha");
        System.out.println("total first "+ order.getTotalPrice());
        System.out.println("add "+quantity* dishService.findById(dishId).getPrice());
        order.setTotalPrice(order.getTotalPrice() + quantity* dishService.findById(dishId).getPrice());
        System.out.println("total after"+ order.getTotalPrice());

        if(existOrderItem == null){
            OrderItem orderItem = mapperService.toOrderItem(orderItemDto);
            orderItemService.saveOrderItem(orderItem);
            orderItemDto.setId(orderItem.getId());
            return ResponseEntity.status(HttpStatus.OK).body(orderItemDto);
        }
        existOrderItem.setQuantity(quantity + existOrderItem.getQuantity());
        orderItemService.saveOrderItem(existOrderItem);
        return ResponseEntity.status(HttpStatus.OK).body(existOrderItem.toDto());
    }

    // guest chỉ có thể update quantity
    @PutMapping(value = "/order-item/{id}/update")
    public ResponseEntity<?> updateOrderItem(@PathVariable int id, @RequestBody OrderItemDto orderItemDto){
        OrderItem orderItem = orderItemService.findById(id);
        if(orderItem == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not found order item.");
        }
        if(orderItem.getOrder().getStatus()!=-1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't update because order has sent.");
        }
        int quantity = orderItemDto.getQuantity();
        if(quantity < 1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantity must greater 0.");
        }
        Order order = orderItem.getOrder();
        order.setTotalPrice(order.getTotalPrice() + (quantity - orderItem.getQuantity()) * orderItem.getDish().getPrice());
        orderItem.setQuantity(quantity);
        orderItemService.saveOrderItem(orderItem);
        return ResponseEntity.status(HttpStatus.OK).body(orderItem.toDto());
    }

    @DeleteMapping(value = "/order-item/{id}/delete")
    public ResponseEntity<?> deleteOrderItem(@PathVariable int id){
        OrderItem orderItem = orderItemService.findById(id);
        if(orderItem == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not found order item.");
        }
        if(orderItem.getOrder().getStatus()!=-1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't delete because order has sent.");
        }
        Order order = orderItem.getOrder();
        order.setTotalPrice(order.getTotalPrice() - orderItem.getQuantity() * orderItem.getDish().getPrice());
        boolean kt = orderItemService.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body("Delete order item successful.");
    }









}
