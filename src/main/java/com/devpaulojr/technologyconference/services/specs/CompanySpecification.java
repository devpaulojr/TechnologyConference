package com.devpaulojr.technologyconference.services.specs;

import com.devpaulojr.technologyconference.model.Company;
import org.springframework.data.jpa.domain.Specification;

public class CompanySpecification {

    public static Specification<Company> likeName(String name){
        return ((root, query, cb) ->
                cb.like( cb.upper(root.get("name")), "%" + name.toUpperCase() + "%" ));
    }

    public static Specification<Company> equalCnpj(String cnpj){
        return ((root, query, cb) ->
                cb.equal(root.get("cnpj"), cnpj));
    }

    public static Specification<Company> likeAddress(String address){
        return ((root, query, cb) ->
                cb.like( cb.upper(root.get("address")), "%" + address.toUpperCase() + "%" ));
    }

    public static Specification<Company> likeNeighborhood(String neighborhood){
        return ((root, query, cb) ->
                cb.like( cb.upper(root.get("neighborhood")), "%" + neighborhood.toUpperCase() + "%" ));
    }

    public static Specification<Company> likeCity(String city){
        return ((root, query, cb) ->
                cb.like( cb.upper(root.get("city")), "%" + city.toUpperCase() + "%" ));
    }

    public static Specification<Company> equalState(String state){
        return ((root, query, cb) ->
                cb.equal(root.get("state"), state));
    }
}
