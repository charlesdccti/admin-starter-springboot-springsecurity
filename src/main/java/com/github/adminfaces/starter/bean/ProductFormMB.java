/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adminfaces.starter.bean;

import com.github.adminfaces.starter.model.Car;
import com.github.adminfaces.starter.model.Product;
import com.github.adminfaces.starter.service.CarService;
import com.github.adminfaces.starter.service.ProductService;

import javax.faces.view.ViewScoped;
import org.omnifaces.util.Faces;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

import static com.github.adminfaces.starter.util.Utils.addDetailMessage;
import static com.github.adminfaces.template.util.Assert.has;

/**
 * @author rmpestano
 */
@Named
@ViewScoped
public class ProductFormMB implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4217152429041828950L;
	private Integer id;
    private Product product;


    @Inject
    CarService carService;
    
    @Inject
    ProductService productService;

    public void init() {
        if(Faces.isAjaxRequest()){
           return;
        }
        if (has(id)) {
            product = productService.findById(id);
        } else {
            product = new Product();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}


    public void save() {
        String msg;
        if (product.getId() == null) {
            productService.insert(product);
            msg = "Product " + product.getModel() + " created successfully";
        } else {
            productService.update(product);
            msg = "Product " + product.getModel() + " updated successfully";
        }
        addDetailMessage(msg);
    }

    public void clear() {
    	product = new Product();
        id = null;
    }

    public boolean isNew() {
        return product == null || product.getId() == null;
    }



}
