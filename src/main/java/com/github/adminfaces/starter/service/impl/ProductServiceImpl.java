package com.github.adminfaces.starter.service.impl;


import static com.github.adminfaces.template.util.Assert.has;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.github.adminfaces.starter.dao.ProductDAO;
import com.github.adminfaces.starter.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.adminfaces.starter.infra.model.Filter;
import com.github.adminfaces.starter.infra.model.SortOrder;
import com.github.adminfaces.starter.model.Product;
import com.github.adminfaces.template.exception.BusinessException;

/**
 * 
 * @author charles
 *
 */
@Component
public class ProductServiceImpl implements ProductService, Serializable {
	
	@Autowired
    ProductDAO productRepository;
	
	
	
	
	public List<Product> findAll(){
		return productRepository.findAll();
	}

	public void insert(Product product) {
		productRepository.save(product);
	}
	
	public Product findById(Integer id) {
		return findAll().stream()
				.filter(p -> p.getId().equals(id))
				.findFirst()
				.orElseThrow(() -> new BusinessException("Product not found with id " + id));
	}

	public void update(Product product) {
		productRepository.save(product);
	}
	
    public List<String> getModels(String query) {
        return findAll().stream().filter(p -> p.getModel()
                .toLowerCase().contains(query.toLowerCase()))
                .map(Product::getModel)
                .collect(Collectors.toList());
    }
    
	public void remove(Product product) {
		productRepository.delete(product);		
	}
	
    public long count(Filter<Product> filter) {
        return findAll().stream()
                .filter(configFilter(filter).stream()
                        .reduce(Predicate::or).orElse(t -> true))
                .count();
    }
    
    public List<Product> paginate(Filter<Product> filter) {
        List<Product> pagedProducts = new ArrayList<>();
        if(has(filter.getSortOrder()) && !SortOrder.UNSORTED.equals(filter.getSortOrder())) {
                pagedProducts = findAll().stream().
                    sorted((c1, c2) -> {
                        if (filter.getSortOrder().isAscending()) {
                            return c1.getId().compareTo(c2.getId());
                        } else {
                            return c2.getId().compareTo(c1.getId());
                        }
                    })
                    .collect(Collectors.toList());
            }

        int page = filter.getFirst() + filter.getPageSize();
        if (filter.getParams().isEmpty()) {
            pagedProducts = pagedProducts.subList(filter.getFirst(), page > findAll().size() ? findAll().size() : page);
            return pagedProducts;
        }

        List<Predicate<Product>> predicates = configFilter(filter);

        List<Product> pagedList = findAll().stream().filter(predicates
                .stream().reduce(Predicate::or).orElse(t -> true))
                .collect(Collectors.toList());

        if (page < pagedList.size()) {
            pagedList = pagedList.subList(filter.getFirst(), page);
        }

        if (has(filter.getSortField())) {
            pagedList = pagedList.stream().
                    sorted((c1, c2) -> {
                        boolean asc = SortOrder.ASCENDING.equals(filter.getSortOrder());
                        if (asc) {
                            return c1.getId().compareTo(c2.getId());
                        } else {
                            return c2.getId().compareTo(c1.getId());
                        }
                    })
                    .collect(Collectors.toList());
        }
        return pagedList;
    }
    
    public List<Predicate<Product>> configFilter(Filter<Product> filter) {
        List<Predicate<Product>> predicates = new ArrayList<>();
        if (filter.hasParam("id")) {
            Predicate<Product> idPredicate = (Product p) -> p.getId().equals(filter.getParam("id"));
            predicates.add(idPredicate);
        }
        
        if (filter.hasParam("minPrice") && filter.hasParam("maxPrice")) {
            Predicate<Product> minMaxPricePredicate = (Product p) -> p.getPrice()
                    >= Double.valueOf((String) filter.getParam("minPrice")) && p.getPrice()
                    <= Double.valueOf((String) filter.getParam("maxPrice"));
            predicates.add(minMaxPricePredicate);
        } else if (filter.hasParam("minPrice")) {
            Predicate<Product> minPricePredicate = (Product p) -> p.getPrice()
                    >= Double.valueOf((String) filter.getParam("minPrice"));
            predicates.add(minPricePredicate);
        } else if (filter.hasParam("maxPrice")) {
            Predicate<Product> maxPricePredicate = (Product p) -> p.getPrice()
                    <= Double.valueOf((String) filter.getParam("maxPrice"));
            predicates.add(maxPricePredicate);
        }

        if (has(filter.getEntity())) {
            Product filterEntity = filter.getEntity();
            if (has(filterEntity.getModel())) {
                Predicate<Product> modelPredicate = (Product p) -> p.getModel().toLowerCase().contains(filterEntity.getModel().toLowerCase());
                predicates.add(modelPredicate);
            }

            if (has(filterEntity.getPrice())) {
                Predicate<Product> pricePredicate = (Product p) -> p.getPrice().equals(filterEntity.getPrice());
                predicates.add(pricePredicate);
            }

            if (has(filterEntity.getName())) {
                Predicate<Product> namePredicate = (Product p) -> p.getName().toLowerCase().contains(filterEntity.getName().toLowerCase());
                predicates.add(namePredicate);
            }
        }
        return predicates;
    }

}















