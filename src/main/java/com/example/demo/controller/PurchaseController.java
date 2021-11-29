package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Purchase;
import com.example.demo.entity.PurchaseItem;
import com.example.demo.entity.Supplier;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.PurchaseItemRepository;
import com.example.demo.repository.PurchaseRepository;
import com.example.demo.repository.SupplierRepository;
import com.example.demo.validator.InventoryValidator2;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {
	
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private PurchaseItemRepository purchaseItemRepository;
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private InventoryValidator2 inventoryValidator2;
	
	@RequestMapping("/")
	public String index(Model model, 
						@RequestParam(name = "supplier_id", required = false) Long supplier_id) {
		
		Purchase purchase = new Purchase();		
		
		if (supplier_id == null) {						
			model.addAttribute("purchases", purchaseRepository.findAll());
		} else {
			Supplier supplier = supplierRepository.findById(supplier_id).get();
			purchase.setSupplier(supplier);
			model.addAttribute("purchases", purchaseRepository.findBySupplier(supplier));
		}
		model.addAttribute("purchase", purchase);
		model.addAttribute("suppliers", supplierRepository.findAll());
		model.addAttribute("employees", employeeRepository.findAll());
		return "purchase";
	}
	
	@RequestMapping("/add")
	public String add(Purchase purchase) {
		purchaseRepository.save(purchase);
		return "redirect:/purchase/";
	}
	
	@RequestMapping("/edit/{pid}")
	public String edit(Model model, @PathVariable("pid") Long pid) {
		Purchase purchase = purchaseRepository.findById(pid).get();
		model.addAttribute("purchase", purchase);
		model.addAttribute("suppliers", supplierRepository.findAll());
		model.addAttribute("employees", employeeRepository.findAll());
		return "purchase-update";
	}
	
	@RequestMapping("/update/{pid}")
	public String update(Purchase purchase, @PathVariable("pid") Long pid) {
		purchase.setId(pid);
		purchaseRepository.save(purchase);
		return "redirect:/purchase/";
	}
	
	@RequestMapping("/delete/{pid}")
	public String delete(@PathVariable("pid") Long pid) {		
		purchaseRepository.deleteById(pid);
		return "redirect:/purchase/";
	}
	
	//---------------------------------------------------------------------------------
	
	@RequestMapping("/{pid}/view/item")
	public String viewItem(Model model, @PathVariable("pid") Long pid) {
		Purchase purchase = purchaseRepository.findById(pid).get();
		PurchaseItem purchaseItem = new PurchaseItem();
		model.addAttribute("purchase", purchase);
		model.addAttribute("purchaseItem", purchaseItem);
		model.addAttribute("products", productRepository.findAll());
		return "purchaseitem";
	}
	
	@RequestMapping("/{pid}/add/item")
	public String addItem(PurchaseItem purchaseItem, Model model, BindingResult result,@PathVariable("pid") Long pid) {
		inventoryValidator2.validate(purchaseItem, result);
		if (result.hasErrors()) {
			Purchase purchase = purchaseRepository.findById(pid).get();
			model.addAttribute("purchase", purchase);
			model.addAttribute("purchaseItem", purchaseItem);
			model.addAttribute("products", productRepository.findAll());
			return "purchaseitem";
		}
		Purchase purchase = purchaseRepository.findById(pid).get();
		purchaseItem.setPurchase(purchase);
		purchaseItemRepository.save(purchaseItem);
		return "redirect:/purchase/" + pid + "/view/item";
	}
	@RequestMapping("{pid}/edit/item/{iid}")
	public String editItem(Model model, @PathVariable("pid") Long pid, @PathVariable("iid") Long iid) {
		Purchase purchase = purchaseRepository.findById(pid).get();
		PurchaseItem purchaseItem = purchaseItemRepository.findById(iid).get();
		model.addAttribute("purchase", purchase);
		model.addAttribute("purchaseItem", purchaseItem);
		model.addAttribute("products", productRepository.findAll());
		return "purchaseitem";
	}
	
	@RequestMapping("{pid}/delete/item/{iid}")
	public String deleteItem(@PathVariable("pid") Long pid, @PathVariable("iid") Long iid) {
		purchaseItemRepository.deleteById(iid);
		return "redirect:/purchase/" + pid + "/view/item";
	}
}
