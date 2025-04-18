package com.devpaulojr.technologyconference.repositories.specs;

import com.devpaulojr.technologyconference.model.User;
import org.springframework.data.jpa.domain.Specification;


public class UserSpecification {

    public static Specification<User> likeFirstName(String firstName){
        return ((root, query, cb) ->
            cb.like( cb.upper(root.get("firstName")), "%" + firstName.toUpperCase() + "%" ));
    }

    public static Specification<User> likeLastName(String lastName){
        return ((root, query, cb) ->
            cb.like( cb.upper(root.get("lastName")), "%" + lastName.toUpperCase() + "%" ));
    }

    public static Specification<User> equalEmail(String email){
        return ((root, query, cb) ->
            cb.equal( root.get("email"), email ));

    }

    public static Specification<User> equalPhoneNumber(String phoneNumber){
        return ((root, query, cb) ->
                cb.equal( root.get("phoneNumber"), phoneNumber));
    }

    public static Specification<User> equalVip(Boolean vip){
        return ((root, query, cb) ->
                cb.equal( root.get("vip"), vip ));
    }

}
