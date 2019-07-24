package com.github.adminfaces.starter.bean;

import static com.github.adminfaces.starter.util.Utils.addDetailMessage;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Component;

import com.github.adminfaces.starter.infra.model.Filter;
import com.github.adminfaces.starter.model.Car;
import com.github.adminfaces.starter.model.Product;
import com.github.adminfaces.starter.service.ProductService;
import com.github.adminfaces.template.exception.BusinessException;

@Named
@Component
public class ProductListMB implements Serializable {


	@Inject
    ProductService productService;

    Integer id;
    LazyDataModel<Product> products;
    
    Filter<Product> filter = new Filter<>(new Product());
    List<Product> selectedProducts; //products selected in checkbox column
    List<Product> filteredValue;// datatable filteredValue attribute (column filters)
    
    
    
    @PostConstruct
    public void initDataModel() {
    	products = new LazyDataModel<Product>() {
    		 @Override
             public List<Product> load(int first, int pageSize,
                                   String sortField, SortOrder sortOrder,
                                   Map<String, Object> filters) {
								
                 com.github.adminfaces.starter.infra.model.SortOrder order = null;
                 if (sortOrder != null) {
                     order = sortOrder.equals(SortOrder.ASCENDING) ? com.github.adminfaces.starter.infra.model.SortOrder.ASCENDING
                             : sortOrder.equals(SortOrder.DESCENDING) ? com.github.adminfaces.starter.infra.model.SortOrder.DESCENDING
                             : com.github.adminfaces.starter.infra.model.SortOrder.UNSORTED;
                 }
                 
                 filter.setFirst(first).setPageSize(pageSize)
	                 .setSortField(sortField).setSortOrder(order)
	                 .setParams(filters);
		         List<Product> list = productService.paginate(filter);
		         setRowCount((int) productService.count(filter));
		         return list;
    		 }
    		 
             @Override
             public int getRowCount() {
                 return super.getRowCount();
             }

             @Override
             public Product getRowData(String key) {
                 return productService.findById(new Integer(key));
             }
             
			//             @Override
			//             public Object getRowKey(Product object) {
			//            	 return super.getRowKey(object);
			//             }
    	};
    }
    
    public void clear() {
        filter = new Filter<Product>(new Product());
    }

    public List<String> completeModel(String query) {
        List<String> result = productService.getModels(query);
        return result;
    }

    public void findProductById(Integer id) {
        if (id == null) {
            throw new BusinessException("Provide Product ID to load");
        }
        selectedProducts.add(productService.findById(id));
    }
    
    public void delete() {
        int numProducts = 0;
        for (Product selectedProduct : selectedProducts) {
            numProducts++;
            productService.remove(selectedProduct);
        }
        selectedProducts.clear();
        addDetailMessage(numProducts + " products deleted successfully!");
    }

    
	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LazyDataModel<Product> getProducts() {
		return products;
	}

	public void setProducts(LazyDataModel<Product> products) {
		this.products = products;
	}

	public Filter<Product> getFilter() {
		return filter;
	}

	public void setFilter(Filter<Product> filter) {
		this.filter = filter;
	}

	public List<Product> getSelectedProducts() {
		return selectedProducts;
	}

	public void setSelectedProducts(List<Product> selectedProducts) {
		this.selectedProducts = selectedProducts;
	}

	public List<Product> getFilteredValue() {
		return filteredValue;
	}

	public void setFilteredValue(List<Product> filteredValue) {
		this.filteredValue = filteredValue;
	}
    
    
    
}
