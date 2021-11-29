package com.example.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.entity.PurchaseItem;

@Component
public class InventoryValidator2 implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return PurchaseItem.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PurchaseItem purchaseItem = (PurchaseItem)target;
		if (purchaseItem.getAmount() == null || purchaseItem.getAmount() ==0) {
			errors.rejectValue("amount", "purchaseItem.amount.required", "請輸入數量");
		} else if (purchaseItem.getAmount() > 5000) {
			errors.rejectValue("amount", "purchaseItem.amount.over", "數量過多,已超過購買權限");
		} 
	}
	

}
